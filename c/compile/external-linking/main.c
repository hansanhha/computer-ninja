#include <stdio.h>

int global = 0;
void foo();

int main()
{
    foo();
    printf("global=%d\n", global);
    return 0;
}
