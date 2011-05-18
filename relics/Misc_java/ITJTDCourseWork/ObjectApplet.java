import java.awt.*;
import java.applet.*;


public class ObjectApplet extends Applet
{
	private void simplePlay(){
        SimpleClass s = new SimpleClass(5);
    }
    public void paint(Graphics g){
        Circle c = new Circle(100);
        c.setX(130);
        c.setY(160);
        c.draw(g);
    }
}