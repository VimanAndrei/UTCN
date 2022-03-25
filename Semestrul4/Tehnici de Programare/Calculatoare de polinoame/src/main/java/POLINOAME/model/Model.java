package POLINOAME.model;

public class Model {

    private Polynomial polinom;

    public Model() {
        polinom=new Polynomial();
    }

    public void reset() {
        polinom.polinom.clear();
    }

    public void setValue(String value) {
        polinom.crearePolinom(value);
    }

    public String getValue() {
        return polinom.toString();
    }

    public void adunarePolinoame(String input2){
        //creem polinomul 2 din inputul utilizatorului
        Polynomial polinom1 = new Polynomial();
        polinom1.crearePolinom(input2);

        Polynomial rezultat = new Polynomial();


        int lenghtPolinom = polinom.getSize();
        int lenghtPolinom1 = polinom1.getSize();
        int i = 0, j = 0;
        Monomial a, b;

        while(i < lenghtPolinom && j < lenghtPolinom1){

            a=polinom.getPolinom().get(i);
            b=polinom1.getPolinom().get(j);

            if(a.getExponent() > b.getExponent()){
                rezultat.polinom.add(a);
                i++;
            }else{
                if(a.getExponent() < b.getExponent()){
                    rezultat.polinom.add(b);
                    j++;
                }else{

                   Monomial x = a.adunare(b);
                    rezultat.polinom.add(x);
                    i++;
                    j++;
                }
            }

        }

        while(i < lenghtPolinom){
            a=polinom.getPolinom().get(i);
            rezultat.polinom.add(a);
            i++;
        }

        while(j < lenghtPolinom1){
            b=polinom1.getPolinom().get(j);
            rezultat.polinom.add(b);
            j++;
        }

        polinom = rezultat;

    }


    public void scaderePolinoame2(String input2){

        Polynomial polinom1 = new Polynomial();
        polinom1.crearePolinom(input2);
        //inmultirea fiecarui element din polinom cu -1
        for(Monomial m: polinom1.polinom) {
            m.setCoeficient(m.getCoeficient()*-1);
        }

        Polynomial rezultat = new Polynomial();
        adunarePolinoame(polinom1.toString());

    }

    public void scaderePolinoame(String input2){
        //creem polinomul 2 din inputul utilizatorului
        Polynomial polinom1 = new Polynomial();
        polinom1.crearePolinom(input2);

        Polynomial rezultat = new Polynomial();


        int lenghtPolinom = polinom.getSize();
        int lenghtPolinom1 = polinom1.getSize();
        int i = 0, j = 0;
        Monomial a, b;

        while(i < lenghtPolinom && j < lenghtPolinom1){

            a=polinom.getPolinom().get(i);
            b=polinom1.getPolinom().get(j);

            if(a.getExponent() > b.getExponent()){
                rezultat.polinom.add(a);
                i++;
            }else{
                if(a.getExponent() < b.getExponent()){
                    b.setCoeficient((-1.0)*b.getCoeficient());
                    rezultat.polinom.add(b);
                    j++;
                }else{

                    Monomial x = a.scadere(b);
                    rezultat.polinom.add(x);
                    i++;
                    j++;
                }
            }

        }

        while(i < lenghtPolinom){
            a=polinom.getPolinom().get(i);
            rezultat.polinom.add(a);
            i++;
        }

        while(j < lenghtPolinom1){
            b=polinom1.getPolinom().get(j);
            b.setCoeficient((-1.0)*b.getCoeficient());
            rezultat.polinom.add(b);
            j++;
        }

        polinom = rezultat;

    }

    public void inmultirePolinoame(String input2){
        //creem polinomul 2 din inputul utilizatorului
        Polynomial polinom1 = new Polynomial();
        polinom1.crearePolinom(input2);

        Polynomial copy = new Polynomial();
        Monomial aux;
        /*
        for(monomial m : polinom.polinom){
            copy.polinom.add(m);
        }*/
        copy.polinom.addAll(polinom.polinom);

        polinom.polinom.clear();

        for(Monomial m1 : copy.polinom){
            Polynomial intermediar = new Polynomial();
            for(Monomial m2 : polinom1.polinom){
                 aux = m1.inmultire(m2);
                intermediar.polinom.add(aux);
            }
            this.adunarePolinoame(intermediar.toString());
        }

    }

    public void derivarePolinoame(){


        Polynomial copy = new Polynomial();

        /*
        for(monomial m : polinom.polinom) {
            copy.polinom.add(m);
        }*/
        copy.polinom.addAll(polinom.polinom);

        polinom.polinom.clear();

        for(Monomial m1 : copy.polinom){
            Monomial aux = new Monomial();
            double c;
            double e;
            c=m1.getCoeficient();
            e=m1.getExponent();
            c=c*e;
            e--;
            aux.setExponent(e);
            aux.setCoeficient(c);
            if(e>=0) {
                polinom.polinom.add(aux);
            }
        }

    }


    public void integrarePolinoame() {

        Polynomial copy = new Polynomial();

        /*
        for (monomial m : polinom.polinom) {
            copy.polinom.add(m);
        }*/
        copy.polinom.addAll(polinom.polinom);

        polinom.polinom.clear();

        for (Monomial m1 : copy.polinom) {
            Monomial aux = new Monomial();
            double c;
            double e;
            c = m1.getCoeficient();
            e = m1.getExponent();
            if (e >= 0) {
                e++;
                c = c / e;
                aux.setExponent(e);
                aux.setCoeficient(c);
                if (e >= 0) {
                    polinom.polinom.add(aux);
                }
            }

        }
    }
}
