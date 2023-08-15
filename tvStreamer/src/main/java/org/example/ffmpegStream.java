package org.example;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.io.FileUtils.copyFile;

public class ffmpegStream extends Thread{
    public static Path streamFilesLocation = Path.of(new File(".").getAbsolutePath()+
            "/tvStreamer/src/main/java/org/example/stream/video").toAbsolutePath();
    public static String channelFiles = String.valueOf(Path.of(new File(".").getAbsolutePath() +
            "/tvStreamer/src/main/java/org/example/stream/channels").toAbsolutePath()) + "/";
    public ffmpegStream() throws IOException, InterruptedException {
        FileUtils.deleteDirectory(new File(Path.of(new File(".").getAbsolutePath()
                +"/tvStreamer/src/main/java/org/example/stream/video/").toUri()));
        new File(String.valueOf(new File(Path.of(new File(".").getAbsolutePath()
                +"/tvStreamer/src/main/java/org/example/stream/video/").toUri()).mkdirs()));
        /*List<String> command = new ArrayList<>();
        command.add("ffmpeg ");
        command.add("-rtbufsize 1000M ");
        command.add("-f dshow ");
        command.add("-i video=\"USB Video\":audio=\"Digital Audio Interface (4- USB Digital Audio)\" ");
        command.add("-c:v h264_qsv ");
        command.add("-crf 19 ");
        command.add("-preset veryfast");
        command.add("-c:a aac -b:a 128k -ac 2 " );
        command.add("-vf " );
        command.add("scale=1280x720 ");
        command.add("-hls_list_size 3 ");
        command.add("-hls_flags delete_segments+append_list ");
        command.add("-segment_time 0 ");
        command.add("-hls_time 0 ");
        command.add(streamFilesLocation+"/out.m3u8 ");*/
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
                streamFilesLocation+"/out.m3u8 ";
        System.out.println(command);
        Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe");
        TimeUnit.SECONDS.sleep(3);
        //Process exec = new ProcessBuilder(command).start();
        //long pid = exec.pid();
        //System.out.println("Stream pid: "+ pid +" started!");
        Runtime.getRuntime().exec(command);
    }
}
