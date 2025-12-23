#include <stdio.h>

struct car {
    char *name;
    float price;
    int speed;
};

void set_price(struct car *, float);

int main(void) {
    struct car model_y = {.speed=175, .name="Saturn SL/2"};

    set_price(&model_y, 799.99);
    
    printf("Price: %f\n", model_y.price);
    return 0;
}

void set_price(struct car *c, float price) {
    c->price = price;
}
