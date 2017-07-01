/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ServiceFactory
{
	private static Map<String, iService> serviceList = new HashMap<>();
	
	private static ResourceBundle bundle = null;
	
	public static iService getConfigManager()
	{
		if (serviceList.containsKey("ConfigManager") == false)
		{
			serviceList.put("ConfigManager", new ConfigManager());
		}
		return (serviceList.get("ConfigManager"));
	}
	
	public static iService getFileManager()
	{
		if (serviceList.containsKey("FileManager") == false)
		{
			serviceList.put("FileManager", new FileManager());
		}
		return (serviceList.get("FileManager"));
	}

	public static iService getClientManager()
	{
		if (serviceList.containsKey("ClientManager") == false)
		{
			serviceList.put("ClientManager", new ClientManager());
		}
		return (serviceList.get("ClientManager"));
	}
	
	public static iService getAPIManager()
	{
		if (serviceList.containsKey("APIManager") == false)
		{
			serviceList.put("APIManager", new APIManager());
		}
		return (serviceList.get("APIManager"));
	}
	
	public static iService getPackageManager()
	{
		if (serviceList.containsKey("PackageManager") == false)
		{
			serviceList.put("PackageManager", new PackageManager());
		}
		return (serviceList.get("PackageManager"));
	}
	
	public static ResourceBundle getResourceBundle(boolean reload)
	{
		if (bundle == null || reload == true)
		{
			ConfigManager config = (ConfigManager) ServiceFactory.getConfigManager();
			bundle = ResourceBundle.getBundle("lang.lang", new Locale(config.getConfig().getLang()));
		}
		return (bundle);
	}
}
