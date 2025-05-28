#include <stdio.h>
#include <ctype.h>

int main() {
    FILE *inputFile, *outputFile;
    char ch;
    int count = 0;

    inputFile = fopen("input.c", "r");
    outputFile = fopen("digit.txt", "w");

    if (inputFile == NULL || outputFile == NULL) {
        printf("File could not be opened!\n");
        return 1;
    }

    fprintf(outputFile, "Digits found in the file:\n");

    while ((ch = fgetc(inputFile)) != EOF) {
        if (isdigit(ch)) {
            if (count > 0) {
                fputc(',', outputFile);  // Add comma after each digit (except the first)
            }
            fputc(ch, outputFile);  // Write the digit
            count++;
        }
    }

    fprintf(outputFile, "\n\nTotal digits found: %d\n", count);

    printf("Formatted digit output written to 'digits.txt'.\n");

    fclose(inputFile);
    fclose(outputFile);

    return 0;
}
