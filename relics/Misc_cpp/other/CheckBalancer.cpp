#include <iostream>
#include <fstream.h>
#include <time.h>
#include <iomanip.h>
#include <stdio.h> 


double readOldBalance();
void adjustBalance(double oldBalance);
void error(int code);
void closing();

void main()
{
	double balance;
	
	balance = readOldBalance();
	adjustBalance(balance);
}

double readOldBalance()
{
	double balance = 0;
	ifstream in_file;
    in_file.open("balance.txt");

	if (!in_file)
		error(1);
	else{
	   in_file >> balance;
	   in_file.close();}

	return balance;
}

void adjustBalance(double oldBalance)
{
	double amount, balance;
	char reas[20];

	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(2);

	cout << "Old balance is " << oldBalance << endl;	
	cout << "Amount to subtract: ";
	cin  >> amount;
	if (amount != 0)
	{
		cout << "Reason: ";
		cin  >> reas;
	}
		
	if( amount == 0){
		return;}
	
	balance = oldBalance - amount;

	cout << "Your new balance is " << balance << endl;
	if (balance <= 0)
		cout << "\n***OVERDRAWN***\n***OVERDRAWN***\n***OVERDRAWN***\n***OVERDRAWN***";
	
	ofstream txt;
	ofstream log;
    txt.open("balance.txt");
    log.open("balance.log.txt", std::ios_base::app);
   	
   	if ((!txt) || (!log))
		error(2);
	else
	{
		// Clock crap
		time_t rawtime;
		struct tm * timeinfo;
 		time ( &rawtime );
		timeinfo = localtime ( &rawtime );
		// end clock crap

		txt << balance;
		txt.close();
		
		log << "------------------------\n";
		log << "$" << amount << endl;
		log << "To: " << reas << endl;
		log << "Old = " << oldBalance << endl;
		log << "New = " << balance << endl;
		log << asctime (timeinfo) << "\n\n";

		log.close();
	}
}

void error( int code)
{
	if (code == 1){
		cout << "Unable to open file for input";
		return;}
	if (code == 2){
		cout << "Unable to open file for output";
		return;}
}