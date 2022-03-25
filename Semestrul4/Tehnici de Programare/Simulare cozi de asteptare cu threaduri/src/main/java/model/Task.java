package model;

public class Task implements Comparable<Task> {
    private int orderNumber;
    private int arrivalTime;
    private int waitingTime;
    private int  processingPeriod;

    // FinishTime = arrivalTime + processingPeriod+ waitingPeriodOnChosenServer


    public Task(int orderNumber,int arrivalTime,int processingPeriod){
        this.orderNumber=orderNumber;
        this.arrivalTime=arrivalTime;
        this.processingPeriod=processingPeriod;
    }
    //gettere

    public int getOrderNumber(){
        return this.orderNumber;
    }
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    public int getWaitingTime(){
        return this.waitingTime;
    }
    public int getProcessingPeriod(){
        return this.processingPeriod;
    }

    //settere

    public void setOrderNumber(int orderNumber){
         this.orderNumber = orderNumber;
    }
    public void setArrivalTime(int arrivalTime){
         this.arrivalTime = arrivalTime;
    }
    public void setWaitingTime(int finishTime){
          this.waitingTime = finishTime;
    }
    public void setProcessingPeriod(int processingPeriod){
          this.processingPeriod = processingPeriod;
    }

    @Override
    public String toString() {
        String rez = "";
        rez =   "(ID: " + (this.orderNumber+1) +
                " arrival: " + this.arrivalTime +
                " processing: "+ this.processingPeriod +")";
        return rez;
    }

    @Override
    public int compareTo(Task o) {
        return this.arrivalTime-o.getArrivalTime();
    }
}
