package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View {
    private JFrame frameClients = new JFrame();
    private JFrame frameOrders = new JFrame();
    private JFrame frameProducts = new JFrame();
    private JFrame displayFrame = new JFrame();
    private JButton    bonTotal   = new JButton("BILL");
    //Clients
    private JTextField idClientsText = new JTextField();
    private JTextField nameClientsText = new JTextField();
    private JTextField addressClientsText = new JTextField();
    private JButton    insertClient = new JButton("INSERT");
    private JButton    editClient   = new JButton("EDIT");
    private JButton    deleteClient   = new JButton("DELETE");
    private JButton    displayClient   = new JButton("DISPLAY DATA");
    //Orders
    private JTextField idOrdersText = new JTextField();
    private JTextField clientIdOrdersText = new JTextField();
    private JTextField productIdOrdersText = new JTextField();
    private JTextField quantityOrdersText = new JTextField();
    private JButton    insertOrders = new JButton("INSERT");
    private JButton    editOrders   = new JButton("EDIT");
    private JButton    deleteOrders   = new JButton("DELETE");
    private JButton    displayOrders   = new JButton("DISPLAY DATA");
    //Products
    private JTextField idProductsText = new JTextField();
    private JTextField priceProductsText = new JTextField();
    private JTextField quantityProductsText = new JTextField();
    private JTextField productNameProductsText = new JTextField();
    private JButton    insertProducts = new JButton("INSERT");
    private JButton    editProducts   = new JButton("EDIT");
    private JButton    deleteProducts   = new JButton("DELETE");
    private JButton    displayProducts   = new JButton("DISPLAY DATA");
    //Display
    private JTable tabela = new JTable();

    public View(){

        frameClients.setTitle("Clients");
        frameClients.setLayout(new GridLayout(2,0));
        JPanel textFieldsClients= new JPanel();
        textFieldsClients.setLayout(new GridLayout(3,2));
        textFieldsClients.add(new JLabel("id= "));
        textFieldsClients.add(idClientsText);
        textFieldsClients.add(new JLabel("name= "));
        textFieldsClients.add(nameClientsText);
        textFieldsClients.add(new JLabel("address= "));
        textFieldsClients.add(addressClientsText);
        frameClients.add(textFieldsClients);
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(insertClient);
        buttons.add(editClient);
        buttons.add(deleteClient);
        buttons.add(displayClient);
        buttons.add(bonTotal);
        frameClients.add(buttons);

        frameOrders.setTitle("Orders");
        frameOrders.setLayout(new GridLayout(2,0));
        JPanel textFieldsOrders= new JPanel();
        textFieldsOrders.setLayout(new GridLayout(4,2));
        textFieldsOrders.add(new JLabel("id= "));
        textFieldsOrders.add(idOrdersText);
        textFieldsOrders.add(new JLabel("client id= "));
        textFieldsOrders.add(clientIdOrdersText);
        textFieldsOrders.add(new JLabel("product id= "));
        textFieldsOrders.add(productIdOrdersText);
        textFieldsOrders.add(new JLabel("quantity= "));
        textFieldsOrders.add(quantityOrdersText);
        frameOrders.add(textFieldsOrders);
        JPanel buttons1 = new JPanel(new FlowLayout());
        buttons1.add(insertOrders);
        buttons1.add(editOrders);
        buttons1.add(deleteOrders);
        buttons1.add(displayOrders);
        frameOrders.add(buttons1);


        frameProducts.setTitle("Products");
        frameProducts.setLayout(new GridLayout(2,0));
        JPanel textFieldsProducts= new JPanel();
        textFieldsProducts.setLayout(new GridLayout(4,2));
        textFieldsProducts.add(new JLabel("id= "));
        textFieldsProducts.add(idProductsText);
        textFieldsProducts.add(new JLabel("price= "));
        textFieldsProducts.add(priceProductsText);
        textFieldsProducts.add(new JLabel("quantity= "));
        textFieldsProducts.add(quantityProductsText);
        textFieldsProducts.add(new JLabel("product name= "));
        textFieldsProducts.add(productNameProductsText);
        frameProducts.add(textFieldsProducts);
        JPanel buttons2 = new JPanel(new FlowLayout());
        buttons2.add(insertProducts);
        buttons2.add(editProducts);
        buttons2.add(deleteProducts);
        buttons2.add(displayProducts);
        frameProducts.add(buttons2);

        frameClients.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameOrders.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameProducts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frameOrders.setVisible(true);
        frameClients.setVisible(true);
        frameProducts.setVisible(true);

        frameProducts.setSize(300,300);
        frameOrders.setSize(300,300);
        frameClients.setSize(300,300);


    }

    public void setTotal(Object[][] data, Object[] column) {
        displayFrame = new JFrame();
        displayFrame.setTitle("Display");
        tabela = new JTable(data, column);
        JScrollPane sp = new JScrollPane(tabela);
        displayFrame.add(sp);

        displayFrame.setSize(500, 200);
        displayFrame.setVisible(true);
    }


    public String getIdClientsText() {
        return idClientsText.getText();
    }

    public String getNameClientsText() {
        return nameClientsText.getText();
    }

    public String getAddressClientsText() {
        return addressClientsText.getText();
    }

    public String getIdOrdersText() {
        return idOrdersText.getText();
    }

    public String getClientIdOrdersText() {
        return clientIdOrdersText.getText();
    }

    public String getProductIdOrdersText() {
        return productIdOrdersText.getText();
    }

    public String getQuantityOrdersText() {
        return quantityOrdersText.getText();
    }

    public String getIdProductsText() {
        return idProductsText.getText();
    }

    public String getPriceProductsText() {
        return priceProductsText.getText();
    }

    public String getQuantityProductsText() {
        return quantityProductsText.getText();
    }

    public String getProductNameProductsText() {
        return productNameProductsText.getText();
    }

    public void insertClientListener(ActionListener insertClient) {
        this.insertClient.addActionListener(insertClient);
    }
    public void editClientListener(ActionListener editClient) {
        this.editClient.addActionListener(editClient);
    }
    public void deleteClientListener(ActionListener deleteClient) {
        this.deleteClient.addActionListener(deleteClient);
    }
    public void displayClientListener(ActionListener displayClient) {
        this.displayClient.addActionListener(displayClient);
    }

    public void insertProductsListener(ActionListener insertProducts) {
        this.insertProducts.addActionListener(insertProducts);
    }
    public void editProductsListener(ActionListener editProducts) {
        this.editProducts.addActionListener(editProducts);
    }
    public void deleteProductsListener(ActionListener deleteProducts) {
        this.deleteProducts.addActionListener(deleteProducts);
    }
    public void displayProductsListener(ActionListener displayProducts) {
        this.displayProducts.addActionListener(displayProducts);
    }

    public void insertOrdersListener(ActionListener insertOrders) {
        this.insertOrders.addActionListener(insertOrders);
    }
    public void editOrdersListener(ActionListener editOrders) {
        this.editOrders.addActionListener(editOrders);
    }
    public void deleteOrdersListener(ActionListener deleteOrders) {
        this.deleteOrders.addActionListener(deleteOrders);
    }
    public void displayOrdersListener(ActionListener displayOrders) {
        this.displayOrders.addActionListener(displayOrders);
    }
    public void bonTotalListener(ActionListener bonTotal) {
        this.bonTotal.addActionListener(bonTotal);
    }


}
