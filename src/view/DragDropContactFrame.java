/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.event.*;
import javax.swing.*;
import model.MyDragDropListener;
import java.awt.dnd.DropTarget;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.color.*;
import java.awt.Point;
import java.util.ArrayList;
import model.GroupInfo;

import javafx.scene.shape.Circle;
import model.GroupInfo;
import model.TrackingInfo;
import service.ConfigManager;
import service.ServiceFactory;
import java.util.List;
/**
 *
 * @author Timothy
 */

public class DragDropContactFrame extends javax.swing.JFrame {
private static final long serialVersionUID = 1L;
private JLabel myJLabel;

private List<JFrame> listNames;
private Boolean isMain = false;
public static int numberOfFrame = 0;
public static int ID = 0;
public  int numberOfContact = 0;
public MyDragDropListener  myDragDropListener;
public double currentAngle = 0.0;
public Point.Double position = new Point.Double(0,0);
public enum type {MASTER, GROUP, USER};
static public List<GroupInfo> groups;
public GroupInfo concernedGroupInfo;
public String email;
//private Object cfg;



public DragDropContactFrame(String name) 
{
 /*Circle circle = new Circle();
circle.setCenterX(100.0f);
circle.setCenterY(100.0f);
circle.setRadius(50.0f); */  
    // Set the frame title
    super("Drag and drop test");
    // Set the size
  

    // Create the label
    myJLabel = new JLabel("");
myJLabel.setVerticalTextPosition(JLabel.CENTER);
myJLabel.setHorizontalTextPosition(JLabel.LEFT);
   // myJLabel.setBackground(new Color(78, 198,233));
    // Create the drag and drop listener
    myDragDropListener = new MyDragDropListener(true, 2, name, null);
    // Connect the label with a drag and drop listener
    new DropTarget(myJLabel, myDragDropListener);
    setMessage(name);
    email = name;
     //myJLabel1 = new JFrame("Contact1"); /*("Contact1", (int) CENTER_ALIGNMENT);*/
     //myJLabel2 = new JFrame("Contact2");
     //myJLabel3 = new JFrame("Contact3");
     //myJLabel.setText("Contact1");
  /*  myJLabel1.getContentPane().add(BorderLayout.CENTER, myJLabel1);
    myJLabel2.getContentPane().add(BorderLayout.CENTER, myJLabel2);
    myJLabel3.getContentPane().add(BorderLayout.CENTER, myJLabel3);
     myJLabel2.getContentPane().setBackground(new Color(78, 198,233));
      myJLabel3.getContentPane().setBackground(new Color(78, 198,233));
     myJLabel1.getContentPane().setBackground(new Color(78, 198,233));*/

        
// Show the frame
    
   // // Add the label to the content
    this.getContentPane().add(BorderLayout.CENTER, myJLabel);
    this.getContentPane().setBackground(new Color(78, 198,233));
    // Show the frame
    this.setVisible(false);
}

public void visible(Boolean state)
{
    this.setVisible(state);
}

public void setMessage(String text)
{
    text =  text.replace("@", ".");
    String[] parts = text.split("\\.");
    parts[0] = "         " + parts[0];
    myJLabel.setText(parts[0]);
}





public void setPosition(Point.Double p)
{
    this.setLocation((int)p.x,(int) p.y + 10);
    position = new Point.Double(p.x, p.y);
}




public void setPopUpType(int sizex, int sizey)
{
   
      
      this.setBackground(new Color(78, 198,233));
      this.toFront();
      this.setUndecorated(true);
      this.setShape(new RoundRectangle2D.Double (10, 0, 100, 100, 20, 20));
      this.setSize(sizex,sizey);
    //this.setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
   // this.set
     
    this.setVisible(true);
    this.setAlwaysOnTop(true);
   // this.setSize(300, 200);
   //this.setVisible(true);
    }
        
}



