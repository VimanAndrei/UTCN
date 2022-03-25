package POLINOAME;

import POLINOAME.controller.Controller;
import POLINOAME.model.Model;
import POLINOAME.view.View;

public class Main {

    public static void main(String[] args) {

        Model pModel      = new Model();
        View pView       = new View(pModel);
        Controller pController = new Controller(pModel, pView);
        pView.setVisible(true);

    }

}
