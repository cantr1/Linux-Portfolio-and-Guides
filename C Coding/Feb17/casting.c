#include <stdio.h>
#include <stdlib.h>

int main()
{
    // casting refers to altering data types in a process
    /* an operation between two integers will result
    in an integer, but if you add a float with an int, the result
    will be a float */

    int num1=5, num2=2;
    double result;
    result = num1/(double)num2; //assuming num2 != 0
    printf("result = %.3lf \n", result); //if we want our result to be a float
    return 0;                            //we use casting '()' - we declare type
}
