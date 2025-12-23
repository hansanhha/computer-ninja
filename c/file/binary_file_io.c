#include <stdio.h>

typedef struct {
    int id;
    double value;
    char name[20];
} Record;

int main(void) {

    Record rec = {100, 3.14, "Test"};
    Record read_rec;

    FILE *fp = fopen("data.bin", "wb");
    if (fp) {
        fwrite(&rec, sizeof(Record), 1, fp);
        fclose(fp);
    }

    fp = fopen("data.bin", "rb");
    if (fp) {
        size_t read_count = fread(&read_rec, sizeof(Record), 1, fp);
        if (read_count == 1) {
            printf("ID: %d, Value: %.2f, Name: %s\n", read_rec.id, read_rec.value, read_rec.name);
        }
        fclose(fp);
    }
}
