%{
#include <stdio.h>
#include <stdlib.h>
#include <time.h>


char despreVreme[10][200] = {
        "Va fi frumos!\n>", 
        "Va fi caldura mare!\n>",
        "Va fi furtuna!\n>",
        "Va ploua putin!\n>",
        "Va fi innorat!\n>"
        "Va fi frumos dar pe seara va ploua!\n>", 
        "Va fi caldura sa nu uiti sa te hidratezi!\n>",
        "Va fi furtuna doar dimineata!\n>",
        "Va ploua putin dar seara in rest va fi senin!\n>",
        "Va fi innorat dar nu va ploua!\n>"
        };

char despreCuriozitati[50][600] = {
        "Candy Crush Saga este cea mai descărcată aplicație de jocuri din toate timpurile. Acest joc foarte captivant are peste 5 milioane de descărcări pe Facebook, IOS și pe Android",
        "Youtube este al doilea motor de căutare după Google. De remarcat este faptul că este chiar mai mare decât bing, ask.com, și decât Yahoo colectiv.",
        "Săgeata care rulează de la litera A la Z din Amazon indică faptul că totul este disponibil în acest magazin.",
        "Săgeata care rulează de la litera A la Z din Amazon arată, de asemenea, că firma poate să livreze produse oriunde în lume, în orice moment.",
        "Imprimanta Laser Color consuma de 3 ori mai mult decat un pc sau laptop performant.",
        "In anul 2012, în jur de 17 copii au fost botezați Siri.",
        "Primul logo Apple i-a fost atribuit lui Isaac Newton stând sub un copac cu un măr deasupra capului.",
        "90% din telefoanele din Japonia sunt impermeabile pentru a putea fi folosite chiar și la duș.",
        "Ziua de 30 noiembrie este recunoscută ca fiind ziua Securității Calculatoarelor în lumea tech.",
        "Părul ursului polar nu este alb, ci incolor. Firele de păr groase sunt transparente şi reflectă lumina, ceea ce face ca blana ursului polar să pară albă.",
        "O girafă are şapte oase în gât, precum oamenii, dar sunt mult mai mari.",
        "Ridurile de pe nasul gorilelor sunt unice pentru fiecare animal în parte.",
        "Nu există viermi de sex masculin sau feminin. Toţi viermii au atât părţi masculine, cât şi feminine.",
        "Dinţii unui liliac vampir sunt atât de ascuţiţi încât muşcătura sa nu poate fi resimţită deloc.",
        "Limba unui cameleon are cel puţin lungimea corpului lui, dar poate apuca prada într-o fracţiune de secundă.",
        "Ochii unui vultur au capacitatea de a vedea de cel puţin patru ori mai clar decât cei ai oamenilor.",
        "Bizonii adulţi sunt cele mai mari mamifere terestre din America de Nord.",
        "Pentru a zbura, păsările colibri îşi pot scutura aripile de aproximativ 200 de ori pe secundă.",
        "Pinguinii imperiali pot rămâne sub apă până la 27 de minute şi se pot scufunda la o adâncime de până la 500 de metri.",
        "Atunci când găsesc o sursă de apă, cămilele sălbatice bactriane (unele dintre cele mai rare mamifere de pe planetă) beau până la 50 de litri.",
        "Cea mai bună tensiune arterială, la adult, este 120/70 mmHg.",
        "Nu există cancer ca o singură entitate, ci peste 300 de tipuri de cancer, fiecare cu mecanisme proprii, celule diferite, răspunsuri distincte la terapii.",
        "Glanda este o denumire populară greșită a tiroidei; corpul are zeci de glande.",
        "Un om poate simți durere la nivelul unui membru superior sau inferior care nu mai există; se numește sindromul membrului fantomă.",
        "Creierul nu doare pentru că nu are nociceptori (receptori de durere). Se poate face așa numita brain awake surgery, fără anestezie generală.",
        "Cea mai bună frecvență cardiacă la care raportul performanță/consum de oxigen este ideal este de 55-65 bpm.",
        "Inima este primul organ funcțional și începe să pompeze sânge în a patra săptămână de viață intrauterină.",
        "Creierul consumă în regim bazal cam 25% din energia întregului organism.",
        "Unul dintre cele mai importante gesturi pe care le putem face pentru sănătate este spălatul pe mâini.",
        "Diabeticii pot face infarct miocardic acut fără durere, pentru că neuropatia diabetică le alterează transmiterea nervoasă."
};

 time_t t;
 struct tm tm;
 int ind  = 0;

%}

