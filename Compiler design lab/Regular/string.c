#include <stdio.h>
#include <string.h>

int main() {
    char str[100];
    printf("Enter a string (only a and b): ");
    scanf("%s", str);

    int i = 0;
    int len = strlen(str);

    // Step 1: (a/b)* → go through all a/b until first required b
    while (i < len && (str[i] == 'a' || str[i] == 'b')) {
        if (str[i] == 'b') break; // Stop at 1st required 'b'
        i++;
    }

    if (i >= len || str[i] != 'b') {
        printf("❌ The string does NOT match the pattern.\n");
        return 0;
    }
    i++; // skip the 1st 'b'

    // Step 2: (a/b)* → again skip a/b until second required 'b'
    while (i < len && (str[i] == 'a' || str[i] == 'b')) {
        if (str[i] == 'b') break; // Stop at 2nd required 'b'
        i++;
    }

    if (i >= len || str[i] != 'b') {
        printf("❌ The string does NOT match the pattern.\n");
        return 0;
    }
    i++; // skip the 2nd 'b'

    // Step 3: only one more 'a' or 'b' should be left
    if (i < len && (str[i] == 'a' || str[i] == 'b')) {
        i++;
    } else {
        printf("❌ The string does NOT match the pattern.\n");
        return 0;
    }

    // Step 4: nothing should remain
    if (i == len) {
        printf("✅ The string matches the pattern.\n");
    } else {
        printf("❌ The string does NOT match the pattern.\n");
    }

    return 0;
}
