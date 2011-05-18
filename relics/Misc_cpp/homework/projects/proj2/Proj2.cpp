/* Author    : Derek Gathright
   Instructor: John Reinke
   Class     : EECS 138, MWF 12:30
   Filename  : proj2.cpp

   Purpose:
   	A program that sells tickets for a movie theater and keeps track of the:
      	1. Number of tickets available for 4 different movie showings.
         2. Number of tickets sold.
         3. Profit from ticket sales.
      It includes 5 functions (excluding main) and error checks all input and
      output.
*/

#include<iostream.h>

//Clear Screen function
#include <conio.h>
void clrscr(void);

// Function prototypes
int  main_menu(double choice);
void purchase_ticket(int &tickets_left, double &profit, int &tickets_sold,
	int show_code);
int  determine_price(double price, int price_code);
void error(int error_code);
int  valid_number_checker(double input);
void display_remaining(int tickets_left, int loop_count);
void exit_program(int tickets_sold, double revenue, int final_loop_count,
	double total_revenue);

/* Main Function:
  		Main center of program, most variables are passed through here, and this
      directs to the various functions through the user through the program.
*/
int main()
{
   // Variables that keep track of revenue per movie
   double profit_large_early = 0;
	double profit_large_late  = 0;
	double profit_small_early = 0;
	double profit_small_late  = 0;

	// Variables that keep track of tickets sold per movie
   int tickets_sold_large_early = 0;
   int tickets_sold_large_late  = 0;
   int tickets_sold_small_early = 0;
   int tickets_sold_small_late  = 0;

	// Variables that keep track of tickets still avaiable per movie
   int tickets_left_large_early = 300;
   int tickets_left_large_late  = 300;
   int tickets_left_small_early = 200;
   int tickets_left_small_late  = 200;

   clrscr(); //Clear screen function
	bool exit_loop = false; /* exit_main_loop is the vaiable that
   									keeps the program in a loop. */

	while (exit_loop == false)
   {
      double main_menu_choice = main_menu(main_menu_choice);

      // These 6 if/else statements check to make sure the menu choice is valid
	   if (main_menu_choice == 1) //sends user to Purchase Ticket Menu Function
      {
         int show_code = 1; //show_code = what theater/time
   		purchase_ticket(tickets_left_large_early, profit_large_early,
         	tickets_sold_large_early, show_code);
      }

      else

      if (main_menu_choice == 2) //sends user to Purchase Ticket Menu Function
      {
         int show_code = 2; //show_code = what theater/time
			purchase_ticket(tickets_left_large_late, profit_large_late,
         	tickets_sold_large_late, show_code);
      }

      else
      if (main_menu_choice == 3) //sends user to Purchase Ticket Menu Function
		{
         int show_code = 3; //show_code = what theater/time
			purchase_ticket(tickets_left_small_early, profit_small_early,
         	tickets_sold_small_early, show_code);
      }

      else
      if (main_menu_choice == 4) //sends user to Purchase Ticket Menu Function
		{
      	int show_code = 4; //show_code = what theater/time
         purchase_ticket(tickets_left_small_late, profit_small_late,
       		tickets_sold_small_late, show_code);
      }

      else
	   if (main_menu_choice == 5) //To Display Remaining Tickets Function
         {
			display_remaining(tickets_left_large_early, 1);
         display_remaining(tickets_left_large_late, 2);
         display_remaining(tickets_left_small_early, 3);
         display_remaining(tickets_left_small_late, 4);
         }

      else
	   if (main_menu_choice == 6) // To Exit Program Function
   	{
         clrscr();

     		int total_revenue = (profit_large_early + profit_large_late +
         	profit_small_early + profit_small_late);

	      exit_program(tickets_sold_large_early, profit_large_early, 1,
         	total_revenue);
	      exit_program(tickets_sold_large_late, profit_large_late, 2,
         	total_revenue);
	      exit_program(tickets_sold_small_early, profit_small_early, 3,
         	total_revenue);
	      exit_program(tickets_sold_small_late, profit_small_late, 4,
         	total_revenue);

      	exit_loop = true;
      }

		else
         error(3); // "Invalid menu selection" error
   }
	return 0;
}

