#include <iostream.h>
#include <string>
#include <fstream.h>

int main()
{
   char 	 c;
   char 	 line[41];
   char 	 checkNumberString[3];
   char 	 oldBalanceString[41];
   char 	 newBalanceString[41];
   int 	 numberOfChecks = 0;
   int 	 numberOfDeposits = 0;
   int 	 counter = 0;
   int 	 checkNumber = 0;
   int 	 checkAmountCharLength = 0;
   double checkAmount = 0.00;
   double oldBalance = 0.00;
   double newBalance = 0.00;

   numberOfLines = 10;
   checkBookFile = "mybankbook.txt";
   check *checkArray = NULL;
   double *checkAmountString;

	checkArray = new check [numberOfLines - 2];

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

   oldBalance = int(oldBalanceString);
   newBalance = int(newBalanceString);

   for (int i = 3, counter = 0; i < numberOfLines; i++)
   {
      infile.get(c);
      while (c != '\n')
      {
         infile.get(c);
         counter++;
         line[counter] = c;
      }

      for (int j = 0; j < strlen(line); j++)
      {
         if (strstr(line, "cashed"))
         {
         	checkArray[i - 2].setCashed(true);
         	checkType = 0;
         }
         else if (strstr(line, "uncashed"))
         {
         	checkArray[i - 2].setCashed(false);
         	checkType = 0;
         }
         else
         {
         	checkType = 1;
         }

      	if (checkType == 0)
         {

            //determineCheckNumber();
           	checkNumberString[0] = line[6];
            checkNumberString[1] = line[7];
            checkNumberString[2] = line[8];
            checkNumber = int(checkNumberString);
            checkArray[i - 2].setCheckNumber(checkNumber);


            //determineCheckAmount();
            checkAmountCharLength = ((strchr(line, '.') + 2) - (strchr(line, '$') +1))
          	for (int k = strchr(line, '$') + 1 , int l = 0 ; k <= strchr(line, '.') + 2; k++)
            {
               checkAmountString = new double [checkAmountCharLength];
            	checkAmountString[l] = line[k];
					checkAmount = int(checkAmountString);
               checkArray[i - 2].setCheckAmount(checkAmount);

         }
      }
	}
}