package com.tools;

import java.util.ArrayList;

public class TaskList<E> extends ArrayList<E> {
    private String name;

    public TaskList(){}

    public TaskList(String n){
        super();
        name=n;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        name=n;
    }
}
