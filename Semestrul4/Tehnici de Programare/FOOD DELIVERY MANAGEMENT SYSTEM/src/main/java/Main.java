
import businessLayer.DeliveryService;
import presentationLayer.ControllerFrameStart;
import presentationLayer.FrameStart;


public class Main {

    public static void main(String[] args) {

        FrameStart start = new FrameStart();
        DeliveryService dS = new DeliveryService();
        new ControllerFrameStart(start,dS);


    }

}
