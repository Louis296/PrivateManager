package com.gui;


import com.task.ATask;
import com.tools.TaskList;
import com.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewTaskListFrame {
    JDialog newTaskListFrame;
    JTextField nameText;
    JPanel buttonPanel;
    JPanel namePanel;
    public void go(){
        newTaskListFrame=new JDialog();
        newTaskListFrame.setBounds(
                new Rectangle(
                        (int) MainFrame.frame.getBounds().getX()+50,
                        (int) MainFrame.frame.getBounds().getY()+50,
                        300,150
                )
        );
        namePanel=new JPanel();
        JLabel nameLabel=new JLabel("Name: ");
        nameText=new JTextField(10);
        namePanel.add(nameLabel);
        namePanel.add(nameText);

        buttonPanel=new JPanel();
        JButton ok=new JButton("OK");
        JButton cancel=new JButton("Cancel");
        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        ok.addActionListener(new OkListener());
        cancel.addActionListener(e->newTaskListFrame.setVisible(false));

        newTaskListFrame.getContentPane().add(BorderLayout.CENTER,namePanel);
        newTaskListFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        newTaskListFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        newTaskListFrame.setVisible(true);
    }

    class OkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            TaskList<ATask> newTasks=new TaskList<ATask>(nameText.getText());
            MainFrame.taskLists.add(newTasks);
            MainFrame.tasks=newTasks;
            MainFrame.list.setListData(Tools.taskToString(newTasks));
            MainFrame.list1.setListData(new String[0]);
            MainFrame.infoArea.setText(" ");
            newTaskListFrame.setVisible(false);
        }
    }

}
