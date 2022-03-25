package controller;

import model.SimulationManager;
import view.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private View v;
    private SimulationManager sim;
    private Thread t;
    public Controller(SimulationManager s,View v){
        this.sim=s;
        this.v=v;
        t = new Thread(s);
        v.summitListener(new summitActionListener());
        v.startListener(new startActionListener());
    }

    public class summitActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int N,Q,timeLim,tMaxA,tMinA,tMaxS,tMinS;
            N = Integer.parseInt(v.getUserInputN());
            Q = Integer.parseInt(v.getUserInputQ());
            timeLim = Integer.parseInt(v.getUserInputTimeLimit());
            tMaxA = Integer.parseInt(v.getUserInputTMaxA());
            tMinA = Integer.parseInt(v.getUserInputTMinA());
            tMaxS = Integer.parseInt(v.getUserInputTMaxS());
            tMinS = Integer.parseInt(v.getUserInputTMinS());
            sim.setNumberOfServers(Q);
            sim.setNumberOfClients(N);
            sim.setTimeLimit(timeLim);
            sim.setMaxArrivalTime(tMaxA);
            sim.setMinArrivalTime(tMinA);
            sim.setMaxProcessingTime(tMaxS);
            sim.setMinProcessingTime(tMinS);

        }
    }
    public class startActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            sim.generate();
            v.reset();
            t.start();


        }
    }

}
