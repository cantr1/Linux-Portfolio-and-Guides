#include <stdio.h>
#include <stdint.h>
int main()
{
    // Salary Calculator
    float x, y;
    printf("Input hourly rate: ");
    scanf("%f", &x);
    printf("Input hours worked each week: ");
    scanf("%f", &y);
    printf("Processing...\n");

    // Weekly
    printf("Weekly Salary: $%.2f\n", x * y);

    //Monthly
    printf("Sum of total values: $%.2f\n", 4 * (x * y));

    // Yearly
    printf("Yearly Salary: $%.2f\n", 52 * (x * y));

    return 0;
}
