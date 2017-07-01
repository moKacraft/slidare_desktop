/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package utils.fileTransfer;

/**
 *
 * @author Madeline
 */
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

public class fileReceiver extends Thread {
    private ServerSocket ss;
    private String _fileName;
    private int _fileSize;

    public fileReceiver(
            int port,
            String fileName,
            int fileSize) {
        try {
            ss = new ServerSocket(port);
            _fileName = fileName;
            _fileSize = fileSize;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            try {
                Socket clientSock = ss.accept();
                saveFile(clientSock);
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void saveFile(
            Socket clientSock)
            throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream(_fileName);
        byte[] buffer = new byte[4096];

        //int fileSize = 61455;
        int read = 0;
        int totalRead = 0;
        int remaining = _fileSize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }
        System.out.println("file received");
        fos.close();
        dis.close();
    }
}
