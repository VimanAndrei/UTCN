package presentationLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class FrameStart {
    private JButton adminButton = new JButton("ADMINISTRATOR");
    private JButton clientButton = new JButton("CLIENT");


    public FrameStart(){
        JFrame frame = new JFrame();
        frame.setSize(300,150);
        frame.setLayout(new GridLayout(3,1));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Meniu de start");
        JPanel p1 = new JPanel();
        p1.add(adminButton);
        p1.setSize(100,200);
        JPanel p2 = new JPanel();
        p2.add(clientButton);
        p2.setSize(100,200);

        frame.add(p1);
        frame.add(p2);

    }

    public void adminButtonListener(ActionListener adminButton) {
        this.adminButton.addActionListener(adminButton);
    }

    public void clientButtonListener(ActionListener clientButton) {
        this.clientButton.addActionListener(clientButton);
    }


}
