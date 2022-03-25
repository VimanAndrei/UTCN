#include <TimerOne.h>
#include "DHT.h"
#include "MQ135.h"
#include "LIGHT.h"

#define DHTPIN A0
#define LIGHTSENSOR A2
#define AIRSENSOR A1
#define LED1 2
#define LED2 3

volatile int citire = 0;

float temp = 0.0f, humi = 0.0f, lightS = 0.0f, airQ = 0.0f;

String output = "";
String delimitare = ",";

String  inputString = "";
bool stringComplete = false;
void setup() {
  Serial.begin(9600);
  Serial1.begin(9600);
  initializeDHTPin(DHTPIN);
  initializeMQPin(AIRSENSOR);
  initializeLIGHTPin(LIGHTSENSOR);
  pinMode(LED1, OUTPUT);
  pinMode(LED2, OUTPUT);
  Timer1.initialize(4500000);
  Timer1.attachInterrupt(readSensors);

}
void readSensors(void) {
  citire = 1;
}



void serialEvent1() {
  while (Serial1.available()) {
    char inChar = (char)Serial1.read();
    if (inChar != '\n') {
      inputString += inChar;
    }
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
}

void loop() {
  if (citire == 1) {
    citire = 0;
    DHT11();
    temp = returnTemperature();
    humi = returnHumidity();
    airQ = readMQ();
    lightS = readLIGHT();
    output = temp + delimitare + humi + delimitare + lightS + delimitare + airQ ;
    Serial1.println(output);
    output = "";
  }



  if (stringComplete == true) {
    Serial.println(inputString);
    if (strstr(inputString.c_str(), "aprinde led1")) {
      digitalWrite(LED1, HIGH);

    }
    if (strstr(inputString.c_str(), "stinge led1")) {
      digitalWrite(LED1, LOW);

    }
    //camera 2
    if (strstr(inputString.c_str(), "aprinde led2")) {
      digitalWrite(LED2, HIGH);

    }
    if (strstr(inputString.c_str(), "stinge led2")) {
      digitalWrite(LED2, LOW);

    }

    inputString = "";
    stringComplete = false;

  }

}
