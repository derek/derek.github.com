#ifndef CHECK_H
#define CHECK_H

#include <string.h>
#include <iostream.h>
#include <fstream.h>

class check
{
	private:
   		int 	number;
      	double 	amount;
      	char 	cashed[9];
      	int 	tNumber;
      	char 	tType[8];

   public:
	  check(int a = 0, double b = 0.00, char * = "", char * = "", int e = 0);
      ~check();
      int 		getCheckNumber();
      double 	getCheckAmount();
      char 		*getCashed();
      int 		getTransactionNumber();
      char 		*getTransactionType();
      
      void 		setCheckNumber(const int numberOfChecks);
      void 		setCheckAmount(const double checkAmount);
      void 		setCashed(const char *cashedOrNot);
      void 		setTransacionNumber(const int num);
      void 		setTransactionType(const char *transactionType);
      void 		outputInfoFile(ofstream &outfile);
};
#endif