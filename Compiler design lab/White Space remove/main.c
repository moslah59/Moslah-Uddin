
#include <stdio.h>
#include <ctype.h>

int main() {
    FILE *inputFile, *outputFile;
    char ch;

    // Input and output file paths
    inputFile = fopen("input.c", "r");     // Input C code file
    outputFile = fopen("output.txt", "w"); // Output without whitespace

    if (inputFile == NULL || outputFile == NULL) {
        printf("File could not be opened!\n");
        return 1;
    }

    while ((ch = fgetc(inputFile)) != EOF) {
        // Write only non-whitespace characters
        if (!isspace(ch)) {
            fputc(ch, outputFile);
        }
    }

    printf("Whitespace removed. Output written to 'output.txt'.\n");

    fclose(inputFile);
    fclose(outputFile);

    return 0;
}
