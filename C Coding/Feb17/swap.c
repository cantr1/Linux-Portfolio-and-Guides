#include <stdio.h>
#include <stdlib.h>

int main()
{
    float num1=5.78, num2=10.2;
    float temp;
    printf("Num 1 = %f\n", num1);
    printf("Num 2 = %f\n", num2);
    temp=num1;
    num1=num2;
    num2=temp;
    printf("Num 1 = %f\n", num1);
    printf("Num 2 = %f\n", num2);
}
