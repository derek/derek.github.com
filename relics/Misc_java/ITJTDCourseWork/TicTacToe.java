import java.awt.*;        //Required for Frame()
import java.awt.event.*;
import java.applet.*;
import java.lang.*;
/**
* This class implements the game of tictactoe as an array of buttons. 
* Button indexes map to the array as follows:
*0|1|2
*-----
*            3|4|5
*            -----
*            6|7|8
*/
public class TicTacToe extends Applet implements ActionListener{
//.....................................Class Scope Variables...................................
    private Button[] buttons;
    int cWins, pWins;        //Computer wins ... Player wins
//..................................Overridden Methods of Applet...............................
    public void init() {
        initGUI();
    }
//........................................Event handling.......................................
    /* The actionPerformed() method is called whenever an ActionEvent source that the TicTacToeE
     * is registered as a listener with issues an event. It contains the main logic for the 
     * game.
     */
    public void actionPerformed(ActionEvent evt){
        Button b = (Button)(evt.getSource());        //Which button was pressed
        int index = getIndex(b);
        if(b.getLabel().equals("")){        //Check for legal move and update GUI
            b.setLabel("X");
        }
        else{
            System.out.println("Illegal Move");
            return;
        }
        if(win("X")){        //Check for player win
            pWins++;
            System.out.println("You Win! ... Win #" + pWins);
            for(int i = 0; i < buttons.length; i++) buttons[i].setLabel("");        //Reset GUI
        }
        computerMove("O");        //Make computer move
        if(win("O")){        //Check for computerPlayer win
            cWins++;
            System.out.println("Computer Wins" + cWins);
            for(int i = 0; i < buttons.length; i++) buttons[i].setLabel("");        //Reset GUI
        }
    }
//........................................Custom Methods.......................................
    /* The computerMove() method decides where the computer will move. In this case, we
     * simply choose random squares until we find one that is empty. Try modifying this method
     * to give the computer more "intelligent" behavior.
     */
    private void computerMove(String s){
        int i, counter = 0;
        while(counter < 50){
            i = (int)(Math.random()*8);
            if(buttons[i].getLabel().equals("")){
                buttons[i].setLabel(s);
                return;
            }
            counter++;
        }
        System.out.println("Counter Timed Out");
    }
    /* the getIndex() method takes a Button object as an argument and returns the index of that 
     * button in the buttons[] array. If the Button is not found, -1 is returned.
     */
    private int getIndex(Button b){
        for(int i = 0; i < buttons.length; i++){
            if(b == buttons[i]){
                return i;
            }
        }
        return -1;
    }
    /* The initGUI() method sets up the graphical user interface for this applet.
     */
    private void initGUI(){
        buttons = new Button[9];
        setLayout(new GridLayout(3,3));        //Replace default FlowLayout
        for(int i = 0; i < 9; i++){
            buttons[i] = new Button();        //Create Button and add it to the array
            add(buttons[i]);        //add the Button to the GUI
            buttons[i].addActionListener(this);        //Add TicTacToeE as a listener for Button Action events
        }
    }
    /* the win() method takes a string as an argument and checks to see if the 
     * player with that String on their Button has won the game.
     */
    private boolean win(String s){
        int counter;
        //Check for horizontal win
        for(int i = 0; i < 3; i ++){        //increment through rows
            counter = 0;
            for(int j = 0; j < 3; j++){        //increment through squares in a row
                if(buttons[i*3 + j].getLabel().equals(s)) counter++;
                else break;
            }
            if(counter == 3) return true;
        }
        //Check for vertical win
        for(int i = 0; i < 3; i ++){        //increment through columns
            counter = 0;
            for(int j = 0; j < 3; j++){        //increment through squares in a column
                if(buttons[i + j*3].getLabel().equals(s)) counter++;
                else break;
            }
            if(counter == 3) return true;
        }        
        //Check for diagonal win
        if(buttons[0].getLabel().equals(s) &&
        buttons[4].getLabel().equals(s) &&
        buttons[8].getLabel().equals(s)) return true;
        if(buttons[2].getLabel().equals(s) &&
        buttons[4].getLabel().equals(s) &&
        buttons[6].getLabel().equals(s)) return true;
        return false;
    }
}
