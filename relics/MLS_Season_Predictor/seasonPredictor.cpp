#include <iostream.h>
#include <iomanip.h>
#include <time.h>

char getResult(int hSkill, int advantage, int aSkill, int tieRatio);
void outputPoints(int points[], int wins[], int ties[], int losses[]);
void outputTable(char game[][12], char gameDiv[][12]);


int main()
{
	cout << "MLS Season predictor     Written by Derek Gathright   Lucid16@yahoo.com\n";

	srand ( time(NULL) );
	
	int teamSkill[12], teamPoints[12], teamHomeAdvantage[12], tieRatio = 6, wins[12], ties[12], losses[12];
	char game[12][12], realResults = 'y', gameDiv[12][12], repeat = 'y';

	teamSkill[0] = 5;// LA
	teamSkill[1] = 7;// KC
	teamSkill[2] = 4;// SJ
	teamSkill[3] = 4;// Colorado
	teamSkill[4] = 7;// Chicago	
	teamSkill[5] = 5;// Dallas					
	teamSkill[6] = 7;// Tampa Bay
	teamSkill[7] = 4;// Columbus
	teamSkill[8] = 6;// DC
	teamSkill[9] = 6;// NY
	teamSkill[10] = 4;// Miami
	teamSkill[11] = 3;// NE					

	teamHomeAdvantage[0] = 2;// LA
	teamHomeAdvantage[1] = 1;// KC
	teamHomeAdvantage[2] = 3;// SJ
	teamHomeAdvantage[3] = 2;// Colorado
	teamHomeAdvantage[4] = 2;// Chicago	
	teamHomeAdvantage[5] = 2;// Dallas					
	teamHomeAdvantage[6] = 1;// Tampa Bay
	teamHomeAdvantage[7] = 2;// Columbus
	teamHomeAdvantage[8] = 3;// DC
	teamHomeAdvantage[9] = 1;// NY
	teamHomeAdvantage[10] = 1;// Miami
	teamHomeAdvantage[11] = 1;// NE					
	
	do
	{
		for (int i = 0; i <= 11; i++) teamPoints[i] = 0;
	
		//cout << "Would you like to use the real results from the MLS games so far? (y/n): ";
		//cin  >> realResults;
	
		for (int count = 0; count < 1; count++)
			for (int i = 0; i <= 11; i++)
				for (int j = 0; j <= 11; j++)
				{
					wins[i] = 0;
					ties[i] = 0;
					losses[i] = 0;

					game[i][j] = ' ';
					if(i != j) game[i][j] = getResult(teamSkill[i], teamHomeAdvantage[j], teamSkill[j], tieRatio);

					gameDiv[i][j] = ' ';
					if(i != j) gameDiv[i][j] = getResult(teamSkill[i], teamHomeAdvantage[j], teamSkill[j], tieRatio);
				}
		
			if ((realResults == 'y') || (realResults == 'Y'))
			{
				// *** Input real results here ***
				// game[home][away] = 'home team result'
				// Ex. LA @ KC, KC wins
				// game[1][0] = 'W';
				// game[1][0] = 'F';
				
				/*      LA    KC    SJ    Col   Chi   Dal   TB    CB    DC    NY    Mia   NE
				LA       -    .-.   .-.   .-x   .     .     .     .     .     .     .     .
				KC      .-x    -    .-.   .-.   .     .     .     .     .     .     x     .
				SJ      .-x   .-.    -    .-.   .     x     .     .     .     .     .     .
				Col     .-.   .-.   .-.    -    .     .     .     .     .     x     x     .
				Chi     .     .     .     .      -    .-.   .-.   .-.   x     .     .     .
				Dal     .     .     .     .     .-x    -    .-.   .-.   .     .     .     .
				TB      .     .     .     .     .-.   .-x    -    .-x   .     .     .     .
				C-Bus   .     x     .     .     .-x   .-.   .-.    -    .     .     .     .
				DC      .     x     .     .     .     .     .     .      -    .-.   .-.   .-x
				NY      .     .     .     .     .     .     x     .     .-.    -    .-.   .-x
				Mia     .     x     x     .     .     .     .     .     .-.   .-.    -    .-.
				NE      .     .     .     .     .     .     .     .     .-.   .-.   .-x    - */
				
				// 4/7 games
				gameDiv[2][0]  	= 'W'; 	// LA  @ SJ
				gameDiv[9][11] 	= 'W'; 	// NE  @ NY
				game[8][1]    	= 'W';	// KC  @ DC
				game[10][3]    	= 'W';	// Col @ Mia
				gameDiv[7][4] 	= 'T'; 	// Chi @ Clb
				gameDiv[5][6]  	= 'L'; 	// TB  @ Dal
				
				// 4/14 games
				game[2][5] 		= 'L'; 	//  Dal @ SJ
				gameDiv[6][7] 	= 'L'; 	//  Clb @ TB
				gameDiv[11][10] = 'L'; 	//  Mia @ NE
				gameDiv[1][0] 	= 'W'; 	//  LA  @ KC
				game[3][9] 		= 'T'; 	//  NY  @ Col
				game[4][8] 		= 'W'; 	//  DC  @ Chi
				
				// 4/18 games
				game[10][1] 	= 'W'; 	// Mia @ KC
				
				// 4/21 games
				gameDiv[0][3]	= 'W'; 	//Colorado at Los Angeles
				game[9][6]  	= 'L'; 	//Tampa Bay at MetroStars 
				gameDiv[5][4]  	= 'W'; 	//Chicago at Dallas
				game[10][2] 	= 'W'; 	//San Jose at Miami
				game[7][1]  	= 'L'; 	//Kansas City at Columbus 
				gameDiv[8][11] 	= 'W'; 	//New England at D.C. United
			}
		
			for (int i = 0; i < 12; i++)
			{
				if (i < 4)
					for (int k = 4; k < 12; k++)
						gameDiv[i][k] = ' ';
				if ((i > 3) && (i < 8))
					for (int k = 0; k < 12; k++)
						if ((k < 4) || (k > 7))
							gameDiv[i][k] = ' ';
				if (i > 7)
					for (int k = 0; k < 8; k++)
						gameDiv[i][k] = ' ';
			}

			for (int home = 0; home <= 11; home++)
				for (int away = 0; away <= 11; away++)
				{
					if (game[home][away] == 'W'){ 		teamPoints[home]  	= teamPoints[home] + 3; 
											      		wins[home] 			= wins[home] + 1; 
												  		losses[away]		= losses[away] + 1;
												  		}
					if (game[home][away] == 'T'){ 		teamPoints[home]  	= teamPoints[home] + 1; 
												  		ties[home] 			= ties[home] + 1;
												 		}

					if (game[home][away] == 'L'){ 		teamPoints[away]  	= teamPoints[away] + 3; 
												  		wins[away] 			= wins[away] + 1; 
												  		losses[home] 		= losses[home] + 1;
												  		}
					if (game[home][away] == 'T'){ 		teamPoints[away]  	= teamPoints[away] + 1; 
												  		ties[away] 			= ties[away] + 1;
												  		}
											
					if (gameDiv[home][away] == 'W'){ 	teamPoints[home] 	= teamPoints[home] + 3; 
													 	wins[home] 	  		= wins[home] + 1; 
													 	losses[away] 	  	= losses[away] + 1;
													 	}
					if (gameDiv[home][away] == 'T'){ 	teamPoints[home] 	= teamPoints[home] + 1; 
													 	ties[home] 	  		= ties[home] + 1;
													 	}

					if (gameDiv[home][away] == 'L'){ 	teamPoints[away] 	= teamPoints[away] + 3; 
													 	wins[away] 	  		= wins[away] + 1; 
													 	losses[home] 	  	= losses[home] + 1;
													 	}
					if (gameDiv[home][away] == 'T'){ 	teamPoints[away] 	= teamPoints[away] + 1; 
													 	ties[away] 	  		= ties[away] + 1;
													 	}
				}

		outputPoints(teamPoints, wins, ties, losses);
		outputTable(game, gameDiv);
		//cout << "\n\tNote: All results are the home teams results. Read the HOME teams on the" << "left and the AWAY teams on top.";
	//-----------------------------------------------
		int place[11], team[11], temp;
		
		for (int i = 0; i < 12; i++)
		{
			place[i] = teamPoints[i];
			team[i] = i;
		}
		
		for (int i = 11; i>=0; i--)
		{
		    for (int j = 0; j<i; j++) 
	    	{
				if (teamPoints[j] > teamPoints[j+1]) 
				{
			    	temp = teamPoints[j];
		    		teamPoints[j] = teamPoints[j+1];
			    	teamPoints[j+1] = temp;

			    	temp = team[j];
		    		team[j] = team[j+1];
			    	team[j+1] = temp;
				}
	    	}
		
		cout << endl;
		
		if 		(i ==11)		cout << endl;
		if 		(team[i] == 1)	cout << "KC   ";
		else if (team[i] == 2)	cout << "SJ   ";
		else if (team[i] == 3)	cout << "Col  ";
		else if (team[i] == 4)	cout << "Chi  ";
		else if (team[i] == 5)	cout << "Dal  ";
		else if (team[i] == 6)	cout << "TB   ";
		else if (team[i] == 7)	cout << "C-Bus";
		else if (team[i] == 8)	cout << "DC   ";
		else if (team[i] == 9)	cout << "NY   ";
		else if (team[i] == 10)	cout << "Mia  ";
		else if (team[i] == 11) cout << "NE   ";
		else 					cout << "LA   ";

		cout << " - " << teamPoints[i];
	
		if (i == 4)				cout << "\n----------";
		}
	//-----------------------------------------------
		cout << "\n\nWould you like to run the program again? (y/n): ";
		cin >> repeat;
		
		if ((repeat == 'y') || (repeat == 'Y'))
			cout << "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	}
	while ((repeat == 'y') || (repeat == 'Y'));
	
	cout << "\nType something to exit: ";
	char a;
	cin >> a;
}

char getResult(int hSkill, int advantage, int aSkill, int tieRatio)
{
	int homeSkill;
	int awaySkill;
	int total;
	int random;
	int tieChance;

	homeSkill = hSkill + advantage;
	awaySkill = aSkill;
	tieChance = (hSkill+aSkill)/tieRatio;	
	total     = homeSkill + awaySkill + tieChance;

	random = rand()%total+1;

	if (random <= homeSkill) return ('W'); //Home Team Wins
	else if ((random > homeSkill) && (random < awaySkill + homeSkill)) return ('L'); // Away Team Wins
	else return ('T'); //Both teams tie
}	
	
void outputPoints(int points[], int wins[], int ties[], int losses[])
{
		cout << "\nWest" << setw(27) << "Central" << setw(22) << "East\n";
		cout << setw(6) << "W" << setw(4) << "T" << setw(4) << "L" << setw(8) << "POINTS";
		cout << setw(8) << "W" << setw(4) << "T" << setw(4) << "L" << setw(8) << "POINTS";
		cout << setw(8) << "W" << setw(4) << "T" << setw(4) << "L" << setw(8) << "POINTS";

		cout << "\n----------------------  ----------------------  ----------------------";
	for (int i = 0; i <= 3; i++) // Prints out the # of points (every run)
	{
		if (i == 0){ cout << "\nLA" << setw(4) << wins[i]   << setw(4) << ties[i]   << setw(4) << losses[i]   << setw(4) << points[i];
					 cout << setw(9) << "Chi"   << setw(3) << wins[i+4] << setw(4) << ties[i+4] << setw(4) << losses[i+4] << setw(4) << points[i+4];
					 cout << setw(8) <<  "DC"    << setw(4) << wins[i+8] << setw(4) << ties[i+8] << setw(4) << losses[i+8] << setw(4) << points[i+8];}
		if (i == 1){ cout << "\nKC"  << setw(4) << wins[i]   << setw(4) << ties[i]   << setw(4) << losses[i]   << setw(4) << points[i];
					 cout << setw(9) <<  "Dal"   << setw(3) << wins[i+4] << setw(4) << ties[i+4] << setw(4) << losses[i+4] << setw(4) << points[i+4];
					 cout << setw(8) <<  "NY"    << setw(4) << wins[i+8] << setw(4) << ties[i+8] << setw(4) << losses[i+8] << setw(4) << points[i+8];}
		if (i == 2){ cout << "\nSJ"  << setw(4) << wins[i]   << setw(4) << ties[i]   << setw(4) << losses[i]   << setw(4) << points[i];
					 cout << setw(9)  << "TB"    << setw(3) << wins[i+4] << setw(4) << ties[i+4] << setw(4) << losses[i+4] << setw(4) << points[i+4];
					 cout << setw(9)  << "Mia"   << setw(3) << wins[i+8] << setw(4) << ties[i+8] << setw(4) << losses[i+8] << setw(4) << points[i+8];}
		if (i == 3){ cout << "\nCol" << setw(3) << wins[i]   << setw(4) << ties[i]   << setw(4) << losses[i]   << setw(4) << points[i];
					 cout << setw(9)  << "Clb" << setw(3) << wins[i+4] << setw(4) << ties[i+4] << setw(4) << losses[i+4] << setw(4) << points[i+4];
					 cout << setw(9)  << "NE "   << setw(3) << wins[i+8] << setw(4) << ties[i+8] << setw(4) << losses[i+8] << setw(4) << points[i+8];}
	}
}

void outputTable(char game[][12], char gameDiv[][12])
{
	cout << "\n\n\tLA    KC    SJ    Col   Chi   Dal   TB    CB    DC    NY    Mia   NE";

	for (int i = 0; i <= 11; i++)
	{
		if (i == 0)  cout << "\nLA   ";
		if (i == 1)  cout << "\nKC   ";		
		if (i == 2)  cout << "\nSJ   ";		
		if (i == 3)  cout << "\nCol  ";		
		if (i == 4)  cout << "\nChi  ";
		if (i == 5)  cout << "\nDal  ";
		if (i == 6)  cout << "\nTB   ";
		if (i == 7)  cout << "\nC-Bus";		
		if (i == 8)  cout << "\nDC   ";		
		if (i == 9)  cout << "\nNY   ";		
		if (i == 10) cout << "\nMia  ";
		if (i == 11) cout << "\nNE   ";
				
		for (int j = 0; j <= 11; j++)
		{
			if (i < 4) 
				if (j < 4)
				{
					cout << "   " << game[i][j];
					cout << "-" << gameDiv[i][j];
				}
			if	(i < 4)
				if (j > 3)
					cout << "   " << game[i][j] << "  ";

			if ((i > 3) && (i < 8))
				if ((j > 3) && (j < 8))
				{
					cout << "   " << game[i][j];
					cout << "-" << gameDiv[i][j];
				}
				else 
					cout << "   " << game[i][j] << "  ";

			if (i > 7) 
				if (j > 7)
			{
				cout << "   " << game[i][j];
				cout << "-" << gameDiv[i][j];
			}
			else
				cout << "   " << game[i][j] << "  ";
		}
	}
}