package com.task;



public class LittleTask extends ATask {
    private String ddl;


    public LittleTask(String n, String d, Boolean s, String k, String dd) {
        super(n, d, s, k);
        ddl = dd;
    }

    @Override
    public String getDDL() {
        return ddl;
    }


}
