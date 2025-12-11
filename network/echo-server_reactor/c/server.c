/* 
    This source code use event loop, but not dispatch to other handler
    Instead of just echo receviced message from the client
*/

#include <stdio.h>      // standard i/o
#include <stdlib.h>     // IPv4, IPv6 address handling
#include <string.h>     // string and memory handling (memset, memcpy)
#include <unistd.h>     // posix api (close, read/write)
#include <arpa/inet.h>  // IPv4, IPv6 address conversion and network byte order conversion
#include <sys/socket.h> // socket api
#include <sys/event.h>  // kqueue
#include <netinet/in.h> // IPv4, IPv6 address struct
#include <stdatomic.h>  // mutex
#include <fcntl.h>      // file descriptor control
#include <errno.h>      // error

#define MAX_EVENTS  1000
#define SERVER_PORT 12345
#define BUF_SIZE    1024

// set the socket to non-blocking mode
void set_nonblocking(int fd) {
    // fcntl: file descriptor contorl
    // F_GETFL: get file status flags
    int flags = fcntl(fd, F_GETFL, 0); 
    if (flags == -1) {
        perror("fcntl(F_GETFL) failed");
        exit(EXIT_FAILURE);
    }

    // F_SETFL: sets file status flags
    // add O_NONBLOCK flag(non-blocking mode) through OR bit operation in the original flags
    if (fcntl(fd, F_SETFL, flags | O_NONBLOCK) == -1) {
        perror("fcntl(F_SETFL) failed");
        exit(EXIT_FAILURE);   
    }
}

// change the event type/flags for the monitored socket (server or client)
void kevent_change(int kq, int fd, int filter, int flags) {

    // changes structure that used to kqueue system communicate with the kernel
    struct kevent changes[1];

    // set up the changes structure according to the parameters
    // fd(ident): target fd
    // filter: watching event type (read, write, etc)
    // flags: action to request from kernel (add, enable)
    EV_SET(&changes[0], fd, filter, flags, 0, 0, NULL);
    if (kevent(kq, changes, 1, NULL, 0, NULL) == -1) {
        perror("kevent_change() failed");
        exit(EXIT_FAILURE);
    }

    /*
        filter: 'watching event types'
    
        EVFILT_READ (Event Filter Read): Readable Event
        - occured on the server socket: requested newly connection -> can call accept()
        - occured on the client socket: recevied message from the client -> can call read()

        EVFILT_WRITE (Event Filter Write): Writable Event
        - can call write() when you have free space of output buffer of the socket 

        flags: 'action to request from kernel'
        (you can combine multiple flag with the OR operator '|')
        
        EV_ADD: add event to watchlist (used to register newly client socket)
        EV_DELETE: remove event from watchlist (when disconneted to the client)
        EV_ENABLE: start/resume watch (using with EV_ADD or resume disabled event)  
        EV_DISABLE: suspend event (when suspend the EVFLT_WRITE)
        EV_ONESHOT: watch once and remove automatically
    */
}

int main() {
    
    int server_fd, client_fd, kq = -1;
    int rc, on = 1;
    struct sockaddr_in server_addr, client_addr;
    socklen_t client_addr_len;
    char buffer[BUF_SIZE];

    struct kevent event_list[MAX_EVENTS];

    // 1. create socket
    server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd < 0) {
        perror("socket() failed");
        exit(EXIT_FAILURE);
    }

    // 2. configure socket option
    rc = setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));
    if (rc < 0) {
        perror("setsockopt(SO_REUSEADDR) failed");
        exit(EXIT_FAILURE);
    }

    // 3. initialize ipv4 address structure
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family      = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    server_addr.sin_port        = htons(SERVER_PORT);

    // 4. assign ip:port address to the socket
    rc = bind(server_fd, (struct sockaddr*)&server_addr, sizeof(server_addr));
    if (rc < 0) {
        perror("bind() failed");
        exit(EXIT_FAILURE);
    }

    // 5. set server socket(reactor) to non-blocking mode
    set_nonblocking(server_fd);

    // 6. listen from the client connection request
    rc = listen(server_fd, MAX_EVENTS);
    if (rc < 0) {
        perror("listen() failed");
        exit(EXIT_FAILURE);
    }

    // 7. create kqueue instance
    // invoke the kqueue system call, kernel create i/o event queue to watch and manage in the own (same the 'epoll_create' of epoll)
    // returned 'kq' file descriptor is the kqueue itself that we register event or get result from the kernel
    kq = kqueue();
    if (kq < 0) {
        perror("kqueue() failed");
        close(server_fd);
        exit(EXIT_FAILURE);
    }

    // 8. register server socket to the kqueue (wathcing EVFILT_READ event)
    // EVFILT_READ(Event Filter Read): watching event type, server socket can call accept() when occured read event
    // EV_ADD | EV_EANBLE: correspoding event add to kqueue and enable it
    kevent_change(kq, server_fd, EVFILT_READ, EV_ADD | EV_ENABLE);

    printf("Server started on port %d using kqueue\n", SERVER_PORT);

    // 9. start event loop, waiting 'kevent'
    while (1) {
        // blocking for event occuring (kevent)
        // 1st arg: kqueue instance
        // 2nd, 3rd arg: new change list made with EV_SET (NULL and 0 if there're no new changes)
        // 4rd arg: an array wehere the kernel will store events
        // 5th arg: maximum number of event
        // 6th arg: timeout
        // nevents: occured number of events
        int nevents = kevent(kq, NULL, 0, event_list, MAX_EVENTS, NULL);
        if (nevents < 0) {
            perror("kevent() failed");
            break;
        }

        // iterate event loop and handle it
        for (int i = 0; i < nevents; i++) {
            int fd = event_list[i].ident; // ident is fd

            // CASE A: occured event on the server socket(fd == server_fd) -> requested newly connection
            // accept connection and set newly created client fd to non-blocking
            // 
            if (fd == server_fd) {
                client_addr_len = sizeof(client_addr);
                client_fd = accept(server_fd, (struct sockaddr*)&client_addr, &client_addr_len);

                if (client_fd < 0) {
                    // if the queue is empty when accept() is called
                    // EAGAIN(Try Again), EWOULDBLOCK(Would Block)
                    // indicates that 'the task connot be completed immediately,
                    // but this is a temporary condition and you may succed if you try agian later'
                    if (errno == EAGAIN || errno == EWOULDBLOCK) {
                        continue;
                    }
                    perror("accept() failed");
                    continue;
                }

                set_nonblocking(client_fd);
                kevent_change(kq, client_fd, EVFILT_READ, EV_ADD | EV_ENABLE);

                printf("New client connected: FD %d\n", client_fd);
            }

            // CASE B: occured read event on the client socket (read or EOF)
            else if (event_list[i].filter == EVFILT_READ) {
                // disconnected
                if (event_list[i].flags & EV_EOF) {
                    printf("Client disconnected: FD %d\n", fd);
                    kevent_change(kq, fd, EVFILT_READ, EV_DELETE);
                    close(fd);
                    continue;
                }

                // read data from the client
                ssize_t n = read(fd, buffer, BUF_SIZE);
                if (n < 0) {
                    if (errno != EAGAIN) {
                        perror("read() failed");
                        kevent_change(kq, fd, EVFILT_READ, EV_DELETE);
                        close(fd);
                    }
                } else {
                    // echo reading data
                    write(fd, buffer, n);
                }
            }
        }
    }

    close(server_fd);
    close(kq);
    return 0;
}
