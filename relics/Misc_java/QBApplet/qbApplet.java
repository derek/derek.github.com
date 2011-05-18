/* 
	qbApplet.java

	Author:			???
	Description:	
*/


import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javabook.*;

public class qbApplet extends Applet 
{

// BEGIN GENERATED CODE
	// member declarations
	javax.swing.JLabel clabel = new javax.swing.JLabel();
	javax.swing.JLabel jLabel1 = new javax.swing.JLabel();
	javax.swing.JLabel ylabel = new javax.swing.JLabel();
	javax.swing.JLabel jLabel3 = new javax.swing.JLabel();
	javax.swing.JLabel ilabel = new javax.swing.JLabel();
	javax.swing.JTextField jTextField1 = new javax.swing.JTextField();
	javax.swing.JTextField jTextField2 = new javax.swing.JTextField();
	javax.swing.JTextField jTextField3 = new javax.swing.JTextField();
	javax.swing.JTextField jTextField4 = new javax.swing.JTextField();
	javax.swing.JTextField jTextField5 = new javax.swing.JTextField();
// END GENERATED CODE

	boolean isStandalone = false;

	public qbApplet() 
	{
	}

	// Retrieve the value of an applet parameter
	public String getParameter(String key, String def) 
	{
		return isStandalone ? System.getProperty(key, def) :
			(getParameter(key) != null ? getParameter(key) : def);
	}

	// Get info on the applet parameters
	public String[][] getParameterInfo() 
	{
		return null;
	}

	// Get applet information
	public String getAppletInfo() 
	{
		return "Applet Information";
	}

	// Initialize the applet
	public void init() 
	{
		try {
			initComponents();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//qbApplet();
	}
	
	public void start(){
	
	


	public void initComponents() throws Exception
	{
// BEGIN GENERATED CODE
		// the following code sets the frame's initial state
		clabel.setSize(new java.awt.Dimension(150, 20));
		clabel.setVisible(true);
		clabel.setText("Number of completions");
		clabel.setLocation(new java.awt.Point(60, 100));

		jLabel1.setSize(new java.awt.Dimension(150, 20));
		jLabel1.setVisible(true);
		jLabel1.setText("Number of attempts");
		jLabel1.setLocation(new java.awt.Point(60, 130));

		ylabel.setSize(new java.awt.Dimension(150, 20));
		ylabel.setVisible(true);
		ylabel.setText("Number of yards");
		ylabel.setLocation(new java.awt.Point(60, 160));

		jLabel3.setSize(new java.awt.Dimension(150, 20));
		jLabel3.setVisible(true);
		jLabel3.setText("Number of touchdowns");
		jLabel3.setLocation(new java.awt.Point(60, 190));

		ilabel.setSize(new java.awt.Dimension(150, 20));
		ilabel.setVisible(true);
		ilabel.setText("Number of interceptions");
		ilabel.setLocation(new java.awt.Point(60, 220));

		jTextField1.setSize(new java.awt.Dimension(90, 20));
		jTextField1.setVisible(true);
		jTextField1.setLocation(new java.awt.Point(220, 100));

		jTextField2.setSize(new java.awt.Dimension(90, 20));
		jTextField2.setVisible(true);
		jTextField2.setLocation(new java.awt.Point(220, 130));

		jTextField3.setSize(new java.awt.Dimension(90, 20));
		jTextField3.setVisible(true);
		jTextField3.setLocation(new java.awt.Point(220, 160));

		jTextField4.setSize(new java.awt.Dimension(90, 20));
		jTextField4.setVisible(true);
		jTextField4.setLocation(new java.awt.Point(220, 190));

		jTextField5.setSize(new java.awt.Dimension(90, 20));
		jTextField5.setVisible(true);
		jTextField5.setLocation(new java.awt.Point(220, 220));

		setSize(new java.awt.Dimension(525, 451));
		setLayout(null);
		setLocation(new java.awt.Point(0, 0));
		add(clabel);
		add(jLabel1);
		add(ylabel);
		add(jLabel3);
		add(ilabel);
		add(jTextField1);
		add(jTextField2);
		add(jTextField3);
		add(jTextField4);
		add(jTextField5);


		jTextField1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jTextField1ActionPerformed(e);
			}
		});
// END GENERATED CODE
	}}
	
	public void qbApplet()
	{
	/*MainWindow mw = new MainWindow("Derek's QB Rating Program");
		MultiInputBox ib = new MultiInputBox(mw,5);
		OutputBox ob = new OutputBox(mw);
		
		
		mw.setVisible(true);
		ob.setVisible(true);
		
		String[] s = new String[5];
		
		s[0] = "# of Completions";
		s[1] = "# of Attempts";
		s[2] = "# of Yards";
		s[3] = "# of Touchdowns";
		s[4] = "# of Interceptions";
				
		ib.setLabels(s);
		
		String[] in = new String[5];
		in = ib.getInputs();
		
		double attempts = Convert.toDouble(in[0]);
		double completions = Convert.toDouble(in[0]);
		double yards = Convert.toDouble(in[0]);
		double touchdowns = Convert.toDouble(in[0]);
		double interceptions = Convert.toDouble(in[0]);
		
		double poc = (((completions/attempts)-.3) * .05) * 100;
		double aygpa = (((yards / attempts) - 3) * .25);
		double potp = ((touchdowns / attempts) * .2 * 100);
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
		
		ob.print("Total QB rating = " + total);*/
	
	}
	
	public void textArea1TextValueChanged(java.awt.event.TextEvent e)
	{
	}
	
	public void jTextField1ActionPerformed(java.awt.event.ActionEvent e)
	{
	}
	
	
	

}
