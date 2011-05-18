import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.lang.*;

public class WCQ extends Applet implements ActionListener
{
	boolean isStandalone = true;

	//*** Initialize Components
	public Label[][] 	levels 		= new Label[3][7];
	public Team[] 		team 		= new Team[6];
	public Button[]		plus 		= new Button[12];
	public Button[]		minus 		= new Button[12];
	public Button 		run1, run2, run3;
	public Label[][] 	standings 	= new Label[9][7];	
	public Label[][]	results		= new Label[7][7];
	public Label[][] 	points 		= new Label[3][7];
	public Label[][] 	ctq 		= new Label[2][7];
	public int[]		magicNumber = new int[6];
	public int 			timesRun;
	public Button 		resetbut;
	public TextArea		output;
	public Button 		showbut;

	boolean isRun3 = false;

	public int  tieRatio = 3;

	public static void main(String[] args) 
	{
		WCQ applet = new WCQ();
	}

	public WCQ()
	{
		createGUI();
	}
	
	public void createGUI()
	{
		setLayout(null);
		
		team[0] = new Team("ME", 5, 11, 8, 8, 0);
		team[1] = new Team("US", 5, 6, 7, 2, 1);
		team[2] = new Team("JA", 4, 4, 5, 8, 2);
		team[3] = new Team("CR", 6, 5, 9, 5, 3);
		team[4] = new Team("TT", 2, 3, 4, 11, 4);
		team[5] = new Team("HO", 6, 4, 11, 8, 5);
		
		//***LEVELS	
		add(levels[0][0] = new Label("Team"));
		add(levels[1][0] = new Label("Skill"));
		add(levels[2][0] = new Label("HFA"));

		for (int i = 1; i < 7; i++)		
			add(levels[0][i] = new Label(team[i-1].getName()));
		for (int i = 1; i < 7; i++)		
			add(levels[1][i] = new Label(i2s(team[i-1].getSkill())));
		for (int i = 1; i < 7; i++)		
			add(levels[2][i] = new Label(i2s(team[i-1].getHFA())));

		for (int i = 0; i < 3; i++)
			levels[i][0].setBounds(10 + (70*i), 140, 60, 15);
		for (int i = 0; i < 3; i++)		
			for (int j = 1; j < 7; j++)		
				levels[i][j].setBounds(10 + (70*i), 140 + (15 * j), 20, 15);
				
		for (int i = 0; i < 12; i++)
			add(plus[i] = new Button("+"));
		for (int i = 0; i < 12; i++)
			add(minus[i] = new Button("-"));
		for (int i = 0; i < 6; i++)		
			minus[i].setBounds(70, 155 + (15*i), 10, 15);
		for (int i = 0; i < 6; i++)
			plus[i].setBounds(100, 155 + (15*i), 10, 15);
		for (int i = 6; i < 12; i++)		
			minus[i].setBounds(140, 155 + (15*(i-6)), 10, 15);
		for (int i = 6; i < 12; i++)
			plus[i].setBounds(170, 155 + (15*(i-6)), 10, 15);
		
		for (int i = 0; i < 12; i++)
			minus[i].addActionListener(this);
		for (int i = 0; i < 12; i++)
			plus[i].addActionListener(this);
		//***END LEVELS	
		
		//***STANDINGS	
		add(standings[0][0] = new Label(""));
		add(standings[1][0] = new Label("GP"));
		add(standings[2][0] = new Label("W"));
		add(standings[3][0] = new Label("T"));
		add(standings[4][0] = new Label("L"));
		add(standings[5][0] = new Label("GF"));
		add(standings[6][0] = new Label("GA"));
		add(standings[7][0] = new Label("GD"));
		add(standings[8][0] = new Label("Pts"));

		for (int i = 1; i < 7; i++)		
			add(standings[0][i] = new Label(team[i-1].getFullName()));
		for (int i = 1; i < 7; i++)		
			add(standings[1][i] = new Label(i2s(team[i-1].getGames())));
		for (int i = 1; i < 7; i++)		
			add(standings[2][i] = new Label(i2s(team[i-1].getWins())));
		for (int i = 1; i < 7; i++)		
			add(standings[3][i] = new Label(i2s(team[i-1].getTies())));
		for (int i = 1; i < 7; i++)		
			add(standings[4][i] = new Label(i2s(team[i-1].getLosses())));
		for (int i = 1; i < 7; i++)		
			add(standings[5][i] = new Label(i2s(0)));
		for (int i = 1; i < 7; i++)		
			add(standings[6][i] = new Label(i2s(0)));
		for (int i = 1; i < 7; i++)		
			add(standings[7][i] = new Label(i2s(0)));
		for (int i = 1; i < 7; i++)		
			add(standings[8][i] = new Label(i2s(team[i-1].getPoints())));
		
		for (int i = 0; i < 9; i++)
			standings[i][0].setBounds(110 + (30*i), 20, 30, 15);
		for (int i = 0; i < 7; i++)
			standings[0][i].setBounds(10, 20 + (15 * i), 110, 15);
		for (int i = 1; i < 9; i++)		
			for (int j = 1; j < 7; j++)		
				standings[i][j].setBounds(110 + (30*i), 20 + (15 * j), 30, 15);
		//***END STANDINGS
		
		//***Run Buttons
		add(run1			= new Button("Run Qualifying with every games computer determined"));
		add(run2			= new Button("Run Qualifying using games played and have the computer determine the rest"));
		add(run3			= new Button("View current standings"));
		
		run1.setBounds(70, 310, 460, 20);
		run2.setBounds(70, 285, 460, 20);
		run3.setBounds(70, 260, 460, 20);
		//***END RUN BUTTONS
		
		//***Run RESULTS
		
		add(results[0][0] 	= new Label());	

		for (int i = 1; i < 7; i++)
		{
			add(results[0][i] 	= new Label(team[i-1].getName()));
			add(results[i][0] 	= new Label(team[i-1].getName()));
			add(results[i][1] 	= new Label(c2s(team[0].getHomeGame(i-1))));	
			add(results[i][2] 	= new Label(c2s(team[1].getHomeGame(i-1))));	
			add(results[i][3] 	= new Label(c2s(team[2].getHomeGame(i-1))));	
			add(results[i][4] 	= new Label(c2s(team[3].getHomeGame(i-1))));	
			add(results[i][5] 	= new Label(c2s(team[4].getHomeGame(i-1))));	
			add(results[i][6] 	= new Label(c2s(team[5].getHomeGame(i-1))));	
		}
		
		for (int i = 0; i < 7; i++)		
			for (int j = 0; j < 7; j++)		
				results[j][i].setBounds(270+(73*j), 135+(15*i), 20, 15);
	
		//***END RESULTS
		
		//***Run POINTS
		add(points[0][0] = new Label("Team"));
		add(points[1][0] = new Label("Points"));
		add(points[2][0] = new Label("Magic Number"));

		for (int i = 1; i < 7; i++)
			add(points[0][i] = new Label(team[i-1].getName()));
		for (int i = 1; i < 7; i++)
			add(points[1][i] = new Label(i2s(team[i-1].getPoints())));
		for (int i = 1; i < 7; i++)
			add(points[2][i] = new Label(i2s(magicNumber[i-1])));
			
		for (int i = 0; i < 7; i++)		
			for (int j = 0; j < 2; j++)		
				points[j][i].setBounds(450+(50*j), 20+(15*i), 50, 15);
	
		for (int i = 0; i < 7; i++)		
			points[2][i].setBounds(550, 20+(15*i), 85, 15);
		//***END POINTS

		//***Run CTQ
		add(ctq[0][0] = new Label("Team"));
		add(ctq[1][0] = new Label("Chance to Qualify %"));
		
		for (int i = 1; i < 7; i++)
			add(ctq[0][i] = new Label(team[i-1].getName()));
		for (int i = 1; i < 7; i++)
			add(ctq[1][i] = new Label(i2s(team[i-1].getChance())));

		for (int i = 0; i < 7; i++)		
			ctq[0][i].setBounds(650, 20+(15*i), 50, 15);
		for (int i = 0; i < 7; i++)		
			ctq[1][i].setBounds(700, 20+(15*i), 200, 15);
		//***END CTQ

		add(output = new TextArea());
		output.setBounds(400, 350, 200, 100);

		add(resetbut = new Button("Reset"));
		resetbut.setBounds(250, 350, 100, 20);
		
		add(showbut = new Button("show"));
		showbut.setBounds(250, 380, 100, 20);

		
		resetbut.addActionListener(this);
		run1.addActionListener(this);
		run2.addActionListener(this);
		run3.addActionListener(this);
		showbut.addActionListener(this);
	}
	
