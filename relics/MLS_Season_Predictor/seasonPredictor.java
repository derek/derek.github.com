import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.lang.*;
import java.lang.Object.*;
  

public class MLS extends Applet implements ActionListener, ItemListener
{
	boolean isStandalone = true;
	
	public Team[] team = new Team[12];
	
	public Choice	type = new Choice();
	public int 		week;
	public int 		temp;
	public Double 	dotemp;
	public String 	stemp;
	public int 		rows = 13;
	public int 		cols = 8;
	public int 		a;
	public int 		b;
	
	public Button[] plus		= new Button[24];
	public Button[] minus		= new Button[24];
	public Label[] 	tSkill 		= new Label[12];
	public Label[] 	tHFA 		= new Label[12];
	
	public Label[]  division    = new Label[3];
	
	public Label[]  teamName 	= new Label[12];
	public Label[]  teamName2 	= new Label[12];
	public Label[]  tgamesPlayed= new Label[12];
	public Label[]  twins   	= new Label[12];
	public Label[]  tties   	= new Label[12];
	public Label[]  tlosses 	= new Label[12];
	public Label[]  tpoints 	= new Label[12];
	
	public Label[][] stats		= new Label[cols][rows];
	public Label[][] rankings   = new Label[9][13];

	public TextArea output 		= new TextArea();

	public int timesToRun 		= 100;

	public static int LATEST_WEEK = 20;
	public String[] stype = {
//							 "Stats up to Week 28 (final seasons stats)",
//							 "Stats up to Week 27 (current stats)",
//							 "Stats up to Week 26 (current stats)",
//							 "Stats up to Week 25 (current stats)",
//							 "Stats up to Week 24 (current stats)",
//							 "Stats up to Week 23 (current stats)",
//							 "Stats up to Week 22 (current stats)",
//							 "Stats up to Week 21 (current stats)",
							 "Stats up to Week 20 (current stats)",
							 "Stats up to Week 19",
							 "Stats up to Week 18",
							 "Stats up to Week 17",
							 "Stats up to Week 16",
							 "Stats up to Week 15",
							 "Stats up to Week 14",
							 "Stats up to Week 13",
							 "Stats up to Week 12",
							 "Stats up to Week 11",
							 "Stats up to Week 10",
							 "Stats up to Week 9",
							 "Stats up to Week 8",
							 "Stats up to Week 7",
							 "Stats up to Week 6",
							 "Stats up to Week 5",
							 "Stats up to Week 4",
							 "Stats up to Week 3",
							 "Stats up to Week 2",
							 "Stats up to Week 1",
							 "Full season (Simulate entire season)",
							 "Full season (Use real game data and simulate the rest of the season)",							 
							 };
	
	public static void main(String[] args) 
	{
		MLS applet = new MLS();
	}
	
	public MLS()
	{
		setLayout(null);
		add(output);
		output.setBounds(5,	420, 900, 300);

		//                  NAME  SKILL|HFA|DIV| #| GF| GA
		team[0]  = new Team("LA", 	7, 	 2,	 1,  0, 38, 29 );
		team[1]  = new Team("KC", 	5, 	 1,	 1,  1, 23, 38);
		team[2]  = new Team("SJ", 	6, 	 2,  1,  2, 32, 20);
		team[3]  = new Team("CO", 	4, 	 2,  1,  3, 27, 31);

		team[4]  = new Team("CH", 	6, 	 2,  0,  4, 36, 19);
		team[5]  = new Team("DA", 	4, 	 1,  0,  5, 34, 31);
		team[6]  = new Team("TB", 	3, 	 1,  0,  6, 24, 50);
		team[7]  = new Team("CB", 	8, 	 2,  0,  7, 29, 27);

		team[8]  = new Team("DC", 	4, 	 2,  2,  8, 30, 37);
		team[9]  = new Team("NY", 	6, 	 2,  2,  9, 28, 25);
		team[10] = new Team("MI", 	6, 	 1,  2, 10, 39, 24);
		team[11] = new Team("NE", 	3, 	 1,  2, 11, 24, 32);
		
		createGUI();
		for (int i = 0; i < 24; i++)
		{	
			minus[i].addActionListener(this);
			plus[i].addActionListener(this);
		}
		type.addItemListener(this);
		placeValues(0);
	}

	public void program()
	{	
		for (int i = 0; i < 12; i++)
			team[i].reset();
	
		if (week == -1) 
		{	
			for (int i = 0; i < 12; i++)
				for (int j = 0; j < 12; j++)		
					getResult(i, j, 0);

			for (int i = 0; i < 12; i++)
				for (int j = 0; j < 12; j++)		
					getResult(i, j, 1);

			clearGames();
		}

		if (week == 0) 
		{	
			for (int i = 0; i < 12; i++)
				for (int j = 0; j < 12; j++)		
					getResult(i, j, 0);

			for (int i = 0; i < 12; i++)
				for (int j = 0; j < 12; j++)		
					getResult(i, j, 1);

		for (int i = 0; i < 12; i++)	

			clearGames();
			week = stype.length - 3;
			playedGames();
			
		}

		else 
		{	
			playedGames();
		}

		for (int i = 0; i < 12; i++)
			team[i].calculatePoints();

		
		for (int i = 0; i < 12; i++)
		{
			team[i].calculateWinPercentage();
			team[i].calculateGamePoints();
		}
		
		for (int i = 0; i < 12; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				if (team[i].getHomeGame(j) 	 	!= 'x')
				{	
					team[i].addOPoints(team[j].getPoints());
					team[i].addOGames(team[j].getGames());
					team[i].addOWinPercentage(team[j].getWinPercentage());
				}
				if (team[i].getAwayGame(j) 	 	!= 'x')
				{	
					team[i].addOPoints(team[j].getPoints());
					team[i].addOGames(team[j].getGames());
					team[i].addOWinPercentage(team[j].getWinPercentage());
				}
				if (team[i].getHomeDivGame(j)  	!= 'x')
				{	
					team[i].addOPoints(team[j].getPoints());
					team[i].addOGames(team[j].getGames());
					team[i].addOWinPercentage(team[j].getWinPercentage());
				}
				if (team[i].getAwayDivGame(j)  	!= 'x')
				{	
					team[i].addOPoints(team[j].getPoints());
					team[i].addOGames(team[j].getGames());
					team[i].addOWinPercentage(team[j].getWinPercentage());
				}
			}
		}
		rankTeams();
		placeValues(1);

