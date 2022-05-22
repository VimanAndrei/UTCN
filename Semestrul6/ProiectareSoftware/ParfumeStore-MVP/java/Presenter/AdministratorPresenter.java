package Presenter;

import Model.User;
import Model.UserService;
import View.IAdministratorView;

import javax.swing.*;

public class AdministratorPresenter {
    private IAdministratorView view;
    private UserService userService;

    public AdministratorPresenter(IAdministratorView view) {
        this.view = view;
        this.userService = new UserService();
    }

    public void listEmployee() {
        view.afisUsers(userService.toString());
    }

    public void addNewUser() {
        String username = view.getUserNameFromTField();
        String password = view.getPasswordFromTField();
        String role = view.getRoleFromTField();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume de utilizator!");
        } else {
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Adaugati o parola!");
            } else if (role.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Adaugati un rol pentru aceasta persona!");
            } else {
                User user = new User(username, password, role);
                if (!userService.addUser(user)) {
                    JOptionPane.showMessageDialog(null, "Nu a putut fi adaugat acest utilizator deoarece exista deja!");
                }
            }
        }
    }

    public void deleteUser() {
        String username = view.getUserNameFromTField();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume de utilizator pentru a realiza stergerea!");
        } else {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Nu exista acest utilizator !");
            } else {
                userService.removeUser(user);
            }
        }

    }

    public void updateUser() {
        String username = view.getUserNameFromTField();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Adaugati un nume de utilizator pentru a realiza actualizarea!");
        } else {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                JOptionPane.showMessageDialog(null, "Nu exista acest utilizator !");
            } else {
                String newUsername = view.getUpdateUserNameFromTField();
                String newPassword = view.getUpdatePasswordFromTField();
                String newRole = view.getUpdateRoleFromTField();
                if (newUsername.isEmpty() && newPassword.isEmpty() && newRole.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nu ati introdus nici un camp pentru actualizare!");
                } else {
                    if (newUsername.isEmpty()) {
                        newUsername = user.getUserName();
                    }
                    if (newPassword.isEmpty()) {
                        newPassword = user.getPassword();
                    }
                    if (newRole.isEmpty()) {
                        newRole = user.getRole();
                    }
                    User newUser = new User(newUsername, newPassword, newRole);
                    userService.updateUser(user, newUser);
                }
            }
        }
    }
}
