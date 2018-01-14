/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

public class DragDropGroupFrame extends javax.swing.JFrame {
private static final long serialVersionUID = 1L;
private JLabel myJLabel;
private List<DragDropContactFrame> listNames;
private List<String> listEmailAddresses;
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

public DragDropBoundaries Boundaries;
//private Object cfg;



public DragDropGroupFrame(int type) 
{
 /*Circle circle = new Circle();
circle.setCenterX(100.0f);
circle.setCenterY(100.0f);
circle.setRadius(50.0f); */  
    // Set the frame title
    super("Drag and drop test");
    // Set the size
  
    //state = type;
    // Create the label
    myJLabel = new JLabel("Drag something here!", (int) CENTER_ALIGNMENT);

   // myJLabel.setBackground(new Color(78, 198,233));
    // Create the drag and drop listener
    myDragDropListener = new MyDragDropListener(true, type);
    // Connect the label with a drag and drop listener
    new DropTarget(myJLabel, myDragDropListener);
    if (type == 1)
    {
      groups = new ArrayList<GroupInfo>() ;
      listEmailAddresses = new ArrayList<String>();
      listNames = new ArrayList<DragDropContactFrame>();

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

    }       
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
    Boundaries.setVisible(state);
    
   
}

public void setMessage(String text)
{
    myJLabel.setText(text);
}

public void setPeopleInGroup(String names)
{
    DragDropContactFrame tmpDDCF = new DragDropContactFrame(names);
    tmpDDCF.setPopUpType(200,100);
    tmpDDCF.visible(false);
    listNames.add(tmpDDCF);       
}



public void setNumberOfContact(int nb)
{
    numberOfContact = nb;
    numberOfFrame = nb;
}

public void hideContacts()
{
    System.out.println("herenot");
     for (int inc = 0;inc < listNames.size() ; ++inc)
        {
             //listNames.get(inc).setLocation((int)position.x, (int)position.y + 100 * (inc +1));
             listNames.get(inc).visible(false);
        }  
}

public void  setDragDropBoundaries()
{
   Boundaries = new DragDropBoundaries("");
   
   Boundaries.setPopUpType(100, 100 + listNames.size() * 100);
   Boundaries.setLocation((int)position.x, (int) position.y);
}

public void SetContactsPosition()
{
     for (int inc = 0;inc < listNames.size() ; ++inc)
        {
             listNames.get(inc).setLocation((int)position.x, (int)position.y + 100 * (inc +1));
             listNames.get(inc).visible(true);
             Boundaries.setLocation((int)position.x, (int) position.y);
        }
     
}

public  Point.Double rotation(Point.Double p, double theta) {
    

    currentAngle = Math.toDegrees(theta) % 360;
   /* if (currentAngle < 0)
        currentAngle = (currentAngle + 360) % 360; */

    System.out.println("currentAngle:" + currentAngle);
    Double x = p.x;
    Double y = p.y;
    Double cx = 15.0 + 75.0;
    Double cy = 15.0 + 75.0;
    Double xrot = Math.cos(theta) * (x-cx) - Math.sin(theta) * (y - cy) + cx;
    Double yrot = Math.sin(theta) * (x-cx) + Math.cos(theta) * (y-cy) + cy;
    p  = new Point.Double(xrot, yrot);
    System.out.println("Point:" + p);
    return (p);
}

public void setPosition(Point.Double p)
{
    this.setLocation((int)p.x,(int) p.y);
    position = new Point.Double(p.x, p.y);
}

public void determineStateOfVisibility()
{
    
    if (position != null && position.x <= 10 || position.y <= 10)
    {
        this.setVisible(false);
         for (int inc = 0;inc < listNames.size() ; ++inc)
         {
             listNames.get(inc).setVisible(false);
         }
    
    }
    else
    {
       this.setVisible(true);
       /*myJLabel1.setVisible(true);
       myJLabel1.setSize(50, 50 );
       myJLabel1.setLocation((int)position.x, (int)position.y + 100);
       myJLabel2.setVisible(true);
       myJLabel2.setSize(50, 50 );
       myJLabel2.setLocation((int)position.x, (int)position.y + 200);
       myJLabel3.setVisible(true);
       myJLabel3.setSize(50, 50 );
       myJLabel3.setLocation((int)position.x, (int)position.y + 300);
*/
     //  myJLabel1.setShape(new Ellipse2D.Double(0,0,50,50));
    }
}

public void MoveObjectsToPosition()
{
    if (position.x <= 10 || position.y <= 10)
    {
        this.setVisible(false);
    }
    else
    {
        this.setVisible(true);  
    }
}


public void setPopUpType(int state)
{
    if (TrackingInfo.connect == false)
    {
        System.out.println("RETUNED");   
        return;
    }
    ID += 1;
    //System.out.println("Pssed");
    if (state == 0)
    {   
        System.out.println("pop");
        this.setLocation(15,15);
        this.setBackground(new Color(78, 198,233));
        this.setSize(190, 190);
        this.toFront();
        this.setUndecorated(true);
        this.setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
        this.setAlwaysOnTop(true);
    }
    if (state == 1) /* Group*/
    {
      System.out.print("hahah");
      this.setSize(120, 120 );
      this.setLocation(300 ,60 /** (numberOfFrame % 5)*/);
      System.out.print("initial Location:" +this.getLocation());
      position = new Point.Double(this.getLocation().x, this.getLocation().y);
      Point.Double pd = this.rotation(new Point.Double(this.getLocation().x,this.getLocation().y), Math.toRadians(35 * (numberOfFrame)));
      this.setLocation((int)(pd.x) - 60,(int)(pd.y) - 60);
      position = new Point.Double(this.getLocation().x, this.getLocation().y);
     
      
      this.setBackground(new Color(78, 198,233));
      this.toFront();
      this.setUndecorated(true);
     // this.setShape(new RoundRectangle2D.Double(10, 10, 100, 100, 50, 50));
   
    this.setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
   // this.set
     
    this.setVisible(true);
    this.setAlwaysOnTop(true);
   // this.setSize(300, 200);
   //this.setVisible(true);
    }
        
    if (state == 4)
    {      
        

   // myJLabel.setBackground(new Color(78, 198,233));
    // Create the drag and drop listener
   
    // Connect the label with a drag and drop listener
    
     /* myJLabel1.setSize(50, 50 );
      myJLabel1.setLocation((int)position.x, (int)position.y + 100);
      myJLabel2.setSize(50, 50 );
      myJLabel2.setLocation((int)position.x, (int)position.y + 200);     
      myJLabel3.setSize(50, 50 );
      myJLabel3.setLocation((int)position.x, (int)position.y + 300);*/
            

    //System.out.print("initial Location:" +this.getLocation());
      //position = new Point.Double(this.getLocation().x, this.getLocation().y);
      ///Point.Double pd = this.rotation(new Point.Double(this.getLocation().x,this.getLocation().y), Math.toRadians(35 * (numberOfFrame)));
      //this.setLocation((int)(pd.x) - 60,(int)(pd.y) - 60);
      //position = new Point.Double(this.getLocation().x, this.getLocation().y);
      //currentAngle = 35 * (numberOfFrame);
      //this.rotat rotation
     // myJLabel1.setBackground(new Color(78, 198,233));
      //myJLabel2.setBackground(new Color(78, 198,233));
      //myJLabel3.setBackground(new Color(78, 198,233));
      
        //this.setShape(new RoundRectangle2D.Double(0,0,getWidth(),getHeight()),50,50);
   // this.set
     
    //this.setVisible(true);
    this.setAlwaysOnTop(true);
    }
}

public void setTextFrame (String text)
{
    myJLabel.setText(text);
}


public void setVisibleFrame (boolean state)
{
    this.setVisible   (state);
    if (state == false)
    {
      //  MainView.mainView.HideMiniPopUp();
    }
}

}