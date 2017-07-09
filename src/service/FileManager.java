/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONAware;

import javafx.stage.Stage;
import javafx.stage.FileChooser;
import utils.DialogSys;

/**
 * Writing and reading for file can be done by the File Manager
 * 
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class FileManager implements iService
{
    private final PackageManager packageManager;
    
    private File file;
    
    private String lastPathUsed = null;
    
    public FileManager()
    {
        this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
    }
    
    /**
     * Write a JSON in a file
     *
     * @param filepath path to the file including the filename
     * @param obj object to write in the file
     */
    public void writeJson(String filepath, JSONAware obj)
    {
        try (FileWriter file = new FileWriter(filepath))
        {
            file.write(obj.toJSONString());
            file.flush();
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Read a JSON from a file
     *
     * @param filepath path to the file including the filename
     * @return object from json or null
     */
    public Object readJson(String filepath)
    {
        try
        {
            Object obj = this.packageManager.setJSONObject(new FileReader(filepath)).getObject();
            return (obj);
        }
        catch (FileNotFoundException e)
        {
            DialogSys.ds.showError("File not found", "File was not found", "The following file was not found : " + filepath);
//			System.err.println(e.getMessage());
//			System.exit(1);
        }
        return (null);
    }
    
    /**
     * A select file popup can be open from the stage
     * Only specified file could be seen
     *
     * @param stage the stage of the api
     */
    public void initFileChooserConfig(Stage stage)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load configuration");
        if (this.lastPathUsed != null)
        {
            fileChooser.setInitialDirectory(
                    new File(this.lastPathUsed)
            );
        }
        
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("SYS", "*.sys")
        );
        this.file = fileChooser.showOpenDialog(stage);
        if (this.file != null)
            this.lastPathUsed = file.getParent();
    }
    
    /**
     * Get the path of the file specied
     *
     * @return absolute pathfile
     */
    public String getFilePath()
    {
        if (file != null)
            return (file.getAbsolutePath());
        return ("");
    }
}
