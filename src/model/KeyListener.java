/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Timothy
 */
import model.IKeyHandle;
//import model.KeyHandleWindows;
 import org.jnativehook.GlobalScreen;
//import Source.View.MainView;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
     public IKeyHandle keyHandle = null;
     public boolean keyPressed = false;
     
     public KeyListener()
     {
           // keyHandle = new KeyHandleWindows();
        //((KeyHandleWindows)keyHandle).addObserver(this);
     }
     @Override
    public void nativeKeyPressed(NativeKeyEvent e) 
    {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
         System.out.println("Key Pressed 2: " + TrackingInfo.keyName);
     
        if ((TrackingInfo.keyName != null) && ( NativeKeyEvent.getKeyText(e.getKeyCode())).matches(TrackingInfo.keyName) == true)
        {
            DragDropTracking.dragDropTracking.showBubble();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
         if ((TrackingInfo.keyName != null) && ( NativeKeyEvent.getKeyText(e.getKeyCode())).matches(TrackingInfo.keyName) == true)
        {
           
            DragDropTracking.dragDropTracking.hideBubble();
            DragDropTracking.dragDropTracking.HideMiniPopUp();
            
        }
     
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
         //System.out.println("Key Typed 2: " + keyHandle.getKeyName());
    
    }
     public void notifyViewToChangeKeyHandleText(String text)
    {
       //  this.setChanged();
         //MainView.mainView.changeActivationName(text);
         //notifyObservers(text);
    }
    
/*@Override
    public void update(Observable o, Object arg) 
    {
        
        if (o.toString().contains("Source.Model.KeyHandleWindows"))
        {
            notifyViewToChangeKeyHandleText(arg.toString());
        
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
   
}
