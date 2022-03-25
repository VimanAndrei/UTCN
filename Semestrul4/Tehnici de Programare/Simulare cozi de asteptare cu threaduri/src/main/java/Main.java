import controller.Controller;
import model.SimulationManager;
import view.View;

public class Main {
    public static void main(String[] args){
        View view = new View();
        SimulationManager gen = new SimulationManager(view);
        Controller controller= new Controller(gen,view);
        view.setVisible(true);
    }
}
