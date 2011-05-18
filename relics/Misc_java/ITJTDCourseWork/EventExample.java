import java.applet.*;
import java.awt.event.*;
import java.awt.*;
public class EventExample extends Applet implements ActionListener{
    public void init()
    {
        Button b = new Button("Push");
        add(b);
        b.addActionListener(this);
    }
    public void actionPerformed(ActionEvent evt)
    {
        Color c = new Color((float)(Math.random()), (float)(Math.random()), (float)(Math.random()));
        System.out.println("Color changed");
        setBackground(c);
        repaint();
    }
}
