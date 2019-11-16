package com.tools;

import com.gui.MainFrame;
import com.task.ATask;

public class TasksOutputer {
    ATask selectedTask;
    public void go(){
        selectedTask=MainFrame.tasks.get(MainFrame.selectedIndex);
        MainFrame.infoArea.setText("Name: "+selectedTask.getName()+"\n");
        MainFrame.infoArea.append("Description: "+selectedTask.getDescription()+"\n");
        MainFrame.infoArea.append("State: "+Tools.getState(selectedTask)+"\n");
        if (selectedTask.getKey().equals("P")){
            MainFrame.infoArea.append("StartDate: "+selectedTask.getStartdate()+"\n");
            MainFrame.infoArea.append("Times: "+selectedTask.getTimes()+"\n");
            MainFrame.infoArea.append("Cycle: "+selectedTask.getCycle()+"\n");
        }
        else
            MainFrame.infoArea.append("DeadLine: "+selectedTask.getDDL()+"\n");

    }
}
