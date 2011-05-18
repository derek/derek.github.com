#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>
#include <stdio.h>

int main()
{
   int score;
	ifstream hw_data;
   hw_data.open("hw2.data");

   if (!hw_data)
   {
   	cerr << "Input file not opened";
      exit(1);
   }

   else
   {
      int high, low;
	   while (! hw_data.eof( ))
	   {
   		hw_data >> score;
      	if (score > high)
         	high = score;

			if (score < low)
         	low = score;
      }
   	cout << "High = " << high << endl;
      cout << "Low  = " << low << endl;
      hw_data.close();
   }
	return 0;
}

