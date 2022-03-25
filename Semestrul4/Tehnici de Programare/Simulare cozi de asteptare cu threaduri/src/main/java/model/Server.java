package model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Server implements Runnable {

    private int waitingPeriod;
    private int serverWaitingTime;//timpul total de cand a fost deschisa coada
    private BlockingQueue<Task> tasks;
    private int id;

    public Server(int id, int maxLoad) {
        this.id = id;
        this.waitingPeriod = 0;
        this.serverWaitingTime = 0;
        this.tasks = new ArrayBlockingQueue<Task>(maxLoad);
    }

    public int getSizeQueue() {
        return this.tasks.size();
    }

    public int getWaitingPeriod() {
        return this.waitingPeriod;
    }

    public void addTask(Task newTask) {
        tasks.add(newTask);
        waitingPeriod += newTask.getProcessingPeriod();
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    @Override
    public void run() {
        while (true) {
            while (tasks.peek() != null) {
                Task t = tasks.peek();
                try {
                    Thread.sleep(1000);
                    this.waitingPeriod--;
                    int time = t.getProcessingPeriod();
                    time--;
                    t.setProcessingPeriod(time);
                    if (t.getProcessingPeriod() == 0) {
                        tasks.take();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        String r = "";
        if (this.waitingPeriod == 0 || tasks.peek() == null || tasks.peek().getProcessingPeriod() == 0 || tasks.size()==0) {
            r = "CLOSED";
        } else {
            r = tasks.toString();
        }
        return r;
    }
}
