package View;


import Controller.ControllerAdministrator;
import Model.Observer;
import Model.User.ModelUsers;
import Model.User.User;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;

@Getter
@Setter
public class AdministratorView extends JFrame implements Observer {
    private JPanel panel1;
    private JButton saveButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton listUsersButton;


    private JTextField username;
    private JTextField password;

    private JButton romaniaButton;
    private JButton englishButton;
    private JButton franceButton;
    private JButton germanButton;

    private JComboBox comboBox;

    private JLabel roleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton filteringButton;


    private Frame displayFrame;
    private JTable table;
    private Object[] columnNames = {"Username", "Password", "Role"};
    private List<User> users;

    String selectedUsername;
    String selectedPassword;
    String selectedRole;

    private ModelUsers model;
    private ControllerAdministrator controller;
    public AdministratorView(ModelUsers modelUsers) {
        this.model = modelUsers;
        this.controller = new ControllerAdministrator(this);
        this.users = this.model.getService().getUsers();


        this.setTitle("Administrator");
        this.setSize(500, 300);
        table = new JTable();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.setVisible(true);

        this.model.addObserver(this);
        this.model.addObserver(this.controller);

        this.model.setOperation("language");

    }

    public void setTotal() {
        if (displayFrame != null) displayFrame.dispose();
        displayFrame = new JFrame();
        selectedUsername = null;
        selectedPassword = null;
        selectedRole = null;
        Object[][] data = new Object[users.size()][3];
        for (int i = 0; i<users.size();i++){
            User u = users.get(i);
            data[i][0] = u.getUsername();
            data[i][1] = u.getPassword();
            data[i][2] = u.getRole();

        }
        table = new JTable(data, this.columnNames);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                selectedUsername = table.getValueAt(table.getSelectedRow(), 0).toString();
                selectedPassword = table.getValueAt(table.getSelectedRow(), 1).toString();
                selectedRole = table.getValueAt(table.getSelectedRow(), 2).toString();


            }
        });
        JScrollPane sp = new JScrollPane(table);
        displayFrame.add(sp);
        displayFrame.setSize(500, 200);
        displayFrame.setLocation(500, 0);
        displayFrame.setVisible(true);
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        romaniaButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\romania.png"));
        germanButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\germany.png"));
        englishButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\united-kingdom.png"));
        franceButton = new JButton(new ImageIcon("A:\\Faculta\\An3-Sem2\\PS(Proiectare Software)\\Tema3\\Image\\france.png"));
        String comboData1[]={"administrator","angajat"};
        comboBox = new JComboBox(comboData1);

    }

    private void languageSetData(){
        List<String> componentsName = this.model.getLanguage().getLanguageComponent();
        String username,password,role,save,delete,update,listUsers,filter,admin,empl;
        username = componentsName.get(0);
        password = componentsName.get(1);
        role = componentsName.get(2);
        save = componentsName.get(3);
        delete = componentsName.get(4);
        update = componentsName.get(5);
        listUsers = componentsName.get(6);
        filter = componentsName.get(7);
        admin = componentsName.get(8);
        empl = componentsName.get(9);
        columnNames = new Object[]{username, password, role};
        roleLabel.setText(role);
        usernameLabel.setText(username);
        passwordLabel.setText(password);
        saveButton.setText(save);
        deleteButton.setText(delete);
        filteringButton.setText(filter);
        updateButton.setText(update);
        listUsersButton.setText(listUsers);
        ComboBoxModel comboboxModel = new DefaultComboBoxModel(new String[]{admin, empl});
        this.comboBox.setModel(comboboxModel);
    }

    @Override
    public void update() {
        if(this.model.getOperation().equals("language")){
            this.languageSetData();
            this.setTotal();
        }
        if(this.model.getOperation().equals("vizualizare")){
            this.setTotal();
        }
    }
}
