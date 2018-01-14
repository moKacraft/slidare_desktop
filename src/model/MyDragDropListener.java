/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Main;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import utils.security.encryption;
import view.DragDropGroupFrame;

/**
 *
 * @author Timothy
 */
public class MyDragDropListener implements DropTargetListener
{
 
    public Boolean MainPopUp = false;
    public static Boolean ActivateMiniPopUps = false;
     public static Boolean ActivatePopUps = false;
    public static int valueDragDrop = 0;
    public int concernedDragDrop;
    public int type;
    public Boolean canDeactivate1 =false;
  
    public Boolean canDeactivate2 =false;
    public static int groupType = 0;
    public int concernedGroupType = 0;
    private static Boolean leftBoundaries = false;
    public String email;
    public DragDropGroupFrame group;
    // public List<JSONObject> listContacts = new ArrayList<JSONObject>();
    
    public MyDragDropListener(Boolean state, int _type, String _email, DragDropGroupFrame _group)
    {
        MainPopUp = state;
        concernedDragDrop = valueDragDrop;
        ++valueDragDrop;
         type = _type;
         if (type == 1)
         {
             concernedGroupType = groupType;
             ++groupType;
             group = _group;
             
         }
     email = _email;
    }
    
    
    public void sendPackage(DropTargetDropEvent event, String _email,  String[] users)
    {
        if (type == 3)
            return;
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File f = new File(file.toString());
                                
                                System.out.println(f.getName());
                                System.out.println("File path inside is '" + file + "'.");
                               
                                //users[0] =  /*(String)(DragDropTracking.listGroups.get(concernedDragDrop - 1).get("email"))*/_email;
                                
                                System.out.println("users[0]" + users[0] + "\nf.getname " + f.getName());
                                
//                                if (users[0].equals("juju@gmail.com") || users[0].equals("testbeta@gmail.com") || users[0].equals("testssss@gmail.com")) {
//                                   Main.socket.emit("request file transfer", f.getName(), file, users, file.toString(), f.getName(),"", "","", "", f.length());
//                                    return;
//                                }
                                
                                try {
                                    encryption _crypt = new  encryption();
                                    
                                    String key =  encryption.SHA256("my secret key", 32);
//                                    String key =  new String("12341234123412341234123412341234");

                                    Main.dialogSend.setVisible(true);
                                    Main.labelSend.setText("Encrypting file...");
                                    
                                    _crypt.encryptFile(file.toString(), _crypt.generateRandomString(), key);

                                    System.out.println("Salt: " + _crypt.get_fileSalt() + " length: " + _crypt.get_fileSalt().length +
                                            "\nIV: " + _crypt.get_fileIV() + " length: " + _crypt.get_fileIV().length);
                                    
                                    byte[] salt = _crypt.get_fileSalt();
                                    System.out.println(salt[0] + " " + salt[1] + " " + salt[2] + " " + salt[3] + " " + salt[4] + " " + salt[5] + " " + salt[6] + " " + salt[7]);

                                    Main.labelSend.setText("Sending file...");
                                    Main.socket.emit("request file transfer", f.getName(),
                                    file, users, _crypt.get_fileEncryptedName(), f.getName(),
                                    _crypt.get_fileSHA1(), Base64.encodeBase64String(_crypt.get_fileSalt()),
                                    Base64.encodeBase64String(_crypt.get_fileIV()), _crypt.get_fileKey(), f.length());
                                } catch (NoSuchAlgorithmException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (NoSuchPaddingException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (UnsupportedEncodingException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InvalidKeySpecException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InvalidKeyException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InvalidParameterSpecException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (BadPaddingException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalBlockSizeException ex) {
                                    Logger.getLogger(MyDragDropListener.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }).start();
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
          ActivatePopUps = false;
          ActivateMiniPopUps = false;

            //DragDropTracking.dragDropTracking.HideGroupMiniPopUp();
          DragDropTracking.dragDropTracking.ShowGroupMiniPopUp();
          DragDropTracking.dragDropTracking.HideMiniPopUp();
    }
    
    
    @Override
    public void drop(DropTargetDropEvent event) 
    {
        if (type == 2)
        {
          String[] users = new String[1];
          users[0] =  /*(String)(DragDropTracking.listGroups.get(concernedDragDrop - 1).get("email"))*/email;
          sendPackage(event, email, users);
        }
         if (type == 1 && group.listEmailAddresses != null)
        { 
             String[] users = new String[group.listEmailAddresses.size()];
            for (int inc = 0;inc < group.listEmailAddresses.size() ; ++inc)
        {
            users[inc] = group.listEmailAddresses.get(inc);
            
             //listNames.get(inc).setLocation((int)position.x, (int)position.y + 100 * (inc +1));
            
        }  
            sendPackage(event, email,users);
        }
        
    }
    
    @Override
    public void dragEnter(DropTargetDragEvent event)
    {
        System.out.println("wwwwwwwwwwwwwww");
        
        if (type ==0 && ActivatePopUps == false) {
            System.out.println("heyeheyhey");
            ActivatePopUps = true;
            DragDropTracking.dragDropTracking.ShowGroupMiniPopUp();
            //MainView.mainView.ShowMiniPopUp();
        }
        if (type == 1 && ActivateMiniPopUps == false)
        {
            System.out.println("ooooooooooooo");
            DragDropTracking.dragDropTracking.ShowMiniPopUp(concernedGroupType);
            
            //DragDropTracking.dragDropTracking.
            //MainView.mainView.ShowMiniPopUp();
        }
         if (type == 2)
         {
            System.out.println("cccccccc");
            ActivateMiniPopUps = true;
             
         }
           if (type == 3)
           {
             leftBoundaries = false;  
           }
   
    }
    
    @Override
    public void dragExit(DropTargetEvent event)
    {
        if (type == 0 && concernedDragDrop == 0) 
        {
             System.out.println("11111");
            ActivatePopUps = false;
            //DragDropTracking.dragDropTracking.HideGroupMiniPopUp();
        }
        if (type == 2)
        {
         leftBoundaries = true;
        }
         if (type == 1)
        {
         leftBoundaries = true;
        }
        if (type == 3 && leftBoundaries == false) 
        {
            System.out.println("2222");
           ActivatePopUps = false;
            ActivateMiniPopUps = false;

            //DragDropTracking.dragDropTracking.HideGroupMiniPopUp();
            DragDropTracking.dragDropTracking.ShowGroupMiniPopUp();
            DragDropTracking.dragDropTracking.HideMiniPopUp();
          
        }
       /* if (ActivatePopUps = false && ActivateMiniPopUps == false)
        {
          System.out.println("stopmebytheT");
          DragDropTracking.dragDropTracking.HideGroupMiniPopUp();  
        }*/
         System.out.println("vvvvvvvv");
         
        
    }
    
    @Override
    public void dragOver(DropTargetDragEvent event) 
    {
    
     
     
    }
    @Override
    public void dropActionChanged(DropTargetDragEvent event) 
    {
    }
}

