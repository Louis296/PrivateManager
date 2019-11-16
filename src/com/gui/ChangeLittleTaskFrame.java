package com.gui;

import com.task.ATask;
import com.task.LittleTask;
import com.tools.Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeLittleTaskFrame {
    JDialog changeLittleFrame;
    JTextField nameText;
    JTextField descriptionText;
    JTextField ddlText;
    JComboBox stateBox;
    ArrayList<ATask> littleTasks;
    JDialog fatherFrame;
    ATask selectedTask;
    public ChangeLittleTaskFrame(ArrayList<ATask> a, JDialog f){
        littleTasks=a;
        fatherFrame=f;
        selectedTask=littleTasks.get(ChangeFrame.selectedIndex);
    }
    public void go(){
        changeLittleFrame =new JDialog();
        changeLittleFrame.setTitle("Change LittleTask");
        changeLittleFrame.setBounds(
                new Rectangle(
                        (int) fatherFrame.getBounds().getX()+50,
                        (int) fatherFrame.getBounds().getY()+50,
                        300,400
                )
        );

        JPanel p1=new JPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
        nameText=new JTextField(selectedTask.getName());
        descriptionText=new JTextField(selectedTask.getDescription());
        ddlText=new JTextField(selectedTask.getDDL());
        stateBox=new JComboBox();
        stateBox.addItem("Finish");
        stateBox.addItem("Not Finish");
        if (selectedTask.getState())
            stateBox.setSelectedIndex(0);
        else
            stateBox.setSelectedIndex(1);
        p1.add(Box.createVerticalStrut(5));
        p1.add(new JLabel("Name: "));
        p1.add(nameText);
        p1.add(new JLabel("Description: "));
        p1.add(descriptionText);
        p1.add(new JLabel("State: "));
        p1.add(stateBox);
        p1.add(new JLabel("DeadLine: "));
        p1.add(ddlText);
        JPanel p2=new JPanel();
        JButton ok=new JButton("OK");
        JButton cancel=new JButton("Cancel");
        ok.addActionListener(new OkListener());
        cancel.addActionListener(e-> changeLittleFrame.setVisible(false));
        p2.add(ok);
        p2.add(cancel);

        changeLittleFrame.getContentPane().add(BorderLayout.NORTH,p1);
        changeLittleFrame.getContentPane().add(BorderLayout.SOUTH,p2);
        changeLittleFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        changeLittleFrame.setVisible(true);
    }
    class OkListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Boolean state;
            if (stateBox.getSelectedIndex()==0)
                state=true;
            else
                state=false;

            ATask a=new LittleTask(nameText.getText(),descriptionText.getText(),state,"Li",ddlText.getText());
            littleTasks.set(ChangeFrame.selectedIndex,a);
            changeLittleFrame.setVisible(false);
        }
    }
}
