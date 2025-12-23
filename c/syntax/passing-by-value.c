#include <stdio.h>

void plus_one(int);

void plus_one(int n) {
    n = n + 1;
    printf("value of the parameter n in the plus_one(n): %d\n", n);
}

int main() {

    int i = 10;

    printf("value of the i before plus_one(i): %d\n", i);

    plus_one(10);

    printf("value of the i after plus_one(i): %d\n", i);
}
