#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define SERVER_IP   "127.0.0.1"
#define SERVER_PORT 12345
#define BUFFER_SIZE 1024

int main() {

    int sock_fd = -1;
    int rc = 1;
    struct sockaddr_in server_addr;
    char send_buf[BUFFER_SIZE];
    char recv_buf[BUFFER_SIZE];

    // 1. create socket
    sock_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (sock_fd < 0) {
        perror("socket() failed");
        exit(1);
    }

    // 2. configure server address
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_port   = htons(SERVER_PORT);
    rc = inet_pton(AF_INET, SERVER_IP, &server_addr.sin_addr);
    if (rc <= 0) {
        perror("inet_pton() failed");
        exit(1);
    }

    // 3. connect to server
    rc = connect(sock_fd, (struct sockaddr*)&server_addr, sizeof(server_addr));
    if (rc < 0) {
        perror("connect() failed");
        exit(1);
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

            if (strncmp(send_buf, "quit", 4) == 0) {
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
        
    close(sock_fd);
    printf("Client connection closed.\n");
    return 0;
}