/* 
	QBRating.java

	Author:			???
	Description:	
*/


import javabook.*;
import javabook.MainWindow.*;
public class QBRating 
{
	static public void main(String[] args){
		new QBRating();
	}
	
	public QBRating(){
		MainWindow mw = new MainWindow("Derek's QB Rating Program");
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
		
		ob.print("Total QB rating = " + total);
	}
}
