int LIGHT_Pin;

void initializeLIGHTPin(int pin) {
  LIGHT_Pin = pin;
  pinMode(LIGHT_Pin,INPUT);
}

float readLIGHT() {
  float val = analogRead(LIGHT_Pin);
  return (val*100.0)/float(1005);
}
