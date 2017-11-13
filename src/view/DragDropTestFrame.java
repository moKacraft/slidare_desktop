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

import javafx.scene.shape.Circle;
import model.TrackingInfo;
import service.ConfigManager;
import service.ServiceFactory;
/**
 *
 * @author Timothy
 */
public class DragDropTestFrame extends javax.swing.JFrame {
private static final long serialVersionUID = 1L;
private JLabel myJLabel;
private Boolean isMain = false;
public static int numberOfFrame = 0;
public  int numberOfContact = 0;
public MyDragDropListener  myDragDropListener;
public double currentAngle = 0.0;
public Point.Double position;
//private Object cfg;



public DragDropTestFrame() 
{
 /*Circle circle = new Circle();
circle.setCenterX(100.0f);
circle.setCenterY(100.0f);
circle.setRadius(50.0f); */  
    // Set the frame title
    super("Drag and drop test");
    // Set the size


    // Create the label
    myJLabel = new JLabel("Drag something here!", (int) CENTER_ALIGNMENT);

   // myJLabel.setBackground(new Color(78, 198,233));
    // Create the drag and drop listener
    myDragDropListener = new MyDragDropListener(true);

    // Connect the label with a drag and drop listener
    new DropTarget(myJLabel, myDragDropListener);

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
    myJLabel.setText(text);
}

public void setNumberOfContact(int nb)
{
    
    numberOfContact = nb;
    numberOfFrame = nb;
}

public  Point.Double rotation(Point.Double p, double theta) {
    

    currentAngle = Math.toDegrees(theta) % 360;
   /* if (currentAngle < 0)
        currentAngle = (currentAngle + 360) % 360; */

    System.out.println("currentAngle:" + currentAngle);
    Double x = position.x;
    Double y = position.y;
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
}

public void determineStateOfVisibility()
{
    
    if (currentAngle >= 0 && currentAngle <= 105)
    {
        this.setVisible(true);
    }
    else
    {
        this.setVisible(false);     
    }
}

public void setPopUpType(Boolean state, int width)
{
    if (TrackingInfo.connect == false)
    {
    System.out.println("RETUNED");
        
        return;
    }
    System.out.println("PAssed");
    if (state == true)
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
    else
    {
      this.setSize(120, 120 );
      this.setLocation(150 * width,60 /** (numberOfFrame % 5)*/);
      System.out.print("initial Location:" +this.getLocation());
      position = new Point.Double(this.getLocation().x, this.getLocation().y);
      Point.Double pd = this.rotation(new Point.Double(this.getLocation().x,this.getLocation().y), Math.toRadians(35 * (numberOfFrame)));
      this.setLocation((int)(pd.x) - 60,(int)(pd.y) - 60);
      position = new Point.Double(this.getLocation().x, this.getLocation().y);
      //currentAngle = 35 * (numberOfFrame);
      //this.rotat rotation
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