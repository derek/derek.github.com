/* 
	Frame1.java

	Author:			???
	Description:	
*/

package ITJTDApplications;

import java.awt.*;
import java.awt.event.*;

public class Frame1 extends Frame 
{

// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
	// member declarations
// END GENERATED CODE

	public Frame1()
	{
	}

	public void initComponents() throws Exception
	{
// IMPORTANT: Source code between BEGIN/END comment pair will be regenerated
// every time the form is saved. All manual changes will be overwritten.
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		setSize(new java.awt.Dimension(350, 450));
		setLayout(null);
		setTitle("ITJTDApplications.Frame1");
		setLocation(new java.awt.Point(0, 0));


		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				thisWindowClosing(e);
			}
		});
// END GENERATED CODE
	}
  
  	private boolean mShown = false;
  	
	public void addNotify() 
	{
		super.addNotify();
		
		if (mShown)
			return;
			
		// move components to account for insets
		Insets insets = getInsets();
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			Point location = components[i].getLocation();
			location.move(location.x, location.y + insets.top);
			components[i].setLocation(location);
		}

		mShown = true;
	}

	// Close the window when the close box is clicked
	void thisWindowClosing(java.awt.event.WindowEvent e)
	{
		setVisible(false);
		dispose();
		System.exit(0);
	}
	
}
