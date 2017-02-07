 #include "FastLED.h"
#include <SoftwareSerial.h>

int bluetoothTx = 2;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 3;  // RX-I pin of bluetooth mate, Arduino D3

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

// How many leds in your strip?
#define NUM_LEDS 14

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806 define both DATA_PIN and CLOCK_PIN
#define DATA_PIN 4
#define CLOCK_PIN 13

// Define the array of leds
#define NUM_FRETTES 20
CRGB leds[NUM_LEDS];
int dot = 0;
int brightness = 128;

int incomingByte1 = 0; // corde [1,6]
int incomingByte2 = 0; // frette [1,NUM_FRETTES] (20, 21, 22, 23 ou 24 selon le modèle de guitare)
int incomingByte3 = 0; // doigt [1,5]
int sensorValue = analogRead(A0);

void setup() { 
  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
  Serial.begin(9600);
  bluetooth.begin(9600);  // Start bluetooth serial at 9600
  
  leds[0] = CHSV(0, 255, 128);
  LEDS.show();
  
}

void loop() {
  while (bluetooth.available() == 0) {
    delay(1);
  }
  incomingByte1 = bluetooth.read()- '0';
  
  while (bluetooth.available() == 0) {
    delay(1);
  }
  incomingByte2 = bluetooth.read()- '0';
  
  while (bluetooth.available() == 0) {
    delay(1);
  }
  incomingByte3 = bluetooth.read()- '0';

 

  // Test code : 0,0,X
  if (incomingByte1 == 0 && incomingByte1 == 0) {
    // LEDs test
    if (incomingByte3 == 1) {
      leds[0] = CRGB::White;
      LEDS.show();
      delay(250);
      for (int i=0;i<NUM_LEDS-1;i++) {
        leds[i+1] = CRGB::White;
        leds[i] = CRGB::Black;
        LEDS.show();
        delay(250);
      }
      leds[NUM_LEDS-1] = CRGB::Black;
      LEDS.show();
    }

    // Battery test
    else if (incomingByte3 == 2) {
      float voltage = sensorValue * (5.0 / 1023.0);
      if (voltage<4) {
        Serial.println(voltage);
        bluetooth.write("1");
      }
    }
  }

  // Set code : 0,1,X
  else if (incomingByte1 == 0 && incomingByte1 == 1) {
    brightness = incomingByte3;
  }

  
  else {
    // Color definition
    int hue;
    if (incomingByte3 == 1) {
      hue = 128;
    }
    else if (incomingByte3 == 2) {
      hue = 0;
    }
    else if (incomingByte3 == 3) {
      hue = 64;
    }
    else if (incomingByte3 == 4) {
      hue = 96;
    }
    else {
      hue = 160;
    }

    CHSV color = CHSV(hue, 255, brightness);
    leds[NUM_FRETTES*(incomingByte1-1)+incomingByte2-1] = color;
    LEDS.show();
  }
}