%token ENDPROP SAL CASA CE CUM TE NUMESTI CHEAMA APR IN LUMINA TOATA CAMERA BUC BAIE STINGE VREAU SA VAD UN LA TV MA MERG AFARA DE CAINE MEU II ESTE FOAME SETE REVE NE MAI AUVE POTI AJUTA VREMEA VREMCAND PLOUA SENIN INNOR IMI SPUI CURIOZ STI

%%
comenzi :
        | comenzi comenzi 
        ;
comenzi : salut_msg
        | interogari
        | instr_apr
        | instr_sti
        | actiuni_s
        | actiuni_h
        | actiuni_vreme
        | curiozitati
        | stop
        ;

salut_msg : SAL ENDPROP {printf("[CHAT]: SALUTARE!\n>");}
          | SAL CASA ENDPROP {printf("[CHAT]: Salutare! Cu ce te pot ajuta?\n> ");}
          ;

interogari : CUM TE NUMESTI ENDPROP {printf("[CHAT]: Eu sunt casa ta inteligenta a cheama CASUTA!\n>");}
           | CUM TE CHEAMA ENDPROP {printf("[CHAT]: Eu sunt casa ta inteligenta si ma cheama CASUTA!\n>");}
           | CUM MA POTI AJUTA ENDPROP {printf("[CHAT]: Te pot ajuta cu diferite cumenzi. Incearca - ma!\n>");}
           ;

instr_apr   : APR LUMINA IN TOATA CASA ENDPROP {printf("[CHAT]: Am aprins toate luminile!\n>");};
            | APR LUMINA IN CAMERA ENDPROP {printf("[CHAT]: Am aprins luminile in camera!\n>");};
            | APR LUMINA IN BUC ENDPROP {printf("[CHAT]: Am aprins lumina in bucatarie!\n>");};
            | APR LUMINA IN BAIE ENDPROP {printf("[CHAT]: Am aprins lumina in baie!\n>");}
            | APR TOATA LUMINA ENDPROP  {printf("[CHAT]: Am aprins toate luminile!\n>");}
            ;

instr_sti   : STINGE LUMINA IN TOATA CASA ENDPROP {printf("[CHAT]: Am stins toate luminile!\n>");};
            | STINGE LUMINA IN CAMERA ENDPROP {printf("[CHAT]: Am stins luminile in camera!\n>");};
            | STINGE LUMINA IN BUC ENDPROP {printf("[CHAT]: Am stins lumina in bucatarie!\n>");};
            | STINGE LUMINA IN BAIE ENDPROP {printf("[CHAT]: Am stins lumina in baie!\n>");}
            | STINGE TOATA LUMINA ENDPROP  {printf("[CHAT]: Am stins toate luminile!\n>");}
            ;

actiuni_s   : VREAU SA VAD UN TV ENDPROP  {printf("[CHAT]: Am stins toate luminile in camera si am aprins televizorul!\n>");}
            | VREAU SA MA VAD LA UN TV ENDPROP  {printf("[CHAT]: Am stins toate luminile in camera si am aprins televizorul!\n>");}
            | VREAU SA MA VAD LA TV ENDPROP  {printf("[CHAT]: Am stins toate luminile in camera si am aprins televizorul!\n>");}
            | VREAU SA MERG LA UN TV ENDPROP {printf("[CHAT]: Sting toate luminile si inchid usa!\n>");}
            | VREAU SA MERG AFARA ENDPROP {printf("[CHAT]: Sting toate luminile si inchid usa!\n>");}
            | VREAU SA MERG DE CASA ENDPROP {printf("[CHAT]: Sting toate luminile si inchid usa!\n>");}
            ;

