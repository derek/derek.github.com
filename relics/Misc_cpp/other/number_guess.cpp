#include <iostream.h>
#include <stdlib.h>

int main()
{
	randomize();
   int random_number = (rand() % 101);
   int guess = 0;

   while (guess != random_number)
   {
      cout << "Guess a number between 1 and 100: ";
      cin >> guess;
		cout << endl;
		if (guess > random_number)
      	cout << "Guess too high" << endl;
      if (guess < random_number)
      	cout << "Guess too low" << endl;
	}

   cout << "right";
return 0;
}

