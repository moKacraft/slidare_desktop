/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package utils.streaming;

import io.socket.client.Socket;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import static java.lang.Thread.sleep;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Madeline
 */
public class streamer implements Runnable{
    
    private Socket socket = null;

    public streamer(Socket socketIO) {
        socket = socketIO;
    }
    
    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
  // The images must be the same size.
  if (imgA.getWidth() == imgB.getWidth() && imgA.getHeight() == imgB.getHeight()) {
    int width = imgA.getWidth();
    int height = imgA.getHeight();

    // Loop over every pixel.
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        // Compare the pixels for equality.
        if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
          return false;
        }
      }
    }
  } else {
    return false;
  }

  return true;
}

    @Override
    public void run() {
        //System.out.println(encryption.SHA1("/Users/julienathomas/Downloads/slidare_desktop2/Untitled.mov"));
        System.out.println("dedans!!!!");
        boolean ok = true;
        try {
            //Socket s=new Socket("127.0.0.1",4242);
            //DataOutputStream dout=new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
            Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Robot r = new Robot();
            BufferedImage img;
            
            int j = 0;
            
                j++;
                BufferedImage prevImgs[] = null;
                socket.emit("begin streaming", "stream.png");
                while (ok) {
                    img = r.createScreenCapture(rec);
                    
                    Image tmp = img.getScaledInstance(img.getWidth() /4 , img.getHeight() / 4, Image.SCALE_SMOOTH);
                    BufferedImage dimg = new BufferedImage(img.getWidth() / 4, img.getHeight() / 4, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = dimg.createGraphics();
                    g2d.drawImage(tmp, 0, 0, null);
                    g2d.dispose();

                    


                int rows = 2; //You should decide the values for rows and cols variables
                int cols = 10;
                int chunks = rows * cols;

                int chunkWidth = dimg.getWidth() / cols; // determines the chunk width and height
                int chunkHeight = dimg.getHeight() / rows;
                int count = 0;
                BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
                for (int x = 0; x < rows; x++) {
                    for (int y = 0; y < cols; y++) {
                        //Initialize the image array with image chunks
                        imgs[count] = new BufferedImage(chunkWidth, chunkHeight, dimg.getType());

                        // draws the image chunk
                        Graphics2D gr = imgs[count++].createGraphics();
                        gr.drawImage(dimg, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                        gr.dispose();
                    }
                }
                for (int i = 0; i < imgs.length; i++) {
//                    ImageIO.write(imgs[i], "jpg", new File("img" + i + ".jpg"));
                    if (prevImgs != null) {
                        if (compareImages(imgs[i], prevImgs[i]) == false) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(imgs[i], "jpg", baos);
                            byte[] imageInByte = baos.toByteArray();
                            String newBuf = Base64.encodeBase64String(imageInByte);
//                            System.out.println("size :" + newBuf.length());
                            baos.close();
                            socket.emit("processing streaming", i, newBuf);
//                            Thread.sleep(50);
                        }
//                        System.out.println("same one");
                    } else {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(imgs[i], "jpg", baos);
                        byte[] imageInByte = baos.toByteArray();
                        String newBuf = Base64.encodeBase64String(imageInByte);
//                        System.out.println("size :" + newBuf.length());
                        baos.close();
                        socket.emit("processing streaming", i, newBuf);
//                        Thread.sleep(50);
                    }
                }
                prevImgs = imgs;
//                break;
//                    Image tmp = img.getScaledInstance(img.getWidth() /4 , img.getHeight() / 4, Image.SCALE_SMOOTH);
//                    BufferedImage dimg = new BufferedImage(img.getWidth() / 4, img.getHeight() / 4, BufferedImage.TYPE_INT_ARGB);
//                    Graphics2D g2d = dimg.createGraphics();
//                    g2d.drawImage(tmp, 0, 0, null);
//                    g2d.dispose();
//                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                    ImageIO.write(dimg, "jpg",baos);
//                    baos.flush();
//                    byte[] imageInByte = baos.toByteArray();
//                    String newBuf = Base64.encodeBase64String(imageInByte);
//                    System.out.println("size :" + newBuf.length());
//                    baos.close();
//                    socket.emit("processing streaming", newBuf);
//                    Thread.sleep(50);
                }
                socket.emit("end streaming", "finished");

                //s.setSendBufferSize(65536);
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
//                }
//                if (j > 150)
//                    ok = false;
//            //}
            //dout.close();
        }
        catch (Exception e){
            System.out.println("msg: " + e.toString());
        }
    }
}
