#include <stdio.h>
#include <stdint.h>

int main()
{
    // Create a program that returns the nth term
    int x;
    printf("Input a number: ");
    scanf("%d", &x);
    printf("Processing...\n");

    //Print the output of the sequence
    printf("Output: %d\n", 1+(x-1)*2);
    return 0;
}
