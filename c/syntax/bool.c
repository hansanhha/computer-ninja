#include <stdio.h>
#include <stdbool.h>  // C23에선 생략

int main() {
    // bool은 0이면 false, 0이 아니면 true로 판단한다
    // true는 1로 취급된다
    bool x = true; 

    if (x) {
        printf("x is true!\n");
    }
}
