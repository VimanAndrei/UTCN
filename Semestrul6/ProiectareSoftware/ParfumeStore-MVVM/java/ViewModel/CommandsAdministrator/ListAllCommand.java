package ViewModel.CommandsAdministrator;

import Model.User;
import Model.UserService;
import ViewModel.ICommand;
import ViewModel.VMAdministrator;

import java.util.List;

public class ListAllCommand implements ICommand {
    private VMAdministrator vmAdministrator;
    private UserService userService;


    public ListAllCommand(VMAdministrator administrator,UserService userService) {
        this.vmAdministrator = administrator;
        this.userService = userService;
    }


    @Override
    public void Execute() {
        List <User> users = userService.getUsers();
        Object[][] data = new Object[users.size()][3];
        for (int i = 0; i<users.size();i++){
            User u = users.get(i);
            data[i][0] = u.getUsername();
            data[i][1] = u.getPassword();
            data[i][2] = u.getRole();

        }
        vmAdministrator.setData(data);

    }
}
