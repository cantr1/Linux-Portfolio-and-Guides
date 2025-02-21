#include <stdio.h>
#include <stdlib.h>

int main()
{
    int grade1, grade2, grade3;
    double avg_grade;
    printf("Enter Grade 1: ");
    scanf("%d", &grade1);
    printf("Enter Grade 2: ");
    scanf("%d", &grade2);
    printf("Enter Grade 3: ");
    scanf("%d", &grade3);
    avg_grade = (double)(grade1+grade2+grade3)/3.0;
    printf("The Average Grade = %lf\n", avg_grade);
    return 0;
}
