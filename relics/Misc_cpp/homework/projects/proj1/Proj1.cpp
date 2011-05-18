
/* Author    : Derek Gathright
 * Instructor: John Reinke
 * Class     : EECS 138, MWF 12:30
 * Filename  : Proj1.cpp
 *
 * Purpose:
 * This program takes input of an X/Y coordinate and tells the user which
 * quadrant the point is in for a user determined amout of times.
 */

#include <iostream.h>

int main()
{
   int test_count_end_number = 0; // The user determined amount of times to test
   int test_count = 0; // The actual number of coordinates tested
	int xcor = 0; // The input X coordinate
   int ycor = 0; // The input Y coordinate
	cout << "How many points would you like to test? ";
   cin  >>  test_count_end_number;
   endl;

	while ( test_count < test_count_end_number )
     {
     test_count++;
		cout << "For point #";
     cout << test_count;
     cout << ", enter X and Y coordinates separated by a space: ";
     cin  >> xcor >> ycor;

     if ( (xcor > 0) && (ycor > 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is in quadrant I"
      		<< endl << endl;

     if ( (xcor < 0) && (ycor > 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is in quadrant II"
      		<< endl << endl;

     if ( (xcor < 0) && (ycor < 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is in quadrant III"
      		<< endl << endl;

     if ( (xcor > 0) && (ycor < 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is in quadrant IV"
      		<< endl << endl;

     if ( (xcor != 0) && (ycor == 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is on the X-Axis"
      		<< endl << endl;

     if ( (xcor == 0) && (ycor != 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is on the Y-Axis"
      		<< endl << endl;

     if ( (xcor == 0) && (ycor == 0) )
     cout << "The point (" << xcor << ", " << ycor << ") is the orgin"
      		<< endl << endl;

     endl;
	}
	cout << "This is the end of the program, Thank You!" << endl;
   return 0;
	}
