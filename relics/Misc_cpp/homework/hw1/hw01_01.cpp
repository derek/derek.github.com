/* Author    : Derek Gathright
 * Instructor: John Reinke
 * Class     : EECS 138, MWF 12:30
 * Filename  : hw01_01.cpp
 *
 * Purpose:
 * This is a program that reads two integers and determines and prints if the
 * first is a mulitple of the second.
 */

#include <iostream.h>

int main()
{
   int num1, num2, remainder;
   cout << "Enter two integers: ";
   cin >> num1 >> num2;
	remainder = num1 % num2;

   if ((remainder == 0) && ( num1 != num2))
   {
			cout << "\n\nThe first integer is a multiple of the second\n";
   }

   if ((remainder > 0) && (num1 > num2))
   {
     		cout << "\n\nThe first integer is not a multiple of the second\n";
   }

   if (num1 == num2)
   {
   	cout << "\n\nThe 2 numbers are the same\n";
   }

   if (num1 < num2)
   {
   	cout << "\n\nThe first number is not a multiple of the second because" <<
      " the first number is smaller than the second\n";
   }

   return 0;
}
