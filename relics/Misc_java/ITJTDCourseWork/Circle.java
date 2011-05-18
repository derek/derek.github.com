import java.awt.*;
public class Circle extends Shape{
    private int radius;
    public Circle(int radius){
        this.radius = radius;
    }
    public void draw(Graphics g){
        g.drawOval(x, y, 2*radius, 2*radius);
    }
}
