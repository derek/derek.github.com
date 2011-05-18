import java.awt.*;
import java.applet.*;
public class ComponentExample extends Applet
{
    public void init()
    {
        componentElements();
        setLayout(null);
        cbgExample();
        dialogExample();
        cursorExample();
    }
    
    private void componentElements()
    {
        Label label = new Label("I am a Label Component");
        Button button = new Button("I am a Button component");
        TextField tf = new TextField("I am a textField Component");
        setLayout(null);
        label.setBounds(100,100,150,20);
        button.setBounds(250,100, 140,20);
        tf.setBounds(100, 150, 200, 20);
        add(label);
        add(button);
        add(tf);
        label.setVisible(true);
        button.setVisible(true);
        tf.setVisible(true);
    }
    
    private void cbgExample()
    {
    	Checkbox[] cb = new Checkbox[5];
	    CheckboxGroup cbg = new CheckboxGroup();
    	for(int i = 0; i < 5; i++)	
    	{
	        cb[i] = new Checkbox("Box #" + i, cbg, false);
    	    add(cb[i]);
	        cb[i].setBounds(75*i + 10, 10, 60, 20);
    	    cb[i].setVisible(true);
	    }
	}
    
    private void dialogExample()
    {
    	Button b = new Button("I am a Button");
	    Dialog dialog = new Dialog(new Frame());
    	dialog.add(b);
	    dialog.show(true);
	}
	
	private void cursorExample()
	{
    	setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
}