	public String i2s(int temp)
	{
		return Integer.toString(temp);
	}

	public String d2s(double dtemp)
	{
		return new Double(dtemp).toString();
	}
	
	public String c2s(char c)
	{
		return new Character(c).toString();
	}
	
	public int s2i(String stemp)
	{
		return Integer.parseInt(stemp);
	}

	public void actionPerformed(ActionEvent event)
	{
		for (int z = 0; z < 1000; z++)
		{
		String stemp;
		int temp;
		timesRun++;
		for (int i = 0; i < 6; i++)
		{
			if(event.getSource() == plus[i])
			{
				stemp = (levels[1][i+1].getText());
				temp = Integer.parseInt(stemp);				
				temp++;
				stemp = Integer.toString(temp);
				levels[1][i+1].setText(stemp);
			}

			if(event.getSource() == minus[i])
			{
				stemp = (levels[1][i+1].getText());
				temp = Integer.parseInt(stemp);				
				if (temp != 0) temp--;
				stemp = Integer.toString(temp);
				levels[1][i+1].setText(stemp);
			}
		}

		for (int i = 6; i < 12; i++)
		{
			if(event.getSource() == plus[i])
			{
				stemp = (levels[2][i-5].getText());
				temp = Integer.parseInt(stemp);				
				temp++;
				stemp = Integer.toString(temp);
				levels[2][i-5].setText(stemp);
			}

			if(event.getSource() == minus[i])
			{
				stemp = (levels[2][i-5].getText());
				temp = Integer.parseInt(stemp);				
				if (temp != 0) temp--;
				stemp = Integer.toString(temp);
				levels[2][i-5].setText(stemp);
			}
		}

		for (int i = 0; i < 6; i++)		
			team[i].reset();
		
		if (event.getSource() == resetbut)
		{
			for (int i = 0; i < 6; i++)		
			{	
				team[i].reset();
				magicNumber[i] = 0;
				team[i].setChanceTotal(0);
				timesRun = 0;
			}			
		}
		
		if (event.getSource() == run1)
		{
			computeResults();
			isRun3 = false;
		}

		if (event.getSource() == run2)
		{
			computeResults();
			useGamesPlayed();
			isRun3 = false;
		}
		
		if (event.getSource() == run3)
		{
			useGamesPlayed();
			isRun3 = true;
		}

		for (int i = 0; i < 6; i++)		
		{
			team[i].calculatePoints();
		}


		if ((event.getSource() == run1) || (event.getSource() == run2) || (event.getSource() == run3))
		{
			rankTeams();
	 		CTQ();
 			outputTable();
			outputStandings();
			outputPoints();
		}
		}
		if (event.getSource() == showbut)
		{
			output.append(ctq[0][1].getText() + " " +ctq[1][1].getText() + "\n");
			output.append(ctq[0][2].getText() + " " +ctq[1][2].getText() + "\n");
			output.append(ctq[0][3].getText() + " " +ctq[1][3].getText() + "\n");
			output.append(ctq[0][4].getText() + " " +ctq[1][4].getText() + "\n");
			output.append(ctq[0][5].getText() + " " +ctq[1][5].getText() + "\n");
			output.append(ctq[0][6].getText() + " " +ctq[1][6].getText() + "\n\n");
	}}
	
//---------------------------------------------------------------------------------------------	
	void rankTeams()
	{
		for (int i = 5; i > -1; i--)
			for (int j = 5; j > -1; j--)
				if (team[i].getPoints() > team[j].getPoints())
					team[i].setRank(team[i].getRank()-1);

		for (int a = 5; a > -1; a--)		
		for (int i = 5; i > -1; i--)
			for (int j = 5; j > -1; j--)
				if ((team[i].getRank() == team[j].getRank()) && (i != j))
				{
					if (team[i].getGD() > team[j].getGD())		// i has greater gd
						team[i].setRank(team[i].getRank()-1);
						
					else // gd is tied
					{
						if (team[i].getHomeGame(j) == 'W' && team[i].getAwayGame(j) == 'W')			// Team i wins both games
							team[i].setRank(team[i].getRank()-1);
				
						else if (team[i].getHomeGame(j) == 'W' && team[i].getAwayGame(j) == 'T')	// Team i wins 1 and ties 1
							team[i].setRank(team[i].getRank()-1);
					
						else if (team[i].getHomeGame(j) == 'W' && team[i].getAwayGame(j) == 'x')	// Team i wins the only game
							team[i].setRank(team[i].getRank()-1);
				
						else if (team[i].getSkill() > team[j].getSkill())
							team[i].setRank(team[i].getRank()-1);
					
						else if (team[i].getSkill() < team[j].getSkill())
							team[j].setRank(team[j].getRank()-1);

						else if (team[i].getSkill() == team[j].getSkill())
						{
							double random = Math.round(Math.random()*1);
							if (random == 0)
								team[j].setRank(team[j].getRank()-1);
							else
								team[i].setRank(team[i].getRank()-1);
						}
						
						else if (team[i].getHomeGame(j) == 'x')
						{
							double random = Math.round(Math.random()*1);
							if (random == 0)
								team[j].setRank(team[j].getRank()-1);
							else
								team[i].setRank(team[i].getRank()-1);
						}
					}
				}
	}
	void rankTeamsCTQ()
	{
		for (int i = 0; i < 6; i++)		
			team[i].setCTQRank(6);
	
		for (int i = 5; i > -1; i--)
			for (int j = 5; j > -1; j--)
				if (team[i].getChance() > team[j].getChance())
					team[i].setCTQRank(team[i].getCTQRank()-1);
		
		for (int k = 5; k > -1; k--)
			for (int i = 5; i > -1; i--)
				for (int j = 5; j > -1; j--)
					if ((team[i].getCTQRank() == team[j].getCTQRank()) && (i != j))
					{
						if (team[i].getPoints() > team[j].getPoints())
							team[i].setCTQRank(team[i].getCTQRank()-1);
						else
							team[j].setCTQRank(team[j].getCTQRank()-1);
					}	
	}
	char opposite(char r)
	{
			if 		(r == 'W') return 'L';
			else if (r == 'L') return 'W';
			else if (r == 'T') return 'T';
			return 'x';
	}

