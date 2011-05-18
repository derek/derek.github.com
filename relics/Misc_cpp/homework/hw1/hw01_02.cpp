/* Author    : Derek Gathright
 * Instructor: John Reinke
 * Class     : EECS 138, MWF 12:30
 * Filename  : hw01_02.cpp
 *
 * Purpose:
 * This is a program that tests the output to for a homework assignment
 */

#include <iostream.h>

int main()
{
   int y, x = 1, total = 0;

   while (x <= 10)
   {
   	y = x*x;
      cout << y << endl;
      total += y;
      x++;
   }
   cout << "Total is " << total << endl;

   return 0;
}
