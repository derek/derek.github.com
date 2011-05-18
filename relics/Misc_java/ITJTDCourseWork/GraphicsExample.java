import java.awt.*;
import java.applet.*;
public class GraphicsExample extends Applet{
    public void init()
    {
    
    }
    
    public void paint(Graphics g)
    {
        drawShapes(g);
    }
    
    /* The drawShapes() method demonstrates the use of a number of the drawing methods in the 
    * Graphics class.
    */
    private void drawShapes(Graphics g)
    {
        int x1, y1, x2, y2;
        int x[] = {100, 300, 400, 200, 200};//array of x coordinates for polygon
        int y[] = {100, 100, 200, 400, 200};//array of y coordinates for polygon
        x1 = 15;
        y1 = 25;
        x2 = 200;
        y2 = 136;
        g.setColor(Color.blue);
        //Draw two lines
        g.drawLine(x1, y1, x2, y2);
        //Draw a rectangle
        g.drawRect(x1, y1, x2, y2);
        //Draw an oval
        g.drawOval(x1, y1, x2, y2);
        //Draw a polygon
        g.drawPolygon(x, y, 5);
        //Fill a polygon offset by 200 
        for(int i = 0; i < x.length; i ++) x[i] = x[i] + 200;
        g.fillPolygon(x, y, 5);
    }
}
