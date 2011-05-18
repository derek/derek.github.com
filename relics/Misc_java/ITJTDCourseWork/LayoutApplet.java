import java.awt.*;
import java.applet.*;

public class LayoutApplet extends Applet
{
	public void init()
    {
    	//flow();
    	//border();
    	//grid();
    	nest();
    }

    private void flow()
    {
        int n = 10;                    //Number of Buttons to add
        Button[] buttons = new Button[n];
        for(int i = 0; i < n; i ++)
        {
            buttons[i] = new Button("#" + i);
            add(buttons[i]);
        }
    }
    
    private void border()
    {
        Button b1, b2, b3, b4, b5, b6;
        b1 = new Button("Button #1");
        b2 = new Button("Button #2");
        b3 = new Button("Button #3");
        b4 = new Button("Button #4");
        b5 = new Button("Button #5");
        b6 = new Button("Button #6");
        setLayout(new BorderLayout());
        add(b1);
        add(b2, "North");
        add(b3, "South");
        add(b4, "East");
        add(b5, "West");    
        add(b6);
    }
    
    private void grid()
    {
        int n = 10;        //Number of Buttons to add
        Button[] buttons = new Button[n];
        setLayout(new GridLayout(2,5));
        for(int i = 0; i < n; i ++){
            buttons[i] = new Button("#" + i);
            add(buttons[i]);
        }
    }
    
    private void nest(){
        int n = 10;
        Button[] buttons = new Button[n];
        Panel gridPanel = new Panel();
        gridPanel.setLayout(new GridLayout(5,2));
        setLayout(new BorderLayout());
        for(int i = 0; i < n; i ++){
            buttons[i] = new Button("#" + i);
            gridPanel.add(buttons[i]);
        }
        add(gridPanel, "West");
    }
}
