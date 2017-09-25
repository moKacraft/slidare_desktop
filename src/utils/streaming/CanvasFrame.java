package utils.streaming;

import controller.Main;
import utils.streaming.Controller;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_ProfileRGB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.APIManager;
import service.ConfigManager;
import service.PackageManager;
import service.ServiceFactory;

/**
 *
 * @author Samuel Audet
 *
 * Make sure OpenGL or XRender is enabled to get low latency, something like
 *      export _JAVA_OPTIONS=-Dsun.java2d.opengl=True
 *      export _JAVA_OPTIONS=-Dsun.java2d.xrender=True
 */
public class CanvasFrame extends JFrame {
    
    private String[] users;
    JList list;
    
    private static Settings settings = Settings.getSettingInstance();
//    private static SettingDialog settingDialog = null;
    private Controller controller = null;
    
    public static class Exception extends java.lang.Exception
    {
        public Exception(String message) { super(message); }
        public Exception(String message, Throwable cause) { super(message, cause); }
    }
    
    public static String[] getScreenDescriptions()
    {
        GraphicsDevice[] screens = getScreenDevices();
        String[] descriptions = new String[screens.length];
        for (int i = 0; i < screens.length; i++) {
            descriptions[i] = screens[i].getIDstring();
        }
        return descriptions;
    }
    
    public static DisplayMode getDisplayMode(int screenNumber)
    {
        GraphicsDevice[] screens = getScreenDevices();
        if (screenNumber >= 0 && screenNumber < screens.length) {
            return screens[screenNumber].getDisplayMode();
        } else {
            return null;
        }
    }
    
    public static double getGamma(int screenNumber)
    {
        GraphicsDevice[] screens = getScreenDevices();
        if (screenNumber >= 0 && screenNumber < screens.length) {
            return getGamma(screens[screenNumber]);
        } else {
            return 0.0;
        }
    }
    
    public static double getDefaultGamma()
    {
        return getGamma(getDefaultScreenDevice());
    }
    
    public static double getGamma(GraphicsDevice screen)
    {
        ColorSpace cs = screen.getDefaultConfiguration().getColorModel().getColorSpace();
        if (cs.isCS_sRGB()) {
            return 2.2;
        } else {
            try {
                return ((ICC_ProfileRGB)((ICC_ColorSpace)cs).getProfile()).getGamma(0);
            } catch (RuntimeException e) { }
        }
        return 0.0;
    }
    
    public static GraphicsDevice getScreenDevice(int screenNumber) throws Exception
    {
        GraphicsDevice[] screens = getScreenDevices();
        if (screenNumber >= screens.length) {
            throw new Exception("CanvasFrame Error: Screen number " + screenNumber + " not found. " +
                    "There are only " + screens.length + " screens.");
        }
        return screens[screenNumber];//.getDefaultConfiguration();
    }
    
    public static GraphicsDevice[] getScreenDevices()
    {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    }
    
    public static GraphicsDevice getDefaultScreenDevice()
    {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    }
    
    public CanvasFrame(String title)
    {
        this(title, 0.0);
    }
    
    public CanvasFrame(String title, double gamma)
    {
        super(title);
        init(false, null, gamma);
    }
    
    public CanvasFrame(String title, GraphicsConfiguration gc)
    {
        this(title, gc, 0.0);
    }
    
    public CanvasFrame(String title, GraphicsConfiguration gc, double gamma)
    {
        super(title, gc);
        init(false, null, gamma);
    }
    
    public CanvasFrame(String title, int screenNumber, DisplayMode displayMode) throws Exception
    {
        this(title, screenNumber, displayMode, 0.0);
    }
    
    public CanvasFrame(String title, int screenNumber, DisplayMode displayMode, double gamma) throws Exception
    {
        super(title, getScreenDevice(screenNumber).getDefaultConfiguration());
        init(true, displayMode, gamma);
    }
    
