package View;



import ViewModel.VMEmployee;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeView extends JFrame {
    private JButton deleteButton;
    private JButton afisareParfumuriButton;
    private JButton filtrareDupaProducatorButton;
    private JButton filtrareDupaDisponibilitateButton;
    private JButton addButton;
    private JButton updateButton;

    @Bind(value = "text", target = "numberOfCopies.value")
    private JTextField numOfCopies;
    @Bind(value = "text", target = "manufacturerName.value")
    private JTextField manufacturerName;
    @Bind(value = "text", target = "price.value")
    private JTextField price;
    @Bind(value = "text", target = "parfumeAmount.value")
    private JTextField parfumeAmount;
    @Bind(value = "text", target = "numberOfSoldCopies.value")
    private JTextField numOfSoldCopies;

    private JPanel parfumeName;
    @Bind(value = "text", target = "storeName.value")
    private JTextField storeName;
    @Bind(value = "text", target = "barCode.value")
    private JTextField barCode;
    @Bind(value = "text", target = "parfumeName.value")
    private JTextField parfumeNameF;

    private JPanel mainPanel;

    private JButton cautare;
    private JButton filtrareDupaPretButton;
    private JButton salvareInMaiMulteButton;
    private JButton afisareProduseDintrUnButton;
    private JButton vizualizareStatisticiButton;


    private Frame displayFrame;
    private JTable table;
    private final Object[] columnNames = {"Store Name", "Parfume Name", "Manufacturer Name","Number Of Copies","Bar Code","Price","Parfume Amount","Number Of Sold Copies"};

    private VMEmployee vmEmployee;

    public EmployeeView(){
        this.setTitle("Employee");
        this.setSize(450,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);

        vmEmployee = new VMEmployee();
        try {
            Binder.bind(this, vmEmployee);
        } catch (BindingException e) {
            System.out.println("Error binding on administrator view!");
        }


        afisareParfumuriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.listAllCommand.Execute();
                setTotal(vmEmployee.getData(),columnNames);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.insertCommand.Execute();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.deleteCommand.Execute();

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.updateCommand.Execute();
            }
        });
        cautare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.searchByParfumeNameCommand.Execute();
                if(vmEmployee.getData() != null) {
                    setTotal(vmEmployee.getData(), columnNames);
                }
            }
        });
        filtrareDupaDisponibilitateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.filterByNumOfCopies.Execute();
                if(vmEmployee.getData() != null) {
                    setTotal(vmEmployee.getData(), columnNames);
                }
            }
        });
        filtrareDupaProducatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.filterByManufacturerName.Execute();
                if(vmEmployee.getData() != null) {
                    setTotal(vmEmployee.getData(), columnNames);
                }
            }
        });
        filtrareDupaPretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.filterByPrice.Execute();
                if(vmEmployee.getData() != null) {
                    setTotal(vmEmployee.getData(), columnNames);
                }
            }
        });
        salvareInMaiMulteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.saveAsOtherFiles.Execute();
            }
        });
        afisareProduseDintrUnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.filterByStoreNameCommand.Execute();
                if(vmEmployee.getData() != null) {
                    setTotal(vmEmployee.getData(), columnNames);
                }
            }
        });
        vizualizareStatisticiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmEmployee.chartCommand.Execute();
            }
        });
    }

    public void setTotal(Object[][] data, Object[] column) {
        if (displayFrame != null) displayFrame.dispose();
        displayFrame = new JFrame();
        displayFrame.setTitle("Parfumes");
        table = new JTable(data, column);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {

                String storeName = table.getValueAt(table.getSelectedRow(), 0).toString();
                String parfumeName = table.getValueAt(table.getSelectedRow(), 1).toString();
                String manufacturerName = table.getValueAt(table.getSelectedRow(), 2).toString();
                String numberOfCopies = table.getValueAt(table.getSelectedRow(), 3).toString();
                String barCode = table.getValueAt(table.getSelectedRow(), 4).toString();
                String price = table.getValueAt(table.getSelectedRow(), 5).toString();
                String parfumeAmount  = table.getValueAt(table.getSelectedRow(), 6).toString();
                String numberOfSoldCopies = table.getValueAt(table.getSelectedRow(), 7).toString();

                vmEmployee.setSelectedStoreName(storeName);
                vmEmployee.setSelectedParfumeName(parfumeName);
                vmEmployee.setSelectedManufacturerName(manufacturerName);
                vmEmployee.setSelectedNumberOfCopies(numberOfCopies);
                vmEmployee.setSelectedBarCode(barCode);
                vmEmployee.setSelectedPrice(price);
                vmEmployee.setSelectedParfumeAmount(parfumeAmount);
                vmEmployee.setSelectedNumberOfSoldCopies(numberOfSoldCopies);


            }
        });
        JScrollPane sp = new JScrollPane(table);
        displayFrame.add(sp);
        displayFrame.setSize(1050, 200);
        displayFrame.setLocation(450, 0);
        displayFrame.setVisible(true);
        displayFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                vmEmployee.setSelectedStoreName(null);
                vmEmployee.setSelectedParfumeName(null);
                vmEmployee.setSelectedManufacturerName(null);
                vmEmployee.setSelectedNumberOfCopies(null);
                vmEmployee.setSelectedBarCode(null);
                vmEmployee.setSelectedPrice(null);
                vmEmployee.setSelectedParfumeAmount(null);
                vmEmployee.setSelectedNumberOfSoldCopies(null);
            }
        });
    }

}
