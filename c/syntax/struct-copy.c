#include <stdio.h>

struct car {
    int speed;
};

int main(void) {

    struct car model_s, model_y;

    model_s.speed = 100;
    
    // shallow copy
    model_y = model_s;
    model_y.speed = 150;

    printf("model_s speed is %d\n", model_s.speed);
    printf("model_y speed is %d\n", model_y.speed);

    return 0;
}
