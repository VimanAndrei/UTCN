package test;

//2x^5+2x^4+2x^3+1x^2
//10x^4+4x^3+2x^3+8x^2
//1x^2+1x^1+1x^0

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import POLINOAME.model.Model;


public class test1 {

    @Test
    public void transformareTest(){
        Model a = new Model();
        a.setValue("1x^2+1x^1+1x^0");
        Assertions.assertEquals(a.getValue(),"+1.00x^2+1.00x^1+1.00x^0");

    }

    @Test
    public void addTest(){
        Model a = new Model();
        a.setValue("2x^5+2x^4+2x^3+1x^2");
        a.adunarePolinoame("1x^2+1x^1+1x^0");
        Assertions.assertEquals(a.getValue(),"+2.00x^5+2.00x^4+2.00x^3+2.00x^2+1.00x^1+1.00x^0");
    }

    @Test
    public void subTest(){
        Model a = new Model();
        a.setValue("2x^5+2x^4+2x^3+1x^2");
        a.scaderePolinoame("1x^2+1x^1+1x^0");
        Assertions.assertEquals(a.getValue(),"+2.00x^5+2.00x^4+2.00x^3+0.00x^2-1.00x^1-1.00x^0");
    }

    @Test
    public void mulTest(){
        Model a = new Model();
        a.setValue("2x^3+1x^2");
        a.inmultirePolinoame("1x^2+1x^1");
        Assertions.assertEquals(a.getValue(),"+2.00x^5+3.00x^4+1.00x^3");
    }

    @Test
    public void deriTest(){
        Model a = new Model();
        a.setValue("10x^4+4x^3+2x^3+8x^2");
        a.derivarePolinoame();
        Assertions.assertEquals(a.getValue(),"+40.00x^3+12.00x^2+6.00x^2+16.00x^1");
    }

    @Test
    public void integrareTest(){
        Model a = new Model();
        a.setValue("40x^3+12x^2+6x^2+16x^1");
        a.integrarePolinoame();
        Assertions.assertEquals(a.getValue(),"+10.00x^4+4.00x^3+2.00x^3+8.00x^2");
    }



}
