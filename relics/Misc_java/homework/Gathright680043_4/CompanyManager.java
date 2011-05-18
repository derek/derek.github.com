/* 	Gathright680043_4.java
 *
 * 	Title  :	Project 4
 * 	Author :	Derek Gathright
 * 	KUID   : 	680043
 * 	Class  :	EECS 138   11:30 - 12:30		
 *
 * 	Project 4:
 *		A GUI applet reads in info from a file called input.dat, 
 *		processes the data, and then displys it in a simple GUI.
 */


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;


// The is the CompanyManager class, and it creates  
// and holds the sole Company object in the program. 

// It also creates the frame for the applet 
// components and calls the main program.

public class CompanyManager extends Applet 
{
	static public void main(String[] args) throws IOException
	{
		Company company = new Company();
		
		Frame frame = new Frame();
		frame.setTitle("Derek Gathright-----680043-----Project 3");
		frame.add(company);
		frame.setSize(300, 150);
		frame.setVisible(true);

		company.main();
	}
}

// This is the Company class, the heart of the program
public class Company extends CompanyManager implements ActionListener
{
	
	// Initializes and creates all of the primitive objects.
	public String 		stemp;
	public Double		dtemp;
	public int 			numberOfDivisions;
	public double[] 	price = new double[10];
	
	// Initializes all of the GUI components
	public Label 		divisionLA, totalLA, averageLA;
	public TextField 	divisionTF, totalTF, averageTF;
	public List			list;

	// Initializes the Division object array
	public Division[] 	division;
	
	// Simple main function that calls the two other functions
	// that run the program.
	public void main() throws IOException
	{
		GUI();
		readInfo();
	}
	
	// The readInfo function reads in the data, from the input.dat file,
	// sorts it, and distributes it to the Division objects
	
	public void readInfo() throws IOException
	{
		// Open the file
		File inFile 			= new File("input.dat");
		FileReader fileReader 	= new FileReader(inFile);
		BufferedReader br 		= new BufferedReader(fileReader);
	
		// Read how many Divisions to create and then creates the Division array
		numberOfDivisions = Integer.parseInt(br.readLine());
		division = new Division[numberOfDivisions];
					
		// This loop creates the individual Divisions, 
		// reads in the data, and sends it to the Division objects,
		// and the closes the file
		for (int i = 0; i < numberOfDivisions; i++)
		{
			division[i] = new Division();
			division[i].setName(br.readLine());
			list.add(division[i].getName());
			for (int j = 0; j < 10; j++)
			{
				price[j] = Double.parseDouble(br.readLine());
			}
			division[i].setPrices(price);
		}
		br.close();
	}
	
	// The GUI function creates the GUI part of the applet and it's components
	public void GUI()
	{
		setLayout(null);

		// Creates all of the components in the applet
		divisionLA 	= new Label("Division: ");
		totalLA 	= new Label("Total: ");
		averageLA 	= new Label("Average: ");
		divisionTF 	= new TextField();	
		totalTF 	= new TextField();	
		averageTF 	= new TextField();
		list		= new List(numberOfDivisions, false);

		// Adds all of the components in the applet
		add(divisionLA);
		add(totalLA);
		add(averageLA);
		add(divisionTF);
		add(totalTF);
		add(averageTF);
		add(list);
		list.addActionListener(this);

		// Places all of the components in the applet
		divisionLA.setBounds(	115, 10, 50, 25);
		totalLA.setBounds(		115, 40, 50, 25);
		averageLA.setBounds(	115, 70, 50, 25);
		divisionTF.setBounds(	175, 10, 100, 25);
		totalTF.setBounds(		175, 40, 100, 25);
		averageTF.setBounds(	175, 70, 100, 25);
		list.setBounds(			10, 10, 100, 100);
	}
	
	// The actionPerformed fuction is called when someone double clicks
	// on one of the Divisions and it displays the info.
	public void actionPerformed(ActionEvent event)
	{
		for (int i = 0; i < numberOfDivisions; i++)
		{
			if (list.getSelectedIndex() == i)
			{
				divisionTF.setText(division[i].getName());
				
				dtemp = new Double(division[i].getTotal());
				totalTF.setText("$" + dtemp.toString());

				dtemp = new Double(division[i].getAverage());
				averageTF.setText("$" + dtemp.toString());
			}
		}
	}
}

public class Division extends Company
{
	// Initializes and creates all of the primitive objects.
	public String name;
	public double[] prices = new double[10];
	public double total, average;
	
	// Function to set the Division name
	public void setName(String name)
	{
		this.name = name;
	}

	// Function to get the Division name	
	public String getName()
	{
		return name;
	}

	// Function to set the prices array and calculates the average and total
	public void setPrices(double price[])
	{
		for (int i = 0; i < 10; i++)
		{
			prices[i] = price[i];
			total = total + price[i];
		}
		
		total = Math.round(total*100);
		total = total/100;		

		average = Math.round(total*10);
		average = average/100;
	}

	// Function to get the average price
	public double getAverage()
	{
		return average;
	}

	// Function to get the total price	
	public double getTotal()
	{
		return total;
	}
}