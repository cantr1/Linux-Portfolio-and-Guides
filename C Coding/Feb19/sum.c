#include <stdio.h>
#include <stdint.h>
int main()
{
    int x, y, z;
    printf("Input a starting value: ");
    scanf("%d", &x);
    printf("Input the difference between values: ");
    scanf("%d", &y);
    printf("Input the number of iterations: ");
    scanf("%d", &z);
    printf("Processing...\n");

    int output = x + (z-1)*y;
    printf("Output at given value: %d\n", output);

    // sum formula for arithmetic sequence
    int sum = ((x + output) * z) / 2;
    printf("Sum of total values: %d\n", sum);
    return 0;
}
