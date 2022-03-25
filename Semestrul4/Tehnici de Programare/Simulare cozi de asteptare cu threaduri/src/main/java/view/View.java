package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private JTextField N = new JTextField("4");
    private JTextField Q = new JTextField("2");
    private JTextField timeLim    = new JTextField("60");
    private JTextField tMinA  = new JTextField("2");
    private JTextField tMaxA  = new JTextField("30");
    private JTextField tMinS  = new JTextField("2");
    private JTextField tMaxS  = new JTextField("4");
    private JButton    summit = new JButton("SUBMIT");
    private JButton    start   = new JButton("START");
    private JTextPane Rezultat = new JTextPane();




    public View() {
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2,0));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(8,2));
        panel1.add(new JLabel("N= "));
        panel1.add(N);
        panel1.add(new JLabel("Q= "));
        panel1.add(Q);
        panel1.add(new JLabel("Simulation time= "));
        panel1.add(timeLim);
        panel1.add(new JLabel("Time min arrival= "));
        panel1.add(tMinA);
        panel1.add(new JLabel("Time max arrival= "));
        panel1.add(tMaxA);
        panel1.add(new JLabel("Time min service= "));
        panel1.add(tMinS);
        panel1.add(new JLabel("Time max service= "));
        panel1.add(tMaxS);
        panel1.add(summit);
        panel1.add(start);

        Rezultat.setEditable(false);
        content.add(panel1);
        content.add(Rezultat);

        this.setContentPane(content);
        this.pack();
        this.setTitle("SIMULARE");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setTotal(String newTotal) {
        Rezultat.setText(newTotal);
    }


    public void reset() {
        Rezultat.setText(" ");
    }
    public String getUserInputN() {
        return N.getText();
    }
    public String getUserInputQ() {
        return Q.getText();
    }
    public String getUserInputTimeLimit() {
        return timeLim.getText();
    }
    public String getUserInputTMaxA() {
        return tMaxA.getText();
    }
    public String getUserInputTMinA() {
        return tMinA.getText();
    }
    public String getUserInputTMaxS() {
        return tMaxS.getText();
    }
    public String getUserInputTMinS() {
        return tMinS.getText();
    }
    public void summitListener(ActionListener summit) {
        this.summit.addActionListener(summit);
    }
    public void startListener(ActionListener start) {
        this.start.addActionListener(start);
    }

}
