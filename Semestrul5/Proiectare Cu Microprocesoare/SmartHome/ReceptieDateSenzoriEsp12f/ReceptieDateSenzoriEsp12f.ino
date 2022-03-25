#include <ESP8266WiFi.h>

String  inputString = "", values;
bool stringComplete = false;
int value1 = LOW;
int value2 = LOW;
String temp , humi , lightS , airQ;

const char* ssid = "Andrei";
const char* password = "casa4ap1";

WiFiServer server(80);

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  Serial.println("Connecting to the Network");
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);

  }
  Serial.println("WiFi connected");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());



  server.begin();  // Starts the Server



}

void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();
    if (inChar != '\n') {
      inputString += inChar;
    }
    if (inChar == '\n') {
      stringComplete = true;
    }
  }
}
void loop() {

  if (stringComplete == true) {
    values = inputString;
    int fristCommaIndex = values.indexOf(',');
    int secondCommaIndex = values.indexOf(',', fristCommaIndex + 1);
    int thirdCommaIndex = values.indexOf(',', secondCommaIndex + 1);
    int fourthCommaIndex = values.indexOf(',', thirdCommaIndex + 1);

    temp = values.substring(0, fristCommaIndex);
    humi = values.substring(fristCommaIndex + 1, secondCommaIndex);
    lightS = values.substring(secondCommaIndex + 1, thirdCommaIndex);
    airQ = values.substring(thirdCommaIndex + 1);
    inputString = "";
    stringComplete = false;
  }

  WiFiClient client = server.available();
  if (!client) {
    return;
  }

  String request = client.readStringUntil('\r');
  client.flush();

  //becul1
  if (request.indexOf("/LED1=ON") != -1) {
    Serial.println("aprinde led1");
    value1 = HIGH;
  }

  if (request.indexOf("/LED1=OFF") != -1) {
    Serial.println("stinge led1");
    value1 = LOW;
  }
  //becul 2
  if (request.indexOf("/LED2=ON") != -1) {
    Serial.println("aprinde led2");
    value2 = HIGH;
  }

  if (request.indexOf("/LED2=OFF") != -1) {
    Serial.println("stinge led2");
    value2 = LOW;
  }

    client.println("HTTP/1.1 200 OK");
    client.println("Content-Type: text/html");
    client.println("");
    String html = "<!DOCTYPE html> <html> <head> <title>SMART HOME</title> <script> function refresh(refreshPeriod) { setTimeout('location.reload(true)', refreshPeriod); } window.onload = refresh(5000); </script> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"> <style> html {font-family: Arial; display: inline-block; text-align: center;} h1 {font-size: 4.0rem;} h2 {font-size: 1.0rem;} h3 {font-size: 0.80rem;} h4 {font-size: 2.0rem;} body {max-with: 600px; margin:0px auto; padding-bottom: 25px;} .button { background-color: #4CAF50; /* Green */ border: none; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; } </style> <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"> </head> <body>";;
    client.println(html);
    client.println("<h1>SMART HOME <i class=\"fa fa-home\"></i></h1>");
    client.print("<h2><i class=\"fa fa-medkit\" ></i> Calitatea aerului: ");
    client.print(airQ);
    client.println(" PPM</h2>");
    client.print("<h2><i class=\"fa fa-thermometer-3\"></i> Temperatura: ");
    client.print(temp);
    client.println(" &degC </h2>");
    client.print("<h2><i class=\"fa fa-tint\"></i> Umiditatea: ");
    client.print(humi);
    client.println(" %</h2>");
    client.print("<h2>Nivel luminozitate: ");
    client.print(lightS);
    client.println(" %</h2>");

    client.print("<h2>CONTROL LED1: ");
    if (value1 == HIGH) {
      client.println("ON</h2>");
    } else {
      client.println("OFF</h2>");
    }
    client.println("<a href=\\\"/LED1=ON\\\"\\\"><button class=\"button\">ON</button></a>");
    client.println("<a href=\\\"/LED1=OFF\\\"\\\"><button class=\"button\">OFF</button></a>");

    client.print("<h2>CONTROL LED2: ");
    if (value2 == HIGH) {
      client.println("ON</h2>");
    } else {
      client.println("OFF</h2>");

    }
    client.println("<a href=\\\"/LED2=ON\\\"\\\"><button class=\"button\">ON</button></a>");
    client.println("<a href=\\\"/LED2=OFF\\\"\\\"><button class=\"button\">OFF</button></a>");

    client.println("</body>");
    client.println("</html>");


}
