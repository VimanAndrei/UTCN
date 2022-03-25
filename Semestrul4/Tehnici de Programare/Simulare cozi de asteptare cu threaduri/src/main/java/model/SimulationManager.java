package model;

import view.View;
import strategy.SelectionPolicy;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationManager implements Runnable {
    private int timeLimit = 60;
    private int minProcessingTime = 2;
    private int maxProcessingTime = 4;
    private int minArrivalTime = 2;
    private int maxArrivalTime = 30;
    private int numberOfServers = 2;
    private int numberOfClients = 4;
    private int maxWaitingTime = 0;
    private int totalProcessingTime = 0;
    private int clientiProcesati = 0;

    private double nr = 0;
    private double nrMaxpers = 0;
    private int pickHour;
    private String rez="";
    private String concl="";
    private View view;


    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    public List<Task> generatedTasks;
    FileWriter fileWriter;
    PrintWriter printWriter;

    public SimulationManager(View v) {
        this.view = v;
    }

    public void generate() {
        scheduler = new Scheduler(this.numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        this.generateNRandomTasks();
    }

    public int randomProcessingTime() {
        return (this.minProcessingTime +
                (int) (Math.random() *
                        (this.maxProcessingTime - this.minProcessingTime)));
    }

    public int randomArrivingTime() {
        return (this.minArrivalTime +
                (int) (Math.random() *
                        (this.maxArrivalTime - this.minArrivalTime)));
    }

    private void generateNRandomTasks() {
        generatedTasks = new ArrayList<Task>();
        for (int i = 0; i < this.numberOfClients; i++) {
            Task t = new Task(i, randomArrivingTime(), randomProcessingTime());
            generatedTasks.add(t);
        }
        Collections.sort(generatedTasks);
    }

    @Override
    public void run() {

        int currentTime = 0;
        Task taskk = null;
        try {
            fileWriter = new FileWriter("Trash.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        printWriter = new PrintWriter(fileWriter);
        while (currentTime < timeLimit) {
            for (Task t : generatedTasks) {
                if (t.getArrivalTime() == currentTime) {

                    scheduler.dispatchTask(t);
                    taskk = t;
                    clientiProcesati++;
                    maxWaitingTime += t.getWaitingTime();
                    totalProcessingTime += taskk.getProcessingPeriod();
                }
            }
            if (taskk != null) {

                generatedTasks.remove(taskk);
            }
            rez = new String();
            printWriter.printf("Time " + (currentTime + 1) + "\n");
            rez = rez + "Time " + (currentTime + 1) + "\n";
            printWriter.printf("Waiting clients: ");
            rez = rez + "Waiting clients: ";
            for (Task t : generatedTasks) {
                printWriter.printf(t.toString() + "; ");
                rez = rez + t.toString() + "; ";
            }
            printWriter.printf("\n");
            rez = rez + "\n";
            for (int i = 0; i < scheduler.getServersSize(); i++) {
                printWriter.printf("Queue " + i + ": ");
                rez = rez + "Queue " + i + ": ";
                nr += scheduler.getServer(i).getSizeQueue();
                for (Task t : scheduler.getServer(i).getTasks()) {
                    printWriter.printf(t + "; ");
                    rez = rez + t + "; ";
                }
                printWriter.printf("\n");
                rez = rez + "\n";
            }
            nr = nr / numberOfServers;
            currentTime++;

            if (nrMaxpers < nr) {
                nrMaxpers = nr;
                pickHour = currentTime;
            }
            try {
                Thread.sleep(1000);
                view.setTotal(rez);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printWriter.printf("\n");
        concl = concl + "\n";
        printWriter.printf("Waiting time: " + (double) maxWaitingTime / clientiProcesati);
        concl = concl + "Waiting time: " + (double) maxWaitingTime / clientiProcesati;
        printWriter.printf("\n");
        concl = concl + "\n";
        printWriter.printf("Service time: " + (double) totalProcessingTime / clientiProcesati);
        concl = concl + "Service time: " + (double) totalProcessingTime / clientiProcesati;
        printWriter.printf("\n");
        concl = concl + "\n";
        printWriter.printf("Peak Hour: " + (double) pickHour);
        concl = concl + "Peak Hour: " + (double) pickHour;
        printWriter.printf("\n");
        concl = concl + "\n";
        view.reset();
        view.setTotal(concl);

        scheduler.stopThreads();
        printWriter.close();

    }

    public void setTimeLimit(int time) {
        this.timeLimit = time;
    }

    public void setMinProcessingTime(int min) {
        this.minProcessingTime = min;
    }

    public void setMaxProcessingTime(int max) {
        this.maxProcessingTime = max;
    }

    public void setMinArrivalTime(int min) {
        this.minArrivalTime = min;
    }

    public void setMaxArrivalTime(int max) {
        this.maxArrivalTime = max;
    }

    public void setNumberOfServers(int Q) {
        this.numberOfServers = Q;
    }

    public void setNumberOfClients(int N) {
        this.numberOfClients = N;
    }


}
