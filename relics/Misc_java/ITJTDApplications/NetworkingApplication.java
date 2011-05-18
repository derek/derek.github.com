package ITJTDApplications;
import java.net.*;
import java.io.*;
public class NetworkingApplication {
    static public void main(String[] args){
        new NetworkingApplication();
    }
    public NetworkingApplication() {
        //catchSomethin();
        getPage();
    }
    private int count(String toCount, String countFrom){
        int index = 0;
        int n = 0;
        while((index = countFrom.indexOf(toCount, index)) > -1){
            n++;
            index = index + toCount.length();
        }
        return n;
    }
    private void catchSomethin(){
        try{
            String s = null;
            System.out.println(s.length());
        }
        catch(Exception e){
            System.out.println("The exception " + e + " was thrown when the program tried to access s.length()");
        }
        System.out.println("I have survived the null pointer exception");
    }
    private void getPage(){
        String urlString = "http://www.espn.com";
        String line;
        try{
            URL url = new URL(urlString);
            BufferedReader bin = new BufferedReader( new InputStreamReader(url.openStream()));
            while((line = bin.readLine()) != null){
                System.out.println(line);
            }
            bin.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
