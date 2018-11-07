const int LED = 13;
const int AUDIO = 2;
long randNumber;

// the setup routine runs once when you press reset:
void setup() {
   // initialize the digital pin as an output.
   pinMode(LED, OUTPUT);
   pinMode(AUDIO, OUTPUT);
   Serial.begin(9600);
   randomSeed(analogRead(0));
   delay(30000);
}
long cas = 0;
long pocetCyklov = 0;
// the loop routine runs over and over again forever:
void loop() {
  if (cas == 0){
    cas = millis();
  }
  if (millis() >= cas + pocetCyklov * 1000){
    randNumber = random(8);
    if (randNumber == 0){
      tone(AUDIO, 220, 100);
      Serial.print("2");
      pocetCyklov++;
    } else {
      tone(AUDIO, 440, 100);
      Serial.print("0");
      pocetCyklov++;
    }
  }
}



