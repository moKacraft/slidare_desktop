/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package utils.fileTransfer;

/**
 * Created by Madeline on 08/05/2017.
 */
import java.io.File;

public class fileUtils {
    public static int getFileSize(String filePath) {
        File file = new File(filePath);
        return ((int)file.length());
    }

    public static String getFileType(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return (file.getName());
    }
}

