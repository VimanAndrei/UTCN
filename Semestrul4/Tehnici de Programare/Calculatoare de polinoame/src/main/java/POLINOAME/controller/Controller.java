package POLINOAME.controller;

import POLINOAME.model.Model;
import POLINOAME.view.View;

import java.awt.event.*;

public class Controller {

    private Model p_model;
    private View p_view;

    public Controller(Model Model, View View) {
        this.p_model = Model;
        this.p_view  = View;
        //... Add listeners to the view.
        p_view.addAddListener(new AddListener());
        p_view.addSubListener(new SubListener());
        p_view.addMultiplyListener(new MultiplyListener());
        p_view.addDivideListener(new DivideListener());
        p_view.addDerivateListener(new DerivateListener());
        p_view.addIntegrateListener(new IntegrateListener());
    }


    class AddListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            p_model.reset();
            p_view.reset();
            String userInput1;
            String userInput2;
            userInput1 = p_view.getUserInput1();
            userInput2 = p_view.getUserInput2();
            p_model.setValue(userInput1);
            p_model.adunarePolinoame(userInput2);
            p_view.setTotal(p_model.getValue());
        }
    }

    class SubListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            p_model.reset();
            p_view.reset();
            String userInput1;
            String userInput2;
            userInput1 = p_view.getUserInput1();
            userInput2 = p_view.getUserInput2();
            p_model.setValue(userInput1);
            p_model.scaderePolinoame(userInput2);
            p_view.setTotal(p_model.getValue());


        }
    }

    class MultiplyListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {

                p_model.reset();
                p_view.reset();
                String userInput1;
                String userInput2;
                userInput1 = p_view.getUserInput1();
                userInput2 = p_view.getUserInput2();
                p_model.setValue(userInput1);
                p_model.inmultirePolinoame(userInput2);
                p_view.setTotal(p_model.getValue());


            }
    }

    class DivideListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            p_view.setTotal("Nu este implementata operatia!");

        }
    }

    class DerivateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            p_model.reset();
            p_view.reset();
            String userInput;
            userInput = p_view.getUserInput1();
            p_model.setValue(userInput);
            p_model.derivarePolinoame();
            p_view.setTotal(p_model.getValue());

        }
    }

    class IntegrateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            p_model.reset();
            p_view.reset();
            String userInput;
            userInput = p_view.getUserInput1();
            p_model.setValue(userInput);
            p_model.integrarePolinoame();
            p_view.setTotal(p_model.getValue());
        }
    }
}
