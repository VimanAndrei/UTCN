package presentationLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdministratorFrame extends JFrame {
    JTextPane data = new JTextPane();
    JScrollPane scroll = new JScrollPane(data);

    JTextPane compozit = new JTextPane();
    JScrollPane scrollCompozit = new JScrollPane(compozit);
    JTextField titleCompozitTf = new JTextField("Title Composit Menu");

    JTextPane orderTP = new JTextPane();
    JScrollPane scrollOrder= new JScrollPane(orderTP);

    JTextField titelTf = new JTextField("TITLE");
    JTextField ratingTf = new JTextField("RATING");
    JTextField priceTf = new JTextField("PRICE");
    JTextField proteinTf = new JTextField("PROTEIN");
    JTextField fatTf = new JTextField("FAT");
    JTextField caloriesTf = new JTextField("CALORIES");
    JTextField sodiumTf = new JTextField("SODIUM");
    JTextField startHour = new JTextField("14");
    JTextField endHour = new JTextField("15");
    JTextField productNo=new JTextField();
    JTextField specificNo = new JTextField();
    JTextField amount = new JTextField();
    JTextField specificDate = new JTextField();


    JButton importData = new JButton("IMPORT");
    JButton addBaseProduct = new JButton("ADD BASE PRODUCT");
    JButton deleteProduct = new JButton("DELETE BASE PRODUCT");
    JButton addToList = new JButton("ADD TO LIST");
    JButton compose = new JButton("COMPOSE");
    JButton editProduct = new JButton("EDIT BASE PRODUCT");
    JButton raport1 = new JButton("RAPORT 1");
    JButton raport2 = new JButton("RAPORT 2");
    JButton raport3 = new JButton("RAPORT 3");
    JButton raport4 = new JButton("RAPORT 4");




    public AdministratorFrame(){
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(2,0));
        //partea de sus
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(0,2));
        panel1.add(scroll);



        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(0,2));

        JPanel panel3 =new JPanel();
        panel3.setLayout(new GridLayout(8,2));
        panel3.add(new JLabel("Title:"));
        panel3.add(titelTf);
        panel3.add(new JLabel("Price:"));
        panel3.add(priceTf);
        panel3.add(new JLabel("Sodium:"));
        panel3.add(sodiumTf);
        panel3.add(new JLabel("Rating:"));
        panel3.add(ratingTf);
        panel3.add(new JLabel("Protein:"));
        panel3.add(proteinTf);
        panel3.add(new JLabel("Calories:"));
        panel3.add(caloriesTf);
        panel3.add(new JLabel("Fat:"));
        panel3.add(fatTf);
        panel3.add(new JLabel("Title Composit Menu:"));
        panel3.add(titleCompozitTf);

        panel4.add(panel3);
        panel4.add(scrollCompozit);
        panel1.add(panel4);
        content.add(panel1);


        //partea de jos
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(0,3));

        panel2.add(scrollOrder);

        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(0,2));
        //butoane
        panel5.add(importData);
        panel5.add(addBaseProduct);
        panel5.add(deleteProduct);
        panel5.add(editProduct);
        panel5.add(addToList);
        panel5.add(compose);
        panel5.add(raport1);
        panel5.add(raport2);
        panel5.add(raport3);
        panel5.add(raport4);

        panel2.add(panel5);

        JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayout(0,2));
        panel6.add(new JLabel("Start hour:"));
        panel6.add(startHour);
        panel6.add(new JLabel("End hour:"));
        panel6.add(endHour);
        panel6.add(new JLabel("Products ordered more: "));
        panel6.add(productNo);
        panel6.add(new JLabel("Specified number of times:"));
        panel6.add(specificNo);
        panel6.add(new JLabel("Amount:"));
        panel6.add(amount);
        panel6.add(new JLabel("Specified date: "));
        panel6.add(specificDate);

        panel2.add(panel6);


        content.add(panel2);

        this.setContentPane(content);
        this.pack();
        this.setSize(2000,800);
        this.setVisible(true);
        this.setTitle("ADMINISTRATION");
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void importDataListener(ActionListener importData) {
        this.importData.addActionListener(importData);
    }
    public void editProductListener(ActionListener editProduct) {
        this.editProduct.addActionListener(editProduct);
    }
    public void deleteProductListener(ActionListener deleteProduct) {
        this.deleteProduct.addActionListener(deleteProduct);
    }
    public void addBaseProductListener(ActionListener addBaseProduct) {
        this.addBaseProduct.addActionListener(addBaseProduct);
    }
    public void addToListLisener(ActionListener addToList){
        this.addToList.addActionListener(addToList);
    }
    public void composeLisener(ActionListener compose){
        this.compose.addActionListener(compose);
    }

    public void raport1Lisener(ActionListener listener){
        this.raport1.addActionListener(listener);
    }
    public void raport2Lisener(ActionListener listener){
        this.raport2.addActionListener(listener);
    }
    public void raport3Lisener(ActionListener listener){
        this.raport3.addActionListener(listener);
    }
    public void raport4Lisener(ActionListener listener){
        this.raport4.addActionListener(listener);
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

    public String getTitleCompozitTf() {
        return titleCompozitTf.getText();
    }

    public String getStartHour() {
        return startHour.getText();
    }

    public String getEndHour() {
        return endHour.getText();
    }

    public String getProductNo() {
        return productNo.getText();
    }

    public String getSpecificNo() {
        return specificNo.getText();
    }

    public String getAmount() {
        return amount.getText();
    }

    public String getSpecificDate() {
        return specificDate.getText();
    }

    public void setTotalData(String total){
        data.setText(total);
    }
    public void setTotalCompozit(String total){
        compozit.setText(total);
    }
    public void setTotalOrderTP(String total){
        orderTP.setText(total);
    }
}
