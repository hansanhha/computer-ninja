#include <stdio.h>

// Passing as a pointer to the first element
void times2(int *a, int len)
{
    printf("------times2------\n");
    for (int i = 0; i < len; i++)
        printf("%d\n", a[i] * 2);
}

// Same thing, but using array notation
void times3(int a[], int len)
{
    printf("------times3-------\n");
    for (int i = 0; i < len; i++)
        printf("%d\n", a[i] * 3);
}

// Same thing, but using array notation with size
void times4(int a[5], int len)
{
    printf("------times4-----\n");
    for (int i = 0; i < len; i++)
        printf("%d\n", a[i] * 4);
}

int main(void)
{
    printf("-----original------\n");
    int x[5] = {11, 22, 33, 44, 55};

    for (int i = 0; i < 5; i++)
        printf("%d\n", x[i]);

    times2(x, 5);
    times3(x, 5);
    times4(x, 5);
}
