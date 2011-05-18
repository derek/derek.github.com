/*
 * Filename: hw4.cpp
 * Class: eecs138
 *
 * This file contains the main program for HomeWork 4, which tests the Friend
 * class. It should be used - UNALTERED - for the assignment.
 */

#include <iostream.h>
#include "friend.h"

int main()
{
	Friend firstFriend, secondFriend;
	
	cout << "\n\tFirst Friend - Initial Values\n";
	firstFriend.displayInfo();
	cout << "\n\tSecond Friend - Initial Values\n";
	secondFriend.displayInfo();

	firstFriend.setName("Bubba Jones");
	firstFriend.setAge(25);
	secondFriend.setName("Mickey Mouse");
	secondFriend.setAge(72);

	cout << "\n\tFirst Friend - Current Data\n";
	firstFriend.displayInfo();
	cout << "\n\tSecond Friend - Current Data\n";
	secondFriend.displayInfo();
	return 0;
}
