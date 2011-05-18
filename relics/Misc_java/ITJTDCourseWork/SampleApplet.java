import java.awt.*;
import java.applet.*;
public class SampleApplet extends Applet{
    public void init(){
        //Enter code called once when Applet is created
        System.out.println("SampleApplet.init() executed");
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.red);
        g.drawLine(0,0,100,50);
        g.drawOval(100,100,30,30);
        g.drawRect(100,100,30,40);
        g.fillOval(200,200,30,30);
    }
    public void start(){
        //Enter code run when the browser starts the Applet
    }
    public void stop(){
        //Enter code run when the browser stops the Applet
    }
}
