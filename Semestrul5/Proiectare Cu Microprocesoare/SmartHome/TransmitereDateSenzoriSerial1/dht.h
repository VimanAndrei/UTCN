#include <stdio.h>
#include <math.h>

int DHT11_Pin; // DHT11 Data Pin

double Humidity = 0.0;
double Temp = 0.0;

void initializeDHTPin(int pin) {
  DHT11_Pin = pin;
}

double returnTemperature(){
  return Temp;
}
double returnHumidity(){
  return Humidity;
}

void DelayTimer(long int DelayValue) {
  long int DelayTime = micros();
  do {

  } while (micros() - DelayTime < DelayValue);
}

double transform(int n) {
  double rez = double(n);
  while (n != 0) {
    rez = rez / 10.0f;
    n = n / 10;
  }
  return rez;
}

int twoPow(int n){
  int rez = 1;
  if ( n == 0 ) return rez;
  else{
    while ( n > 0){
      rez*=2;
      n--;
    }
  }
  return rez;
}


void DHT11() {

  long int DataTime = 0;

  byte Result[45];
  byte DataArray = 0;
  byte DataCounter = 0;

  bool BlockDHT = false;

  pinMode(DHT11_Pin, OUTPUT);
  digitalWrite(DHT11_Pin, HIGH);
  DelayTimer(250000); 
  digitalWrite(DHT11_Pin, LOW);
  DelayTimer(20000);   
  digitalWrite(DHT11_Pin, HIGH);
  DelayTimer(40); 
  pinMode(DHT11_Pin, INPUT);


  // read the Bits and put them into a Result array (It will count 42 bits. The first two one are useless due my code)

  do {
    if (digitalRead(DHT11_Pin) == 0 && BlockDHT == false) {
      BlockDHT = true;  //If DHT pin is low, go to next Dataset
      Result[DataArray] = (micros() - DataTime);
      DataArray++;
      DataTime = micros();
    }
    if (digitalRead(DHT11_Pin) == 1) {
      BlockDHT = false; // As long as DHT pin is Hight add time in Microseconds to Result
    }


  } while ((micros() - DataTime) < 150); // if DTH Sensor high for more than 150 usec, leave loop

  // Asign 1 or 0 to Result variable. If more than 80uS Data as "1"
  // Starting at Data set 02. First two Datasets are ignored!

  for (int  i = 2; i < DataArray; i++) {
    if (Result[i] <= 90) Result[i] = 0; else Result[i] = 1;
    //Serial.print(Result[i]); Serial.print(" ");
  }


  int it = 0, ft = 0, ih = 0, fh = 0;
  int index = 2;
  for (int i = 0; i < 8; i++) {
    int sr = 0;
    if (Result[index] == 1) {
      sr = twoPow(7-i);
      ih += sr;
    }
    index++;
  }
  for (int i = 0; i < 8; i++) {
    int sr = 0;
    if (Result[index] == 1) {
      sr = twoPow(7-i);
      fh += sr;
    }
    index++;

  }
  for (int i = 0; i < 8; i++) {
    int sr = 0;
    if (Result[index] == 1) {
      sr = twoPow(7-i);
      it += sr;
    }
    index++;
  }

  for (int i = 0; i < 8; i++) {
    int sr = 0;
    if (Result[index] == 1) {
      sr = twoPow(7-i);
      ft += sr;
    }
    index++;
  }

  Temp = float(it) + transform(ft);
  Humidity = (float)ih + transform(fh);
}
