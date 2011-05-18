#include <iostream.h>


int main()
{
	double attempts, completions, td, interceptions, yards;
   cout << "\nEnter the number of completions: ";
   cin >> completions;
   cout << "\nEnter the number of attempts: ";
   cin >> attempts;
   cout << "\nEnter the number of yards: ";
   cin >> yards;
   cout << "\nEnter the number of touchdowns: ";
   cin >> td;
   cout << "\nEnter the number of interceptions: ";
   cin >> interceptions;

   double poc = (((completions/attempts)-.3) * .05) * 100;
   double aygpa = (((yards / attempts) - 3) * .25);
   double potp = ((td / attempts) * .2 * 100);
   double poi = (2.375 - ((interceptions / attempts) * 100) * .25);

	if (poc < 0) poc = 0;
	if (poc > 2.375) poc = 2.375;

	if (aygpa < 0) aygpa = 0;
	if (aygpa > 2.375) aygpa = 2.375;

	if (potp < 0) potp = 0;
	if (potp > 2.375) potp = 2.375;

	if (poi < 0) poi = 0;
	if (poi > 2.375) poi = 2.375;

   double total = (((poc + aygpa + potp + poi) / 6) * 100);
   cout << endl << "Total QB rating = " << total;
}
