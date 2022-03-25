package POLINOAME.model;

public class Monomial {

    private double coeficient;
    private double exponent;

    Monomial(){
        this.coeficient=0.0;
        this.exponent=0.0;
    }
    public Monomial(double coeficient, double exponent){
        this.coeficient=coeficient;
        this.exponent=exponent;
    }


    public void setExponent(double e){
        this.exponent=e;
    }
    public void setCoeficient(double c){
        this.coeficient=c;
    }

    public double getExponent(){
        return this.exponent;
    }
    public double getCoeficient(){
         return this.coeficient;
    }

     @Override
    public String toString(){
         String s = String.format("%.2f", coeficient);

         if (coeficient >= 0)
             return "+" + s + "x^" + (int)exponent;
         else
             return s + "x^" + (int) exponent ;

     }

     //implementam adunarea de monoame
    // la un alt monom dam suma a doua monoame
    public Monomial adunare (Monomial m1){

        Monomial rez = new Monomial();

        rez.setExponent(this.exponent);
        rez.setCoeficient(this.coeficient+m1.coeficient);

        return rez;

    }
        //implementam scaderea de monoame
        // la un alt monom dam diferenta celor doua monoame
        public Monomial scadere (Monomial m1){

            Monomial rez = new Monomial();

            rez.setExponent(this.exponent);
            rez.setCoeficient(this.coeficient-m1.coeficient);

            return rez;

        }

    //implementam inmultirea de monoame
    // la un alt monom dam produsul celor doua monoame
    public Monomial inmultire (Monomial m1){

        Monomial rez = new Monomial();

        rez.setExponent(this.exponent+m1.exponent);
        rez.setCoeficient(this.coeficient*m1.coeficient);

        return rez;
    }

}