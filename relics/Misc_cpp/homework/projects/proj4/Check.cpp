#include "check.h"

check::check(int a, double b, char *c, char *d, int e)
{
	number  = a;
    amount  = b;
    strcpy(cashed, c);
    strcpy(tType, d);
    tNumber = e;
}

check::~check()
{
	;
}

int check::getCheckNumber()
{
	return number;
}

double check::getCheckAmount()
{
	return amount;
}

char *check::getCashed()
{
	return cashed;
}

int check::getTransactionNumber()
{
	return tNumber;
}

char *check::getTransactionType()
{
	return tType;
}

void check::setCheckNumber(const int numberOfChecks)
{
	number = numberOfChecks;
}

void check::setCheckAmount(const double checkAmount)
{
	amount = checkAmount;
}

void check::setCashed(const char *cashedOrNot)
{
	strcpy(cashed, cashedOrNot);
}

void check::setTransacionNumber(const int transactionNumber)
{
	tNumber = transactionNumber;
}

void check::setTransactionType(const char *transactionType)
{
	strcpy(tType, transactionType);
}

void check::outputInfoFile(ofstream &outfile)
{
	outfile.setf(ios::fixed);
	outfile.precision(2);
	outfile << tType << " " << number << " $" << amount << " " << cashed << endl;
}