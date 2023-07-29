
#include <Arduino.h>
#include <IRremoteESP8266.h>
#include <IRsend.h>

/*
1 	Power 	0x41000FF		68157695
2 	1 		  0x41038C7		68171975
3 	2		    0x410B847		68204615
4 	3		    0x4107887		68188295
5 	4		    0x41002FD		68158205
6 	5		    0x410827D		68190845
7 	6		    0x41042BD		68174525
8 	7		    0x41022DD		68166365
9 	8		    0x410A25D		68199005
10 	9		    0x410629D		68182685
11	0		    0x410E21D		68215325
12	Ch+		  0x410609F		68182175
13	Ch-		  0x410E01F		68214815
14	Vol+		0x41040BF		68174015
15	Vol-		0x410C03F		68206655
16	i		    0x4100AF5		68160245
17	Left		0x41028D7		68167895
18	Right	  0x4106897		68184215
19	Up		  0x41048B7		68176055
20	Down		0x410C837		68208695
21	Ok		  0x410A857		68200535
22	Exit		0x41008F7		68159735
23	Opt		  0x410EA15		68217365
24	Back		0x41030CF		68169935
25	Guide	  0x410B04F		68202575
26	Media	  0x4105AA5		68180645
27	Menu		0x4108877		68192375
*/

const uint16_t kIrLed = 4;  // ESP8266 GPIO pin to use. Recommended: 4 (D2).
IRsend irsend(kIrLed);  // Set the GPIO to be used to sending the message.

void setup() {
  irsend.begin();
  Serial.begin(115200, SERIAL_8N1);
  //Serial.begin(115200, SERIAL_8N1, SERIAL_TX_ONLY);
}

void loop() {
  //Serial.println("Sent");
  //irsend.sendNEC(button[12]);
  //delay(100);
  String readString;
  while (Serial.available()>0) {
    char c = Serial.read();  // current char from serial
    readString += c; // add to string
    delay(2);  //slow looping to allow buffer to fill with next character
    if(Serial.available()==1){
        irsend.sendNEC(readString.toInt());
    }
  }
}


