/* 
 * 680043Gathright1.java
 *
 * Title  :	Project 1
 * Author :	Derek Gathright
 * KUID   : 680043
 * Class  :	EECS 138 
 * Time   : 11:30 - 12:30		
 */

package Gathright680043_1;
import javabook.*;

public class Gathright680043_1
{
	static public void main(String[] args)
	{
		MainWindow mainWindow = new MainWindow("Project1 by Derek Gathright 680043");
		mainWindow.setVisible(true);
		
		InputBox inputBox = new InputBox(mainWindow);
		OutputBox outputBox = new OutputBox(mainWindow);

		double cTemp, fTemp;
		fTemp = inputBox.getDouble("Enter Temperature in Fahrenheit       ");
		cTemp = (fTemp-32)*5/9;
	
		outputBox.setVisible(true);
		outputBox.printLine(fTemp + " degrees Fahrenheit is " + cTemp + " degrees Celsius");
		
		if (fTemp <= 32)
			outputBox.print("It is now freezing");
	}
}
