package POLINOAME.view;

import POLINOAME.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class View extends JFrame {

    private JTextField userInputPolinom1 = new JTextField(25);
    private JTextField userInputPolinom2 = new JTextField(25);
    private JTextField totalRez     = new JTextField(25);

    //butoane de calcul
    private JButton    multiplyBtn = new JButton("Multiply");
    private JButton    subBtn    = new JButton("Subtract");
    private JButton    addBtn = new JButton("Add");
    private JButton divideBtn = new JButton("Divide");
    private JButton    integBtn = new JButton("Integrare");
    private JButton derivateBtn = new JButton("Derivare");



    private Model p_model;

    public View(Model Model) {

        this.p_model=Model;

        totalRez.setEditable(false);

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2,1));



        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3,2));
        panel1.add(new JLabel("  First Polynomial= "));
        panel1.add(userInputPolinom1);
        panel1.add(new JLabel("  Second Polynomial= "));
        panel1.add(userInputPolinom2);
        panel1.add(new JLabel("  Result Polynomial= "));
        panel1.add(totalRez);
        content.add(panel1);



        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,2));
        panel2.add(addBtn);
        panel2.add(subBtn);
        panel2.add(multiplyBtn);
        panel2.add(divideBtn);
        panel2.add(derivateBtn);
        panel2.add(integBtn);
        content.add(panel2);



        this.setContentPane(content);
        this.pack();

        this.setTitle("Polynom Calculator");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


   public void reset(){
        totalRez.setText("");
    }
    public String getUserInput1() {
        return userInputPolinom1.getText();
    }
    public String getUserInput2() {
        return userInputPolinom2.getText();
    }
    public void setTotal( String newTotal) {
            totalRez.setText(newTotal);
    }

    public void addMultiplyListener(ActionListener mal) {
        multiplyBtn.addActionListener(mal);
    }
    public void addAddListener(ActionListener adn) {
        addBtn.addActionListener(adn);
    }
    public void addSubListener(ActionListener sub) {
        subBtn.addActionListener(sub);
    }
    public void addDivideListener(ActionListener div) {
        divideBtn.addActionListener(div);
    }
    public void addIntegrateListener(ActionListener integrate) {
        integBtn.addActionListener(integrate);
    }
    public void addDerivateListener(ActionListener derivate) {
        derivateBtn.addActionListener(derivate);
    }

}