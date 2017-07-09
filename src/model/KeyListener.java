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
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener {
    public IKeyHandle keyHandle = null;
    public boolean keyPressed = false;
     
    public KeyListener()
    {
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) 
    {
        String key1 = NativeKeyEvent.getKeyText(e.getKeyCode());
 
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).matches( "Left Control") == true) {
            key1 = "Ctrl";
        }
        if ((TrackingInfo.keyName != null) &&  key1.matches(TrackingInfo.keyName) == true) {
            DragDropTracking.dragDropTracking.showBubble();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) 
    {
        String key1 = NativeKeyEvent.getKeyText(e.getKeyCode());
 
        if (NativeKeyEvent.getKeyText(e.getKeyCode()).matches( "Left Control") == true) {
            key1 = "Ctrl";
        }
        if ((TrackingInfo.keyName != null) && key1.matches(TrackingInfo.keyName) == true) {
            DragDropTracking.dragDropTracking.hideBubble();
            DragDropTracking.dragDropTracking.HideMiniPopUp();
        }
     
    }

    public void nativeKeyTyped(NativeKeyEvent e)
    {
        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }
    
//    public void notifyViewToChangeKeyHandleText(String text) {
//         
//    }
    
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
