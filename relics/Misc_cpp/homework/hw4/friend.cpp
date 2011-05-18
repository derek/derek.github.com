#include <iostream.h>
#include <string.h>
#include "friend.h"

Friend::Friend()
{
	strcpy(name, "none");
   age = 0;
}

void Friend::setName(char *str)
{
	strcpy(name, str);
}

void Friend::setAge(int amount)
{
	age = amount;
}

char *Friend::getName()
{
	return name;
}

int Friend::getAge()
{
	return age;
}

void Friend::displayInfo()
{
	cout << "\nName: " << name << endl;
	cout << "Age: " << age << endl;
}
