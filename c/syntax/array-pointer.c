#include <stdio.h>

int main() {

    int a[5] = {1, 2, 3, 4, 5};
    int *p;

    p = a;

    printf("address of the a: %p\n", a);
    printf("address of the p: %p\n", p);

    printf("value of the *a: %d\n", *a);
    printf("value of the a[0]: %d\n", a[0]);
    printf("value of the *p: %d\n", *p);
}
