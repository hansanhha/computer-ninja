#include <stdio.h>
#include <unistd.h>

int main() {
    printf("PID = %d\n", getpid());
    printf("SID = %d\n", getsid(0));
}
