/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package utils.streaming;

import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacpp.*;

/**
 *
 * @author Madeline
 */
public class Recorder {
    public static FFmpegFrameRecorder getRecorder(String outputFileName, double frameRate, int outWidth, int outHeight) {
        FFmpegFrameRecorder recorder = null;
		try {
			recorder = FFmpegFrameRecorder.createDefault(outputFileName, outWidth, outHeight);
			recorder.setFormat("flv");

	        recorder.setFrameRate(frameRate);

//	        recorder.setVideoOption("crf", "28"); //where 0 is lossless, 23 is default

	        recorder.setVideoOption("tune", "zerolatency");
	        recorder.setVideoOption("preset", "fast");
	        recorder.setVideoOption("fflags", "nobuffer");
	        recorder.setVideoOption("analyzeduration", "0");


	        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
//	        recorder.setVideoQuality(0); // 千万不要设置高质量。。花的你不要不要的。。
	        recorder.setVideoOption("x264opts", "keyint=25:min-keyint=25:scenecut=-1");

	        recorder.setVideoOption("threads", "0");



//	        recorder.setAudioCodec(org.bytedeco.javacpp.avcodec.AV_CODEC_ID_AAC);
//	        recorder.setAudioChannels(2);
	        return recorder;
		} catch (org.bytedeco.javacv.FrameRecorder.Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
