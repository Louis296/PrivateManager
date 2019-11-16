package com.task;

import java.io.Serializable;
import java.util.ArrayList;

public class ATask implements Serializable {
    protected String name;
    protected String description;
    protected Boolean state;
    protected String key;
    public ATask(String n, String d, Boolean s,String k){
        name=n;
        description=d;
        state=s;
        key=k;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public Boolean getState(){
        return state;
    }
    public String getKey(){
        return key;
    }
    public String getDDL(){
        return null;
    }
    public String getStartdate(){
        return null;
    }
    public int getTimes(){
        return 0;
    }
    public int getCycle(){
        return 0;
    }
    public ArrayList<ATask> getTasks(){
        return null;
    }

}
