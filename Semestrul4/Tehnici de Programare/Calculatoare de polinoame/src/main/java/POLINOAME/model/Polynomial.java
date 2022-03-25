package POLINOAME.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    protected List<Monomial> polinom;
    private String monomialFormat = "([+-]?[\\d\\.]*)([a-zA-Z]?)\\^?(\\d*)";

     Polynomial() {
         polinom=new ArrayList<Monomial>();
     }

     public void crearePolinom(String input){
         Pattern p1 = Pattern.compile(monomialFormat);
         Matcher m1 = p1.matcher(input);
         while(!m1.hitEnd()){
                 double c , e;
                 m1.find();
                 c = Double.valueOf(m1.group(1));
                 e = Double.valueOf(m1.group(3));
                 Monomial b = new Monomial(c, e);
                 polinom.add(b);
         }

     }

    @Override
    public String toString() {
        String s = "";
        for(Monomial m : polinom) {
            s += m.toString();
        }
        return s;
    }

    public int getSize(){
         return polinom.size();
    }

    public List<Monomial> getPolinom(){
         return this.polinom;
    }

}