	public void setGame(int h, char r, int a)
	{
		team[h].setHomeGame(a, r);
		team[a].setAwayGame(h, opposite(r));
	}
	
	public void computeResults()
	{
		for (int i = 0; i < 6; i++)		
			for (int j = 0; j < 6; j++)		
				if (i!=j) getResult(i, j);
	}
	
	public void useGamesPlayed()
	{
		
		//****ROUND 1****
		setGame(1,'W',0);// Mexico 		   		@ US
		setGame(2,'W',4);// Trinidad & Tobago 	@ Jamaica
		setGame(3,'T',5);// Honduras 		   	@ Costa Rica

		//****ROUND 2****
		setGame(0,'W',2);// Jamaica 		   	@ Mexico
		setGame(5,'L',1);// US	    		   	@ Honduras
		setGame(3,'W',4);// Trinidad & Tobago  	@ Costa Rica

		//****ROUND 3**** 04/25/01
		setGame(4,'T',0);// Mexico 		    	@ Trinidad & Tobago
		setGame(1,'W',3);// Costa Rica 			@ US
		setGame(2,'T',5);// Honduras			@ Jamaica
		
		//****ROUND 4**** 06/16/01
		setGame(0,'L',3);// Costa Rica 	    	@ Mexico
		setGame(2,'T',1);// United States 	    @ Jamaica
		setGame(4,'L',5);// Honduras 		    @ Trinidad & Tobago

		//****ROUND 5**** 06/20/01
		setGame(5,'W',0);// Mexico 		    	@ Honduras
		setGame(1,'W',4);// Trinidad & Tobago  	@ US
		setGame(3,'W',2);// Jamaica 		    @ Costa Rica

		//****ROUND 6**** 06/30/01
		setGame(5,'L',3);// Costa Rica 	   		@ Honduras
		setGame(4,'L',2);// Jamaica 		    @ Trinidad & Tobago
		setGame(0,'W',1);// United States	    @ Mexico
				
		//****ROUND 7**** 09/01/01
		setGame(1,'L',5);// Honduras 			@ United States
		setGame(2,'L',0);// Mexico 		    	@ Jamaica
		setGame(4,'L',3);// Costa Rica 		    @ Trinidad & Tobago

		//****ROUND 8**** 09/04/01
		setGame(5,'W',2);// Jamaica 		    @ Honduras
		setGame(0,'W',4);// Trinidad & Tobago 	@ Mexico
		setGame(3,'T',1);// United States 		@ Costa Rica

		//****ROUND 9**** 10/07/01
//		setGame(5,'W',4); // Trinidad & Tobago  @ Honduras 
//		setGame(3,'T',0);// Mexico             	@ Costa Rica 
//		setGame(1,'W',2);// Jamaica            	@ United States 

		//****ROUND 10**** 11/11/01
//		setGame(0,'W',5);// Honduras           	@ Mexico 
//		setGame(2,'T',3);// Costa Rica         	@ Jamaica 
//		setGame(4,'L',1);// United States      	@ Trinidad & Tobago
		
		/*//***PREDICTIONS
		setGame(1,'T',0);// Mexico 		   		@ US
		setGame(2,'L',4);// Trinidad & Tobago 	@ Jamaica
		setGame(3,'W',5);// Honduras 		   	@ Costa Rica
		setGame(0,'W',2);// Jamaica 		   	@ Mexico
		setGame(5,'T',1);// US	    		   	@ Honduras
		setGame(3,'W',4);// Trinidad & Tobago  	@ Costa Rica
		setGame(4,'W',0);// Mexico 		    	@ Trinidad & Tobago
		setGame(1,'T',3);// Costa Rica 			@ US
		setGame(2,'W',5);// Honduras			@ Jamaica
		setGame(0,'W',3);// Costa Rica 	    	@ Mexico
		setGame(2,'L',1);// United States 	    @ Jamaica
		setGame(4,'L',5);// Honduras 		    @ Trinidad & Tobago
		setGame(5,'L',0);// Mexico 		    	@ Honduras
		setGame(1,'W',4);// Trinidad & Tobago  	@ US
		setGame(3,'W',2);// Jamaica 		    @ Costa Rica
		setGame(5,'L',3);// Costa Rica 	    	@ Honduras
		setGame(4,'L',2);// Jamaica 		    @ Trinidad & Tobago
		setGame(0,'W',1);// United States	    @ Mexico
		setGame(1,'W',5);// Honduras 			@ United States
		setGame(2,'T',0);// Mexico 		    	@ Jamaica
		setGame(4,'L',3);// Costa Rica 		    @ Trinidad & Tobago
		setGame(5,'W',2);// Jamaica 		    @ Honduras
		setGame(0,'W',4);// Trinidad & Tobago 	@ Honduras
		setGame(3,'T',1);// United States 		@ Costa Rica
		setGame(5,'W',4);// Trinidad & Tobago  	@ Honduras 
		setGame(3,'T',0);// Mexico             	@ Costa Rica 
		setGame(1,'W',2);// Jamaica            	@ United States 
		setGame(0,'W',5);// Honduras           	@ Mexico 
		setGame(2,'T',3);// Costa Rica         	@ Jamaica 
		setGame(4,'W',1);// United States      	@ Trinidad & Tobago
		//***END PREDICTIONS*/
	}
	
