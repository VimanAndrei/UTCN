package presentationLayer;

import businessLayer.DeliveryService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerFrameStart{
    private FrameStart fS;
    private DeliveryService dS;

    public ControllerFrameStart(FrameStart fS,DeliveryService dS){
        this.fS = fS;
        this.dS = dS;
        fS.adminButtonListener(new adminButtonActionListener());
        fS.clientButtonListener(new clientButtonActionListene());

    }

    public class adminButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            AdministratorFrame frame = new AdministratorFrame();
            new ControllerAdministratorFrame(frame,dS);

        }
    }

    public class clientButtonActionListene implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginFrame frame = new LoginFrame();
            new ControllerLoginFrame(frame,dS);
        }
    }


}
