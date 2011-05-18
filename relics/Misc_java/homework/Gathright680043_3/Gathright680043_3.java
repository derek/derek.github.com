/* 	Gathright680043_3.java
 *
 * 	Title  :	Project 3
 * 	Author :	Derek Gathright
 * 	KUID   : 	680043
 * 	Class  :	EECS 138   11:30 - 12:30		
 *
 * 	Project 3:
 *		A GUI applet that you can buy text books from and it tracks
 *		the cost when adding and removing books from your shopping cart.
 */

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.lang.*;

public class Gathright680043_3 extends Applet implements ActionListener
{
	//Initializes all of the GUI components
	private List bookList, buyList;
	private Button buyButton, deleteButton, checkoutButton;
	private TextField subtotalText, totalText;
	private Label subtotalLabel, totalLabel;
	
	//Creates the double and string variables/arrays needed in the program
	private double subtotal, total;	
	private double bookPrice[] = {50.99, 45.88, 25.00, 40.00, 75.00};
	private String books[] = {"Visual C++", "Java 2", 
							  "Fortran 90", "BASIC", 
							  "LISP"};
	
	public static void main(String[] args) 
	{
		//Frame is created
		Gathright680043_3 applet = new Gathright680043_3();
		Frame frame = new Frame();
		frame.setTitle("Derek Gathright-----680043-----Project 3");
		frame.add(applet);
		frame.setSize(440, 380);
		frame.setVisible(true);	
	}

	public Gathright680043_3() 
	{
		setLayout(null);
		
		//Create the Buttons, Labels, Text Fields, Lists
		buyButton 		= new Button("Buy");
		deleteButton 	= new Button("Delete");
		checkoutButton 	= new Button("Checkout");
		subtotalLabel	= new Label("Subtotal");	
		totalLabel		= new Label("Total");	
		subtotalText 	= new TextField();
		totalText 		= new TextField();
		bookList 		= new List(5, false);
		buyList 		= new List(5, false);
		
		//Adds the books to the book list
		for (int i = 0; i < books.length; i++)
		{
			bookList.add(books[i]);
		}

		//Sets the text Fields so the user can't change them
		subtotalText.setEditable(false);
		totalText.setEditable(false);
					
		//Add the action listeners to the three buttons
		buyButton.addActionListener(this);
		deleteButton.addActionListener(this);
		checkoutButton.addActionListener(this);
		
		//Add the components to the screen
		add(bookList);
		add(buyButton);
		add(deleteButton);
		add(checkoutButton);
		add(buyList);
		add(subtotalLabel);
		add(totalLabel);	
		add(subtotalText);
		add(totalText);
		
		//Manually place the components on the screen
		bookList.setBounds(40,30,100,150);
		buyList.setBounds(280,30,100,150);
		buyButton.setBounds(180,50,60,30);
		deleteButton.setBounds(180,85,60,30);
		checkoutButton.setBounds(200,260,60,30);
		subtotalLabel.setBounds(260,180,50,20);
		totalLabel.setBounds(260,200,50,20);
		subtotalText.setBounds(320,180,60,20);
		totalText.setBounds(320,200,60,20);
	}

	public void actionPerformed(ActionEvent event)
	{
		//Adds book to buy list and adds price to subtotal
		if (event.getSource() == buyButton)
		{
			buyList.add(bookList.getSelectedItem());
			subtotal = subtotal + bookPrice[bookList.getSelectedIndex()];
			subtotalText.setText("$" + subtotal);
		}

		//removes book from buy list and subtracts price to subtotal		
		else if (event.getSource() == deleteButton)
		{
			for (int i = 0; i < books.length; i++)
				if (buyList.getSelectedItem().equals(books[i]))
					subtotal = subtotal - bookPrice[i];

			buyList.remove(buyList.getSelectedItem());
			subtotalText.setText("$" + subtotal);
		}
		
		//calculates total
		else
		{
			total = subtotal * 1.069;
			totalText.setText("$" + total/2);
		}
	}
}