	void getResult(int h, int a)
	{
		char result;

		int	homeTeamTotal = team[h].getSkill() + team[h].getHFA();
		
		int	awayTeamTotal = team[a].getSkill();
		int	tieRatio = 5;
		int	total = homeTeamTotal + awayTeamTotal + ((homeTeamTotal + awayTeamTotal)/tieRatio);
		double random = Math.round(Math.random()*total+1);
		
		if (random <= homeTeamTotal) 
			result = 'W';
		else if ((random > homeTeamTotal) && (random <= awayTeamTotal + homeTeamTotal))
			result = 'L';
		else 
			result = 'T';
		
		team[h].setHomeGame(a, result);
		team[a].setAwayGame(h, opposite(result));
	}

	void outputTable()
	{
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
				results[j+1][i+1].setText(c2s(team[i].getHomeGame(j)));
	}
	
	void outputPoints()
	{	
		
		for (int i = 0; i < 6; i++)
		{
			if (team[i].getRank() == 0) points[0][1].setText(team[i].getName());		
			if (team[i].getRank() == 1) points[0][2].setText(team[i].getName());		
			if (team[i].getRank() == 2) points[0][3].setText(team[i].getName());		
			if (team[i].getRank() == 3) points[0][4].setText(team[i].getName());		
			if (team[i].getRank() == 4) points[0][5].setText(team[i].getName());		
			if (team[i].getRank() == 5) points[0][6].setText(team[i].getName());		
		}
		
		for (int i = 0; i < 6; i++)
		{
			if (team[i].getRank() == 0) points[1][1].setText(i2s(team[i].getPoints()));		
			if (team[i].getRank() == 1) points[1][2].setText(i2s(team[i].getPoints()));		
			if (team[i].getRank() == 2) points[1][3].setText(i2s(team[i].getPoints()));		
			if (team[i].getRank() == 3) points[1][4].setText(i2s(team[i].getPoints()));		
			if (team[i].getRank() == 4) points[1][5].setText(i2s(team[i].getPoints()));		
			if (team[i].getRank() == 5) points[1][6].setText(i2s(team[i].getPoints()));		
		}
		
		for (int i = 0; i < 6; i++)
		{
		     if (team[i].getRank() == 0) magicNumber[0] = magicNumber[0] + team[i].getPoints();
		     if (team[i].getRank() == 1) magicNumber[1] = magicNumber[1] + team[i].getPoints();
		     if (team[i].getRank() == 2) magicNumber[2] = magicNumber[2] + team[i].getPoints();
		     if (team[i].getRank() == 3) magicNumber[3] = magicNumber[3] + team[i].getPoints();
		     if (team[i].getRank() == 4) magicNumber[4] = magicNumber[4] + team[i].getPoints();
		     if (team[i].getRank() == 5) magicNumber[5] = magicNumber[5] + team[i].getPoints();
		}

		for (int i = 0; i < 6; i++)
			points[2][i+1].setText(d2s(roundDouble(((double)magicNumber[i]/timesRun),10,10)));
	}
		
