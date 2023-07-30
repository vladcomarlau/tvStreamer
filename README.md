# tvStreamer
Java TV set-top box controller and viewer
<br>
<img width="571" alt="Screenshot 2023-07-30 at 09 19 00" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/c51d0dc2-0f07-402b-8ff7-33da555d8817">

Features:
  - Real time HD TV cable streaming with ~6 seconds delay
  - Channel grid menu:
      - automatically updates channel name by taking a screenshot when changing channels
        (This way it stay updated evem if the TV operator adds/removes TV channels from the channel grid)
      - automatically sends corresponding number keys in order to jump between channels as fast as possible
  - Basic username and password authentication using built-in Simple File Server BasicAuthenticator
  - Mobile repsonsive, translucent UI
  - Other controls: channel +/-, channel information, power toggle, back, exit, fullscreen

Hardware used:
  - TV receiver brand KAON, model NA1410HD
  - PLUSIVO Micro ESP8266 with IR transmitter
  - HDMI USB capture card
    
Software used:
  - FFMPEG for HLS protocol live stream enconding (M3U8 / x-mpegURL manifest file with TS video fragments)
  - JAVA Simple File Server with multithreading for screenshot synchronization / waiting
  - jSerialComm for serial port direct USB communication with the ESP8266 microcontroller
  - Maven for dependency management
  - Mostly vanilla Javascript with few parts in JQuerry
  - Vanilla CSS and HTML5

<img width="579" alt="Screenshot 2023-07-30 at 09 53 18" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/5e88f055-ae74-4eea-9061-3fb084f7dfa2">




