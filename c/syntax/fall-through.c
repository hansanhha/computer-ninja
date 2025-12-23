#include <stdio.h>

int main() {

    int x = 1;

    switch (x) {
        case 1:
            printf("1\n");
            // Fall through!
        case 2:
            printf("2\n");
            break;
        case 3:
            printf("3\n");
            break;
    }
}
