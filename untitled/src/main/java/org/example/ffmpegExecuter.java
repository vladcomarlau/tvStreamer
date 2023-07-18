package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

public class ffmpegExecuter extends Thread{
    public ProcessBuilder builder;
    public Process p;
    public ffmpegExecuter() throws IOException, InterruptedException {
        var streamFilesLocation = Path.of(new File(".").getAbsolutePath()+"/untitled/src/main/java/org/example/stream").toAbsolutePath();
        var command = " cmd.exe /c start " +
                "ffmpeg -f dshow -framerate 30 -pixel_format yuyv422 -i video=\"" +
                "USB Video\":audio=\"Digital Audio Interface (USB Digital Audio)\" -b:v 1.4M -b:a 96k " +
                "-vf scale=1280x720 -hls_list_size 1 -hls_flags delete_segments -sc_threshold 0 -g 5 " +
                "-force_key_frames \"expr:gte(t, n_forced * 1)\" -segment_time 0  -hls_time 1 " +
                streamFilesLocation+"/out.m3u8";
        System.out.println(command);
        Runtime.getRuntime().exec("taskkill /F /IM ffmpeg.exe");
        TimeUnit.SECONDS.sleep(3);
        Runtime.getRuntime().exec(command);
    }
}
