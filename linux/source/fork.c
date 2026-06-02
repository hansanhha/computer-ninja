#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>

int main()
{
    pid_t pid;

    printf("Before the fork()\n");
    pid = fork();

    if (pid == 0)
        printf("This Is Child. And the value of PID = %d\n", pid);
    else if (pid > 0)
        printf("This is Parent. And the value of PID = %d\n", pid);
    else
        printf("Fork is Failed\n");

    return 0;
}
