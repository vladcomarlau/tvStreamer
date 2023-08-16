package org.example;

import org.apache.commons.io.FileUtils;

import javax.swing.text.DefaultCaret;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class ffmpegStream extends Thread{
    public static Path streamFilesLocation = Path.of(new File(".").getAbsolutePath()+
            "/stream/video").toAbsolutePath();
    public static String channelFiles = String.valueOf(Path.of(new File(".").getAbsolutePath() +
            "/stream/channels").toAbsolutePath()) + "/";
    public ffmpegStream(String videoDevice, String audioDevice) throws IOException, InterruptedException {
        if(videoDevice == null || audioDevice == null || videoDevice.length()==0 || audioDevice.length() == 0){
            window.textArea1.append("\n" + window.now() + "Cannot start stream: Devices are not selected!");
        }
        else{
            FileUtils.deleteDirectory(new File(Path.of(new File(".").getAbsolutePath()
                    +"/stream/video/").toUri()));
            new File(String.valueOf(new File(Path.of(new File(".").getAbsolutePath()
                    +"/stream/video/").toUri()).mkdirs()));
            var command = " cmd.exe /c start " + //-itsoffset -6   pt sound offset
                    "ffmpeg " +
                    "-rtbufsize 1000M " +
                    "-f dshow " +
                    "-i video=\"" +
                    videoDevice +
                    "\":audio=\""+
                    audioDevice +
                    "\" " +
                    "-c:v h264_qsv -crf 19 -preset veryfast "+
                    "-c:a aac -b:a 128k -ac 2 " +
                    "-vf scale=1280x720 " +
                    "-hls_list_size 3 " +
                    "-hls_flags delete_segments+append_list " +
                    "-segment_time 0  " +
                    "-hls_time 1 " +
                    streamFilesLocation+"/out.m3u8 ";
            window.textArea1.append("\n" + window.now() +" Terminating any opened FFMPEG instances...");
            Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe");
            TimeUnit.SECONDS.sleep(3);

            window.textArea1.append("\n" + window.now() + "Starting FFMPEG...\n" + command+"\n");
            DefaultCaret caret = (DefaultCaret)window.textArea1.getCaret();
            caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
            Runtime.getRuntime().exec(command);
        }
    }
}
