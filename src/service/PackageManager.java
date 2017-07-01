/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package service;

import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * All the package sent to the server come from the PackageManager
 * The package manager is based on the JSON 
 * 
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class PackageManager implements iService
{
	private JSONObject obj;
	
	private JSONArray arr;
	
	private final JSONParser parser = new JSONParser(); 
	
	/**
	 * Init a new packageManager
	 * 
	 * @return PackageManager
	 */
	public PackageManager init()
	{
		this.obj = new JSONObject();
		return (this);
	}
	
	/**
	 * Add a param to the package manager
	 * 
	 * @param name key of the param
	 * @param object value of the param
	 * @return PackageManager
	 */
	public PackageManager addParam(String name, Object object)
	{
		this.obj.put(name, object);
		return (this);
	}
	
	/**
	 * Get the current JSONObject
	 * 
	 * @return JSONObject
	 */
	public JSONObject getJSONOBject()
	{
		return (this.obj);
	}
	
	/**
	 * Get the current JSONArray
	 * 
	 * @return JSONObject
	 */
	public JSONArray getJSONArray()
	{
		return (this.arr);
	}
	
	/**
	 * Ger the current Object from the JSONObject
	 * 
	 * @return Object
	 */
	public Object getObject()
	{
		if (this.obj != null)
			return (this.obj);
		else
			return (this.arr);
	}
	
	/**
	 * Get the extracted string from the JSONObject
	 * 
	 * @return JSON string
	 */
	public String getJSONString()
	{
		return (this.obj.toJSONString());
	}
	
	/**
	 * Converte a string to JSONObject and save it
	 * 
	 * @param content string in JSON
	 * @return instance
	 */
	public PackageManager setJSONObject(String content)
	{
		try
		{
			Object tmp = this.parser.parse(content);

			if (tmp instanceof JSONArray)
				this.setJSONArray((JSONArray) tmp);
			else
				this.setJSONObject((JSONObject) tmp);
		}
		catch (ParseException e)
		{
			System.err.println(e.getMessage());
		}	
		return (this);
	}
	
	/**
	 * Convert a Reader to a JSONObject and save it
	 * 
	 * @param content JSON from file
	 * @return instance
	 */
	public PackageManager setJSONObject(Reader content)
	{
		try
		{
			Object tmp = this.parser.parse(content);

			if (tmp instanceof JSONArray)
				this.setJSONArray((JSONArray) tmp);
			else
				this.setJSONObject((JSONObject) tmp);
		}
		catch (IOException | ParseException e)
		{
			System.err.println(e.getMessage());
		}
		
		return (this);
	}
	
	/**
	 * Simple setter
	 * 
	 * @param jsonObject JSON obj
	 * @return instance
	 */
	public PackageManager setJSONObject(JSONObject jsonObject)
	{
		this.arr = null;
		this.obj = jsonObject;
		return (this);
	}
	
	/**
	 * Simple setter
	 * 
	 * @param jsonArray JSON array
	 * @return instance
	 */
	public PackageManager setJSONArray(JSONArray jsonArray)
	{
		this.obj = null;
		this.arr = jsonArray;
		return (this);
	}
	
	/**
	 * Get the value associate to the key or return the default value on fail
	 * 
	 * @param key key expected
	 * @param dflt default value in fail
	 * @return value
	 */
	public String getStringDefault(String key, String dflt)
	{
		if (this.obj.containsKey(key) && this.obj.get(key) instanceof String)
			return ((String) this.obj.get(key));
		else
			return (dflt);
	}
	
	/**
	 * Get the value associate to the key or return the default value on fail
	 * 
	 * @param key key expected
	 * @param dflt default value in fail
	 * @return value
	 */
	public int getIntDefault(String key, int dflt)
	{
		if (this.obj.containsKey(key) && this.obj.get(key) instanceof Long)
			return (Math.toIntExact((Long) this.obj.get(key)));
		else
			return (dflt);
	}
	
	/**
	 * Get the value associate to the key or return the default value on fail
	 * 
	 * @param key key expected
	 * @param dflt default value in fail
	 * @return value
	 */
	public boolean getBooleanDefault(String key, boolean dflt)
	{
		if (this.obj.containsKey(key) && this.obj.get(key) instanceof Boolean)
			return ((boolean) this.obj.get(key));
		else
			return (dflt);
	}
}