		ssrankTeams();
		rirankTeams();		
		gprankTeams();
		ffrankTeams();
		outputStats();

//		for (int i = 0; i < 12; i++)
//			if (week == stype.length - 3)
//				output.append(team[i].getPoints() + "\t");
//		output.append("\n");
		
//		for (int i = 0; i < 12; i++)	output.append(team[i].getName() + "\t" + team[i].getRank() + "\t" + team[i].getGamePoints() + "\t" + team[i].getSSRank() + "\n");
//		for (int i = 0; i < 12; i++)	output.append(team[i].getName() + "\t" +(team[i].getGamePoints()/team[i].getGames())+ "\n");

		for (int i = 0; i < 12; i++)	
		{
//			output.append(team[i].getName() + "\t" +team[i].getPoints() + "\t" + (team[i].getRank()+1) +"\n");
		}	
	}	
	
	void outputStats()	
	{	
		for (int i = 1; i < cols; i++)
			for (int j = 1; j < rows; j++)
			{
				stats[1][j].setText(i2s(team[j-1].getRank()+1));
				stats[2][j].setText(d2s(team[j-1].getWinPercentage()));
				stats[3][j].setText(i2s(team[j-1].getSSRank()+1));
				stats[4][j].setText(d2s(team[j-1].getOWinPercentageAvg()));
				stats[5][j].setText(d2s(team[j-1].getRating()));
				stats[6][j].setText(d2s(team[j-1].getLGPRating()));
				stats[7][j].setText(d2s(team[j-1].getFFactor()));
			}
			
		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				if (team[i].getRank() == j)
				{	
					rankings[1][j+1].setText(team[i].getName());
					rankings[0][j+1].setText((j+1) + ". ");
					rankings[2][j+1].setText("- " + team[i].getPoints() + " (n/a)");
					if(week == stype.length-2)
						rankings[2][j+1].setText("- " + team[i].getPoints() + " (" + team[i].getGD() + ")");
				}

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				if (team[i].getGPRank() == j)
				{	
					rankings[3][j+1].setText(team[i].getName());
					rankings[4][j+1].setText("- " + team[i].getLGPRating());
				}

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				if (team[i].getRIRank() == j)
				{	
					rankings[5][j+1].setText(team[i].getName());
					rankings[6][j+1].setText("- " + team[i].getRating());
				}

		for (int i = 0; i < 12; i++)
			for (int j = 0; j < 12; j++)
				if (team[i].getFFRank() == j)
				{	
					rankings[7][j+1].setText(team[i].getName());
					rankings[8][j+1].setText("- " + team[i].getFFactor());
				}

		for (int i = 0; i < 12; i++)
		{	
//			if (week != LATEST_WEEK) output.append("Update D-Ranking" + "\n");

//			output.append(team[i].getName() + "\t" + team[i].getPoints() + "\t" + team[i].getGames() + "\t" + team[i].getWins()  + "\t" + team[i].getLosses()  + "\t" + team[i].getTies() + "\t" + team[i].getRank() + "\t");
//			output.append(team[i].getFFRank() + "\t" + team[i].getRIRank() + "\t" + team[i].getGPRank()  + "\t" + team[i].getFFactor() + "\t" + team[i].getRating()  + "\t" + team[i].getLGPRating() + "\t");
//			output.append(team[i].getWinPercentage() + "\t" + team[i].getSSRank() + "\t" + team[i].getOWinPercentageAvg() + "\t" + team[i].getGamePoints() + "\n");
		 }

		if (week == 19)
		{
//			for (int i = 0; i < 12; i++)
//			{
//				if (team[i].getRank() == 8)
//					output.append(team[i].getPoints() + "\n");
//			}
			output.append(team[1].getPoints() + "\t" + team[1].getRank());
			if (team[1].getRank() < team[0].getRank() && team[1].getRank() < team[2].getRank() && team[1].getRank() < team[3].getRank())
				output.append("\tTrue");
			output.append("\n");
		}		
	}

	char getResult(int a, int h, int gameType)
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

		if (gameType == 0)
		{
			team[h].setHomeGame(a, result);
			team[a].setAwayGame(h, opposite(result));
		}	
			
		else if (gameType == 1)
		{
			team[h].setHomeDivGame(a, result);
			team[a].setAwayDivGame(h, opposite(result));
		}	
			
		else if (gameType == 2)
		{
			total = homeTeamTotal + awayTeamTotal;
			
			if (random <= homeTeamTotal) 
				return 'W';
			else if (random > homeTeamTotal)
				return 'L';
			else 
				return 'T';
		}
		return 'x';
	}	
	
	void clearGames()
	{
		for (int i = 0; i < 12; i++)
		{
			for (int j = 0; j < 12; j++)
				if (i == j)
				{
					team[i].setHomeGame(j, 'x');
					team[i].setHomeDivGame(j, 'x');				
					team[i].setAwayGame(j, 'x');
					team[i].setAwayDivGame(j, 'x');				
				}
			
			if ((i > -1) && (i < 4))
				for (int j = 4; j < 12; j++)
				{
					team[i].setHomeDivGame(j, 'x');
					team[i].setAwayDivGame(j, 'x');
				}
				
			if ((i > 3)  && (i < 8))
			{
				for (int j = 0; j < 4; j++)
				{	
					team[i].setHomeDivGame(j, 'x');
					team[i].setAwayDivGame(j, 'x');
				}
				for (int j = 8; j < 12; j++)
				{	
					team[i].setHomeDivGame(j, 'x');
					team[i].setAwayDivGame(j, 'x');
				}
			}
			
			if ((i > 7)  && (i < 12))
				for (int j = 0; j < 8; j++)
				{	
					team[i].setHomeDivGame(j, 'x');
					team[i].setAwayDivGame(j, 'x');
				}
		}
	}
	
	void rankTeams()
	{
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if (team[i].getPPG() > team[j].getPPG())
					team[i].setRank(team[i].getRank()-1);

		for (int a = 11; a > -1; a--)		
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if ((team[i].getRank() == team[j].getRank()) && (i != j))
				{
					if (team[i].getGD() > team[j].getGD())		// i has greater gd
						team[i].setRank(team[i].getRank()-1);
						
/*					else // gd is tied
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
					}*/
				}
	}
	
	int pickRandom(int hn)
	{
		return (int)(Math.round(Math.random()*hn));
	}
	
	void ssrankTeams()
	{	
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if (team[i].getOWinPercentageAvg() > team[j].getOWinPercentageAvg())
					team[i].setSSRank(team[i].getSSRank()-1);

		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if ((team[i].getRank() == team[j].getRank()) && (i != j))
					if (team[i].getRank() > team[j].getRank())
					{
						team[i].setSSRank(team[i].getSSRank()-1);
					}
						
					else
					{
						double random = Math.round(Math.random()*1);
						if (random == 0)
							team[j].setRank(team[j].getRank()-1);
						else
							team[i].setRank(team[i].getRank()-1);
					}
	}
	
	void rirankTeams()
	{	
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if (team[i].getRating() > team[j].getRating())
					team[i].setRIRank(team[i].getRIRank()-1);
		
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if ((team[i].getRIRank() == team[j].getRIRank()) && (i != j))
				{
					if (team[i].getTeamID() < team[j].getTeamID())
					{
						team[i].setRIRank(team[i].getRIRank()-1);
					}

					else
					{
						team[j].setRIRank(team[j].getRIRank()-1);
					}
				}
	}
	
	void gprankTeams()
	{	
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if (team[i].getLGPRating() < team[j].getLGPRating())
					team[i].setGPRank(team[i].getGPRank()-1);
		
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if ((team[i].getGPRank() == team[j].getGPRank()) && (i != j))
				{
					if (team[i].getTeamID() < team[j].getTeamID())
					{
						team[i].setGPRank(team[i].getGPRank()-1);
					}

					else
					{
						team[j].setGPRank(team[j].getGPRank()-1);
					}
				}
	}
	
	void ffrankTeams()
	{	
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if (team[i].getFFactor() > team[j].getFFactor())
					team[i].setFFRank(team[i].getFFRank()-1);
		
		for (int i = 11; i > -1; i--)
			for (int j = 11; j > -1; j--)
				if ((team[i].getFFRank() == team[j].getFFRank()) && (i != j))
				{
					if (team[i].getTeamID() < team[j].getTeamID())
					{
						team[i].setFFRank(team[i].getFFRank()-1);
					}

					else
					{
						team[j].setFFRank(team[j].getFFRank()-1);
					}
				}
	}
	
	int getTeamID(String name)
	{
		if (name.equals("Los Angeles") 	|| name.equals("LA"))	return 0;
		if (name.equals("Kansas City") 	|| name.equals("KC"))	return 1;
		if (name.equals("San Jose") 	|| name.equals("SJ"))	return 2;
		if (name.equals("Colorado") 	|| name.equals("CO"))	return 3;
		if (name.equals("Chicago") 		|| name.equals("CH"))	return 4;;
		if (name.equals("Dallas") 		|| name.equals("DA"))	return 5;
		if (name.equals("Tampa Bay") 	|| name.equals("TB"))	return 6;
		if (name.equals("Columbus") 	|| name.equals("CB"))	return 7;
		if (name.equals("Washinton DC") || name.equals("DC"))	return 8;
		if (name.equals("New York") 	|| name.equals("NY"))	return 9;
		if (name.equals("Miami") 		|| name.equals("MI"))	return 10;
		if (name.equals("New England") 	|| name.equals("NE"))	return 11;
		
		return -2;
	}

	public void actionPerformed(ActionEvent event)
	{
		for (int i = 0; i < 12; i++)
		{
			if(event.getSource() == plus[i])
			{
				stemp = (tSkill[i].getText());
				temp = Integer.parseInt(stemp);				
				temp++;
				stemp = Integer.toString(temp);
				tSkill[i].setText(stemp);
			}

			if(event.getSource() == minus[i])
			{
				stemp = (tSkill[i].getText());
				temp = Integer.parseInt(stemp);				
				if (temp != 0) temp--;
				stemp = Integer.toString(temp);
				tSkill[i].setText(stemp);
			}
		}

		for (int i = 12; i < 24; i++)
		{
			if(event.getSource() == plus[i])
			{
				stemp = (tHFA[i-12].getText());
				temp = Integer.parseInt(stemp);				
				temp++;
				stemp = Integer.toString(temp);
				tHFA[i-12].setText(stemp);
			}

			if(event.getSource() == minus[i])
			{
				stemp = (tHFA[i-12].getText());
				temp = Integer.parseInt(stemp);				
				if (temp != 0) temp--;
				stemp = Integer.toString(temp);
				tHFA[i-12].setText(stemp);
			}
		}
	}
	
	public void itemStateChanged(ItemEvent event)
	{
		for (int a = 0; a < timesToRun; a++)
		{
			if (type.getSelectedIndex() == stype.length - 2)
				week = -1;
			else if (type.getSelectedIndex() == stype.length - 1)
				week = 0;
			else 
				week = stype.length - (type.getSelectedIndex() + 2);
				
			for (int i = 0; i < 12; i++)
			{
				stemp = tSkill[i].getText();
				team[i].setSkill(Integer.parseInt(stemp));
			}
	
			for (int i = 0; i < 12; i++)
			{
				stemp = tHFA[i].getText();
				team[i].setHFA(Integer.parseInt(stemp));
			}
			
			program();
		}
	}

	public void game(int h, char hr, int a,  boolean div)
	{
		if (div == false)
		{
			team[h].setHomeGame(a, hr);
			team[a].setAwayGame(h, opposite(hr));
		}
		
		if (div == true)
		{
			team[h].setHomeDivGame(a, hr);
			team[a].setAwayDivGame(h, opposite(hr));
		}
	}
	
	
	public void placeValues(int a)
	{
		for (int i = 0; i < 12; i++)
		{
			tSkill[i].setText(i2s(team[i].getSkill()));
			tHFA[i].setText(i2s(team[i].getHFA()));
			if (a == 1)
			{
				tgamesPlayed[i].setText(i2s(team[i].getGames()));
				twins[i].setText(i2s(team[i].getWins()));
				tties[i].setText(i2s(team[i].getTies()));
				tlosses[i].setText(i2s(team[i].getLosses()));
				tpoints[i].setText(i2s(team[i].getPoints()));
			}
		}
	}

	char opposite(char r)
	{
			if 		(r == 'W') return 'L';
			else if (r == 'L') return 'W';
			else if (r == 'T') return 'T';
			return 'x';
	}

	public String i2s(int temp)
	{
		return Integer.toString(temp);
	}

	public String d2s(double dtemp)
	{
		Double doubotemp = new Double(dtemp);
		return doubotemp.toString();
	}
	
	public int s2i(String stemp)
	{
		return Integer.parseInt(stemp);
	}


	public void playedGames()
	{
		if (week > 0)
		{
			game(0,  'L', 2,  false);
			game(9,  'W', 11, false);
			game(8,  'W', 1,  false);
			game(10, 'W', 3,  false);
			game(7,  'T', 4,  false);
			game(5,  'L', 6,  false);
		}
		
		if (week > 1)
		{
			game(2,  'L', 5,  false);
			game(6,  'L', 7,  false);
			game(11, 'L', 10, false);
			game(1,  'W', 0,  false);
			game(3,  'T', 9,  false);
			game(4,  'W', 8,  false);
		}
		
		if (week > 2)
		{
			game(1,  'W', 10, false);
			game(0,  'W', 3,  false);
			game(9 , 'L', 6,  false);
			game(5,  'W', 4,  false);
			game(10, 'W', 2,  false);
			game(7,  'L', 1,  false);
			game(8,  'W', 11, false);
		}
		
		if (week > 3)
		{
			game(3,  'L', 0 , false);
			game(6,  'L', 4 , false);
			game(9,  'W', 5 , false);
			game(10, 'W', 11, false);
			game(1,  'T', 2,  false);
		}
		
		if (week > 4)
		{
			game(9 , 'W', 1 , false);
			game(0 , 'W', 1 , false);
			game(7 , 'T', 10, false);
			game(9 , 'W', 8 , false);
			game(4 , 'W', 6 , false);
			game(5 , 'L', 3 , false);
			game(2 , 'W', 11, false);
		}
		
		if (week > 5)
		{
			game(6,  'T', 0,  false);
			game(8,  'W', 11, true);

			game(11, 'W', 4,  false);
			game(9,  'W', 0,  false);
			game(8,  'L', 2,  false);
			game(5,  'W', 7,  false);
			game(3,  'W', 6,  false);
		}
		
		if (week > 6)
		{
			game(11, 'W', 7, false);
			game(9,  'W', 3, false);

			game(2,  'W', 9,  false);
			game(7,  'W', 8,  false);
			game(10, 'W', 6,  false);
			game(4,  'W', 1,  false);
			game(3,  'L', 11, false);
			game(0,  'T', 5,  false);
		}

		if (week > 7)
		{
			game(5, 'L', 2,  false);
			game(3, 'L', 10, false);
			
			game(1, 'W', 9,  false);
			
			game(4, 'T', 2,  false);
			game(6, 'L', 10, false);
			game(7, 'T', 3,  false);
			game(0, 'W', 11, false);
		}		

		if (week > 8)
		{
			game(2,  'T', 3,  false);
			game(10, 'W', 8,  false);
			game(4,  'L', 0,  false);

			game(8,  'L', 4,  false);
			game(7,  'T', 0,  false);
			game(6,  'L', 1,  false);
			game(5,  'L', 10, false);
			game(3,  'L', 2,  false);
			game(11, 'T', 9,  false);
		}		

		if (week > 9)
		{
			game(6,  'L', 5,  false);

			game(8,  'L', 5,  false);
			game(10,  'W', 1,  false);
			game(11,  'W', 6,  false);
			
			game(4,  'W', 7, false);
			game(2,  'W', 0,  false);
		}		

		if (week > 10)
		{
			game(8,  'W', 9,  false);
			game(0,  'W', 1,  true);

			game(1,  'L', 8,  false);
			game(9,  'T', 2, false);
			game(7,  'W', 6,  false);
			game(10, 'T', 0, false);
			game(3,  'L', 5,  false);
		}		
		if (week > 11)
		{
			game(11, 'T', 3, false);
			
			game(0,  'W', 9, false);
			game(6,  'W', 8, false);
		
			game(5,  'L', 7, true);
			game(4, 'T', 11, false);
			game(2,  'W', 1, false);
		}		
		if (week > 12)
		{
			
			game(5,  'T', 0, false);
			game(8,  'T', 3, false);
		
			game(10,  'W', 7, false);
			game(4, 'W', 9, false);
			game(1,  'W', 6, false);
		}		
		if (week > 13)
		{
			game(0,  'L', 7, false);
			game(2,  'T', 10, false);
			game(6,  'L', 9, false);
			game(1,  'L', 4, false);
			game(5,  'T', 11, false);
			game(3,  'W', 8, false);
			game(7,  'T', 5, false);
			game(6,  'L', 4, true);
			game(10,  'L', 9, false);
			game(11,  'L', 1, false);
			game(8,  'L', 0, false);
			game(3,  'W', 2, true);
		}		
		if (week > 14)
		{
			game(2,  'W', 6, false);
			game(11, 'L', 8, false);
			game(4,  'W', 3, false);
			game(1,  'W', 5, false);
			game(0,  'W', 10, false);
		}	
		if (week > 15)
		{
			game(11,  'W', 0, false);
			
			game(10,  'W', 4, false);
			game(7, 'W', 9, false);
			game(5,  'W', 6, true);
			game(3,  'T', 1, false);
			game(2,  'W', 8, false);
			
			game(9,  'W', 8, true);
			game(6,  'L', 3, false);
			game(10, 'T', 11, true);
			game(7,  'W', 2, false);
			game(4,  'W', 5, false);
			
		}		
		if (week > 16)
		{
			game(1,  'W', 11, false);
		}		
		if (week > 17)
		{
			game(7,  'W', 4, true);
			
			game(8,  'T', 10, false);
			game(3,  'T', 4, false);
			game(6,  'W', 2, false);
			game(11,  'W', 5, false);
			game(9,  'W', 10, false);
			game(1,  'L', 7, false);
		}		
		if (week > 18)
		{
			game(11, 'L', 2, false);

			game(11,  'L', 10, true);
			game(4,  'W', 6, true);
			game(5,  'W', 9, false);
			game(2, 'T', 7, false);
			game(0,  'W', 8, false);
			game(3,  'T', 1, true);
		}		
		if (week > 19)
		{
			game(8,  'L', 9, true);
			game(7,  'W', 5, true);
			game(4,  'L', 10, false);
			game(1,  'L', 0, true);

			game(2,  'L', 4, false);
			game(0,  'W', 6, false);
			game(11,  'W', 8, true);
			game(10,  'W', 5, false);
 			
 			game(9,  'L', 7, false);
			game(1,  'W', 3, false);		
		}		
		if (week > 20)
		{		}		
		if (week > 21)
		{		}		
		if (week > 22)
		{		}		
		if (week > 23)
		{		}		
		if (week > 24)
		{		}		
		if (week > 25)
		{		}		
		if (week > 26)
		{		}		
		if (week > 27)
		{		}		
		if (week > 28)
		{		}		
	}
	
	public void createGUI()
	{
		setLayout(null);
		
		for (int i = 0; i < 12; i++)		
		{
			add(teamName[i] = new Label(team[i].getName()));
			
			if (i == 4 || i == 8) temp += 20;
			if (i == 0) temp += 20;

			teamName[i].setBounds(5, (i*20)+temp+20, 20, 20);
		}	

		//*** (SKILL/ADVANTAGE) **//
		Label sLabel = new Label("Skill");
		Label aLabel = new Label("Advantage");
		
		add(sLabel);
		add(aLabel);
		
		sLabel.setBounds(40, 20, 40, 20);
		aLabel.setBounds(85, 20, 70, 20);		

		temp = 20;
		
		for (int i = 0; i < 12; i++)
		{
			tSkill[i]   = new Label("", tSkill[i].CENTER);
			tHFA[i] 	= new Label("", tHFA[i].CENTER);
			minus[i]	= new Button("-");
			plus[i]		= new Button("+");
			minus[i+12]	= new Button("-");
			plus[i+12]	= new Button("+");
				
			add(tSkill[i]);
			add(tHFA[i]);
			add(minus[i]);
			add(plus[i]);
			add(minus[i+12]);
			add(plus[i+12]);

			if (i == 4 || i == 8) temp += 20;

			tSkill[i].setBounds( 40,  (i*20)+temp+20, 30, 20 );
			tHFA[i].setBounds(   100, (i*20)+temp+20, 30, 20 );

			minus[i].setBounds(    30,  (i*20)+temp+20, 10, 20 );
			plus[i].setBounds(     70,  (i*20)+temp+20, 10, 20 );
			minus[i+12].setBounds( 90,  (i*20)+temp+20, 10, 20 );
			plus[i+12].setBounds(  130, (i*20)+temp+20, 10, 20 );
		}
		//*** END
		
		//*** (STANDINGS) **//
		division[0] = new Label("WEST");		
		division[1] = new Label("CENTRAL");		
		division[2] = new Label("EAST");

		Label[] gamesPlayed   = new Label[3];
		Label[] wins   = new Label[3];
		Label[] ties   = new Label[3];
		Label[] losses = new Label[3];
		Label[] points = new Label[3];		

		add(division[0]);
		add(division[1]);
		add(division[2]);
		
		division[0].setBounds( 244, 0, 100, 20);
		division[1].setBounds( 433, 0, 100, 20);
		division[2].setBounds( 647, 0, 100, 20);

		for (int i = 0; i < 4; i++)		
		{
			add(teamName2[i] = new Label(team[i].getName()));
			teamName2[i].setBounds(170, (i*20)+40, 20, 20);
		}	
		for (int i = 4; i < 8; i++)		
		{
			add(teamName2[i] = new Label(team[i].getName()));
			teamName2[i].setBounds(370, ((i-4)*20)+40, 20, 20);
		}	
		for (int i = 8; i < 12; i++)		
		{
			add(teamName2[i] = new Label(team[i].getName()));
			teamName2[i].setBounds(570, ((i-8)*20)+40, 20, 20);
		}	

		
		for (int i = 0; i < 3; i++)		
		{
			add(gamesPlayed[i] = new Label("GP"));
			gamesPlayed[i].setBounds(200+(i*200), 20, 20, 20);
		
			add(wins[i] = new Label("W"));
			wins[i].setBounds(230+(i*200), 20, 20, 20);
			
			add(ties[i] = new Label("T"));
			ties[i].setBounds(260+(i*200), 20, 20, 20);
			
			add(losses[i] = new Label("L"));
			losses[i].setBounds(290+(i*200), 20, 20, 20);
			
			add(points[i] = new Label("Pts"));
			points[i].setBounds(320+(i*200), 20, 20, 20);
			
		}	

		for (int i = 0; i < 4; i++)
		{
			add(tgamesPlayed[i] = new Label("xx"));
			tgamesPlayed[i].setBounds(200, 40+((i-0)*20), 20, 20);
			
			add(twins[i] = new Label("x"));
			twins[i].setBounds(230, 40+((i-0)*20), 20, 20);
			
			add(tties[i] = new Label("x"));
			tties[i].setBounds(260, 40+((i-0)*20), 20, 20);
			
			add(tlosses[i] = new Label("x"));
			tlosses[i].setBounds(290, 40+((i-0)*20), 20, 20);
			
			add(tpoints[i] = new Label("x"));
			tpoints[i].setBounds(320, 40+((i-0)*20), 20, 20);
		
		}
		
		for (int i = 4; i < 8; i++)		
		{
			add(tgamesPlayed[i] = new Label("xx"));
			tgamesPlayed[i].setBounds(400, 40+((i-4)*20), 20, 20);
			
			add(twins[i] = new Label("x"));
			twins[i].setBounds(430, 40+((i-4)*20), 20, 20);
			
			add(tties[i] = new Label("x"));
			tties[i].setBounds(460, 40+((i-4)*20), 20, 20);
			
			add(tlosses[i] = new Label("x"));
			tlosses[i].setBounds(490, 40+((i-4)*20), 20, 20);
			
			add(tpoints[i] = new Label("x"));
			tpoints[i].setBounds(520, 40+((i-4)*20), 20, 20);
		}
		
		for (int i = 8; i < 12; i++)		
		{
			add(tgamesPlayed[i] = new Label("xx"));
			tgamesPlayed[i].setBounds(600, 40+((i-8)*20), 20, 20);
			
			add(twins[i] = new Label("x"));
			twins[i].setBounds(630, 40+((i-8)*20), 20, 20);
			
			add(tties[i] = new Label("x"));
			tties[i].setBounds(660, 40+((i-8)*20), 20, 20);
			
			add(tlosses[i] = new Label("x"));
			tlosses[i].setBounds(690, 40+((i-8)*20), 20, 20);
			
			add(tpoints[i] = new Label("x"));
			tpoints[i].setBounds(720, 40+((i-8)*20), 20, 20);
		}
		//*** END

		//*** (RANKINGS) **//
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 13; j++)
				add(rankings[i][j] = new Label());

		for (int i = 0; i < 13; i++)		
			rankings[0][i].setBounds(180, 125+(i*20), 47, 20);

		for (int i = 0; i < 13; i++)
			rankings[1][i].setBounds(230, 125+(i*20), 45, 20);
		
		for (int i = 0; i < 13; i++)
			rankings[2][i].setBounds(275, 125+(i*20), 50, 20);

		for (int i = 0; i < 13; i++)
			rankings[3][i].setBounds(340, 125+(i*20), 45, 20);

		for (int i = 0; i < 13; i++)
			rankings[4][i].setBounds(385, 125+(i*20), 50, 20);

		for (int i = 0; i < 13; i++)
			rankings[5][i].setBounds(470, 125+(i*20), 45, 20);

		for (int i = 0; i < 13; i++)
			rankings[6][i].setBounds(515, 125+(i*20), 50, 20);

		for (int i = 0; i < 13; i++)
			rankings[7][i].setBounds(585, 125+(i*20), 75, 20);

		for (int i = 0; i < 13; i++)
			rankings[8][i].setBounds(660, 125+(i*20), 50, 20);

		rankings[0][0].setText("Rank");
		rankings[1][0].setText("Points");
		rankings[2][0].setText("    (GD)");
		rankings[3][0].setText("LGP-Ri");
		rankings[5][0].setText("MLS-Ri");
		rankings[7][0].setText("Fluke Factor");
		//*** END		

		//*** (SEASON TYPE) **//
		type     		= new Choice();
		add(type);
		
		for (int i = 0; i < stype.length; i++)
			type.add(stype[i]);
		
		type.setBounds(30, 390, 700, 20);
		//*** END

		//*** (STATS TITLES) **//
		stats 			= new Label[cols][rows];
		
		for (int i = 0; i < cols; i++)
			for (int j = 0; j < rows; j++)
				stats[i][j] = new Label();
		
		for (int i = 0; i < cols; i++)
			for (int j = 0; j < rows; j++)
			{
				add(stats[i][j]);
				stats[i][j].setBounds(30+(i*90), 420+(j*20), 80, 20);
			}
		
		for (int i = 0; i < cols; i++)
			for (int j = 0; j < rows; j++)
				stats[i][j].setText("xxx");
		for (int i = 1; i < rows; i++)
			stats[0][i].setText(team[i-1].getName());
		
		stats[0][0].setText("Team");
		stats[1][0].setText("Points Rank");
		stats[2][0].setText("Win %");
		stats[3][0].setText("Sched. rank");
		stats[4][0].setText("Oppon. Win %");
		stats[5][0].setText("MLS-Ri");
		stats[6][0].setText("LGP-Ri");
		stats[7][0].setText("Fluke Factor");
		//*** END
	}
}

