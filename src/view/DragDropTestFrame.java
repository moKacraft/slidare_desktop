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
        this.setSize(185, 185);
        this.toFront();
        this.setUndecorated(true);
        this.setShape(new Ellipse2D.Double(0,0,getWidth(),getHeight()));
        this.setAlwaysOnTop(true);
        
    }
    else
    {
      this.setSize(120, 120 );
      this.setLocation(150 * width ,150 * (numberOfFrame % 5));
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