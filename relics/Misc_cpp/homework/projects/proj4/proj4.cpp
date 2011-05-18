#include "proj4.h"

int main()
{
   	
   	char checkBookFile[20];
   	int numberOfLines = 0;
   	double oldBalance = 0.00, bankBalance = 0.00;

 	cout << "Please enter the checkbook file to load: ";
	cin  >> checkBookFile;

	numberOfLines = countLines(checkBookFile);
	
	check *checkArray = NULL;
	if (numberOfLines > 1000)
	{
		cout << numberOfLines;
		error(4);
	}
	
	checkArray = new check [numberOfLines - 1];
	
	if (!checkArray)
       	error(3);
    
    readInFileInfo(checkBookFile, numberOfLines, oldBalance, bankBalance, checkArray);
	sortArray(checkArray, numberOfLines - 2);
	outputInfoScreen(numberOfLines - 2, checkArray, oldBalance, bankBalance);
	outputInfoFile(numberOfLines - 2, checkArray);
	
	delete [] checkArray;
	checkArray = NULL;

	exit (1);
}

//Counts the number of lines in the program
int countLines(char checkBookFile[])
{
	int	lines = 0;
    char temp;

    ifstream infile;
    infile.open(checkBookFile);

    if (!infile)
    {
    	error(1);
    }

    else
	{
	    while (infile.get(temp))
	  	{
		    if (temp == '\n')
	        {
         		lines++;
        	}
		}
	}
	infile.close();
	return lines;
}

//Reads in the information from the input file
void readInFileInfo(char checkBookFile[], int numberOfLines, double &oldBalance, double &bankBalance, check checkArray[])
{
    int checkNumber = 0;
    double checkAmount = 0.00;
    char checkType[8], cashedOrNot[8], temp;
	
    ifstream infile;
    infile.open(checkBookFile);
	if (!infile)
   	{
   		error(1);
	}

  	infile >> temp >> oldBalance >> temp >> bankBalance;

	for (int i = 1; i <= numberOfLines-2; i++)
	{
		infile >> checkType;
		infile >> checkNumber;
		infile >> temp;
		infile >> checkAmount;

		if (checkType[0] == 'c')
		{
			infile >> cashedOrNot;
			checkArray[i].setCashed(cashedOrNot);
		}
		checkArray[i].setTransacionNumber(i);
		checkArray[i].setTransactionType(checkType);
		checkArray[i].setCheckAmount(checkAmount);
		checkArray[i].setCheckNumber(checkNumber);
	}
	infile.close();
}


//Sorts the checkArray in terms of smallest check number to largest check number
void sortArray(check checkArray[], int numberOfTransactions)
{
	int i,j;
	check key;
	
	for(i = 0; i < numberOfTransactions; i++)
	{
		key = checkArray[i];
		for(j = i; j > 0 && checkArray[j - 1].getCheckNumber()> key.getCheckNumber(); j--)
		{
			checkArray[j] = checkArray[j-1];
		}
	checkArray[j] = key;
   	}
}	

//Outputs the information to the screen
void outputInfoScreen(int numberOfTransactions, check checkArray[], double &oldBalance, double &bankBalance)
{

	double totalAmountCashed = 0.00;
	double totalAmountDeposited = 0.00;
    double newBalance = 0.00;
	
	for (int i = 1; i <= numberOfTransactions; i++)
	{
		char *str1 = checkArray[i].getTransactionType();
		char *str2 = checkArray[i].getCashed();
		if ((str1[0] == 'c') && (str2[0] == 'c'))
			totalAmountCashed += checkArray[i].getCheckAmount();
		
		if (str1[0] == 'd')
			totalAmountDeposited += checkArray[i].getCheckAmount();		
	}
	
	newBalance = (oldBalance + totalAmountDeposited - totalAmountCashed);

	cout.setf(ios::fixed)|(ios::right)|(ios::showpoint)|cout.precision(2);
	cout << "\n\nChecks cashed: 	$	" << setw(7) << totalAmountCashed <<  endl;
	cout << "Deposits made: 	$	" << setw(7) << totalAmountDeposited << endl;
	cout << "Bank Claims:   	$	" << setw(7) << bankBalance << endl;
	cout << "New Balance:   	$	" << setw(7) << newBalance << endl;
	cout << "===========================" << endl;
	
	cout.setf(ios::showpos);
	cout << "Difference:    	$	" << setw(7) << (newBalance - bankBalance) << endl;
	cout.unsetf(ios::showpos);
}

//outputs the file to the output file (checkinfo.txt)
void outputInfoFile(int numberOfTransactions, check checkArray[])
{
    ofstream outfile;
    outfile.open("checkinfo.txt");
    if (!outfile)
    	error(2);
    outfile << "Checks cashed:" << endl;
	outfile << "==========================" << endl;
	for (int k = 1; k <= numberOfTransactions; k++)
	{
		char *str1 = checkArray[k].getCashed();
		if (str1[0] == 'c')
			checkArray[k].outputInfoFile(outfile);
	}

    outfile << endl;
	outfile << "Checks Not Cashed:" << endl;
	outfile << "==========================" << endl;
	for( int k = 1; k <= numberOfTransactions; k++)
	{
		char *str2 = checkArray[k].getCashed();
		if (str2[0] == 'u')
			checkArray[k].outputInfoFile(outfile);
	}
	outfile.close();
}

//outputs any errors that occur in the program
void error(int errorCode)
{
	switch(errorCode)
	{
		case 1:
   			cerr << "Unable to open input file.";
   			break;
   			
		case 2:
   			cerr << "Unable to open output file.";
   			break;
   			
		case 3:
	   		cerr << "Error allocating memory.";
   			break;
   			   	
	
		case 4:
			cout << " is an invalid size for the array.";		
   			break;
   	}	
	cout << "\nQuitting...";
	exit (1);
}