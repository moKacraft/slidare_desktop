/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import java.security.NoSuchAlgorithmException;

import java.util.ResourceBundle;
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
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException {
        String[] users = new String[1];
        users[0]= "sophie@slidare.com";
         socket.emit("request file transfer", "nc.txt", users);
}
    
    @FXML
    public void action2(ActionEvent event) throws IOException, NoSuchAlgorithmException {

    }
}