/* Main Menu function:
		The function that displays the main menu and takes input on which menu to
      goto.
*/
int main_menu(double choice)
{
	cout << "\t\tMain Menu\n";

	cout << "1. Purchase Tickets for Large Theater - Early Showing\n";
	cout << "2. Purchase Tickets for Large Theater - Late  Showing\n";
	cout << "3. Purchase Tickets for Small Theater - Early Showing\n";
	cout << "4. Purchase Tickets for Small Theater - Late  Showing\n";
	cout << "5. Show All Remaining Tickets\n";
	cout << "6. Quit Program\n";
	cout << "Enter you selection: ";
	cin  >> choice;
   cout << endl;

   choice = valid_number_checker(choice);

   return (choice);

}

//------------------------------------------------------------------------------
/* Purchase Ticket Menu Function:
		The menu where the user selects what type of ticket(s) they wants
      and validates their input.
*/
void purchase_ticket(int &tickets_left, double &price, int &tickets_sold,
	int show_code)
{
   clrscr();
   int show_sold_out;

   if (tickets_left == 0) // Checks to make sure the show isn't sold out
      {
   	error(5); // "Show sold out" error
      show_sold_out = 1;
      }

   if (show_sold_out != 1)
   {
	   double ticket_number;
	   double ticket_type;

		cout.setf(ios::fixed);
		cout.setf(ios::showpoint);
		cout.precision(2);

	  	cout << "\t\tPurchase Tickets Menu\n\n";
      if (show_code == 1)
      	cout << "Large Theater - Early Showing\n-";
      if (show_code == 2)
      	cout << "Large Theater - Late Showing\n";
      if (show_code == 3)
      	cout << "Small Theater - Early Showing\n-";
      if (show_code == 4)
      	cout << "Small Theater - Late Showing\n";

      cout << "----------------------------\n";
		cout << "1. Kids 12 years and under:            $3.00\n";
		cout << "2. Senior Citizens 65 years and older: $4.00\n";
		cout << "3. All others:                         $5.50\n";
	   cout << "Make selection: ";
	  	cin  >> ticket_type;
	  	cout << endl;

      ticket_type = valid_number_checker(ticket_type);

	   if ((ticket_type == 1) || (ticket_type == 2) || (ticket_type == 3))
	   {

   		cout << "Enter the number of tickets: ";
			cin  >> ticket_number;
			cout << endl;

	      ticket_number = valid_number_checker(ticket_number);

         if ((ticket_number > tickets_left) && (tickets_left != 0))
				error(4); //Too many tickets requested, "Not enough available" error

		  	if ((ticket_number <= tickets_left))
		   {
				if (ticket_number > 0)	/* Checks to make sure the number of tickets
            									requested is greater than zero */
		  		{
					tickets_left -= ticket_number; // Keeps track of tickets left
               tickets_sold += ticket_number; // Keeps track of tickets bought
		         price = (ticket_number * determine_price(price, ticket_type));
		        	cout << "Your total price is $" << price << endl << endl;
				}
		      else
		         error(2); // "Invalid number of tickets" error
  			}

   	}

	   else
		{
	   	error(1); // "Invalid ticket type" error
		}
   }
}
//------------------------------------------------------------------------------
/* Dertermine Price Function:
		This function determines the price of eeach ticket then returns that value

*/
int determine_price(double price, int price_code)
{
	if (price_code == 1)
  		 price = 3;
  	if (price_code == 2)
  		 price = 4;
	if (price_code == 3)
  	    price = 5.5;

	return price;
}
//------------------------------------------------------------------------------
/* Error Function:
		All errors in user input/output are directed here where they
      are told what they did wrong then directs them back to the
      main menu.
*/
void error(error)
{
   cout << "\n\t\t***ERROR***\n";

   if (error == 1)
		cout << "Invalid ticket type.\n";
   if (error == 2)
	   cout << "Invalid number of tickets.\n";
   if (error == 3)
	   cout << "Invalid menu selection.\n";
   if (error == 4)
	   cout << "Not enough tickets available for the number requested.\n";
   if (error == 5)
   	cout << "Show sold out, sorry.\n";

	cout << "Returning to Main Menu...\n\n";
}

