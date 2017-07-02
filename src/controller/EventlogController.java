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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;

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
import javafx.embed.swing.SwingFXUtils;
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
    public void initialize(URL url, ResourceBundle rb) {
       DatagramSocket serverSocket;
        try {
            serverSocket = new DatagramSocket(8756);
            byte[] receiveData = new byte[300000];
            byte[] sendData = new byte[300000];
            while(true)
                {
                 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                 serverSocket.receive(receivePacket);
                 String sentence = new String( receivePacket.getData());
                 System.out.println("RECEIVED: " + sentence);
                }
        } catch (SocketException ex) {
            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException, URISyntaxException {
        socket = IO.socket("http://93.31.237.180:8085");
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
        new Thread(new Runnable() {
        @Override
            public void run() {
            try {
                Rectangle rec=new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                Robot r= new Robot();
                BufferedImage img;
                
                while (1 == 1) {
                    img = r.createScreenCapture(rec);
                    
                    java.awt.Image tmp = img.getScaledInstance(img.getWidth() /4 , img.getHeight() / 4, java.awt.Image.SCALE_SMOOTH);
                    BufferedImage dimg = new BufferedImage(img.getWidth() / 4, img.getHeight() / 4, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = dimg.createGraphics();
                    g2d.drawImage(tmp, 0, 0, null);
                    g2d.dispose();

                    
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    if(ImageIO.write(dimg, "png",baos))
                    {
                        baos.flush();
                        byte[] imageInByte = baos.toByteArray();
                        baos.close();
                        socket.emit("image", Base64.encodeBase64String(imageInByte));
                    }
                    Thread.sleep(50);
                }} catch (AWTException ex) {
                Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
        }).start();
    }
}

