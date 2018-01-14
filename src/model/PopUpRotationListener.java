/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
//import java.awt.geom.Point2D;

import java.awt.Point;

/**
 *
 * @author Timothy
 */
public class PopUpRotationListener  implements DropTargetListener{

    
    public int types = 0;
    public Point.Double point;
    public DragDropTracking ddt;
    private boolean  enter = false;

    @Override
    public void dragEnter(DropTargetDragEvent dtde) 
    {
        enter = true;
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) 
    {
        if (enter == true)
        {
        System.out.println(dtde.getLocation());
        point = new Point.Double(dtde.getLocation().x,dtde.getLocation().y);
        ddt.MouseInput(types);
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) 
    {
        enter = false;
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
    }
    
}
