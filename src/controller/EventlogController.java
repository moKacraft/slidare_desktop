/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;

import java.security.NoSuchAlgorithmException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import utils.DialogSys;
/**
 * FXML Controller class
 *
 * @author Madeline
 */
public class EventlogController extends Controller implements Initializable {

    @FXML
    public TextArea feedBack;
 
    @FXML
    public AnchorPane anchorID;
    
    DialogSys ds = new DialogSys();
    BufferedImage[] images = null;

    io.socket.client.Socket socket;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramSocket serverSocket = null;
                try {
                    serverSocket = new DatagramSocket(4000);
                } catch (SocketException ex) {
                    Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    byte[] receiveData = new byte[65000];
                    byte[] sendData = new byte[65000];
                    while(true)
                       {
                        try {
                            System.out.println("here");
                            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                            serverSocket.receive(receivePacket);
                            String sentence = new String( receivePacket.getData());
                            System.out.println("RECEIVED: " + sentence.length());
                            System.out.println("RECEIVED: " + sentence.charAt(62010));
                            
//                            InetAddress IPAddress = receivePacket.getAddress();
//                            int port = receivePacket.getPort();
//                            String capitalizedSentence = sentence.toUpperCase();
//                            sendData = capitalizedSentence.getBytes();
//                            DatagramPacket sendPacket =
//                                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
//                            serverSocket.send(sendPacket);
                        } catch (IOException ex) {
                            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                       }
                }
        }).start();
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException {
        DatagramSocket ds = null;
        ds = new DatagramSocket();
        byte[] mybytearray = new byte[63000];
        mybytearray[62010] = 'a';
        System.out.println(mybytearray.length);
        DatagramPacket dp = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("83.113.70.210"), 4000);
        ds.send(dp);
    }
    
    @FXML
    public void action2(ActionEvent event) throws IOException, NoSuchAlgorithmException {

    }
}

