package View;

import Presenter.EmployeePresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame implements IEmployeeView {
    private JTextArea textArea1;
    private JButton deleteButton;
    private JButton afisareParfumuriButton;
    private JButton filtrareDupaProducatorButton;
    private JButton filtrareDupaDisponibilitateButton;
    private JButton addButton;
    private JButton updateButton;
    private JTextField numOfCopies;
    private JTextField updNumOfCopies;
    private JTextField manufacturerName;
    private JTextField updManufacturerName;
    private JTextField price;
    private JTextField updPrice;
    private JTextField parfumeAmount;
    private JTextField updParfumeAmount;
    private JTextField numOfSoldCopies;
    private JTextField updNumOfSoldCopies;
    private JPanel parfumeName;
    private JTextField updparfumeName;
    private JTextField storeName;
    private JTextField updStoreName;
    private JPanel mainPanel;
    private JTextField barCode;
    private JTextField updBarCode;
    private JTextField parfumeNameF;
    private JButton cautare;
    private JButton filtrareDupaPretButton;
    private JButton salvareInMaiMulteButton;
    private JButton afisareProduseDintrUnButton;

    public EmployeeView(){
        this.setTitle("Employee");
        this.setSize(1550,820);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.setVisible(true);
        EmployeePresenter employeePresenter = new EmployeePresenter(EmployeeView.this);

        afisareParfumuriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.listParfumes();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.addParfume();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.deleteParfume();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.updateParfume();
            }
        });
        cautare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.searchByParfumeName();
            }
        });
        filtrareDupaDisponibilitateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.filterByNumOfCopies();
            }
        });
        filtrareDupaProducatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.filterByManufacturerName();
            }
        });
        filtrareDupaPretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.filterByPrice();
            }
        });
        salvareInMaiMulteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.saveAsOtherFiles();
            }
        });
        afisareProduseDintrUnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.filterByStore();
            }
        });
    }

    @Override
    public void afisParfumes(String afis) {
        this.textArea1.setText(afis);
    }

    @Override
    public String getManufacturerName() {
        return this.manufacturerName.getText();
    }

    @Override
    public String getNumOfCopies() {
        return this.numOfCopies.getText();
    }

    @Override
    public String getBarCode() {
        return this.barCode.getText();
    }

    @Override
    public String getParfumeAmount() {
        return this.parfumeAmount.getText();
    }

    @Override
    public String getPrice() {
        return this.price.getText();
    }

    @Override
    public String getNumOfSoldCopies() {
        return this.numOfSoldCopies.getText();
    }

    @Override
    public String getParfumeName() {
        return this.parfumeNameF.getText();
    }

    @Override
    public String getStoreName() {
        return this.storeName.getText();
    }

    @Override
    public String getUpdManufacturerName() {
        return this.updManufacturerName.getText();
    }

    @Override
    public String getUpdNumOfCopies() {
        return this.updNumOfCopies.getText();
    }

    @Override
    public String getUpdBarCode() {
        return this.updBarCode.getText();
    }

    @Override
    public String getUpdParfumeAmount() {
        return this.updParfumeAmount.getText();
    }

    @Override
    public String getUpdPrice() {
        return this.updPrice.getText();
    }

    @Override
    public String getUpdNumOfSoldCopies() {
        return this.updNumOfSoldCopies.getText();
    }

    @Override
    public String getUpdParfumeName() {
        return this.updparfumeName.getText();
    }

    @Override
    public String getUpdStoreName() {
        return this.updStoreName.getText();
    }
}
