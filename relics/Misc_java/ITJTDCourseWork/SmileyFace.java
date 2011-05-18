import java.awt.*;
import java.applet.*;
public class SmileyFace extends Applet{
    public void init()
    {
    
    }
    
    public void paint(Graphics g)
    {
        drawShapes(g);
    }
    
    private void drawShapes(Graphics g)
    {

		g.setColor(Color.yellow);
		g.fillOval(0, 0, 400, 400);
		
		g.setColor(Color.black);
		g.fillCircle(99, 99, 99, 99);

    }
}
