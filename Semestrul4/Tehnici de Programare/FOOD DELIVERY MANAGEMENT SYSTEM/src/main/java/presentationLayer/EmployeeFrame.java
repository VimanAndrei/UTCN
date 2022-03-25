package presentationLayer;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class EmployeeFrame implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        JOptionPane.showMessageDialog(new JFrame(), "Angajatii se ocupa de comanda dumneavoastra!");
    }
}
