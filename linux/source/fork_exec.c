#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main()
{
    pid_t pid;
    pid = fork();

    switch (pid)
    {
        case -1:
            printf("fork failed");
            break;
        case 0:
            printf("executing ls -l\n");
            execl("/bin/ls", "ls", "-l", (char*) 0);
            printf("exec failed");
            break;
        default:
            printf("parent process waiting until child process terminated\n");

            // waiting until child process terminated
            wait((int*) 0);
            printf("ls completed\n");
            exit(0);
    }
}
