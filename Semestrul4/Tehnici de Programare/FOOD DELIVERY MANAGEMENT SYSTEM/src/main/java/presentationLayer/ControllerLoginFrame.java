package presentationLayer;

import businessLayer.DeliveryService;
import model.LoggingAccount;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerLoginFrame {
    private LoginFrame lF;
    private DeliveryService dS;

    public ControllerLoginFrame(LoginFrame lF,DeliveryService dS){
        this.lF=lF;
        this.dS=dS;
        lF.loginButtonListener(new loginButtonActionListener());
        lF.registerButtonListener(new registerButtonActionListener());
    }

    public class loginButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String user = lF.getUserTextField();
            String pass = lF.getPasswordField();
            LoggingAccount a = new LoggingAccount(user,pass);
            if(dS.findAccount(a)){
                ClientFrame cF = new ClientFrame();
                new ControllerClientFrame(cF,dS,a);
            }else{

                JOptionPane.showMessageDialog(new JFrame(), "Nu am gasit clientul!\nTe rog sa te inregistrezi!");
            }

        }
    }
    public class registerButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String user = lF.getUserTextField();
            String pass = lF.getPasswordField();
            LoggingAccount a = new LoggingAccount(user,pass);
            dS.addAccount(a);


        }
    }
}