public class Team
{
	private int 	skill;
	private int 	hfa;
	private int 	rank;
	private int 	points;
	private int 	wins;
	private int 	ties;
	private int 	losses;
	private int 	teamNumber;
	private int 	division;
	private int    	ssrank;
	private int    	ffrank;
	private int    	rirank;
	private int    	gprank;
	private int    	drank;
	private int 	games;
	private double  gamePoints;
	private double  rating;
	private double  LGPRating;
	private double  winpct;
	private double  owinpct;
	private double  owinpctavg;
	private double  opoints;
	private int		ogames;
	private String 	name;
	private String 	fullName;
	private int		gd;

	
	private char[] 	homeGame 		= new char[12];
	private char[] 	homeDivGame 	= new char[12];
	private char[] 	awayGame 		= new char[12];
	private char[] 	awayDivGame 	= new char[12];
	
	public Team(String name, int skill, int hfa, int division, int teamNumber, int gf, int ga)
	{
		this.name = name;
		this.skill = skill;
		this.hfa = hfa;
		this.division = division;
		this.teamNumber = teamNumber;
		gd = gf-ga;
		rank = 12;
		ssrank = 11;
		rirank = 11;
		gprank = 11;
			
		if (name.equals("LA"))	fullName = "Los Angeles";
		if (name.equals("KC"))	fullName = "Kansas City";
		if (name.equals("SJ"))	fullName = "San Jose";
		if (name.equals("CO"))	fullName = "Colorado";
		if (name.equals("CH"))	fullName = "Chicago";
		if (name.equals("DA"))	fullName = "Dallas";
		if (name.equals("TB"))	fullName = "Tampa Bay";
		if (name.equals("CB"))	fullName = "Columbus";
		if (name.equals("DC"))	fullName = "Washinton DC";
		if (name.equals("NY"))	fullName = "New York";
		if (name.equals("MI"))	fullName = "Miami";
		if (name.equals("NE"))	fullName = "New England";

		if (name.equals("CB"))	drank = 0;
		if (name.equals("NY"))	drank = 1;
		if (name.equals("CH"))	drank = 2;
		if (name.equals("LA"))	drank = 3;
		if (name.equals("MI"))	drank = 4;
		if (name.equals("SJ"))	drank = 5;
		if (name.equals("KC"))	drank = 6;
		if (name.equals("DC"))	drank = 7;
		if (name.equals("CO"))	drank = 8;
		if (name.equals("TB"))	drank = 9;
		if (name.equals("NE"))	drank = 10;
		if (name.equals("DA"))	drank = 11;
	}
	
