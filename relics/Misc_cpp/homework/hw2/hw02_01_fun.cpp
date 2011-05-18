#include <iostream.h>
#include <fstream.h>
#include <stdlib.h>

int main()
{



   ifstream hw_data;
   ofstream out_hw_data;

   out_hw_data.open("hw2.data");
   randomize();
   int rand_number;
   int counter = 1;
   while (counter < 15)
   {
       rand_number = (rand() % 101);
       counter++;
       cout << rand_number << endl;
       out_hw_data << rand_number << endl;
   }
   out_hw_data.close();


   hw_data.open("hw2.data");


   if (!hw_data)
   {
   	cerr << "Input file not opened";
      exit(1);
   }

   else
   {
      int high = 0, low = 0, score;
	   while (!hw_data.eof())
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

