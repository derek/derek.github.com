// Comments

#include <iostream.h>
// prototypes
void printRev(char name[], char *str, int endOfName);
void recursivePrintRev(char *str, int i);
void recursivePrint(char name[], int i);

int main()
{
	char name[60];
	char *str = NULL;
	int endOfName = 0;

   for (int i = 0; i < 60 ; i++)
   {
		if (name[i] == '\0')
      	endOfName = i;
   }
   for (int i = 0; i < 60; i++)
   {
   	name[i] = ' ';
   }

	cout << "Enter your full name: ";
	cin.getline(name, 60);
	cout << "Your name is: " << name << endl;
	cout << "Backwards (pointers): ";
	printRev(name, str, endOfName);
   int i;
	cout << "\nBackwards (recursively): ";
	recursivePrintRev(name, i = 59);

   cout << "\nForwards  (recursively): ";
   recursivePrint(name, i = 0);
	return 0;
}

void printRev(char name[], char *str,  int endOfName)
{
   for (int i = endOfName; i >= 0; i--)
   	cout << str[i-1];
}

void recursivePrintRev(char name[], int i)
{
	if (i == 0)
   {
   	cout << name[i];
      return;
   }
   else
   {
   	recursivePrintRev(name, i - 1);
      cout << name[i];
      return;
   }
}

void recursivePrint(char name[], int i)
{
	if (i == 59)
   {
   	cout << name[i];
      return;
   }
   else
   {
   	recursivePrintRev(name, i + 1);
      cout << name[i];
      return;
   }
}


