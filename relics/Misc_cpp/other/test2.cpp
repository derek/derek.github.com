#include <iostream.h>
#include <fstream.h>
#include <string>

int main()
{
   char c;
   int counter = 0;
   char line[41];
   int numberOfLines = 10;
   char oldBalanceString[41];
   char newBalanceString[41];
   char checkBookFile[] = "mybankbook.txt";
   int oldBalance = 0.00;
   int newBalance = 0.00;
   int numberOfChecks = 0;
   int numberOfDeposits = 0;

   for (int i = 0;i < 41; i++)
   {
   	line[i] = ' ';
   }

   ifstream infile;
   infile.open(checkBookFile);
   if (!infile)
   	exit(1);

   infile.ignore( '\n', '$');
   infile.getline(oldBalanceString, 40);
   infile.getline(newBalanceString, 40, '$');
	infile.getline(newBalanceString, 40, '\n');

   for (int i = 3, counter = 0; i < numberOfLines; i++)
   {
      infile.get(c);
      while (c != '\n')
      {
         infile.get(c);
         counter++;
         line[counter] = c;
      }

      for (int j = 0; j < strlen(line)
	}

   cout << line;
   //cout << line;
	/*char c;
	while (infile.get(c))
   {
      cout << c;
   	if ((c == 'ch'))// && (c + 1 == 'h'))// && (c + 2 == 'e') && (c + 3 == 'c') && (c + 4 == 'k'))
      {
         cout << "\naaaaaa\n";
      	numberOfChecks++;
      }

      if ((c == 'd') && (c + 1 == 'e') && (c + 2 == 'p') && (c + 3 == 'o')
      && (c + 4 == 's') && (c + 5 == 'i') && (c + 6 == 't'))
      {
      	numberOfDeposits++;
      }
   }*/




   //cout << "Number of Checks   = " << numberOfChecks << endl;
   //cout << "Number of Deposits = " << numberOfDeposits;




   /*   char str[40];
   infile.getline(str, 40, '\n');
   char str2[40];
   infile.getline(str2, 40, '\n');
   cout << "Old Balance = " << oldBalanceString << endl;
   cout << "new Balance = " << newBalanceString << endl;
   cout << "str = " << str << endl;
   cout << "str2 = " << str2 << endl;*/
}