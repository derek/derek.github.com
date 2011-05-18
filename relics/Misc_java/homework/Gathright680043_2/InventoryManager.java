/* 	InventoryManager.java
 *
 * 	Title  :	Project 2
 * 	Author :	Derek Gathright
 * 	KUID   : 	680043
 * 	Class  :	EECS 138   11:30 - 12:30		
 *
 * 	Project 2:
 *		This program is used to keep track of a car inventory. The user enters in however 
 *	many cars he/she wants to keep track of then the program asks to enter in the 
 *	make, model, and the year of the car, then ouputs the data to the screen.
 */

package Project2;
import javabook.*;

public class InventoryManager
{
	static public void main(String[] args) 
	{
		// *****************
		// Object References
		// *****************
		MainWindow mw = new MainWindow("Derek Gathright    Project 2     KUID: 680043");
		InputBox   ib = new InputBox(mw);
		OutputBox  ob = new OutputBox(mw);
		mw.setVisible(true);
				
		// ****************************************
		// Getting size for, and creating the array
		// ****************************************
		int numberOfCars = ib.getInteger("How many cars are in the inventory? ");
		Car[] cars = new Car[numberOfCars];
		
		// *******************
		// Car info input loop
		// *******************
		for (int i = 0; i < numberOfCars; i++)
		{
			cars[i] = new Car();
			cars[i].setMake( ib.getString("Please enter the make of car #"  + (i+1) + "   "));
			cars[i].setModel(ib.getString("Please enter the model of car #" + (i+1) + "   "));
			cars[i].setYear( ib.getInteger("Please enter the year of car #" + (i+1) + "   "));
		}
		
		// ********************
		// Car info output loop
		// ********************		
		ob.setVisible(true);
		for (int i = 0; i < numberOfCars; i++)
			ob.printLine("Car #" + (i+1) + " is a " + cars[i].getYear() + " " + cars[i].getMake() + " " + cars[i].getModel());
	}
}

public class Car
{
	// *****************
	// Object References
	// *****************
	private String make;
	private String model;
	private int    year;

	// *******************
	// Class Set/Get Methods
	// *******************	
	public void setMake(String make)
	{
		this.make = make;
	}
	
	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public String getMake()
	{
		return make;
	}

	public String getModel()
	{
		return model;
	}

	public int getYear()
	{
		return(year);
	}
}