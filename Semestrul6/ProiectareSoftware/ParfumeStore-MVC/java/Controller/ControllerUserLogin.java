package Controller;

import Model.Observer;
import Model.User.ModelUsers;
import Model.User.User;
import View.UserLogin;

import javax.swing.*;

public class ControllerUserLogin implements Observer {
    private UserLogin view;
    private ModelUsers model;

    public ControllerUserLogin(UserLogin view) {
        this.view = view;
        this.model = view.getModel();
        this.addMultipleActionListeners();
    }

    private void addMultipleActionListeners() {
        this.view.getLoginButton().addActionListener(e -> {
            accesLogin();
        });
    }
    private void accesLogin()
    {
        this.model.setOperation("login");
    }

    @Override
    public void update() {
        if(this.model.getOperation().equals("login")) {
            String userName, password;
            userName = view.getUserNameTextField().getText();
            password = view.getPasswordTextField().getText();
            User user = this.model.getService().getUserByUsername(userName);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    if (user.getRole().equals("administrator")) {
                        this.model.setOperation("administrator");
                    } else {
                        this.model.setOperation("employee");
                    }
                    //this.userLogin.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Parola gresita!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nu exista acest username!");
            }
        }
    }
}