//------------------------------------------------------------------------------
/* Valid Number Check Function:
			This function checks to see if the input is a decimal or any other
		non valid number, and if it is, it simply changes the input to zero thus
      creating an error in any input sitution. Basically it filters out any
      decimal inputs and can be used in any inut situtaion in the program.

      	Since there is only 300 possible inputs (integers 1 - 300) in this
      program, the loop scans for a match from all 300 possible inputs. If the
      input matches one of the 300 possibilities then it stops scanning and
      returns that number, and let's the parent funtion determine if it is a
      valid input or not. But if it compares the input to all 300 possibilities
      and doesn't find a match it gives the input the value of zero to be
      returned to the parent function.

      Without this for example if 4.2 or 4.975 was entered it would read it as 4
      and continue with the program.
*/
int valid_number_checker(double input)
{
	int counter = 1;
   bool exit_loop = false;
	while (counter < 301)
  	{
// remove the 7 "//*"'s to see how valid number checker works.
//*		cout << "Does " << input << " = " << counter << "?";
//*		int x = 3000000;
//*		while (x)
//*		x--;

  		if (counter == input)
		{
//*		cout << " YES" << endl;
	  		exit_loop = true;
      }

   	if (exit_loop == true)
     		counter = 301;

   	else
   	{
//*		cout << " NO" << endl;
     		counter++;
   	}
	}
   if (exit_loop == false)
      {
   		input = 0;
//*      cout << "No match found" << endl;
      }
	return (input);
}
//------------------------------------------------------------------------------
/* Display Remaining Tickets Function:
		The function that displays the number of tickets remaining for each movie
      showing.
*/
void display_remaining(int tickets_left, int loop_count)
{
   if (loop_count == 1)
   {
	   clrscr();
		cout << "\t\tShowing tickets remaining:" << endl;
   }

   if (loop_count < 3)
		cout << "Tickets remaining for Large theater";
   if (loop_count > 2)
   	cout << "Tickets remaining for Small theater";
   if ((loop_count == 1) || loop_count == 3)
   	cout << " - Early showing  - ";
   if ((loop_count == 2) || loop_count == 4)
      cout << " - Late showing   - ";

   cout << tickets_left << endl;
	if (loop_count == 4)
   	cout << endl;
}
//------------------------------------------------------------------------------
/* Exit Program Function:
		The final function the user passes through, it loops 4 times to display
      the data from each of the 4 movies, then on the last loop it displays the
      total profit from all the movies.
*/
void exit_program(int tickets_sold, double revenue, int final_loop_count,
	double total_revenue)
{
	cout.setf(ios::fixed);
	cout.setf(ios::showpoint);
	cout.precision(2);

   if (final_loop_count == 1)
	{
		cout << "Theater statistics:" << endl << endl;
		cout << "Large theater - Early showing";
   }

   if (final_loop_count == 2)
		cout << "Large theater - Late showing";

   if (final_loop_count == 3)
		cout << "Small theater - Early showing";

   if (final_loop_count == 4)
		cout << "Small theater - Late showing";

  	cout << "\n\tTickets sold - " << tickets_sold;
	cout << "\n\tRevenue      - $" << revenue << endl;

	if (final_loop_count == 4)
   {
   	cout << "		       -------" << endl;
		cout << "       Total Revenue = $" << total_revenue << endl << endl;
		cout << "Thank You, Good Bye." << endl;
   }
}

