#include <stdio.h>      // standard i/o
#include <stdlib.h>     // IPv4, IPv6 address handling
#include <string.h>     // string and memory handling (memset, memcpy)
#include <unistd.h>     // posix api (close, read/write)
#include <pthread.h>    // pthread api
#include <arpa/inet.h>  // IPv4, IPv6 address conversion and network byte order conversion
#include <sys/socket.h> // socket api
#include <netinet/in.h> // IPv4, IPv6 address struct
#include <stdatomic.h>  // mutex

#define SERVER_PORT 12345 // server's socket binding port
#define BUFFER_SIZE 1024  // maximum buffer size to receive with recv(), 1024bytes = 1KB

// global variable to track active threads on the server
atomic_int g_active_threads = 0;

// accpeted client handling thread function
// pthread function standard signature is
// void* (*start_routine)(void*);
// void* is generic pointer that stores any type pointer
void* handle_client(void *arg) {
    // release dynamic allocation
    int client_fd = *(int*) arg;
    free(arg); 

    char buf[BUFFER_SIZE];
    
    atomic_fetch_add(&g_active_threads, 1);
    printf("[Thread %lu] Client connected. active threads = %d\n\n", pthread_self(), g_active_threads);

    while (1) {
        int n = recv(client_fd, buf, sizeof(buf), 0);
        if (n < 0) {
            printf("[Thread %lu] recv() failed.\n", pthread_self());
            break;
        }
        if (n == 0) {
            break;
        }

        send(client_fd, buf, n, 0);
    }

    atomic_fetch_sub(&g_active_threads, 1);
    printf("[Thread %lu] Client disconnected. active threads = %d\n\n", pthread_self(), g_active_threads);
    close(client_fd);
    pthread_exit(pthread_self());
    return NULL;
}

int main() {
    // listening server socket
    // initial value -1 means as not binded yet on socket
    int server_fd = -1;

    // rc: return code
    // on: SO_REUSEADDR opt value, setsockopt requires integer value as pointer
    int rc, on = 1;
    struct sockaddr_in server_addr;

    // 1. create socket
    // ipv4, tcp(based on stream), default protocol(tcp)
    server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd < 0) {
        perror("socket() failed");
        exit(EXIT_FAILURE);
    }

    // 2. configure socket option (allowing rebinding)
    // SO_REUSEADDR allows to reuse address 
    // basically when tcp server terminated, you can't use same port in TIME_WAIT
    // this option prevents failure of bind() "Address already in use" when server restarted
    rc = setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(on));
    if (rc < 0) {
        perror("setsockopt(SO_REUSEADDR) failed");
        exit(EXIT_FAILURE);
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
        exit(EXIT_FAILURE);
    }

    // 5. listen from the client connection request
    // this makes socket to passive socket state
    // second parameter sets number of connection waiting requests in concurrency (connection queue size)
    rc = listen(server_fd, 10);
    if (rc < 0) {
        perror("listen() failed");
        exit(EXIT_FAILURE);
    }

    printf("Server started on port %d\n", SERVER_PORT);

    // 6. connect to the client and create newly a thread
    while (1) {
        int client_fd = accept(server_fd, NULL, NULL);
        if (client_fd < 0) {
            perror("accept() failed");
            continue;
        }

        // dynamic allocation to pass client_fd to newly thread
        // prevent race condition that override client_fd value in main loop
        int *pclient = malloc(sizeof(int));
        if (!pclient) {
            perror("malloc() failed");
            close(client_fd);
            continue;
        }
        *pclient = client_fd;

        pthread_t tid;
        if (pthread_create(&tid, NULL, handle_client, pclient) != 0) {
            perror("pthread_create() failed");
            close(client_fd);
            free(pclient);
            continue;
        }
    }

    close(server_fd);

    return 0;
}