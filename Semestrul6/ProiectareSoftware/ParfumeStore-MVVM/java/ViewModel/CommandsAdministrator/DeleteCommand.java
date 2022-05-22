package ViewModel.CommandsAdministrator;

import Model.UserService;
import ViewModel.ICommand;
import ViewModel.VMAdministrator;

import javax.swing.*;

public class DeleteCommand implements ICommand {
    private VMAdministrator vmAdministrator;
    private UserService userService;

    public DeleteCommand(VMAdministrator vmAdministrator, UserService userService) {
        this.vmAdministrator = vmAdministrator;
        this.userService = userService;
    }

    @Override
    public void Execute() {
        if(vmAdministrator.getSelectedUsername()==null){
            JOptionPane.showMessageDialog(null, "Nu ati selectat nici o linie din tabela!");
        }else {
            userService.deleteUser(vmAdministrator.getSelectedUsername());
        }
    }
}
