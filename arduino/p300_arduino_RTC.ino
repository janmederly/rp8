#include <Wire.h>
#include "RTClib.h"
RTC_DS3231 rtc;
const byte rtcTimerIntPin = 2;
const int LED = 13;
const int AUDIO = 3;
long randNumber;
volatile byte flag = false;

// the setup routine runs once when you press reset:
void setup() {
   // initialize the digital pin as an output.
   pinMode(LED, OUTPUT);
   pinMode(AUDIO, OUTPUT);
   pinMode(rtcTimerIntPin, INPUT_PULLUP);
   Serial.begin(9600);
   if (! rtc.begin()) {
    Serial.println("Couldn't find RTC");
    while (1);
  }

//  if (rtc.lostPower()) {
//    Serial.println("RTC lost power, lets set the time!");
//    // following line sets the RTC to the date & time this sketch was compiled
//    rtc.adjust(DateTime(F(__DATE__), F(__TIME__)));
//  }

  Serial.print("#");
  delay(30000);
  Serial.println("*");

  rtc.writeSqwPinMode (DS3231_SquareWave1Hz);
  attachInterrupt (digitalPinToInterrupt (rtcTimerIntPin), rtc_interrupt, FALLING);
  randomSeed(analogRead(0));

  flag = false;
  while (!flag) {
  }
}

void rtc_interrupt ()
{
  flag = true;
}

int toneNumber = 0;

// the loop routine runs over and over again forever:
void loop() {
  if (flag){
    randNumber = random(8);
    if (randNumber == 0) {
      tone(AUDIO, 220, 100);
      Serial.print(toneNumber);
      Serial.println(": 2");
    } else {
      tone(AUDIO, 440, 100);
      Serial.print(toneNumber);
      Serial.println(": 0");
    }
    toneNumber++;
    flag = false; 
  }
}

