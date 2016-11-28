#include "FastLED.h"

// How many leds in your strip?
#define NUM_LEDS 14

// For led chips like Neopixels, which have a data line, ground, and power, you just
// need to define DATA_PIN.
#define DATA_PIN 3

// Define the array of leds
CRGB leds[NUM_LEDS];

void setup() { 
  FastLED.addLeds<WS2812B, DATA_PIN, RGB>(leds, NUM_LEDS);
}

void loop() { 
  for(int dot = 0; dot < NUM_LEDS; dot++) { 
    leds[dot] = CRGB::Blue;
    FastLED.show();
    // clear this led for the next time around the loop
    leds[dot] = CRGB::Black;
    delay(30);
  }
}
