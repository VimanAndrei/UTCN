
int MQ135_Pin;

void initializeMQPin(int pin) {
  MQ135_Pin = pin;
  pinMode(MQ135_Pin,INPUT);
}

float readMQ() {
  float val = analogRead(MQ135_Pin);
  return val * 10;
}
