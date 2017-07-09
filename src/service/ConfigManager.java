/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package service;

import model.Config;
import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;
import model.iConfig;
import org.json.simple.JSONObject;
import utils.DialogSys;

/**
 * The configuration can be update and used be this Manager
 * 
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ConfigManager implements iService
{
    private FileManager fileManager;
    
    private Config	config;
    
    private PackageManager packageManager;
    
    private Map<String, String> path = new HashMap<>();
    
    private String fullapppath;
	
    public ConfigManager()
    {
        this.fileManager = (FileManager) ServiceFactory.getFileManager();
        this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
        this.config = new Config();
        
        String u = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
        int x = u.lastIndexOf('/');
        this.fullapppath = u.substring(0, x+1);
        
        this.path.put("datas", "./datas/");
        this.path.put("lang", "./config/");
        this.path.put("account", this.fullapppath + "datas/account.json");
        this.path.put("contacts", this.fullapppath + "datas/contacts.json");
        this.path.put("groups", this.fullapppath + "datas/groups.json");
        this.path.put("config", this.fullapppath + "config/config.sys");
    }
    
    /**
     * Check if a path is defined for a specific module
     *
     * @param module
     */
    private void checkModuleExist(String module)
    {
        if (this.path.containsKey(module) != true)
            throw new RuntimeException("[Service][Config] Module does not exist.");
    }
    
    /**
     * Load default configuration
     */
    public void loadBase()
    {
        load(this.getPathFor("config"));
    }
    
    /**
     * Load a configuration from a path
     *
     * @param filepath absolut filepath from the root of the project
     */
    public void load(String filepath)
    {
        this.setPathFor("config", filepath);
        
        System.out.println("service.ConfigManager.load()" + filepath);
        
        Object obj = fileManager.readJson(filepath);
        JSONObject jsonObject = (JSONObject) obj;
        System.out.println("service.ConfigManager.load()" + jsonObject);
        
        this.packageManager.setJSONObject(jsonObject);
        
        this.config.setLang(this.packageManager.getStringDefault("lang", this.config.getLang()));
        this.config.setMaximized(this.packageManager.getBooleanDefault("maximized", this.config.getMaximized()));
        this.config.setWidth(this.packageManager.getIntDefault("width", this.config.getWidth()));
        this.config.setHeight(this.packageManager.getIntDefault("height", this.config.getHeight()));
        this.config.setUsername(this.packageManager.getStringDefault("username", this.config.getUsername()));
        this.config.setPassword(this.packageManager.getStringDefault("password", this.config.getPassword()));
        this.config.setAutoConnect(this.packageManager.getBooleanDefault("autoconnect", this.config.getAutoConnect()));
    }
    
    /**
     * Save a configuration in a file
     */
    public void save()
    {
        this.packageManager
                .addParam("lang", this.config.getLang())
                .addParam("maximized", this.config.getMaximized())
                .addParam("width", this.config.getWidth())
                .addParam("height", this.config.getHeight())
                .addParam("username", this.config.getUsername())
                .addParam("password", this.config.getPassword())
                .addParam("autoConnect", this.config.getAutoConnect());
        this.fileManager.writeJson(this.getPathFor("config"), this.packageManager.getJSONOBject());
    }
    
    public String getFullAppPath()
    {
        return (this.fullapppath);
    }
    
    /**
     * Set a path for a module
     *
     * @param module modulename lower case
     * @param filepath path from the root of the programm
     */
    public void setPathFor(String module, String filepath)
    {
        module = module.toLowerCase();
        checkModuleExist(module);
        this.path.replace(module, filepath);
    }
    
    /**
     * get a path for a module
     *
     * @param module module name
     * @return filepath path from the root of the programm
     */
    public String getPathFor(String module)
    {
        module = module.toLowerCase();
        checkModuleExist(module);
        return (this.path.get(module));
    }
    
    /**
     * Configure a stage with the configuration load
     *
     * @param stage Stage use in the app
     */
    public void configStage(Stage stage)
    {
        stage.setHeight(this.config.getHeight());
        stage.setWidth(this.config.getWidth());
        if (this.config.getMaximized() == true)
            stage.setMaximized(true);
        
    }
    
    /**
     * Get configuration object
     *
     * @return config set in the manager
     */
    public Config	getConfig()
    {
        return (this.config);
    }
    
    /**
     * Save the configEdited in the file
     *
     * @param configEdited config used to replace the actual one
     */
    public void		saveConfig(iConfig configEdited)
    {
        this.config.setWidth(configEdited.getWidth());
        this.config.setHeight(configEdited.getHeight());
        /* @todo: add the others properties */
        this.save();
    }
}
