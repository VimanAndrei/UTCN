package View;

import Presenter.AdministratorPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministratorView extends JFrame implements IAdministratorView{
    private JPanel panel1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton listUsersButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextPane textPane1;

    public AdministratorView() {
        this.setTitle("Administrator");
        this.setSize(450,300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panel1);
        this.setVisible(true);
        AdministratorPresenter adminPresenter = new AdministratorPresenter(AdministratorView.this);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.addNewUser();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.updateUser();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.deleteUser();
            }
        });
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.listEmployee();
            }
        });
    }

    @Override
    public void afisUsers(String afis) {
        this.textPane1.setText(afis);
    }

    @Override
    public String getUserNameFromTField() {
        return this.textField1.getText();
    }

    @Override
    public String getUpdateUserNameFromTField() {
        return this.textField2.getText();
    }

    @Override
    public String getPasswordFromTField() {
        return this.textField4.getText();
    }

    @Override
    public String getUpdatePasswordFromTField() {
        return this.textField3.getText();
    }

    @Override
    public String getRoleFromTField() {
        return this.textField5.getText();
    }

    @Override
    public String getUpdateRoleFromTField() {
        return this.textField6.getText();
    }
}
