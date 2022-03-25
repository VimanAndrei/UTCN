import bll.OrderBLL;
import controller.Controller;
import view.View;

public class Main {
    public static void main(String[] args){
        View v =new View();
        Controller controller =new Controller(v);
    }
}