	//Set Methods
	void setName(String name)
	{	this.name = name;				}
	void setSkill(int skill)
	{	this.skill = skill;				}
	void setHFA(int hfa)
	{	this.hfa = hfa;					}
	void setGD(int rank)
	{	this.gd = gd;					}
	void setRank(int rank)
	{	this.rank = rank;				}	
	void setDRank(int drank)
	{	this.drank = drank;				}	
	void setSSRank(int ssrank)
	{	this.ssrank = ssrank;			}
	void setRIRank(int rirank)
	{	this.rirank = rirank;			}
	void setGPRank(int gprank)	
	{	this.gprank = gprank;			}
	void setFFRank(int ffrank)
	{	this.ffrank = ffrank;			}
	void setPoints(int points)
	{	this.points = points;			}
	void setOPoints(int opoints)
	{	this.opoints = opoints;			}
	void setDivision(int division)
	{	this.division = division;	  	}
	void setTeamNumber(int teamNumber)
	{	this.teamNumber = teamNumber;	}
	void setHomeGame(int opponent, char result)
	{	homeGame[opponent] = result;	}
	void setHomeDivGame(int opponent, char result)
	{	homeDivGame[opponent] = result;	}
	void setAwayGame(int opponent, char result)
	{	awayGame[opponent] = result;	}
	void setGamePoints(int x)
	{	gamePoints = x;	}
	void setAwayDivGame(int opponent, char result)
	{	awayDivGame[opponent] = result;	}
	
