#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() 
{
    printf("parent process executed\n");
    pid_t pid;
    pid = fork();

    if (pid > 0) {
        printf("parent pid: %ld, child pid: %d\n", (long)getpid(), pid);
        sleep(1);
        printf("parent process terminated\n");
        exit(0);
    }
    else if (pid == 0) {
        printf("child process executed\n");
        for (int i = 0; i < 10; i++) {
            printf("child pid: %ld, parent pid: %ld\n", (long)getpid(), (long)getppid());
            sleep(1);
        }
        printf("child process terminated\n");
        exit(0);
    }
    else {
        perror("fork failed\n");
        exit(1);
    }
}