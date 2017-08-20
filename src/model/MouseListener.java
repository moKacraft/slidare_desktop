/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

public class MouseListener implements NativeMouseInputListener 
{
    
    public String stateMouse = "drop";
   
    
    public void nativeMouseClicked(NativeMouseEvent e) 
    {
        System.out.println("Mouse Clicked: " + e.getClickCount());
        
    }

    public void nativeMousePressed(NativeMouseEvent e) 
    {
        System.out.println("Mouse Pressed: " + e.getButton());
        
    }

    public void nativeMouseReleased(NativeMouseEvent e) 
    {
        //System.out.println("Mouse Released: " + e.getButton());
        stateMouse = "Drop";
       
    }

    public void nativeMouseMoved(NativeMouseEvent e) 
    {
        //System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
    }

    public void nativeMouseDragged(NativeMouseEvent e) 
    {
        stateMouse = "Drag";
        //System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
    }

    
}