	void setPlayoffSkill()
	{
		skill = ((8-rank) + skill);
		hfa   = (hfa+2);
		
		if (rank == 0)			skill = 1000;
		if (rank == 1)			skill = 600;
		if (rank == 2)			skill = 450;
		if (rank == 3)			skill = 300;
		if (rank == 4)			skill = 250;
		if (rank == 5)			skill = 150;
		if (rank == 6)			skill = 100;
		if (rank == 7)			skill = 40;
		if (rank == 8)			skill = 20;
		if (rank == 9)			skill = 10;
		if (rank == 10)			skill = 5;
		if (rank == 11)			skill = 1;
	}
	
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
	int getGD()
	{	return gd;			}
	int getRank()
	{	return rank;		}
	int getDRank()	
	{	return drank;		}
	int getSSRank()
	{	return ssrank;		}
	int getRIRank()
	{	return rirank;		}
	int getGPRank()
	{	return gprank;		}
	int getFFRank()
	{	return ffrank;		}
	int getPoints()
	{	return points;		}
	double getOPoints()
	{	return opoints;		}
	int getDivision()
	{	return division;	}
	int getWins()
	{	return wins;		}
	int getTies()
	{	return ties;		}
	int getLosses()
	{	return losses;		}
	double getGamePoints()
	{	return gamePoints;	}
	int getOGames()
	{	return ogames;		}
	double getWinPercentage()
	{	return winpct;		}
	double getOWinPercentage()
	{	return owinpct;		}
	double getOWinPercentageAvg()
	{	return owinpctavg;	}
	double getRating()
	{	return rating;		}
	double getPPG()
	{	return (double)points/(double)games;		}
//	int getDRank()
//	{	return drank;	}
	double getLGPRating()
	{	return LGPRating;	}

