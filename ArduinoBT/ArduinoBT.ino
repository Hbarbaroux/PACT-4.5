 #include "FastLED.h"
#include <SoftwareSerial.h>

int bluetoothTx = 3;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 2;  // RX-I pin of bluetooth mate, Arduino D3

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx); 

// How many leds in your strip?
#define NUM_LEDS 144

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806 define both DATA_PIN and CLOCK_PIN
#define DATA_PIN 4
#define CLOCK_PIN 13

// Define the array of leds

#define NUM_FRETTES 21
CRGB leds[NUM_LEDS];
int ledarray[]={1,6,11,15,19,23,27,30,34,37,40,43,45,48,50,52,54,56,58,60,62};
int ledarray2[]={1,3,5,7,9,11,13,15,18,20,23,26,29,33,36,40,44,48,52,57,62};
int dot = 0;
int brightness = 128;

int previousLed = 0;

int incomingByte1 = 0; // corde [1,6]
int incomingByte2 = 0; 
int incomingByte3 = 0;// frette [1,NUM_FRETTES] (20, 21, 22, 23 ou 24 selon le modèle de guitare)
int incomingByte4 = 0; // doigt [1,5]

int sensorValue = analogRead(A0);

void setup() { 
  FastLED.addLeds<WS2812B, DATA_PIN, GRB>(leds, NUM_LEDS);
  Serial.begin(9600);
  bluetooth.begin(9600);  // Start bluetooth serial at 9600
   
  leds[0] = CHSV(0, 255, 128);
  LEDS.show();
  LEDS.clear();
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
  
  while (bluetooth.available() == 0) {
    delay(1);
  }
  incomingByte4 = bluetooth.read()- '0';

 

  // Test code : 0,00，X 0001-testled 0002-testbattery
  if (incomingByte1 == 0 && incomingByte2 == 0 && incomingByte3 == 0) {
    // LEDs test
    if (incomingByte4 == 1) {
      leds[ledarray[0]-1] = CRGB::White;
      LEDS.show();
      delay(250);
      for (int i=0;i<20;i++) {
        leds[ledarray[i+1]-1] = CRGB::White;
        leds[ledarray[i]-1] = CRGB::Black;
        LEDS.show();
        delay(500);
      }
      leds[61] = CRGB::Black;
      LEDS.show();
    }

    // Battery test
    else if (incomingByte4 == 2) {
      float voltage = sensorValue * (5.0 / 1023.0);
      if (voltage<4) {
        Serial.println(voltage);
        bluetooth.write("1");
      }
    }
  }

  // Set code : 0,0,1,X
  else if (incomingByte1 == 0 && incomingByte2 == 0&& incomingByte2 == 1) {
    
    brightness = incomingByte3 * 14;
    CHSV color = CHSV(0, 255, brightness);
    FastLED.clear ();
    leds[0] = color;
    LEDS.show();
  }

  
  else {
    // Color definition by corde
    
    int hue;
    if (incomingByte1 == 1) {
      hue = 128;
    }
    else if (incomingByte1 == 2) {
      hue = 0;
    }
    else if (incomingByte1 == 3) {
      hue = 64;
    }
    else if (incomingByte1 == 4) {
      hue = 96;
    }
    else {
      hue = 160;
    }
    
    int lednumber=10 * incomingByte2 + incomingByte3;

    
    // Shift of hue on same note
    if (previousLed != lednumber) {
      previousLed = lednumber;
    }
    else {
      hue += 11; // Adjust to be visible enough without looking like another note
      previousLed = 0;
    }
    
    CHSV color = CHSV(hue, 255, brightness);
    FastLED.clear ();
    leds[ledarray[lednumber-1]-1] = color;
    LEDS.show();
  }
}

