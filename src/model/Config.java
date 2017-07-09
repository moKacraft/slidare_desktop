/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class Config implements iConfig
{
    private String lang;
    private boolean maximized;
    private int	width;
    private int	height;
    private Boolean autoConnect;
    private String username;
    private String password;
    private String token;
    private String id;
    public Config()
    {
        this.lang = "fr";
        this.maximized = false;
        this.width = 800;
        this.height = 600;
        this.autoConnect = false;
        this.username = "";
        this.password = "";
        this.token = "";
        this.id = "";
    }
    
    public void	setLang(String _lang)
    {
        this.lang = _lang;
    }
    
    @Override
    public String getLang()
    {
        return (this.lang);
    }
    
    public void	setMaximized(boolean _maximized)
    {
        this.maximized = _maximized;
    }
    
    @Override
    public boolean getMaximized()
    {
        return (this.maximized);
    }
    
    public void	setWidth(int _width)
    {
        this.width = _width;
    }
    
    @Override
    public int getWidth()
    {
        return (this.width);
    }
    
    public void	setHeight(int _height)
    {
        this.height = _height;
    }
    
    @Override
    public int getHeight()
    {
        return (this.height);
    }
    
    public void	setAutoConnect(boolean _autoConnect)
    {
        this.autoConnect = _autoConnect;
    }
    
    @Override
    public boolean getAutoConnect()
    {
        return (this.autoConnect);
    }
    
    public void	setUsername(String _username)
    {
        this.username = _username;
    }
    
    @Override
    public String getUsername()
    {
        return (this.username);
    }
    
    public void setPassword(String _password)
    {
        this.password = _password;
    }
    
    @Override
    public String getPassword()
    {
        return (this.password);
    }
    
    public void	setToken(String _token)
    {
        this.token = _token;
    }
    
    @Override
    public String getToken()
    {
        return (this.token);
    }
    
    public void	setId(String _id)
    {
        this.id = _id;
    }
    
    @Override
    public String getId()
    {
        return (this.id);
    }
    
    @Override
    public boolean isEqual(Object object)
    {
        iConfig obj = null;
        if (object instanceof iConfig) {
            obj = (iConfig)object;
        } else {
            return (false);
        }
        if (obj == null) {
            return (false);
        }
        obj.getLang();
        return (
                (obj.getLang().equals(this.getLang())) &&
                (obj.getMaximized() == this.getMaximized()) &&
                (obj.getWidth() == this.getWidth()) &&
                (obj.getHeight() == this.getHeight()) &&
                (obj.getAutoConnect()== this.getAutoConnect())
                );
    }
}