actiuni_h   : CAINE MEU II ESTE FOAME ENDPROP {printf("[CHAT]: Acum ii dau de mancare!\n>");}
            | CAINE MEU II ESTE SETE ENDPROP {printf("[CHAT]: Acum ii dau de sa bea!\n>");}
            | II ESTE FOAME CAINE MEU ENDPROP {printf("[CHAT]: Acum ii dau de mancare!\n>");}
            | II ESTE SETE CAINE MEU ENDPROP {printf("[CHAT]: Acum ii dau de sa bea!\n>");}
            | II ESTE FOAME LA CAINE ENDPROP {printf("[CHAT]: Acum ii dau de mancare!\n>");}
            | II ESTE SETE LA CAINE ENDPROP {printf("[CHAT]: Acum ii dau de sa bea!\n>");}
            ;
actiuni_vreme: CUM ESTE VREMEA VREMCAND ENDPROP {
                                                        t = time(NULL);
                                                        tm = *localtime(&t);
                                                        ind = tm.tm_sec % 9;
                                                        printf("[CHAT]: %s",despreVreme[ind]);
                                                }
             | PLOUA VREMCAND ENDPROP {
                                                        t = time(NULL);
                                                        tm = *localtime(&t);
                                                        ind = tm.tm_sec % 2;
                                                        if(ind == 0){
                                                                printf("[CHAT]: Da, sa nu uit de umbrela!\n>");
                                                        }else{
                                                                printf("[CHAT]: Nu, ai nevoie de ochelari de soare deoarece este senin!\n>");                                                                
                                                        }
                     
                                      }
             | ESTE SENIN VREMCAND ENDPROP { 
                                                        t = time(NULL);
                                                        tm = *localtime(&t);
                                                        ind = tm.tm_sec % 2;
                                                        if(ind == 0){
                                                                printf("[CHAT]: Da, ai nevoie de protectie solara!\n>");
                                                        }else{
                                                                printf("[CHAT]: Nu, dar nu va ploua!\n>");                                                                
                                                        }
                                           }
             | ESTE IN INNOR VREMCAND ENDPROP {
                                                        t = time(NULL);
                                                        tm = *localtime(&t);
                                                        ind = tm.tm_sec % 2;
                                                        if(ind == 0){
                                                                printf("[CHAT]: Da, iti recomand umbrela!\n>");
                                                        }else{
                                                                printf("[CHAT]: Nu, va fi un timp foarte frumos!\n>");                                                                
                                                        }
                                              }
             ;

curiozitati : IMI SPUI CURIOZ ENDPROP {
                printf("[CHAT]: Da! Uite una:\n");
                t = time(NULL);
                tm = *localtime(&t);
                ind = tm.tm_sec % 30;
                printf("[CHAT]: %s\n>",despreCuriozitati[ind]);
            }

            | STI CURIOZ ENDPROP{
                printf("[CHAT]: Da! Uite una:\n");
                t = time(NULL);
                tm = *localtime(&t);
                ind = tm.tm_sec % 30;
                printf("[CHAT]: %s\n>",despreCuriozitati[ind]);
            }



stop  : LA REVE ENDPROP {printf("Iti doresc o zi buna!\n"); exit(0);}
      | NE MAI AUVE ENDPROP {printf("Iti doresc o zi buna!\n"); exit(0);}
%%



#include "lex.yy.c"

int main(){
 
        printf("------------------------------------------\n\n");
        printf("        Boot-chatul tau interactiv!        \n\n");
        printf("-------------------------------------------\n\n> ");
        yyparse();

}
int yyerror() {printf("Instructiune neacceptata! La revedere!\n");}