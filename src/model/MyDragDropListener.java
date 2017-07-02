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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;
import utils.security.encryption;

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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File f = new File(file.toString());

                                System.out.println(f.getName());
                                System.out.println("File path inside is '" + file + "'.");
                                String[] users = new String[1];
                                users[0]= "sophie@slidare.com";
                                
                                try {
                                    encryption _crypt = new  encryption();
                                    
                                    String key =  encryption.SHA256("my secret key", 32);
                                    _crypt.encryptFile(file.toString(), "encrypted", key);
//                                    _crypt.decryptFile("./encrypted", "./decrypted", _crypt.get_fileSHA1(), _crypt.get_fileSalt(), _crypt.get_fileIV(), _crypt.get_fileKey());
                                    
                                    
                                    System.out.println("Salt: " + _crypt.get_fileSalt() + " length" + _crypt.get_fileSalt().length + "\nIV: " + _crypt.get_fileIV() + " length: " + _crypt.get_fileIV().length);
                                    
                                    Main.socket.emit("request file transfer", f.getName(), file, users, _crypt.get_fileEncryptedName(), f.getName(), _crypt.get_fileSHA1(), Base64.encodeBase64String(_crypt.get_fileSalt()), Base64.encodeBase64String(_crypt.get_fileIV()), _crypt.get_fileKey(), f.length());
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

