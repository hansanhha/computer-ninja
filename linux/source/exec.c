#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>

int main()
{
    printf("executing ls -l\n");

    execl("/bin/ls", "ls", "-l", (char*) 0);
    perror("execl failed to run ls");
    exit(1);
}
