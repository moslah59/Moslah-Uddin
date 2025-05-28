#include <stdio.h>

int main() {
    /*Declare two variables
    to store the numbers and the sum */
    int num1, num2, sum;

    // Ask the user to input the first number
    printf("Enter the first number: ");
    scanf("%d", &num1);  // Read the first number from the user

    // Ask the user to input the second number
    printf("Enter the second number: ");
    scanf("%d", &num2);  // Read the second number from the user

    // Calculate the sum of the two numbers
    sum = num1 + num2;

    // Display the result (the sum)
    printf("The sum of %d and %d is %d\n", num1, num2, sum);

    return 0;  // Return 0 to indicate that the program ran successfully
}
