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
import java.util.ArrayList;
import java.util.List;
import model.IKeyHandle;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener 
{
    public IKeyHandle keyHandle = null;
    public boolean keyPressed = false;
    private  List<String> ActiveKeys;
    private int cnt = 0;
    private String key1 = "";
    private String key2 = "";
    
    
    public KeyListener()
    {
        this.ActiveKeys = new ArrayList<String>();
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) 
    {
         String KeyEventString = NativeKeyEvent.getKeyText(e.getKeyCode());
     
        
            
        if (KeyEventString.matches( "Left Control") == true) {
                KeyEventString = "Ctrl";
                
        }
        if (TrackingInfo.listKeyName == null)
            return;
        //System.out.println("e:"  + KeyEventString);
        //System.out.println("e 2:"  + key1);  
        //System.out.println("key1:"  + key1);
        //System.out.println("key2:" + key2);
        
        for (int i = 0; i < TrackingInfo.listKeyName.size() ; i++)
        {
            // System.out.println("key*:"  + TrackingInfo.listKeyName.get(i));
            if (TrackingInfo.listKeyName.get(i).matches(KeyEventString) &&
                  ActiveKeys.contains(KeyEventString) == false)
            {
          //      System.out.println("Key" + KeyEventString);
                ActiveKeys.add(KeyEventString);
            }
        }
        //System.out.println("KeySize " + ActiveKeys.size() + " " + TrackingInfo.listKeyName.size());
        if (ActiveKeys.size() == TrackingInfo.listKeyName.size())
        {
            //System.out.println("YOLO");
            if (DragDropTracking.dragDropTracking == null)
                System.out.println("yolomotherfucker");
            DragDropTracking.dragDropTracking.showBubble();
            //DragDropTracking.dragDropTracking.
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) 
    {
        String KeyEventString = NativeKeyEvent.getKeyText(e.getKeyCode());
        //key1 = NativeKeyEvent.getKeyText(e.getKeyCode());
 
        if (TrackingInfo.listKeyName == null)
            return;
        
         if (KeyEventString.matches( "Left Control") == true) {
                KeyEventString = "Ctrl";
                
        }
        /*if (NativeKeyEvent.getKeyText(e.getKeyCode()).matches( "Left Control") == true) {
            key1 = "Ctrl";
        }*/
         for (int i = 0; i < TrackingInfo.listKeyName.size() ; i++)
        {
            if (TrackingInfo.listKeyName.get(i).matches(KeyEventString) &&
                  ActiveKeys.contains(KeyEventString) == true)
            {
                ActiveKeys.remove(KeyEventString);
            }
        }
        if (ActiveKeys.size() != TrackingInfo.listKeyName.size())
        {
            DragDropTracking.dragDropTracking.hideBubble();
            DragDropTracking.dragDropTracking.HideGroupMiniPopUp();
        }
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
