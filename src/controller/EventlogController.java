/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import io.socket.client.IO;
import io.socket.emitter.Emitter;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import java.net.URISyntaxException;
import java.net.URL;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;

import utils.DialogSys;
import utils.security.encryption;
import utils.streaming.streamer;

/**
 * FXML Controller class
 *
 * @author Madeline
 */
public class EventlogController extends Controller implements Initializable {
    
//    private Socket socket;
    private FileOutputStream fos;

    @FXML
    private ImageView imageView0;    
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private ImageView imageView7;
    @FXML
    private ImageView imageView8;
    @FXML
    private ImageView imageView9;
    @FXML
    private ImageView imageView10;
    @FXML
    private ImageView imageView11;
    @FXML
    private ImageView imageView12;
    @FXML
    private ImageView imageView13;
    @FXML
    private ImageView imageView14;
    @FXML
    private ImageView imageView15;
    @FXML
    private ImageView imageView16;
    @FXML
    private ImageView imageView17;
    @FXML
    private ImageView imageView18;
    @FXML
    private ImageView imageView19;

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
        try {
            //        System.out.println(images.length);
//        System.err.println("TESTTESTESTETSTESTESTTESTEST");
//        String test = url.toString();
//        int x = test.lastIndexOf('/');
//        String fullapppath = test.substring(x+1, test.length());
//        if ("sample.fxml".equals(fullapppath)) {
//            System.err.println("ARG");
//            return ;
//        }
           socket = IO.socket("http://54.224.110.79:8080");
            
            socket.on("server ready", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Server ready");
                    Socket sock;
                    try {
                        sock = new Socket("54.224.110.79", (int)args[0]);
                        OutputStream is = sock.getOutputStream();
                        FileInputStream fis = new FileInputStream((String)args[1]);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        byte[] buffer = new byte[4096];
                        int ret;
                        while ((ret = fis.read(buffer)) > 0) {
                            is.write(buffer, 0, ret);
                        }
                        fis.close();
                        bis.close();
                        is.close();
                        sock.close();
                    } catch (IOException ex) {
                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).on("sophie@slidare.com", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Receving File");
                    String transferId = (String) args[2];
                    try {
                        FileOutputStream fos = new FileOutputStream((String) args[0]);
                        Socket sock = new Socket("54.224.110.79", (int)args[1]);
                        InputStream is = sock.getInputStream();
                        byte[] buffer = new byte[4096];
                        int ret;
                        while ((ret = is.read(buffer)) > 0) {
                            fos.write(buffer, 0, ret);
                        }
                        System.out.println("Transfer Finished");
                        fos.close();
                        is.close();
                        sock.close();
                        socket.emit("transfer finished", transferId);
                    } catch (IOException ex) {
                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            socket.connect();
//        
//        try {
//            socket = IO.socket("http://52.33.232.161:8080");
////            socket = IO.socket("http://localhost:8080");
//            
//            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                }
//                
//            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                }
//                
//            }).on("create file", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    System.out.println(args[0]);
//                    try {
//                        fos = new FileOutputStream((String) args[0]);
//                    } catch (FileNotFoundException ex) {
//                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//            }).on("write file", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    try {
//                        byte[] newbuf = Base64.decodeBase64((String) args[0]);
//                        fos.write(newbuf, 0, (int) args[1]);
//                    } catch (IOException ex) {
//                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//            }).on("transfer finished", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    try {
//                        fos.close();
//                        System.out.println("finished");
//                    } catch (IOException ex) {
//                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//            }).on("start streaming", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    System.out.println("start streaming");
//                    // Create window
////                   ds.showPopup(anchorID, "/view/sample.fxml", "sample");
//                }
//                
//            }).on("display streaming", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
////                    System.out.println("display streaming");
//                    byte[] newbuf = Base64.decodeBase64((String) args[1]);
//                    if (images == null) {
//                        images = new BufferedImage[20];
//                    }
//
//                    try {
////                        System.out.println(newbuf.length);
//                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(newbuf));
////                        Image card = SwingFXUtils.toFXImage(image, null );
////                        Image img = new ImageIcon(image).getImage();
////                        Platform.runLater(new Runnable() {
////                            @Override
////                            public void run() {
//                                int idx = (int) args[0];
////                                System.out.println("here");
//                                images[idx] = image;
////                            }
////                        });
//                    } catch (IOException ex) {
//                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                
//            }).on("stop streaming", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    System.out.println("stop streaming");
//                    // Destroy window
//                }
//                
//            });
//            
//            socket.connect();
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(1 == 1) {
//                    try {
//                        updateImageView();
//                        
////                    try {
////                        Thread.sleep(500);
////                    } catch (InterruptedException ex) {
////                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
////                    }
//                    } catch (IOException ex) {
//                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        }).start();
        } catch (URISyntaxException ex) {
            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException {
        String[] users = new String[1];
        users[0]= "sophie@slidare.com";
         socket.emit("request file transfer", "nc.txt", users);
//        try {
//            encryption encrypt = new encryption();
//            String key = encryption.SHA256("key", 32);
//            encrypt.encryptFile("./files/0.jpg", "./files/encrypted.des", key);
//            encrypt.decryptFile("./files/encrypted.des", "./files/test.jpg", encrypt.get_fileSHA1(), encrypt.get_fileSalt(), encrypt.get_fileIV(), key);
//        } catch (NoSuchAlgorithmException |
//                NoSuchPaddingException |
//                UnsupportedEncodingException |
//                InvalidKeySpecException |
//                InvalidKeyException |
//                InvalidParameterSpecException |
//                BadPaddingException |
//                IllegalBlockSizeException |
//                InvalidAlgorithmParameterException ex) {
//            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //DialogSys ds = new DialogSys();
        //ds.showPopup(anchorID, "/view/sample.fxml", "sample");
//        socket.emit("begin transfer", "0.jpg");
//        bis.close();
//        sock.close();

//        socket.emit("end transfer", "finished");
        
    }
    
    @FXML
    public void action2(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                streamer stream = new streamer(socket);
//                stream.run();
//            }
//        }).start();
//Platform.runLater(stream);

        
//        //System.out.println(encryption.SHA1("/Users/julienathomas/Downloads/slidare_desktop2/Untitled.mov"));
//        boolean ok = true;
//        try {
//            //Socket s=new Socket("127.0.0.1",4242);
//            //DataOutputStream dout=new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
//            Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//            Robot r = new Robot();
//            BufferedImage img;
//            while (ok) {
//                img = r.createScreenCapture(rec);
//                //s.setSendBufferSize(65536);
//                img.flush();
//                
//                Image tmp = img.getScaledInstance(img.getWidth() /2 , img.getHeight() / 2, Image.SCALE_SMOOTH);
//                BufferedImage dimg = new BufferedImage(img.getWidth() / 2, img.getHeight() / 2, BufferedImage.TYPE_INT_ARGB);
//                Graphics2D g2d = dimg.createGraphics();
//                g2d.drawImage(tmp, 0, 0, null);
//                g2d.dispose();
//                
//                //System.out.println(img.getWidth() + " "  + img.getHeight());
//                
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                if(ImageIO.write(dimg, "png",baos)) {
//                    baos.flush();
//                    byte[] imageInByte = baos.toByteArray();
//                    baos.close();
//                    
//                    byte[] length = new byte[] {
//                        (byte)(imageInByte.length >>> 24),
//                        (byte)(imageInByte.length >>> 16),
//                        (byte)(imageInByte.length >>> 8),
//                        (byte)imageInByte.length};
//                    
//                    byte[] finalBuf = new byte[imageInByte.length + 4];
//                    finalBuf[0] = length[0];
//                    finalBuf[1] = length[1];
//                    finalBuf[2] = length[2];
//                    finalBuf[3] = length[3];
//                    for (int i=0; i < imageInByte.length; ++i) {
//                        finalBuf[i + 4] = imageInByte[i];
//                    }
//                    
//                    //dout.write(length);
//                    //dout.write(imageInByte);
//                    
//                    // Send stream as png
//                    //socket.emit("begin transfer", "stream.png");
//                    //ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
//                    //byte[] buffer = new byte[4096];
//                    //int ret;
//                    //while ((ret = bis.read(buffer)) > 0) {
//                    //String newBuf = Base64.encodeBase64String(buffer);
//                    //socket.emit("processing transfer", newBuf, ret);
//                    //}
//                    //socket.emit("end transfer", "finished");
//                    
//                    // Send as bytes
//                    socket.emit("begin streaming", "stream.png");
//                    ByteArrayInputStream bis = new ByteArrayInputStream(imageInByte);
//                    byte[] buffer = new byte[4096];
//                    int ret;
//                    while ((ret = bis.read(buffer)) > 0) {
//                        String newBuf = Base64.encodeBase64String(buffer);
//                        socket.emit("processing streaming", newBuf, ret);
//                    }
//                    
//                }
//            }
//            socket.emit("end streaming", "finished");
//            //dout.close();
//        }
//        catch (Exception e){
//            System.out.println("msg: " + e.toString());
//        }
    }
}

