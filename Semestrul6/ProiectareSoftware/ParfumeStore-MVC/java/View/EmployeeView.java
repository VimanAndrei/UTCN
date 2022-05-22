package View;


import Controller.ControllerEmployee;
import Model.Observer;
import Model.Parfume.ModelParfumes;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

@Getter
@Setter
public class EmployeeView extends JFrame implements Observer {
    private JButton deleteButton;
    private JButton afisareParfumuriButton;
    private JButton filtrareDupaProducatorButton;
    private JButton filtrareDupaDisponibilitateButton;
    private JButton addButton;
    private JButton updateButton;


    private JTextField numOfCopies;
    private JTextField manufacturerName;
    private JTextField price;
    private JTextField parfumeAmount;
    private JTextField numOfSoldCopies;

    private JPanel parfumeName;
    private JTextField storeName;
    private JTextField barCode;
    private JTextField parfumeNameF;

    String selectedStoreName;
    String selectedParfumeName;
    String selectedManufacturerName;
    String selectedNumberOfCopies;
    String selectedBarCode;
    String selectedPrice;
    String selectedParfumeAmount;
    String selectedNumberOfSoldCopies;

    private JPanel mainPanel;

    private JButton cautare;
    private JButton filtrareDupaPretButton;
    private JButton salvareInMaiMulteButton;
    private JButton afisareProduseDintrUnButton;
    private JButton vizualizareStatisticiButton;

    private JButton romaniaButton;
    private JButton englishButton;
    private JButton franceButton;
    private JButton germanButton;

    private JLabel numeProducatorLabel;
    private JLabel numarDeProduseRamaseLabel;
    private JLabel codDeBareLabel;
    private JLabel pretulLabel;
    private JLabel dimensiuneaInMlLabel;
    private JLabel numarProduseVanduteLabel;
    private JLabel numeParfumLabel;
    private JLabel numeMagazinLabel;


    private Frame displayFrame;
    private JTable table;
    private Object[] columnNames = {"Store Name", "Parfume Name", "Manufacturer Name", "Number Of Copies", "Bar Code", "Price", "Parfume Amount", "Number Of Sold Copies"};
    private Object[][] data;

    private ModelParfumes model;
    private ControllerEmployee controller;
    public EmployeeView(ModelParfumes modelParfumes) {
        this.model = modelParfumes;
        this.controller = new ControllerEmployee(this);
        this.controller.displayAllParfumes();
        this.setTitle("Employee");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);

        this.model.addObserver(this);
        this.model.addObserver(this.controller);

        this.model.setOperation("language");

    }

    public void setTotal() {
        if (displayFrame != null) displayFrame.dispose();
        displayFrame = new JFrame();
        displayFrame.setTitle("Parfumes");
        table = new JTable(data, columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectedStoreName = null;
        selectedParfumeName = null;
        selectedManufacturerName = null;
        selectedNumberOfCopies = null;
        selectedBarCode = null;
        selectedPrice = null;
        selectedParfumeAmount = null;
        selectedNumberOfSoldCopies = null;
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                selectedStoreName = table.getValueAt(table.getSelectedRow(), 0).toString();
                selectedParfumeName = table.getValueAt(table.getSelectedRow(), 1).toString();
                selectedManufacturerName = table.getValueAt(table.getSelectedRow(), 2).toString();
                selectedNumberOfCopies = table.getValueAt(table.getSelectedRow(), 3).toString();
                selectedBarCode = table.getValueAt(table.getSelectedRow(), 4).toString();
                selectedPrice = table.getValueAt(table.getSelectedRow(), 5).toString();
                selectedParfumeAmount = table.getValueAt(table.getSelectedRow(), 6).toString();
                selectedNumberOfSoldCopies = table.getValueAt(table.getSelectedRow(), 7).toString();


            }
        });
        JScrollPane sp = new JScrollPane(table);
        displayFrame.add(sp);
        displayFrame.setSize(1050, 200);
        displayFrame.setLocation(490, 0);
        displayFrame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        romaniaButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\romania.png"));
        germanButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\germany.png"));
        englishButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\united-kingdom.png"));
        franceButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\france.png"));
    }

    private void languageSetData(){
        List<String> componentsName = this.model.getLanguage().getLanguageComponent();
        String storName = componentsName.get(7);
        String parfumeName = componentsName.get(6);
        String manufacturerName = componentsName.get(0);
        String numberOfCopies = componentsName.get(1);
        String barCode = componentsName.get(2);
        String price = componentsName.get(3);
        String parfumeAmount = componentsName.get(4);
        String numOfSoldCopies = componentsName.get(5);
        numeProducatorLabel.setText(manufacturerName);
        numarDeProduseRamaseLabel.setText(numberOfCopies);
        codDeBareLabel.setText(barCode);
        pretulLabel.setText(price);
        dimensiuneaInMlLabel.setText(parfumeAmount);
        numarProduseVanduteLabel.setText(numOfSoldCopies);
        numeParfumLabel.setText(parfumeName);
        numeMagazinLabel.setText(storName);
        columnNames = new Object[]{storName, parfumeName, manufacturerName, numberOfCopies, barCode, price, parfumeAmount, numOfSoldCopies};
        updateButton.setText(componentsName.get(8));
        addButton.setText(componentsName.get(9));
        deleteButton.setText(componentsName.get(10));
        afisareParfumuriButton.setText(componentsName.get(11));
        afisareProduseDintrUnButton.setText(componentsName.get(12));
        salvareInMaiMulteButton.setText(componentsName.get(13));
        filtrareDupaProducatorButton.setText(componentsName.get(14));
        filtrareDupaDisponibilitateButton.setText(componentsName.get(15));
        cautare.setText(componentsName.get(16));
        filtrareDupaPretButton.setText(componentsName.get(17));
        vizualizareStatisticiButton.setText(componentsName.get(18));
    }

    @Override
    public void update() {
        if(this.model.getOperation().equals("language")) {
            this.languageSetData();
            this.setTotal();
        }
        if(this.model.getOperation().equals("vizualizare")){
            this.setTotal();
        }
        if(this.model.getOperation().equals("salvare_mai_multe_formate")){
            JOptionPane.showMessageDialog(null, "S-a salvat");
        }
        if(this.model.getOperation().equals("statistici")){
            var barChart = new BarChart(this.model.getService());
            barChart.setVisible(true);

            var pieChart = new PieChart(this.model.getService());
            pieChart.setVisible(true);

            var barChartDisponibility = new BarChartDisponibility(this.model.getService());
            barChartDisponibility.setVisible(true);
        }
    }
}
