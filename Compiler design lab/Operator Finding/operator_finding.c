#include <stdio.h>

int main() {
    FILE *in = fopen("input.c", "r");
    FILE *out = fopen("operators.txt", "w");

    if (!in || !out) {
        printf("File open korte parini.\n");
        return 1;
    }

    char ch;

    while ((ch = fgetc(in)) != EOF) {
        if (ch == '+' || ch == '-' || ch == '*' || ch == '/' ||
            ch == '%' || ch == '=' || ch == '<' || ch == '>' ||
            ch == '&' || ch == '|' || ch == '!' || ch == '^' ||
            ch == '~' || ch == '?' || ch == ':' || ch == '.') {

            fputc(ch, out);      // operator character ফাইলে লিখবে
            fputc('\n', out);    // newline দিবে যেন আলাদা করে পড়া যায়
        }
    }

    fclose(in);
    fclose(out);

    printf("Operators saved to operators.txt\n");

    return 0;
}