	void CTQ()
	{	
		if (team[0].getRank() < 3)	team[0].setChanceTotal(team[0].getChance()+100);
		if (team[1].getRank() < 3)	team[1].setChanceTotal(team[1].getChance()+100);
		if (team[2].getRank() < 3)	team[2].setChanceTotal(team[2].getChance()+100);
		if (team[3].getRank() < 3)	team[3].setChanceTotal(team[3].getChance()+100);
		if (team[4].getRank() < 3)	team[4].setChanceTotal(team[4].getChance()+100);
		if (team[5].getRank() < 3)	team[5].setChanceTotal(team[5].getChance()+100);

		rankTeamsCTQ();

		for (int i = 0; i < 6; i++)
		{
			     if (team[i].getCTQRank() == 1) ctq[0][1].setText(team[i].getName());		
			else if (team[i].getCTQRank() == 2) ctq[0][2].setText(team[i].getName());		
			else if (team[i].getCTQRank() == 3) ctq[0][3].setText(team[i].getName());		
			else if (team[i].getCTQRank() == 4) ctq[0][4].setText(team[i].getName());		
			else if (team[i].getCTQRank() == 5) ctq[0][5].setText(team[i].getName());		
			else if (team[i].getCTQRank() == 6) ctq[0][6].setText(team[i].getName());		
		}

		for (int i = 0; i < 6; i++)
		{
				 if (team[i].getCTQRank() == 1) ctq[1][1].setText("%" + i2s(team[i].getChance()/timesRun));		
			else if (team[i].getCTQRank() == 2) ctq[1][2].setText("%" + i2s(team[i].getChance()/timesRun));		
			else if (team[i].getCTQRank() == 3) ctq[1][3].setText("%" + i2s(team[i].getChance()/timesRun));		
			else if (team[i].getCTQRank() == 4) ctq[1][4].setText("%" + i2s(team[i].getChance()/timesRun));		
			else if (team[i].getCTQRank() == 5) ctq[1][5].setText("%" + i2s(team[i].getChance()/timesRun));		
			else if (team[i].getCTQRank() == 6) ctq[1][6].setText("%" + i2s(team[i].getChance()/timesRun));		
		}
	}
	
