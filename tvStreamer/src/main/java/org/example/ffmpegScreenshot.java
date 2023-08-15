package org.example;
import java.io.IOException;
import java.util.Arrays;
import static org.example.ffmpegStream.channelFiles;
import static org.example.ffmpegStream.streamFilesLocation;

public class ffmpegScreenshot extends Thread{
    public String channel;
    public ffmpegScreenshot(String channelX){
        channel = channelX;
    }
    public void run(){
        String[] cmd = {"ffmpeg", "-y", "-i", streamFilesLocation+"/out.m3u8 ",
                        "-vframes", "1", "-filter:v", "\"crop=262:23:840:177\"",
                        channelFiles + channel.substring(0,channel.length()-1)+".jpeg"};
        window.textArea1.append("\n\n" + window.now() + "Taking screenshot of channel name:\n"+Arrays.toString(cmd)+"\n");
        try {
            sleep(18000);
            ProcessBuilder processBuilder1 = new ProcessBuilder(cmd);
            Process p = processBuilder1.start();
            long pid = p.pid();
            window.textArea1.append("\n" + window.now() + "Screenshot process PID "+ pid +" started!");
            sleep(9000);
            Runtime.getRuntime().exec("taskkill /F /PID " + pid);
            window.textArea1.append("\n" + window.now() + "Terminated screenshot process PID " + pid);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
