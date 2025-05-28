#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main() {
    FILE *in = fopen("input.c", "r");
    if (in == NULL) {
        printf("Failed to open input file.\n");
        return 1;
    }

    FILE *out = fopen("keywords.txt", "w");  // আউটপুট ফাইল
    if (out == NULL) {
        printf("Failed to open output file.\n");
        fclose(in);
        return 1;
    }

    const char *keywords[] = {
        "auto","break","case","char","const","continue","default","do",
        "double","else","enum","extern","float","for","goto","if",
        "int","long","register","return","short","signed","sizeof","static",
        "struct","switch","typedef","union","unsigned","void","volatile","while"
    };
    int keyword_count = 32;

    char ch, word[100];
    int i = 0;

    while ((ch = fgetc(in)) != EOF) {
        if (isalpha(ch) || ch == '_') {
            word[i++] = ch;
            while ((ch = fgetc(in)) != EOF && (isalnum(ch) || ch == '_')) {
                word[i++] = ch;
            }
            word[i] = '\0';
            i = 0;

            int found = 0;
            for (int k = 0; k < keyword_count; k++) {
                if (strcmp(word, keywords[k]) == 0) {
                    found = 1;
                    break;
                }
            }

            if (found == 1) {
                fprintf(out, "Keyword found: %s\n", word);
            }

            if (ch == EOF) break;
        }
    }

    fclose(in);
    fclose(out);
    return 0;
}
