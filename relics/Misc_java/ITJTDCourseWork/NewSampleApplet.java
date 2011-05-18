import java.awt.*;
import java.applet.*;
import java.util.*;


public class FundamentalsApplet extends Applet
{
    public void init()
    {
    	arrayPlay(); 
    	scopePlay();
    }
    
    private void arrayPlay()
    {
    	int[] array1;
	    array1 = new int[4];
    	array1[0] = 4;
	    array1[1] = 3;
    	array1[2] = 2;
	    array1[3] = 1;
    	//System.out.println(array1[4]);    //Note: error
	    System.out.println(array1[0] + array1[3]);
    	int[] array2 = {1, 2, 3, 4};
	    System.out.println(array2.length);
	}
	
	int i = 1;
    
    private void scopePlay()
    {
        int i = 5;
        System.out.println(i);
        System.out.println(this.i);    //notice "this" keyword
    }	

}

public class Lesson4Applet extends Applet
{
    public void init()
    {
    forLoopin2();
    whileLoopin(250); 
    branchin();
    }

    private void forLoopin(){
    for(int i = 0; i < 100; i ++){
        System.out.println(i);
	    }
	}
	
	private void forLoopin2(){
    String[] s = {"one", "two", "three", "four", "five"};
    for(int i = 0; i < s.length; i++){
        System.out.println(s[i]);
    	}
	}
	
	private void whileLoopin(long pause){
    //Returns the current time in milliseconds
    long l = new Date().getTime();
    long delta = new Date().getTime() - l;
    while(delta < pause){
        delta = new Date().getTime() - l;
        System.out.println("time elapsed (ms): " + delta);
    	}
	}
	
	private void branchin(){
    int i = 10;
    String name = "Fred";
    String occupation = null;
    if(name == "Fred" && occupation == "Manager") 
        System.out.println("Fred is a manager");
    if(name == "Fred" || occupation .equals("Manager")) 
        System.out.println("Fred or a manager");
    if(i > 9){
        System.out.println(i + " is greater than 9");
    }
    else if(i < 9) System.out.println(i + " is less than 9 ");
    else System.out.println("We assume that " + i + " equals 10");
	}

}
