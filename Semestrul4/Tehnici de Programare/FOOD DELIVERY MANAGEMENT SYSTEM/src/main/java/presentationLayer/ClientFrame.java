package presentationLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientFrame extends JFrame {
  private JTextPane produse = new JTextPane();
  private JScrollPane scrollProduse = new JScrollPane(produse);

  private JTextPane comanda = new JTextPane();
  private JScrollPane scrollComanda = new JScrollPane(comanda);

  private JTextField titelTf = new JTextField();
  private JTextField ratingTf = new JTextField();
  private JTextField priceTf = new JTextField();
  private JTextField proteinTf = new JTextField();
  private JTextField fatTf = new JTextField();
  private JTextField caloriesTf = new JTextField();
  private JTextField sodiumTf = new JTextField();

  private JButton afiseazaProduse = new JButton("LIST PRODUCTS");
  private JButton addToTF = new JButton("ADD TO LIST");
  private JButton cautaDupaTitlu = new JButton("SEARCH BY TITLE");
  private JButton cautaDupaRating = new JButton("SEARCH BY RATING");
  private JButton cautaDupaPret = new JButton("SEARCH BY PRICE");
  private JButton cautaDupaProteine = new JButton("SEARCH BY PROTEIN");
  private JButton cautaDupaGrasimi = new JButton("SEARCH BY FAT");
  private JButton cautaDupaCalorii = new JButton("SEARCH BY CALORIES");
  private JButton cautaDupaSodiu = new JButton("SEARCH BY SODIUM");
    private JButton cauta = new JButton("SEARCH");


  private JButton placeOrder = new JButton("ADD ORDER");

    public ClientFrame(){
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(1,3));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        panel1.setLayout(new GridLayout(1,1));
        panel2.setLayout(new GridLayout(1,1));
        panel3.setLayout(new GridLayout(1,1));

        panel1.add(scrollProduse);
        JPanel panel4 =new JPanel();
        panel4.setLayout(new GridLayout(0,2));
        panel4.add(new JLabel("Title:"));
        panel4.add(titelTf);
        panel4.add(new JLabel("Price:"));
        panel4.add(priceTf);
        panel4.add(new JLabel("Sodium:"));
        panel4.add(sodiumTf);
        panel4.add(new JLabel("Rating:"));
        panel4.add(ratingTf);
        panel4.add(new JLabel("Protein:"));
        panel4.add(proteinTf);
        panel4.add(new JLabel("Calories:"));
        panel4.add(caloriesTf);
        panel4.add(new JLabel("Fat:"));
        panel4.add(fatTf);
        panel4.add(afiseazaProduse);
        panel4.add(addToTF);
        panel4.add(placeOrder);
        panel4.add(cautaDupaTitlu);
        panel4.add(cautaDupaCalorii);
        panel4.add(cautaDupaGrasimi);
        panel4.add(cautaDupaSodiu);
        panel4.add(cautaDupaPret);
        panel4.add(cautaDupaRating);
        panel4.add(cautaDupaProteine);
        panel4.add(cauta);
        panel2.add(panel4);

        panel3.add(scrollComanda);

        content.add(panel1);
        content.add(panel2);
        content.add(panel3);
        this.setContentPane(content);
        this.pack();
        this.setSize(2000,800);
        this.setVisible(true);
        this.setTitle("CLIENT");
    }

    public String getTitelTf() {
        return titelTf.getText();
    }

    public String getRatingTf() {
        return ratingTf.getText();
    }

    public String getPriceTf() {
        return priceTf.getText();
    }

    public String getProteinTf() {
        return proteinTf.getText();
    }

    public String getFatTf() {
        return fatTf.getText();
    }

    public String getCaloriesTf() {
        return caloriesTf.getText();
    }

    public String getSodiumTf() {
        return sodiumTf.getText();
    }

    public void setTotalProduse(String total){
        produse.setText(total);
    }
    public void setTotalComanda(String total){
        comanda.setText(total);
    }

   public void afiseazaProduseListener (ActionListener lisener){
        this.afiseazaProduse.addActionListener(lisener);
   }
   public void addToTFListener (ActionListener lisener){
        this.addToTF.addActionListener(lisener);
   }
   public void cautaDupaTitluListener (ActionListener lisener){
        this.cautaDupaTitlu.addActionListener(lisener);
   }
   public void cautaDupaRatingListener (ActionListener lisener){
        this.cautaDupaRating.addActionListener(lisener);
   }
   public void cautaDupaPretListener (ActionListener lisener){
        this.cautaDupaPret.addActionListener(lisener);
   }
   public void cautaDupaProteineListener (ActionListener lisener){
        this.cautaDupaProteine.addActionListener(lisener);
   }
   public void cautaDupaGrasimiListener (ActionListener lisener){
        this.cautaDupaGrasimi.addActionListener(lisener);
   }
   public void cautaDupaCaloriiListener (ActionListener lisener){
        this.cautaDupaCalorii.addActionListener(lisener);
   }
   public void cautaDupaSodiuListener (ActionListener lisener){
        this.cautaDupaSodiu.addActionListener(lisener);
   }

    public void placeOrderListener (ActionListener lisener){
        this.placeOrder.addActionListener(lisener);
    }
    public void cautaListener (ActionListener lisener){
        this.cauta.addActionListener(lisener);
    }


}
