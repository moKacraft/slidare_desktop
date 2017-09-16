/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package utils.streaming;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

/**
 *
 * @author Madeline
 */
public class Controller {
    private static Settings settings = Settings.getSettingInstance();
    private static FFmpegFrameRecorder rtmpRecorder = null;
    private static FFmpegFrameRecorder fileRecorder = null;
    private static int isStreaming = 0;
    private static int isRecording = 0;
    public static String url = "rtmp://34.227.142.101:1935/myapp/test2";
    
    public int getStreamingState()
    {
        return isStreaming;
    }
    
    public int getRecordingState()
    {
        return isRecording;
    }
    
    public Settings getSettings()
    {
        return settings;
    }
    
    public void recorder(Frame frame) throws org.bytedeco.javacv.FrameRecorder.Exception
    {
        if (isStreaming == 1) {
            rtmpRecorder.record(frame);
        }
        if (isRecording == 1) {
            fileRecorder.record(frame);
        }
    }
    
    public void startFileRecorder()
    {
        String outputFile = settings.getProperty("outputFile");
        double frameRate = Double.parseDouble(settings.getProperty("frameRate"));
        int outWidth = Integer.parseInt(settings.getProperty("outputWidth"));
        int outHeight = Integer.parseInt(settings.getProperty("outputHeight"));
        if (outputFile != null) {
            Date now = new Date();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMdd-HHmmss");
            if (!outputFile.endsWith("/")) {
                outputFile = outputFile + "/";
            }
            String outputFileName = outputFile + ft.format(now) + ".flv";
            System.out.println(outputFileName);
            fileRecorder = Recorder.getRecorder(outputFileName, frameRate, outWidth, outHeight);
            if (fileRecorder != null) {
                try {
                    fileRecorder.start();
                    isRecording = 1;
                } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void stopFileRecorder()
    {
        isRecording = 0;
        if (fileRecorder != null) {
            try {
                fileRecorder.stop();
                fileRecorder = null;
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void startRtmpRecorder()
    {
//        String url = settings.getProperty("url");
//        double frameRate = Double.parseDouble(settings.getProperty("frameRate"));
//        int outWidth = Integer.parseInt(settings.getProperty("outputWidth"));
//        int outHeight = Integer.parseInt(settings.getProperty("outputHeight"));
        
        String url = "rtmp://34.227.142.101:1935/myapp/test2";
        double frameRate = 15;
        int outWidth = 800;
        int outHeight = 500;
        if (url != null) {
            rtmpRecorder = Recorder.getRecorder(url, frameRate, outWidth, outHeight);
        }
        
        if (rtmpRecorder != null) {
            try {
                rtmpRecorder.start();
                isStreaming = 1;
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void stopRtmpRecorder()
    {
        isStreaming = 0;
        if (rtmpRecorder != null) {
            try {
                rtmpRecorder.stop();
                rtmpRecorder = null;
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void clean()
    {
        if (rtmpRecorder != null) {
            try {
                rtmpRecorder.stop();
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (fileRecorder != null) {
            try {
                fileRecorder.stop();
            } catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
