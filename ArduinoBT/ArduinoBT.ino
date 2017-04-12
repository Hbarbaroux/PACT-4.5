#include "FastLED.h"
#include <SoftwareSerial.h>

// How many leds in your strip?
#define NUM_LEDS 14

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.  For led chipsets that are SPI based (four wires - data, clock,
// ground, and power), like the LPD8806 define both DATA_PIN and CLOCK_PIN
#define DATA_PIN 4
#define CLOCK_PIN 13

// Define the array of leds
CRGB leds[NUM_LEDS];

int bluetoothTx = 2;  // TX-O pin of bluetooth mate, Arduino D2
int bluetoothRx = 3;  // RX-I pin of bluetooth mate, Arduino D3

SoftwareSerial bluetooth(bluetoothTx, bluetoothRx);

char octet[4];

void setup() { 
      FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);
      leds[0] = CRGB::White;
      FastLED.show();
      bluetooth.begin(9600);  // Start bluetooth serial at 9600
      Serial.begin(9600);
  }

void loop() {
  /*
  for (int i=0;i<4;i++) {
    if(bluetooth.available())  // If the bluetooth sent any characters
      {
        octet[i] = (char)bluetooth.read();
      }
  }

  if (octet[3] == "1") {
    // Turn the LED on, then pause
    leds[0] = CRGB::Red;
    FastLED.show();
    delay(500);
    // Now turn the LED off, then pause
    leds[0] = CRGB::Black;
    FastLED.show();
    delay(500);
    // Back to white
    leds[0] = CRGB::White;
    FastLED.show();
  }
  */

  if(bluetooth.available())  // If the bluetooth sent any characters
  {
    // Send any characters the bluetooth prints to the serial monitor
    Serial.print((char)bluetooth.read());  
  }
  if(Serial.available())  // If stuff was typed in the serial monitor
  {
    // Send any characters the Serial monitor prints to the bluetooth
    bluetooth.print((char)Serial.read());
  }
}
