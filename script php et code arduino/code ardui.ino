#define LEDPIN 9
#define LEDPIN2 11 
#define LEDPIN3 10 

char L1=0;
boolean ledon = false;
boolean ledon2 = false;
boolean ledon3 = false;


void setup() {
  pinMode(LEDPIN, OUTPUT);
  pinMode(LEDPIN2 , OUTPUT);
  pinMode(LEDPIN3 , OUTPUT);
  
  Serial.begin(9600);
  analogReference(INTERNAL);
}

void loop() {
  if (Serial.available() > 0){
    L1 = Serial.read();
    switch (L1) {
     case 'A':
         analogWrite(LEDPIN,255);
         ledon = true;
         break;
    case 'B':
          analogWrite(LEDPIN,0);
          ledon = false;
          break;
    case 'C':
         analogWrite(LEDPIN2,255);
          ledon2 = true;
          break;
    case 'D':
          analogWrite(LEDPIN2,0);
          ledon2 = false;
          break;
    case 'E':
          analogWrite(LEDPIN3,255);
          ledon3 = true;
          break;
    case 'F':
          analogWrite(LEDPIN3,0);
          ledon3 = false;
          break;
     case '0':
          if(ledon3==true){
          analogWrite(LEDPIN3,0);}
          if(ledon2==true){
          analogWrite(LEDPIN2,0);}
          if(ledon==true){
          analogWrite(LEDPIN,0);}
          break;
    case '1':
          if(ledon3==true){
          analogWrite(LEDPIN3,10);}
          if(ledon2==true){
          analogWrite(LEDPIN2,10);}
          if(ledon==true){
          analogWrite(LEDPIN,10);}
          break;
    case '2':
          if(ledon3==true){
          analogWrite(LEDPIN3,50);}
          if(ledon2==true){
          analogWrite(LEDPIN2,50);}
          if(ledon==true){
          analogWrite(LEDPIN,50);}
          break;
     case '3':
          if(ledon3==true){
          analogWrite(LEDPIN3,90);}
          if(ledon2==true){
          analogWrite(LEDPIN2,90);}
          if(ledon==true){
          analogWrite(LEDPIN,90);}
          break;
       case '4':
          if(ledon3==true){
          analogWrite(LEDPIN3,130);}
          if(ledon2==true){
          analogWrite(LEDPIN2,130);}
          if(ledon==true){
          analogWrite(LEDPIN,130);}
          break;
        case '5':
          if(ledon3==true){
          analogWrite(LEDPIN3,160);}
          if(ledon2==true){
          analogWrite(LEDPIN2,160);}
          if(ledon==true){
          analogWrite(LEDPIN,160);}
          break;
       case '6':
          if(ledon3==true){
          analogWrite(LEDPIN3,190);}
          if(ledon2==true){
          analogWrite(LEDPIN2,190);}
          if(ledon==true){
          analogWrite(LEDPIN,190);}
          break;
       case '7':
          if(ledon3==true){
          analogWrite(LEDPIN3,210);}
          if(ledon2==true){
          analogWrite(LEDPIN2,210);}
          if(ledon==true){
          analogWrite(LEDPIN,210);}
          break;
       case '8':
          if(ledon3==true){
          analogWrite(LEDPIN3,230);}
          if(ledon2==true){
          analogWrite(LEDPIN2,230);}
          if(ledon==true){
          analogWrite(LEDPIN,230);}
          break;
       case '9':
          if(ledon3==true){
          analogWrite(LEDPIN3,255);}
          if(ledon2==true){
          analogWrite(LEDPIN2,255);}
          if(ledon==true){
          analogWrite(LEDPIN,255);}
          break;
       
    default:
          break;
    }
 
    
  }
}
