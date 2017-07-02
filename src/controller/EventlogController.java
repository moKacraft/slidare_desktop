/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import static controller.Main.socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
<<<<<<< HEAD
import java.io.ByteArrayInputStream;
=======
>>>>>>> ea6a89d3f4001b817df7a7caee2c46a62a8816e5
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
<<<<<<< HEAD
import javafx.embed.swing.SwingFXUtils;
=======
>>>>>>> ea6a89d3f4001b817df7a7caee2c46a62a8816e5
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

import utils.DialogSys;
import utils.security.encryption;
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

    @FXML
    public ImageView imageview;
    
    DialogSys ds = new DialogSys();
    BufferedImage[] images = null;

    io.socket.client.Socket socket;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
<<<<<<< HEAD
    public void initialize(URL url, ResourceBundle rb) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Configuration config = new Configuration();
                config.setPort(8085);
                config.setHostname("192.168.1.25");
                config.setMaxFramePayloadLength(300000);
                SocketIOServer server = new SocketIOServer(config);
                server.addConnectListener(
                        (client) -> {
                            System.out.println("Client has Connected!");
                        });
                server.addEventListener("image", String.class,
                        (client, message, ackRequest) -> {
                            byte[] data = Base64.decodeBase64((String) message);

                            BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                            System.out.println(img);
                            Image card = SwingFXUtils.toFXImage(img, null );
                            System.out.println(card);
                            imageview.setImage(card);
                            System.out.println("Client said: " + data.length);
                        });
                server.start();
                
            }
        }).start();
=======
    public void initialize(URL url, ResourceBundle rb) {   
>>>>>>> ea6a89d3f4001b817df7a7caee2c46a62a8816e5
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException, URISyntaxException {
<<<<<<< HEAD
        socket = IO.socket("http://127.0.0.1:8082");
=======
        socket = IO.socket("http://83.113.70.210:8085");
>>>>>>> ea6a89d3f4001b817df7a7caee2c46a62a8816e5
        socket.on("test", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("test");
            }
        });
        socket.connect();
    }
    
    @FXML
    public void action2(ActionEvent event) throws IOException, NoSuchAlgorithmException, AWTException {
        Rectangle rec=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot r= new Robot();
        BufferedImage img;
        while (1 == 1) {
        img = r.createScreenCapture(rec);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(ImageIO.write(img, "png",baos))
        {
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
<<<<<<< HEAD
            baos.close();
            socket.emit("image", imageInByte);
=======
            int i = 0;
            System.out.println(i++ + "length " + imageInByte.length);
            baos.close();
            socket.emit("image", Base64.encodeBase64String(imageInByte));
>>>>>>> ea6a89d3f4001b817df7a7caee2c46a62a8816e5
        }
       }
    }
}