    private void init(final boolean fullScreen, final DisplayMode displayMode, final double gamma)
    {
        this.setSize(500, 200);
        
        Runnable r = new Runnable() { public void run() {
            
            GraphicsDevice gd = getGraphicsConfiguration().getDevice();
            DisplayMode d = gd.getDisplayMode(), d2 = null;
            if (displayMode != null && d != null) {
                int w = displayMode.getWidth();
                int h = displayMode.getHeight();
                int b = displayMode.getBitDepth();
                int r = displayMode.getRefreshRate();
                d2 = new DisplayMode(w > 0 ? w : d.getWidth(), h > 0 ? h : d.getHeight(), b > 0 ? b : d.getBitDepth(), r > 0 ? r : d.getRefreshRate());
            }
            if (fullScreen) {
                setUndecorated(true);
                getRootPane().setWindowDecorationStyle(JRootPane.NONE);
                setResizable(false);
                gd.setFullScreenWindow(CanvasFrame.this);
            } else {
                setLocationByPlatform(true);
            }
            if (d2 != null && !d2.equals(d)) {
                gd.setDisplayMode(d2);
            }
            double g = gamma == 0.0 ? getGamma(gd) : gamma;
//            inverseGamma = g == 0.0 ? 1.0 : 1.0/g;

// Must be called after the fullscreen stuff, but before
// getting our BufferStrategy or even creating our Canvas
setVisible(true);
//setVisible(false);

System.out.println("BEFOR INITCANVAS");


//initCanvas(fullScreen, displayMode, gamma);

initButtons();
        }};
        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            try {
                EventQueue.invokeAndWait(r);
            } catch (java.lang.Exception ex) { }
        }
    }
    
    public void setController(Controller outController)
    {
        controller = outController;
    }
    
    protected void initButtons()
    {
        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start Streaming");
        startButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.getStreamingState()==1) {
                    // isStreaming, Stop it
                    controller.stopRtmpRecorder();
                    startButton.setText("Start Streaming");
//
                } else {
                    // not Streaming, start it
                    controller.startRtmpRecorder();
                    startButton.setText("Stop Streaming");
                    System.out.println(list.getSelectedValuesList());
                    Main.socket.emit("send streaming", controller.url2, list.getSelectedValuesList());
                }
            }});
        JButton recordButton = new JButton("Start Recording");
        recordButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (controller.getRecordingState() == 1) {
                    controller.stopFileRecorder();
                    recordButton.setText("Start Recording");
                } else {
                    controller.startFileRecorder();
                    recordButton.setText("Stop Recording");
                }
            }});
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }});
        
        
        PackageManager packageManager = (PackageManager) ServiceFactory.getPackageManager();
        APIManager APIManager = (APIManager) ServiceFactory.getAPIManager();
        ConfigManager cfg = (ConfigManager) ServiceFactory.getConfigManager();
        List<JSONObject> listContacts = new ArrayList<JSONObject>();
        
        APIManager manager = (APIManager) ServiceFactory.getAPIManager();
        manager.userContacts(cfg.getConfig().getToken());
        
        packageManager.setJSONObject(APIManager.getLastResponse());
        JSONObject contacts = packageManager.getJSONOBject();
        JSONArray arr = (JSONArray) contacts.get("contacts");
        if (arr == null) {
            return;
        }
        users = new String[arr.size()];
        for (int i = 0;i < arr.size() ; i++) {
            listContacts.add(((JSONObject) arr.get(i)));
            users[i] = (String)((JSONObject) arr.get(i)).get("email");
            System.out.println(users[i]);
        }
        
        
        list = new JList(users);
        JScrollPane jsp = new JScrollPane(list);
        JPanel scroll = new JPanel();
        scroll.add(jsp);
        scroll.setSize(500, 200);
        add(jsp, BorderLayout.CENTER);
        
        panel.add(startButton);
        panel.add(exitButton);
        add(panel, BorderLayout.SOUTH);
    }
    
}