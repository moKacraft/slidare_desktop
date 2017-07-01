/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.util.Observable;
import java.util.Observer;
/**
 *
 * @author Timothy
 */
public interface IKeyHandle
{
   public void ActivateKeyListener(); 
   public void setKeyName(String keyName);
   public String getKeyName();
   
    
}