	double getOPointsAverage()
	{	return opoints/getGames();		}
	char getHomeGame(int opponent)
	{	return homeGame[opponent];		}
	char getHomeDivGame(int opponent)
	{	return homeDivGame[opponent];	}
	char getAwayGame(int opponent)
	{	return awayGame[opponent];		}
	char getAwayDivGame(int opponent)
	{	return awayDivGame[opponent];	}
	double getFFactor()
	{	return roundDouble(((double)points/(double)games)*owinpctavg, 1000, 1000);	}

	int getGames()
	{
		games = wins + ties + losses;
		return games;	
	}
	
	//Add methods
	void addOPoints(int x)
	{
		opoints += x;
	}
	void addGamePoints(int x)
	{
		gamePoints += x;
	}
	void addOWinPercentage(double owp)
	{
		owinpct += owp;
		owinpct = roundDouble(owinpct, 1000, 1000);
	}
	void addOGames(double og)
	{
		ogames += og;
	}

	//Calculate Methods
	void calculatePoints()
	{
		for (int i = 0; i < 12; i++)
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
		
		for (int i = 0; i < 12; i++)
		{
			if (homeDivGame[i] == 'W')
				wins++;

			if (homeDivGame[i] == 'T')
				ties++;
			
			if (homeDivGame[i] == 'L')
				losses++;

			if (awayDivGame[i] == 'W')
				wins++;
			
			if (awayDivGame[i] == 'T')
				ties++;
			
			if (awayDivGame[i] == 'L')
				losses++;
		}
		
		points = (wins * 3) + ties;
	}
	
