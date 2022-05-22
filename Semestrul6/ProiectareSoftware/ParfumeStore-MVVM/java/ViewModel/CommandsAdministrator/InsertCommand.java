package ViewModel.CommandsAdministrator;

import Model.User;
import Model.UserBuilder;
import Model.UserService;
import ViewModel.ICommand;
import ViewModel.VMAdministrator;

import javax.swing.*;

public class InsertCommand implements ICommand {
    private VMAdministrator administrator;
    private UserService userService;


    public InsertCommand(VMAdministrator administrator, UserService userService) {
        this.administrator = administrator;
        this.userService = userService;
    }

    @Override
    public void Execute() {
        if(administrator.getUsername().get().isEmpty() || administrator.getPassword().get().isEmpty()||administrator.getRole().get().isEmpty()){
            JOptionPane.showMessageDialog(null, "Unul dintre campuri nu este completat!");
        }else{
            User u = new UserBuilder().setUsername(administrator.getUsername().get()).setPassword(administrator.getPassword().get()).setRole(administrator.getRole().get()).build();
            if(!userService.insertUser(u)){
                JOptionPane.showMessageDialog(null, "Nu poate fi inserat acest utilizator!");
            }
        }

    }
}
