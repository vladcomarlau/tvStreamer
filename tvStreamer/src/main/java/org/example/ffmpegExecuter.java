package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class ffmpegExecuter extends Thread{
    public ffmpegExecuter() throws IOException, InterruptedException {
        FileUtils.deleteDirectory(new File(Path.of(new File(".").getAbsolutePath()
                +"/tvStreamer/src/main/java/org/example/stream/video/").toUri()));
        new File(String.valueOf(new File(Path.of(new File(".").getAbsolutePath()
                +"/tvStreamer/src/main/java/org/example/stream/video/").toUri()).mkdirs()));

        var streamFilesLocation = Path.of(new File(".").getAbsolutePath()+
                "/tvStreamer/src/main/java/org/example/stream/video").toAbsolutePath();
        var command = " cmd.exe /c start " + //-itsoffset -6   pt sound offset
                "ffmpeg -f dshow -framerate 24 -use_wallclock_as_timestamps true -pixel_format yuyv422 -i video=\"" +
                "USB Video\":audio=\"Digital Audio Interface (USB Digital Audio)\" -vsync cfr -b:v 2M -b:a 128k " +
                "-vf scale=1280x720 -hls_list_size 5 -hls_flags delete_segments+append_list -sc_threshold 0 -g 8 " +
                "-force_key_frames \"expr:gte(t, n_forced)\" -segment_time 0  -hls_time 1 " +
                streamFilesLocation+"/out.m3u8";
        System.out.println(command);
        Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe");
        TimeUnit.SECONDS.sleep(3);
        Runtime.getRuntime().exec(command);
    }
    public static void ffmpegScreenshot(String channel) throws IOException {
        var threadScreenshot = new Thread(){
            public void run(){
                try {
                    Thread.sleep(6000);
                    var channelFiles = Path.of(new File(".").getAbsolutePath()+
                            "/tvStreamer/src/main/java/org/example/stream/channels").toAbsolutePath();
                    var command = "cmd.exe /c " +
                            "ffmpeg -y -i http://192.168.0.148:8080/video/out.m3u8 " +
                            " -filter:v \"crop=262:23:840:177\" " +
                            "-vframes 1 -q:v 2 " +
                            channelFiles + "/" + channel.substring(0,channel.length()-1)+".jpeg";
                    System.out.println(command);
                    Runtime.getRuntime().exec(command);
                } catch (InterruptedException ie)
                {
                    Thread.currentThread().interrupt();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        threadScreenshot.start();
    }
}
