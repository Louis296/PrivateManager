package com.tools;


import com.task.ATask;

import java.util.ArrayList;
import java.util.Comparator;

public class TasksSorter {

    public static void sortByName(ArrayList<ATask> tasks){
        Comparator<ATask> comparator= (t1, t2) -> t1.getName().compareTo(t2.getName());
        tasks.sort(comparator);
    }

    public static void sortByDescription(ArrayList<ATask> tasks){
        Comparator<ATask> comparator= (aTask, t1) -> aTask.getDescription().compareTo(t1.getDescription());
        tasks.sort(comparator);
    }

    public static void sortByDDL(ArrayList<ATask> tasks){
        Comparator<ATask> comparator= (aTask, t1) -> {
            if (aTask.getDDL()==null)
                return 1;
            else if (t1.getDDL()==null)
                return -1;
            else{
                int[] a=Tools.getDDLDate(aTask);
                int[] b=Tools.getDDLDate(t1);
                if (a[0]!=b[0]){
                    if (a[0]>b[0])
                        return 1;
                    else
                        return -1;
                }
                else if (a[1]!=b[1]){
                    if (a[1]>b[1])
                        return 1;
                    else
                        return -1;
                }
                else if (a[2]!=b[2]){
                    if (a[2]>b[2])
                        return 1;
                    else
                        return -1;
                }
                else
                    return 0;

            }


        };
        tasks.sort(comparator);
    }

    public static void sortByState(ArrayList<ATask> tasks){
        Comparator<ATask> comparator= (aTask, t1) -> {
            if (aTask.getState()&&t1.getState())
                return 0;
            else if (aTask.getState())
                return 1;
            else if (t1.getState())
                return -1;
            return 0;
        };
        tasks.sort(comparator);
    }
}
