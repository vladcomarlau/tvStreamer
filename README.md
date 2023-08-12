# tvStreamer
Java TV set-top box controller and viewer
<br>
<img width="571" alt="1" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/4de1306c-dc4d-45a2-8588-c89c1ef891d4">


Features:
  - Real time HD TV cable streaming with ~10 seconds delay
  - Channel grid menu:
      - automatically updates channel name by taking a screenshot when changing channels
        (This way it stays updated evem if the TV operator adds/removes TV channels from the channel grid)
      - automatically sends corresponding number keys in order to jump between channels as fast as possible
  - Basic username and password authentication using built-in Simple File Server BasicAuthenticator
  - Mobile repsonsive, translucent UI
  - Other controls: power receiver on/off, channel information, channel +/-, back, exit, fullscreen, mute, play/pause

Requirements:
- FFMPEG installed and added to PATH variable

Hardware used:
  - TV receiver brand KAON, model NA1410HD
  - PLUSIVO Micro ESP8266 with added IR transmitter
  - HDMI USB capture card
    
Software used:
  - FFMPEG for HLS protocol live stream enconding (M3U8 / x-mpegURL manifest file with TS video fragments)
  - JAVA Simple File Server with multithreading for screenshot synchronization / waiting
  - jSerialComm for serial port direct USB communication with the ESP8266 microcontroller
  - Maven for dependency management
  - Mostly vanilla Javascript with few parts in JQuerry
  - Vanilla CSS and HTML5

<img width="579" alt="Screenshot 2023-07-30 at 09 53 18" src="https://github.com/vladcomarlau/tvStreamer/assets/102293760/5e88f055-ae74-4eea-9061-3fb084f7dfa2">




