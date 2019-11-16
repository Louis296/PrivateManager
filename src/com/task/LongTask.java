package com.task;

import java.util.ArrayList;

public class LongTask extends ATask {
    private String ddl;
    private ArrayList<ATask> tasks;

    public LongTask(String n, String d, Boolean s, String k, String dd, ArrayList<ATask> t) {
        super(n, d, s, k);
        ddl=dd;
        tasks=t;
    }

    public LongTask(String n, String d, Boolean s, String k, String dd) {
        super(n, d, s, k);
        ddl=dd;
    }
    @Override
    public String getDDL() {
        return ddl;
    }

    @Override
    public ArrayList<ATask> getTasks() {
        return tasks;
    }
}