	double roundDouble(double num, int m, int d)
	{
		num = Math.round(num*m);
		num = num/d;
		return num;
	}

	void outputStandings()
	{	
		for (int i = 1; i < 7; i++)
		{
			standings[1][i].setText(i2s(team[i-1].getGames()));
			standings[2][i].setText(i2s(team[i-1].getWins()));
			standings[3][i].setText(i2s(team[i-1].getTies()));
			standings[4][i].setText(i2s(team[i-1].getLosses()));
	
			if (isRun3 == true)
			{
				standings[5][i].setText(i2s(team[i-1].getGF()));
				standings[6][i].setText(i2s(team[i-1].getGA()));
				standings[7][i].setText(i2s(team[i-1].getGD()));
			}

			else
			{
				standings[5][i].setText(i2s(0));
				standings[6][i].setText(i2s(0));
				standings[7][i].setText(i2s(0));
			}
			
			standings[8][i].setText(i2s(team[i-1].getPoints()));
		}
	}
}


public class Team
{
	private int 	skill;
	private int 	hfa;
	private int 	rank;
	private int 	ctqRank;
	private int 	points;
	private int 	wins;
	private int 	ties;
	private int 	losses;
	private int 	teamNumber;
	private int 	games;
	private String 	name;
	private String 	fullName;
	private int		gf;
	private int		ga;
	private int		chanceTotal;
	
