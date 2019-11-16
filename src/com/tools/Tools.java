package com.tools;

import com.task.ATask;

import java.util.ArrayList;

public class Tools {
    public static String[] taskToString(ArrayList<ATask> a){
        ArrayList<String> s=new ArrayList<String>();
        for (ATask i:a){
            s.add(i.getName());
        }
        String[] strings=s.toArray(new String[s.size()]);
        return strings;
    }

    public static String[] taskslistsToString(ArrayList<TaskList<ATask>> a){
        ArrayList<String> s=new ArrayList<String>();
        for (TaskList<ATask> i:a){
            s.add(i.getName());
        }
        String[] strings=s.toArray(new String[s.size()]);
        return strings;
    }

    public static String getState(ATask a){
        if (a.getState())
            return "Finish";
        else
            return "Not Finish";
    }

    public static int[] getDDLDate(ATask a){
        if (a.getDDL()!=null){
            String[] s=a.getDDL().split("\\.");
            int year=Integer.parseInt(s[0]);
            int month=Integer.parseInt(s[1]);
            int day=Integer.parseInt(s[2]);
            int[] date={year,month,day};
            return date;
        }
        else
            return null;
    }

    public static Boolean checkInputDDL(String s){
        if (s!=null){
            String[] strings=s.split("\\.");
            String s1=String.join("",strings);
            for (int i=s1.length();--i>=0;){
                if (!Character.isDigit(s1.charAt(i)))
                    return false;
            }
            return true;
        }
        else
            return false;
    }
}
