package model;

import strategy.ConcreteStrategyQueue;
import strategy.ConcreteStrategyTime;
import strategy.SelectionPolicy;
import strategy.Strategy;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private List<Thread> threads;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int noServers, int tasksPerServer){
        this.maxNoServers = noServers;
        this.maxTasksPerServer = tasksPerServer;
        this.servers = new ArrayList<Server>(noServers);
        this.threads = new ArrayList<Thread>(noServers);
        for(int i = 0; i < noServers; i++){
            Server s = new Server(i, tasksPerServer);
            servers.add(s);
            Thread t = new Thread(s,"Q"+(i+1));
            threads.add(t);
            t.start();
        }
    }

    public void stopThreads(){
        for (Thread t:threads) {
            t.stop();
        }
    }

    public void changeStrategy(SelectionPolicy policy) {
        if(policy == SelectionPolicy.SHORTEST_QUEUE)
            strategy = new ConcreteStrategyQueue();
        else if(policy == SelectionPolicy.SHORTEST_TIME)
            strategy = new ConcreteStrategyTime();
    }

    public void dispatchTask(Task t){
        strategy.addTask(servers,t);
    }

    public List<Server> getServers(){
        return this.servers;
    }
    public Server getServer(int i){
        return this.servers.get(i);
    }
    public List<Thread> getThreads(){
        return this.threads;
    }
    public int getServersSize(){
        return this.servers.size();
    }



}
