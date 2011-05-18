#include <iostream>
#include <time.h>

char getResult(int hSkill, int advantage, int aSkill, int tieRatio);
void outputTable(char game[][6]);
void outputPoints(int points[], int max);

int main()
{
	srand (time(NULL));

	int teamSkill[6], teamPoints[6], teamHomeAdvantage[6], tieRatio = 5;
	char game[6][6], realResults = ' ';
	
	teamHomeAdvantage[0] = 15;// Mexico
	teamHomeAdvantage[1] = 6;// United States
	teamHomeAdvantage[2] = 3;// Jamaica
	teamHomeAdvantage[3] = 3;// Costa Rica
	teamHomeAdvantage[4] = 4;// Honduras	
	teamHomeAdvantage[5] = 2;// Trinidad/Tobago					
	
	teamSkill[0] = 8;// Mexico
	teamSkill[1] = 9;// United States
	teamSkill[2] = 2;// Jamaica
	teamSkill[3] = 3;// Costa Rica
	teamSkill[4] = 4;// Honduras	
	teamSkill[5] = 2;// Trinidad/Tobago
	char repeat = 'y';

	do
	{
		for (int i = 0; i < 6; i++) teamPoints[i] = 0;
	
		cout << "HexTester       Written by Derek Gathright       Lucid16@yahoo.com\n";
		cout << "\nWould you like to use the real results from the WCQ'ing games so far? (y/n): ";
		cin  >> realResults;
	
		for (int i = 0; i <= 5; i++)
			for (int j = 0; j <= 5; j++)
			{
				game[i][j] = ' ';
				if(i != j) game[i][j] = getResult(teamSkill[i], teamHomeAdvantage[j], teamSkill[j], tieRatio);
			}
		
			if ((realResults == 'y') || (realResults == 'Y'))
			{
				//game[home][away] = 'home team result'
				
				//****ROUND 1****
				game[1][0] = 'W'; // Mexico 		   	@ US
				game[2][5] = 'W'; // Trinidad & Tobago 	@ Jamaica
				game[3][4] = 'T'; // Honduras 		   	@ Costa Rica
				
				//****ROUND 2****
				game[0][2] = 'W'; // Jamaica 		   	@ Mexico
				game[4][1] = 'L'; // US	    		   	@ Honduras
				game[3][5] = 'W'; // Trinidad & Tobago  @ Costa Rica
	
/*				//****ROUND 3**** 04/25/01
				game[5][0] = 'L'; // Mexico 		    @ Trinidad & Tobago
				game[1][3] = 'T'; // Costa Rica 		@ US
				game[2][4] = 'L'; // Honduras			@ Jamaica
				
				
				//****ROUND 4**** 06/16/01
				game[0][3] = 'W'; // Costa Rica 	    @ Mexico
				game[2][1] = 'L'; // United States 	    @ Jamaica
				game[5][4] = 'L'; // Honduras 		    @ Trinidad & Tobago
				

				//****ROUND 5**** 06/20/01
				game[4][0] = 'L'; // Mexico 		    @ Honduras
				game[1][5] = 'W'; // Trinidad & Tobago  @ US
				game[3][2] = 'W'; // Jamaica 		    @ Costa Rica
				
				
				//****ROUND 6**** 06/30/01
				game[4][3] = 'L'; // Costa Rica 	    @ Honduras
				game[5][2] = 'L'; // Jamaica 		    @ Trinidad & Tobago
				game[0][1] = 'W'; // United States	    @ Mexico
							

				//****ROUND 7**** 09/01/01
				game[1][4] = 'W'; // Honduras		    @ United States
				game[2][0] = 'T'; // Mexico		  	    @ Jamaica
				game[5][3] = 'W'; // Costa Rica 	    @ Trinidad & Tobago
				

				//****ROUND 8**** 09/01/01
				game[4][2] = 'W'; // Jamaica 		    @ Honduras
				game[0][5] = 'W'; // Trinidad & Tobago  @ Mexico 
				game[3][1] = 'T'; // United States      @ Costa Rica 
				

				//****ROUND 9**** 10/07/01
				game[4][5] = 'W'; // Trinidad & Tobago  @ Honduras 
				game[3][0] = 'L'; // Mexico             @ Costa Rica 
				game[1][2] = 'W'; // Jamaica            @ United States 
				

				//****ROUND 10**** 11/11/01
				game[0][4] = 'W'; // Honduras           @ Mexico 
				game[2][3] = 'T'; // Costa Rica         @ Jamaica 
				game[5][1] = 'T'; // United States      @ Trinidad & Tobago*/ 
				
			}
		
			for (int i = 0; i <= 5; i++)
				for (int j = 0; j <= 5; j++)
				{
					if (game[i][j] == 'W') teamPoints[i] = teamPoints[i] + 3;
					if (game[i][j] == 'T') teamPoints[i] = teamPoints[i] + 1;

					if (game[i][j] == 'L') teamPoints[j] = teamPoints[j] + 3;
					if (game[i][j] == 'T') teamPoints[j] = teamPoints[j] + 1;
				}

		//outputPoints(teamPoints, max);
		//-----------------------------------------------
		int place[5], team[5], temp;
		
		for (int i = 0; i < 6; i++)
		{
			place[i] = teamPoints[i];
			team[i] = i;
		}
		
		for (int i = 5; i>=0; i--)
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
		
		if 		(i == 5)		cout << endl;
		if 		(team[i] == 1)	cout << "US         ";
		else if (team[i] == 2)	cout << "Jamaica    ";
		else if (team[i] == 3)	cout << "Costa Rica ";
		else if (team[i] == 4)	cout << "Honduras   ";
		else if (team[i] == 5)	cout << "T&T        ";
		else 					cout << "Mexico     ";

		cout << " - " << teamPoints[i];
	
		if (i == 3)				cout << "\n----------------";
		}
		//-----------------------------------------------

		outputTable(game);
		cout << "\n\nWould you like to run the program again? (y/n): ";
		cin >> repeat;
		
		if ((repeat == 'y') || (repeat == 'Y'))
			cout << "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
	
	} while ((repeat == 'y') || (repeat == 'Y'));
	
	cout << "\n\nType something to exit: ";
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

void outputTable(char game[][6])
{
	cout << "\n\n\t\tMex\tUS\tJam\tCR\tHon\tT&T";
	for (int i = 0; i <= 5; i++)
	{
		if (i == 0) cout << "\nMexico\t";
		if (i == 1) cout << "\nUS\t";		
		if (i == 2) cout << "\nJamaica\t";		
		if (i == 3) cout << "\nCosta Rica";		
		if (i == 4) cout << "\nHonduras";
		if (i == 5) cout << "\nT&T\t  ";

		for (int j = 0; j <= 5; j++) cout << "\t" << game[i][j];
	}
}

void outputPoints(int points[], int max)
{	
	for (int i = 0; i <= 5; i++)
	{
		if (i == 0) cout << "\nMexico's points\t  - ";
		if (i == 1) cout << "\nUS points\t  - ";		
		if (i == 2) cout << "\nJamaica points\t  - ";		
		if (i == 3) cout << "\nCosta Rica Points - ";		
		if (i == 4) cout << "\nHonduras Points\t  - ";
		if (i == 5) cout << "\nT & T points\t  - ";
		cout << " " << points[i] / max;
	}
}