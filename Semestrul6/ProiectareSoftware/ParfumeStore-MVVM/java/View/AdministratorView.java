package View;


import ViewModel.VMAdministrator;
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

public class AdministratorView extends JFrame {
    private JPanel panel1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton listUsersButton;

    @Bind(value = "text", target = "username.value")
    private JTextField username;
    @Bind(value = "text", target = "password.value")
    private JTextField password;
    @Bind(value = "text", target = "role.value")
    private JTextField role;


    private Frame displayFrame;
    private JTable table;
    private final Object[] columnNames = {"Username", "Password", "Role",};


    private VMAdministrator vmAdministrator;


    public AdministratorView() {

        this.setTitle("Administrator");
        this.setSize(450, 300);
        table = new JTable();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setVisible(true);

        this.vmAdministrator = new VMAdministrator();
        try {
            Binder.bind(this, vmAdministrator);
        } catch (BindingException e) {
            System.out.println("Error binding on administrator view!");
        }


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdministrator.insertCommand.Execute();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdministrator.updateCommand.Execute();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdministrator.deleteCommand.Execute();
            }
        });
        listUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vmAdministrator.listAllCommand.Execute();
                setTotal(vmAdministrator.getData(), columnNames);
            }
        });

    }

    public void setTotal(Object[][] data, Object[] column) {
        if (displayFrame != null) displayFrame.dispose();
        displayFrame = new JFrame();
        displayFrame.setTitle("Users");
        table = new JTable(data, column);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                String selectedUsername = table.getValueAt(table.getSelectedRow(), 0).toString();
                String selectedPassword = table.getValueAt(table.getSelectedRow(), 1).toString();
                String selectedRole = table.getValueAt(table.getSelectedRow(), 2).toString();
                vmAdministrator.setSelectedPassword(selectedPassword);
                vmAdministrator.setSelectedRole(selectedRole);
                vmAdministrator.setSelectedUsername(selectedUsername);

            }
        });
        JScrollPane sp = new JScrollPane(table);
        displayFrame.add(sp);
        displayFrame.setSize(500, 200);
        displayFrame.setLocation(450, 0);
        displayFrame.setVisible(true);
        displayFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
              vmAdministrator.setSelectedUsername(null);
              vmAdministrator.setSelectedRole(null);
              vmAdministrator.setSelectedPassword(null);
            }
        });
    }


}
