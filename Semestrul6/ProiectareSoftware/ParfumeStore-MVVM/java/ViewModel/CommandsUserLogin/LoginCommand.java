package ViewModel.CommandsUserLogin;

import Model.User;
import Model.UserService;
import View.AdministratorView;
import View.EmployeeView;
import ViewModel.ICommand;
import ViewModel.VMLogin;

import javax.swing.*;

public class LoginCommand implements ICommand {
    private VMLogin vmLog;
    private UserService userService;

    public LoginCommand(VMLogin vmLog) {
        this.vmLog = vmLog;
        this.userService = new UserService();
    }

    @Override
    public void Execute() {
        String userName, password;
        userName = vmLog.getUsername().get();
        password = vmLog.getPassword().get();
        User user = userService.getUserByUsername(userName);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                if(user.getRole().equals("administrator")){
                    new AdministratorView();
                }else{
                    new EmployeeView();
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
