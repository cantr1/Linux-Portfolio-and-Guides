#include <stdio.h>
#include <stdlib.h>

int main()
{
    int temp;
    double converted;
    printf("Temperature in Celsius: ");
    scanf("%d", &temp);
    converted = (double)((temp*1.8)+32);
    printf("Temperature in Fahrenheit: %.2lf\n", converted);
}