	private char[] 	homeGame 		= new char[6];
	private char[] 	awayGame 		= new char[6];
	
	public Team(String name, int skill, int hfa, int gf, int ga, int teamNumber)
	{
		this.name = name;
		this.skill = skill;
		this.hfa = hfa;
		this.teamNumber = teamNumber;
		this.gf = gf;
		this.ga = ga;

		rank = 6;
		ctqRank = 6;
			
		if (name.equals("ME"))	fullName = "Mexico";
		if (name.equals("US"))	fullName = "United States";
		if (name.equals("JA"))	fullName = "Jamaica";
		if (name.equals("CR"))	fullName = "Costa Rica";
		if (name.equals("TT"))	fullName = "Trinidad & Tobago";
		if (name.equals("HO"))	fullName = "Honduras";
	}
	
	//Set Methods
	void setName(String name)
	{	this.name = name;				}
	void setSkill(int skill)
	{	this.skill = skill;				}
	void setHFA(int hfa)
	{	this.hfa = hfa;					}
	void setRank(int rank)
	{	this.rank = rank;				}	
	void setCTQRank(int rank)
	{	this.ctqRank = rank;				}	
	void setPoints(int points)
	{	this.points = points;			}
	void setTeamNumber(int teamNumber)
	{	this.teamNumber = teamNumber;	}
	void setHomeGame(int opponent, char result)
	{	homeGame[opponent] = result;	}
	void setAwayGame(int opponent, char result)
	{	awayGame[opponent] = result;	}
	void setGF(int x)
	{	gf = x;	}
	void setGA(int x)
	{	ga = x;	}	
	void setChanceTotal(int x)
	{	chanceTotal = x;	}	

	//Get Methods
	String getName()
	{	return name;		}
	String getFullName()
	{	return fullName;	}
	int getSkill()
	{	return skill;		}
	int getTeamID()
	{	return teamNumber;	}
	int getHFA()
	{	return hfa;			}
	int getRank()
	{	return rank;		}
	int getCTQRank()
	{	return ctqRank;		}
	int getPoints()
	{	return points;		}
	int getWins()
	{	return wins;		}
	int getTies()
	{	return ties;		}
	int getLosses()
	{	return losses;		}
	int getGD()
	{	return (gf-ga);	}
	int getGF()
	{	return gf;	}
	int getGA()
	{	return ga;	}
	int getChance()
	{	return chanceTotal;	}

	char getHomeGame(int opponent)
	{	return homeGame[opponent];		}
	char getAwayGame(int opponent)
	{	return awayGame[opponent];		}

	int getGames()
	{
		games = wins + ties + losses;
		return games;	
	}
	
	//Calculate Methods
	void calculatePoints()
	{
		for (int i = 0; i < 6; i++)
		{
			if (homeGame[i]	== 'W')
				wins++;

			if (homeGame[i]	== 'T')
				ties++;
			
			if (homeGame[i]	== 'L')
				losses++;

			if (awayGame[i] == 'W')
				wins++;
			
			if (awayGame[i] == 'T')
				ties++;
			
			if (awayGame[i] == 'L')
				losses++;
		}
		points = (wins * 3) + ties;
	}
	
	//Misc Methods
	void reset()
	{
		rank = 5;
		points = 0;
		wins = 0;
		ties = 0;
		losses = 0;

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 6; j++)
			{
				homeGame[i] = 'x';
				awayGame[i] = 'x';
			}
	}
	
	double roundDouble(double num, int m, int d)
	{
		num = Math.round(num*m);
		num = num/d;
		return num;
	}

	int nameToNumber(String name)
	{
		if (name.equals("Mexico"))				return 0;
		if (name.equals("United States"))		return 1;
		if (name.equals("Jamaica"))				return 2;
		if (name.equals("Costa Rica"))			return 3;
		if (name.equals("Trinidad & Tobago"))	return 4;;
		if (name.equals("Honduras"))			return 5;

		return -1;	
	}
}