#include <stdio.h>
#include <string.h>

int main() {
    char str[100], rev[100];

    printf("Enter a string: ");
    scanf("%s", str);

    // Copy original string to another string
    strcpy(rev, str);

    // Reverse the copied string
    strrev(rev);

    // Compare original with reversed
    if (strcmp(str, rev) == 0) {
        printf("The string is a palindrome.\n");
    } else {
        printf("The string is not a palindrome.\n");
    }

    return 0;
}

