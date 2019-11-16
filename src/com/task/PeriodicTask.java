package com.task;

public class PeriodicTask extends ATask {
    private String startdate;
    private int times;
    private int cycle;

    public PeriodicTask(String n, String d, Boolean s, String k, String st, int t, int c){
        super(n,d,s,k);
        startdate=st;
        times=t;
        cycle=c;
    }
    public String getStartdate(){
        return startdate;
    }
    public int getTimes(){
        return times;
    }
    public int getCycle(){
        return cycle;
    }


}
