package View;


import Presenter.UserPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserLogin extends JFrame implements IUserLogin {
    private JTextField textBoxUserNameTextField;
    private JButton loginButton;
    private JPanel rootJpanel;
    private JPanel rootJpanel1;
    private JLabel textField1;
    private JLabel textField2;
    private JPasswordField textBoxPasswordTextField;


    public UserLogin() {
        this.textField1 = new JLabel("Username: ");
        this.textField2 = new JLabel("Password: ");
        this.setTitle("Login Menu");
        rootJpanel = new JPanel();
        rootJpanel.setLayout(new GridLayout(2,2));
        rootJpanel1 = new JPanel();
        rootJpanel1.setLayout(new GridLayout(2,1));

        rootJpanel.add(textField1);
        rootJpanel.add(textBoxUserNameTextField);
        rootJpanel.add(textField2);
        rootJpanel.add(textBoxPasswordTextField);

        rootJpanel1.add(rootJpanel);
        rootJpanel1.add(loginButton);

        this.add(rootJpanel1);
        this.setSize(300,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserPresenter userPresenter = new UserPresenter(UserLogin.this);
                userPresenter.actionLisenerLogin();
            }
        });
    }

    @Override
    public String getUserName() {
        return this.textBoxUserNameTextField.getText();
    }

    @Override
    public String getPassword() {
       return this.textBoxPasswordTextField.getText();
    }


}
