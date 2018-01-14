/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.MyDragDropListener;
import java.awt.dnd.DropTarget;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.color.*;
import java.awt.Point;
import model.PopUpRotationListener;
/**
 *
 * @author Timothy
 */
public class PopUpRotation extends javax.swing.JFrame 
{
    private static final long serialVersionUID = 1L;
    private JLabel myJLabel;
    private Boolean isMain = false;
    public static int numberOfFrame = 0;
    public  int numberOfContact = 0;
    public PopUpRotationListener  popUpListener;
    public int currentAngle = 0;
    
    public PopUpRotation(int x, int y, int type) 
    {
    super("Drag and drop test");
    // Set the size


    // Create the label
    myJLabel = new JLabel("Drag something here!", (int) CENTER_ALIGNMENT);

   // myJLabel.setBackground(new Color(78, 198,233));
    // Create the drag and drop listener
    popUpListener = new PopUpRotationListener();
    
    // Connect the label with a drag and drop listener
    new DropTarget(myJLabel, popUpListener);
    popUpListener.types = type;
   // // Add the label to the content
    this.getContentPane().add(BorderLayout.CENTER, myJLabel);
    this.getContentPane().setBackground(new Color(100, 100,100,20));
    // Show the frame
    this.setLocation(x,y);
    this.setSize(120, 120);
    this.toFront();
    this.setUndecorated(true);
    this.setBackground(new Color(200, 200,200,50));
   
    this.setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
    this.setAlwaysOnTop(true);
    
  
    }
    
  
}
