package presentationLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginFrame {
    private JLabel userLabel=new JLabel("USERNAME:");
    private JLabel passwordLabel=new JLabel("PASSWORD:");
    private JTextField userTextField=new JTextField("client");
    private JPasswordField passwordField=new JPasswordField("client");
    private JButton loginButton=new JButton("LOGIN");
    private JButton registerButton=new JButton("REGISTER");

    public LoginFrame(){
        JFrame frame =new JFrame();
        frame.setSize(300,300);
        frame.setLayout(new GridLayout(2,1));
        frame.setVisible(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login Client");

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2,2));
        p1.add(userLabel);
        p1.add(userTextField);
        p1.add(passwordLabel);
        p1.add(passwordField);

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(loginButton);
        p2.add(registerButton);
        p2.setSize(100,100);

        frame.add(p1);
        frame.add(p2);

    }

    public String getUserTextField() {
        return userTextField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }

    public void loginButtonListener(ActionListener loginButton) {
        this.loginButton.addActionListener(loginButton);
    }
    public void registerButtonListener(ActionListener registerButton) {
        this.registerButton.addActionListener(registerButton);
    }
}
