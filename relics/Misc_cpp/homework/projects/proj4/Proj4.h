/*	Derek Gathright
	EECS 138
	April 19, 2000
	John Reinke
	12:30 - 1:30
	
	Project 4:
		This is a check balancing program that uses classes to 
		sort, organize, and output data from a given check book file.
*/

#ifndef proj4_h
#define proj4_h

	//includes
	#include <iostream.h>
	#include <fstream.h>
	#include <string>	
	#include <iomanip.h>
	#include "check.h"

	//Function Prototypes
	int  countLines(char checkBookFile[]);
	void readInFileInfo(char checkBookFile[], int numberOfLines, double &oldBalance, double &bankBalance, check checkArray[]);
    void error(int errorCode);
	void outputInfoFile(int numberOfTransactions, check checkArray[]);
	void outputInfoScreen(int numberOfTransactions, check checkArray[], double &oldBalance, double &bankBalance);
	void sortArray(check checkArray[], int numberOfTransactions);

#endif
