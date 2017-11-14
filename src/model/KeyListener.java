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
    private String key1 = "";
    private String key2 = "";
    
    
    public KeyListener()
    {
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) 
    {
//        if (key1 == "")
//            key1 = NativeKeyEvent.getKeyText(e.getKeyCode());
//        else if (key2 == "")
//            key2 = NativeKeyEvent.getKeyText(e.getKeyCode());
//            
//        
//        if (NativeKeyEvent.getKeyText(e.getKeyCode()).matches( "Left Control") == true) {
//            if (key2 == "")
//                key1 = "Ctrl";
//            else
//                key2 = "Ctrl";
//                
//        }
        //System.out.println("e:"  + NativeKeyEvent.getKeyText(e.getKeyCode()));
        //System.out.println("e 2:"  + key1);  
        //System.out.println("key1:"  + key1);
        //System.out.println("key2:" + key2);
//     if ((TrackingInfo.firstKeyName != null) &&  key1.matches(TrackingInfo.firstKeyName) == true &&
//            (TrackingInfo.secondKeyName != null) &&  key2.matches(TrackingInfo.secondKeyName) == true  ||
//                key1.matches(TrackingInfo.secondKeyName) == true &&  key2.matches(TrackingInfo.firstKeyName) == true) 
//        {
//            DragDropTracking.dragDropTracking.showBubble();
//            
//        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) 
    {
        //key1 = NativeKeyEvent.getKeyText(e.getKeyCode());
        //key1 = NativeKeyEvent.getKeyText(e.getKeyCode());
 
        /*if (NativeKeyEvent.getKeyText(e.getKeyCode()).matches( "Left Control") == true) {
            key1 = "Ctrl";
        }*/
//        if ((TrackingInfo.firstKeyName != null) &&  key1.matches(TrackingInfo.firstKeyName) == true &&
//            (TrackingInfo.secondKeyName != null) &&  key2.matches(TrackingInfo.secondKeyName) == true  ||
//                key1.matches(TrackingInfo.secondKeyName) == true &&  key2.matches(TrackingInfo.firstKeyName) == true) 
//        {
//            DragDropTracking.dragDropTracking.hideBubble();
//            DragDropTracking.dragDropTracking.HideMiniPopUp();
//        }
//        key1 = "";
//        key2 = "";
    }

    public void nativeKeyTyped(NativeKeyEvent e)
    {
//        System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
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
