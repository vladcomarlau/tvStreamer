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
        var alternativeCommand = " cmd.exe /c start " + //-itsoffset -6   pt sound offset
                "ffmpeg " +
                "-rtbufsize 100M " +
                "-f dshow " +
                "-i video=\"" +
                "USB Video\":audio=\"Digital Audio Interface (4- USB Digital Audio)\" " +
                "-c:v libx264 -crf 21 -preset veryfast "+
                "-c:a aac -b:a 128k -ac 2 " +
                "-hls_time 4 -hls_playlist_type event " +
                "" + streamFilesLocation + "\"\\out.m3u8\"";

        var command = " cmd.exe /c start " + //-itsoffset -6   pt sound offset
                "ffmpeg " +
                "-rtbufsize 1000M " +
                "-f dshow " +
                "-i video=\"" +
                "USB Video\":audio=\"Digital Audio Interface (4- USB Digital Audio)\" " +
                "-c:v h264_qsv -crf 19 -preset veryfast "+
                "-c:a aac -b:a 128k -ac 2 " +
                "-vf scale=1280x720 " +
                "-hls_list_size 3 " +
                "-hls_flags delete_segments+append_list " +
                "-segment_time 0  " +
                "-hls_time 1 " +
                streamFilesLocation+"/out.m3u8";
        System.out.println(command);
        Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe");
        TimeUnit.SECONDS.sleep(3);
        Runtime.getRuntime().exec(command);
    }
    public static void ffmpegScreenshot(String channel) throws IOException {
        try {
            sleep(6000);
            var channelFiles = Path.of(new File(".").getAbsolutePath()+
                    "/tvStreamer/src/main/java/org/example/stream/channels").toAbsolutePath();
            var command = "cmd.exe /c " +
                    "ffmpeg -y -i http://192.168.0.148:8080/video/out.m3u8 " +
                    " -filter:v \"crop=262:23:840:177\" " +
                    "-vframes 1 -q:v 2 " +
                    channelFiles + "/" + channel.substring(0,channel.length()-1)+".jpeg";
            System.out.println(command);
            Runtime.getRuntime().exec(command);
            Thread.currentThread().interrupt();
        } catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
