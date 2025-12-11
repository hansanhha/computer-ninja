#include <stdio.h>      // standard i/o
#include <string.h>     // string and memory handling (memset, memcpy)
#include <unistd.h>     // posix api (close, read/write)
#include <sys/types.h>  // posix standard type
#include <sys/socket.h> // socket api
#include <netinet/in.h> // IPv4, IPv6 address struct
#include <arpa/inet.h>  // IPv4, IPv6 address conversion and network byte order conversion
#include <stdlib.h>     // IPv4, IPv6 address handling

#define SERVER_IP   "127.0.0.1"
#define SERVER_PORT 12345
#define BUFFER_SIZE 1024

int main() {

    int sock_fd = -1;
    int rc = 1;
    struct sockaddr_in server_addr;
    char send_buf[BUFFER_SIZE];
    char recv_buf[BUFFER_SIZE];

    do {
        // 1. create socket
        sock_fd = socket(AF_INET, SOCK_STREAM, 0);
        if (sock_fd < 0) {
            perror("socket() failed");
            break;
        }

        // 2. configure server address
        memset(&server_addr, 0, sizeof(server_addr));
        server_addr.sin_family = AF_INET;
        server_addr.sin_port   = htons(SERVER_PORT);
        rc = inet_pton(AF_INET, SERVER_IP, &server_addr.sin_addr);
        if (rc <= 0) {
            perror("inet_pton() failed");
            break;
        }

        // 3. connect to server
        rc = connect(sock_fd, (struct sockaddr*)&server_addr, sizeof(server_addr));
        if (rc < 0) {
            perror("connect() failed");
            break;
        }

        printf("Connected to server.\n");
        
        // 4. send/receive messages
        while (1) {
            printf("Enter message: ");
            fflush(stdout); // force empty the output buffer

            // read a line from stdin and store it in send_buf
            // fgets adds '\n' (newline character) end of the string
            if (!fgets(send_buf, sizeof(send_buf), stdin)) {
                // EOF(Ctrl + D) or error
                break;
            }

            int n = send(sock_fd, send_buf, strlen(send_buf), 0);
            if (n < 0) {
                perror("send() failed");
                break;
            }

            int r = recv(sock_fd, recv_buf, sizeof(recv_buf), 0);
            if (r < 0) {
                printf("recv() failed");
                break;
            } else if (r == 0) {
                printf("Server closed connection.\n");
                break;
            }

            // recv() doesn't add '\n' end of the string
            // so we should add manually null terminator to read safely with string functions such as printf()
            recv_buf[r] = '\0';
            printf("Echoed: %s", recv_buf);
        }

    } while (0);
        
    if (sock_fd != -1) close(sock_fd);

    return 0;
}