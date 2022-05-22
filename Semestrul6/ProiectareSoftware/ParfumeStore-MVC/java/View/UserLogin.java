package View;
import Controller.ControllerUserLogin;
import Model.Observer;
import Model.Parfume.ModelParfumes;
import Model.User.ModelUsers;
import lombok.Getter;
import javax.swing.*;


@Getter
public class UserLogin extends JFrame implements Observer {
    private JButton loginButton;
    private JPanel rootJpanel;
    private JLabel jLabelUserName;
    private JLabel jLabelPassword;
    private JTextField userNameTextField;
    private JPasswordField passwordTextField;

    private ModelUsers model;
    private ControllerUserLogin controller;

    public UserLogin(ModelUsers model) {
        this.model = model;
        this.controller = new ControllerUserLogin(this);

        this.add(rootJpanel);
        this.setSize(300,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.model.addObserver(this);
        this.model.addObserver(this.controller);
    }

    @Override
    public void update() {
        if(model.getOperation().equals("employee")){
            ModelParfumes mp = new ModelParfumes();
            new EmployeeView(mp);
        }else if(model.getOperation().equals("administrator")){
            ModelUsers ms = new ModelUsers();
            new AdministratorView(ms);
        }
    }
}
