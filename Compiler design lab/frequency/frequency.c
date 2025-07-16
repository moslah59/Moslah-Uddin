#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define MAX_WORDS 100
#define WORD_LEN 50

int main() {
    char sentence[1000];
    char words[MAX_WORDS][WORD_LEN];
    int freq[MAX_WORDS] = {0};
    int wordCount = 0;

    printf("Enter a sentence:\n");
    fgets(sentence, sizeof(sentence), stdin);

    // Tokenize sentence using space
    char *token = strtok(sentence, " ,.-\n");

    while(token != NULL) {
        // Convert to lowercase for case-insensitive comparison
        for(int i = 0; token[i]; i++) {
            token[i] = tolower(token[i]);
        }

        int found = 0;

        // Check if word already exists
        for(int i = 0; i < wordCount; i++) {
            if(strcmp(words[i], token) == 0) {
                freq[i]++;
                found = 1;
                break;
            }
        }

        // If new word, add to list
        if(!found) {
            strcpy(words[wordCount], token);
            freq[wordCount] = 1;
            wordCount++;
        }

        token = strtok(NULL, " ,.-\n");
    }

    // Print frequencies
    printf("\nWord Frequencies:\n");
    for(int i = 0; i < wordCount; i++) {
        printf("%s: %d\n", words[i], freq[i]);
    }

    return 0;
}
