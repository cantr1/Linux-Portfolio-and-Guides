#include <stdio.h>
#include <stdint.h>

int main()
{
    /* Speed Calculator
    float speed, distance;
    printf("Enter the distance: (miles) ");
    scanf("%f", &distance);
    printf("Enter the speed: (mph) ");
    scanf("%f", &speed);

    printf("The total time to travel in hours: %.2f\n", distance / speed);
    printf("Minutes %.2f \n", (distance / speed) * 60.0);
    */

    /* Time Calculator
    float seconds, minutes, hours;
    printf("Seconds: ");
    scanf("%f", &seconds);
    printf("Minutes = %f\n", seconds / 60.0);
    printf("Hours = %f\n", (seconds / 60.0) / 60.0);
    */

    // Remove the decimal
    float data;
    printf("Enter data: ");
    scanf("%f", &data);
    printf("The integer: %d\n", (int)data);
    printf("The decimal: %.1f", data - (int)data);


    return 0;
}