	void calculateGamePoints()
	{
		for (int i = 0; i < 12; i++)
		{
			if (homeGame[i] == 'W')
				gamePoints += 0;
			if (awayGame[i] == 'W')
				gamePoints += 0;
			if (homeGame[i] == 'T')
				gamePoints += 1;
			if (awayGame[i] == 'T')
				gamePoints += .5;
			if (homeGame[i] == 'L')
				gamePoints += 2;
			if (awayGame[i] == 'L')
				gamePoints += 1.5;
			
			if (homeDivGame[i] == 'W')
				gamePoints += 0;
			if (awayDivGame[i] == 'W')
				gamePoints += 0;
			if (homeDivGame[i] == 'T')
				gamePoints += 1;
			if (awayDivGame[i] == 'T')
				gamePoints += .5;
			if (homeDivGame[i] == 'L')
				gamePoints += 2;
			if (awayDivGame[i] == 'L')
				gamePoints += 1.5;
		}
	}
	void calculateWinPercentage()
	{
		winpct =  (double)points/((wins+ties+losses)*3);
		winpct = roundDouble(winpct, 1000, 1000);
	}
	
	void calculateOWinPercentage()
	{
		owinpctavg = owinpct/games;
		owinpctavg = roundDouble(owinpctavg, 1000, 1000);
	}

