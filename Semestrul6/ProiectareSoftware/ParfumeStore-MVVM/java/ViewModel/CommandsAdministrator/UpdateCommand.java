package ViewModel.CommandsAdministrator;

import Model.User;
import Model.UserBuilder;
import Model.UserService;
import ViewModel.ICommand;
import ViewModel.VMAdministrator;

import javax.swing.*;

public class UpdateCommand implements ICommand {
    private VMAdministrator vmAdministrator;
    private UserService userService;

    public UpdateCommand(VMAdministrator vmAdministrator, UserService userService) {
        this.vmAdministrator = vmAdministrator;
        this.userService = userService;
    }

    @Override
    public void Execute() {
        if(vmAdministrator.getSelectedUsername()==null || vmAdministrator.getSelectedPassword() ==null || vmAdministrator.getSelectedRole() == null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else {
            String username;
            String password;
            String role;

            if(vmAdministrator.getUsername().get().isEmpty()){
                username = vmAdministrator.getSelectedUsername();
            }else{
                username = vmAdministrator.getUsername().get();
            }

            if(vmAdministrator.getPassword().get().isEmpty()){
                password = vmAdministrator.getSelectedPassword();
            }else{
                password = vmAdministrator.getPassword().get();
            }

            if(vmAdministrator.getRole().get().isEmpty()){
                role = vmAdministrator.getSelectedRole();
            }else{
                role = vmAdministrator.getRole().get();
            }
            User user = new UserBuilder().setUsername(username).setPassword(password).setRole(role).build();

            userService.updateUser(vmAdministrator.getSelectedUsername(), user);
        }
    }
}
