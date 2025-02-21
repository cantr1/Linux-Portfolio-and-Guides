#include <stdio.h>
#include <stdlib.h>

int main()
{
    // Calculate the area of a rectangle
    printf("Rectangular Area Calculator\n");
    printf("*********\n*       *\n*********\n");

    // Get the measurements
    double height, width;
    double area;
    double perimeter;
    printf("Rectangle Width: ");
    scanf("%lf", &width);
    printf("Rectangle Height: ");
    scanf("%lf", &height);
    area = width * height;
    perimeter = (width * 2) + (height * 2);

    // Return the calculations
    printf("The area is %.2lf\n", area);
    printf("The perimeter is %.2lf\n", perimeter);
    return 0;
}
