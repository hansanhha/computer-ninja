#include <stdio.h>      // standard i/o
#include <string.h>     // string and memory handling (memset, memcpy)
#include <unistd.h>     // posix api (close, read/write)
#include <sys/types.h>  // posix standard type
#include <sys/socket.h> // socket api
#include <netinet/in.h> // IPv4, IPv6 address struct
#include <sys/poll.h>   // poll() function

#define SERVER_PORT 12345 // server's socket binding port
#define BUFFER_SIZE 1024  // maximum buffer size to receive with recv(), 1024bytes = 1KB

int main() {

    // listening server socket and accepted single client socket
    // initial value -1 means as not binded yet on socket
    int server_fd = -1, client_fd = -1;

    // rc: return code
    // on: SO_REUSEADDR opt value, setsockopt requires integer value as pointer
    int rc, on = 1;

    // IPv4 socket address struct
    // IPv6: sockaddr_in6
    struct sockaddr_in server_addr;

    // buffer for storing recv/send data
    char buffer[BUFFER_SIZE];

    // used to effiency communicate between server and client
    // it does not consume cpu while waiting for the client to send messages
    struct pollfd fds;

    // scope a block of code with do while(0) to execute it only once ("structured error handling" in c programming)
    // you can also immediately out of the statement instead of goto statement
    do {

        // 1. create socket
        // ipv4, tcp(based on stream), default protocol(tcp)
        server_fd = socket(AF_INET, SOCK_STREAM, 0);
        if (server_fd < 0) {
            perror("socket() failed");
            break;
        }

        // 2. configure socket option (allowing rebinding)
        // SO_REUSEADDR allows to reuse address 
        // basically when tcp server terminated, you can't use same port in TIME_WAIT
        // this option prevents failure of bind() "Address already in use" when server restarted
        rc = setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));
        if (rc < 0) {
            perror("setsockopt(SO_REUSEADDR) failed");
            break;
        }

        // 3. initialize ipv4 address structure
        // bind() may fail if garbage vlue is entered
        // so we should initialize server address struct
        memset(&server_addr, 0, sizeof(server_addr));
        // socket address's protocol family
        server_addr.sin_family = AF_INET;
        // htons: host to network short (32bit int -> network endian short converting)
        // tcp/ip uses network byte order (big endian)
        // and cpu such as x86/arm commonly uses little endian
        server_addr.sin_port = htons(SERVER_PORT);
        // htoln: host to network long (32bit int -> network endian converting)
        // INADDR_ANY: 0.0.0.0
        // accept connection from any ipv4 interface of the server system
        server_addr.sin_addr.s_addr = htonl(INADDR_ANY);

        // 4. assign ip:port address to the socket
        // this could cause "Address already in use" failure
        rc = bind(server_fd, (struct sockaddr*)&server_addr, sizeof(server_addr));
        if (rc < 0) {
            perror("bind() failed");
            break;
        }

        // 5. listen from the client connection request
        // this makes socket to passive socket state
        // second parameter sets number of connection waiting requests in concurrency (connection queue size)
        rc = listen(server_fd, 10);
        if (rc < 0) {
            perror("listen() failed");
            break;
        }

        printf("Server started on port %d\n", SERVER_PORT);

        // 6. accpet a client connection
        // when client call connect(), connection push in pending queue of kernel
        // accept() explictly accepts the queued connection and returns a file descriptor for the newly created socket for the client
        // server_fd continues to listen
        client_fd = accept(server_fd, NULL, NULL);
        if (client_fd < 0) {
            perror("accept() failed");
            break;
        }

        printf("Client connected.\n");

        // 7. prepare 'poll' structure
        // POLLIN event notifies the server process that a message has arrived
        memset(&fds, 0, sizeof(fds));
        fds.fd = client_fd;
        fds.events = POLLIN;

        // 8. wait for the client message and returns
        while (1) {
            // poll() blocks until the client sends a message and does not wast cpu
            // it returns value as '< 0: (error):', '0: timeout', '> 0: number of fd in which events occurred'
            // 2rd arg: number of fd to watch (client_fd)
            // 3th arg: timeout (0: non-blocking, -1: forever blocking)
            rc = poll(&fds, 1, -1);
            if (rc < 0) {
                perror("poll() failed");
                break;
            }

            // you can check event type through fds.revents
            if (fds.revents & POLLIN) {
                int n = recv(client_fd, buffer, sizeof(buffer), 0);
                if (n <= 0) {
                    printf("Client disconnected.\n");
                    break;
                }

                send(client_fd, buffer, n, 0);
            }
        }

    } while (0);

    // 9. close the sockets
    if (client_fd != -1) close(client_fd);
    if (server_fd != -1) close(server_fd);

    return 0;
}