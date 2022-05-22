package Model;


import java.util.List;

public abstract class AbstractObserver {
    protected List<Observer> observerList;

    public void addObserver(Observer observer){
        observerList.add(observer);
    }
    public void removeObserver(Observer observer){
        observerList.remove(observer);
    }
    public void notifyObservers(){
        for (Observer o : observerList){
            o.update();
        }
    }
}
