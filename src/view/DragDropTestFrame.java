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
/**
 *
 * @author Timothy
 */
public class DragDropTestFrame extends javax.swing.JFrame {
private static final long serialVersionUID = 1L;
private JLabel myLabel;
private Boolean isMain = false;
public static int numberOfFrame = 0;
   

public DragDropTestFrame() 
{
    
    // Set the frame title
    super("Drag and drop test");
    // Set the size


    // Create the label
    myLabel = new JLabel("Drag something here!", SwingConstants.CENTER);
    
    

    // Create the drag and drop listener
    MyDragDropListener myDragDropListener = new MyDragDropListener(true);

    // Connect the label with a drag and drop listener
    new DropTarget(myLabel, myDragDropListener);

    // Add the label to the content
    this.getContentPane().add(BorderLayout.CENTER, myLabel);

    // Show the frame
    this.setVisible(false);
    

}

public void visible(Boolean state)
{
    this.setVisible(state);
    
}

public void setMessage(String text)
{
    myLabel.setText(text);
}


public void setPopUpType(Boolean state)
{
    if (state == true)
    {
        numberOfFrame = 0;
        this.setSize(250, 250);
        this.toFront();
        this.setAlwaysOnTop(true);
        myLabel.setLocation(0,0);
    }
    else
    {
        this.setSize(120, 120 );
        this.setLocation(300 ,150 * numberOfFrame);
      
        this.toFront();
      this.setUndecorated(true);
        this.setShape(new RoundRectangle2D.Double(10, 10, 100, 100, 50, 50));
           this.setVisible(true);
        this.setAlwaysOnTop(true);
   // this.setSize(300, 200);
   //this.setVisible(true);
     
        numberOfFrame++;
        if (numberOfFrame > 2)
            numberOfFrame = 0;
       
    }
        
    
}

public void setTextFrame (String text)
{
    myLabel.setText(text);
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