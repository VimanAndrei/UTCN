package Presenter;

import Model.User;
import Model.UserService;
import View.AdministratorView;
import View.EmployeeView;
import View.IUserLogin;
import View.UserLogin;
import javax.swing.*;


public class UserPresenter {
    private IUserLogin userLogin;
    private UserService userService;

    public UserPresenter(IUserLogin userLogin) {
        this.userLogin = userLogin;
        this.userService = new UserService();
    }

    public void actionLisenerLogin() {
        String userName, password;
        userName = this.userLogin.getUserName();
        password = this.userLogin.getPassword();
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
