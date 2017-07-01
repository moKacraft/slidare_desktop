/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import view.DragDropTestFrame;
import java.awt.dnd.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import sun.applet.Main;

/**
 *
 * @author Timothy
 */
public class MyDragDropListener implements DropTargetListener{
 
     public Boolean MainPopUp = false;
     public static Boolean ActivatePopUps = false;
     public static int valueDragDrop = 0;
     public int concernedDragDrop;
     
    public MyDragDropListener(Boolean state)
    {
        MainPopUp = state;
        concernedDragDrop = valueDragDrop;
        ++valueDragDrop; 
    }
    
    
     @Override
    public void drop(DropTargetDropEvent event) {

      // if (MainPopUp = false)
        //   return;
        // Accept copy drops
        event.acceptDrop(DnDConstants.ACTION_COPY);

        // Get the transfer which can provide the dropped item data
        Transferable transferable = event.getTransferable();

        // Get the data formats of the dropped item
        DataFlavor[] flavors = transferable.getTransferDataFlavors();

        // Loop through the flavors
        for (DataFlavor flavor : flavors) {

            try {

                // If the drop items are files
                if (flavor.isFlavorJavaFileListType()) {

                    // Get all of the dropped files
                    List files = (List) transferable.getTransferData(flavor);

                    // Loop them through
                    for (Object file : files) {

                        // Print out the file path
                        System.out.println("File path is '" + file + "'.");
                         DragDropTracking.dragDropTracking.showPathText(file.toString());
                         
                    }

                }

            } catch (Exception e) {

                // Print out the error stack
                e.printStackTrace();

            }
        }

        // Inform that the drop is complete
        event.dropComplete(true);

    }

    @Override
    public void dragEnter(DropTargetDragEvent event) 
    {
        System.out.println("wwwwwwwwwwwwwww");
            
        if (concernedDragDrop == 0 && ActivatePopUps == false)
        {
            System.out.println("ooooooooooooo");
             ActivatePopUps = true;
             
             DragDropTracking.dragDropTracking.ShowMiniPopUp();
             //MainView.mainView.ShowMiniPopUp();
        }
    }

    @Override
    public void dragExit(DropTargetEvent event) 
    {
        if (concernedDragDrop == 0)
        {
            ActivatePopUps = false;
           
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent event) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
    }

}

