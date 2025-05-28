#include <stdio.h>
#include <ctype.h>
#include <string.h>

int main() {
    FILE *in = fopen("input.c", "r");
    FILE *out = fopen("output.txt", "w");

    char ch, word[100];
    int i = 0;

    while ((ch = fgetc(in)) != EOF) {
        if (isalpha(ch)) {
            word[i++] = ch;
            while ((ch = fgetc(in)) != EOF && (isalnum(ch) || ch == '_'))
                word[i++] = ch;
            word[i] = '\0';

            // শুধুমাত্র প্রথম ৫ অক্ষর লিখে দিচ্ছি
            for (int j = 0; j < 5 && word[j] != '\0'; j++) {
                fputc(word[j], out);
            }

            // শেষ যে অক্ষর (যেটা identifier শেষ করেছে) সেটা আবার লিখি
            if (ch != EOF)
                fputc(ch, out);

            i = 0; // আবার index reset
        } else {
            fputc(ch, out); // বাকিগুলো সরাসরি কপি
        }
    }

    fclose(in);
    fclose(out);
    printf("Done! Check output.c\n");
    return 0;
}