	void calculateRating()
	{
		rating = ((12-rank) + (winpct*(12)) + (owinpctavg*(12))) ;
		rating = roundDouble(rating, 1000, 1000);
	}

	void calculateLGPRating()
	{
		gamePoints = (gamePoints/games)*7;
		gamePoints = roundDouble(gamePoints, 1000, 1000);
		
		LGPRating = (rank+rank+gamePoints+ssrank) ;
		LGPRating = roundDouble(LGPRating, 1000, 1000);
		
	}

	//Misc Methods
	void reset()
	{
		rank = 11;
		ssrank = 11;
		rirank = 11;
		ffrank = 11;
		gprank = 11;
		points = 0;
		wins = 0;
		ties = 0;
		losses = 0;
		gamePoints = 0;
		rating = 0;
		opoints = 0;
		owinpct = 0;
		winpct = 0;
		ogames = 0;

		for (int i = 0; i < 12; i++)
		{
			for (int j = 0; j < 12; j++)
			{
				homeGame[i] = 'x';
				awayGame[i] = 'x';
			
				if (j < 4)
				{
					homeDivGame[i] = 'x';
					awayDivGame[i] = 'x';
				}

				if (j > 8)
				{
					homeDivGame[i] = 'x';
					awayDivGame[i] = 'x';
				}

				if ((j > 3) && (j < 8))
				{
					homeDivGame[i] = 'x';
					awayDivGame[i] = 'x';
				}
			}
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
		if (name.equals("Los Angeles"))		return 0;
		if (name.equals("Kansas City"))		return 1;
		if (name.equals("San Jose"))		return 2;
		if (name.equals("Colorado"))		return 3;
		if (name.equals("Chicago"))			return 4;;
		if (name.equals("Dallas"))			return 5;
		if (name.equals("Tampa Bay"))		return 6;
		if (name.equals("Columbus"))		return 7;
		if (name.equals("Washinton DC"))	return 8;
		if (name.equals("New York"))		return 9;
		if (name.equals("Miami"))			return 10;
		if (name.equals("New England"))		return 11;

		return -1;	
	}
}