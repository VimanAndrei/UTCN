package Controller;

import Model.Observer;
import Model.User.ModelUsers;
import Model.User.User;
import Model.User.UserBuilder;
import View.AdministratorView;

import javax.swing.*;
import java.util.List;

public class ControllerAdministrator implements Observer {
    private ModelUsers model;
    private AdministratorView view;

    public ControllerAdministrator(AdministratorView administratorView ) {
        this.view = administratorView;
        this.model = administratorView.getModel();
        this.addMultipleActionListeners();


    }

    private void addMultipleActionListeners() {
        this.view.getRomaniaButton().addActionListener(e -> {romania();});
        this.view.getEnglishButton().addActionListener(e -> {english();});
        this.view.getFranceButton().addActionListener(e -> {france();});
        this.view.getGermanButton().addActionListener(e -> {german();});
        this.view.getListUsersButton().addActionListener(e -> {listUsers();});
        this.view.getSaveButton().addActionListener(e -> {saveUser();});
        this.view.getDeleteButton().addActionListener(e -> {deleteUser();});
        this.view.getUpdateButton().addActionListener(e -> {updateUser();});
        this.view.getFilteringButton().addActionListener(e -> {filterUsers();});

    }

    private void filterUsers() {
        int r = this.view.getComboBox().getSelectedIndex();
        String role = null;
        if(r==0){
            role = "administrator";
        }else {
            role = "employee";
        }
        this.view.setUsers(this.model.getService().filterUsers(role));
        this.model.setOperation("vizualizare");
    }

    private void updateUser() {
        String selectedUsername = this.view.getSelectedUsername();
        String selectedPassword = this.view.getSelectedPassword();
        String selectedRole = this.view.getSelectedRole();

        if(selectedUsername == null || selectedPassword == null || selectedRole == null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else {
            String username;
            String password;
            String role;

            if(this.view.getUsername().getText().isEmpty()){
                username = selectedUsername;
            }else{
                username = this.view.getUsername().getText();
            }

            if(this.view.getPassword().getText().isEmpty()){
                password = selectedPassword;
            }else{
                password = this.view.getPassword().getText();
            }

           if(this.view.getComboBox().getSelectedIndex() == 0){
               role = "administrator";
           }else{
               role = "employee";
           }
            User user = new UserBuilder().setUsername(username).setPassword(password).setRole(role).build();

            this.model.getService().updateUser(selectedUsername, user);
            this.view.setUsers(this.model.getService().getUsers());
            this.model.setOperation("vizualizare");

        }
    }

    private void deleteUser() {
        String username = this.view.getSelectedUsername();
        if(username==null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else {
            this.model.getService().deleteUser(username);
            this.view.setUsers(this.model.getService().getUsers());
            this.model.setOperation("vizualizare");
        }
    }

    private void saveUser() {
        String username = this.view.getUsername().getText();
        String password = this.view.getPassword().getText();
        Integer r = this.view.getComboBox().getSelectedIndex();
        String role = "";
        if(r == 0){
            role = "administrator";
        }else {
            role = "employee";
        }

        if(username.isEmpty() || password.isEmpty()||role.isEmpty()){
            JOptionPane.showMessageDialog(null, "Unul dintre campuri nu este completat!");
        }else{
            User u = new UserBuilder().setUsername(username).setPassword(password).setRole(role).build();
            if(!this.model.getService().insertUser(u)){
                JOptionPane.showMessageDialog(null, "Nu poate fi inserat acest utilizator!");
            }
            this.model.setOperation("vizualizare");
        }
    }

    private void listUsers() {
        List<User> users = this.model.getService().getUsers();
        this.view.setUsers(users);
        this.model.setOperation("vizualizare");
    }

    private void romania(){
        this.model.setLanguage("romana");
        this.model.setOperation("language");
    }
    private void english(){
        this.model.setLanguage("engleza");
        this.model.setOperation("language");
    }private void france(){
        this.model.setLanguage("franceza");
        this.model.setOperation("language");
    }
    private void german(){
        this.model.setLanguage("germana");
        this.model.setOperation("language");
    }


    @Override
    public void update() {

    }
}
