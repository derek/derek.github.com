import java.awt.*;
import java.applet.*;
/*import java.net.*;
import java.util.*;
import java.lang.*;*/
public class Smiley extends Applet{
    public void init()
    {
    	/*try
    	{
        	InetAddressgetLocalHost();// throws UnknownHostException;
		} 
		
		catch (UnknownHostException e) 
		{
    		System.err.println("Caught ArrayIndexOutOfBoundsException:" + e.getMessage());
    	}*/
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
		g.fillOval(100, 100, 10, 10);
		g.fillOval(300, 100, 10, 10);

	    for (int x = 1; x<400; x++)
		    //drawArc(x,   y,  width, height, startAngle, arcAngle)
			g.drawArc(100, 240, 200,   100,     -145,       120);
			

    }
    
/*    public static int InetAddressgetLocalHost()
                                throws UnknownHostException{
    	 //String s = InetAddress.toString();
    	//System.out.println(InetAddress.getAddress());
    	InetAddress.getByName("www.yahoo.com");
    return 0;
    	}*/

}
