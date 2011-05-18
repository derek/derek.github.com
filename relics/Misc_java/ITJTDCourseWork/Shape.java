import java.awt.*;
public abstract class Shape{
    protected int x, y;    //Base point of the shape
    public abstract void draw(Graphics g);
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
}
