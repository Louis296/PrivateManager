package com.task;

public class TemporaryTask extends ATask {
    private String ddl;


    public TemporaryTask(String n, String d, Boolean s, String k,String dd) {
        super(n, d, s, k);
        ddl=dd;
    }

    @Override
    public String getDDL() {
        return ddl;
    }


}
