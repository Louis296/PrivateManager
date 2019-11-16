package com.gui;

import com.task.ATask;
import com.task.LittleTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddLittleTaskFrame {
    JDialog addLittleFrame;
    JTextField nameText;
    JTextField descriptionText;
    JTextField ddlText;
    JComboBox stateBox;
    ArrayList<ATask> littleTasks;
    JDialog fatherFrame;
    public AddLittleTaskFrame(ArrayList<ATask> a,JDialog f){
        littleTasks=a;
        fatherFrame=f;
    }
    public void go(){
        addLittleFrame=new JDialog();
        addLittleFrame.setTitle("Add LittleTask");
        addLittleFrame.setBounds(
                new Rectangle(
                        (int) fatherFrame.getBounds().getX()+50,
                        (int) fatherFrame.getBounds().getY()+50,
                        300,400
                )
        );

        JPanel p1=new JPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
        nameText=new JTextField();
        descriptionText=new JTextField();
        ddlText=new JTextField();
        stateBox=new JComboBox();
        stateBox.addItem("Finish");
        stateBox.addItem("Not Finish");

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
        cancel.addActionListener(e->addLittleFrame.setVisible(false));
        p2.add(ok);
        p2.add(cancel);

        addLittleFrame.getContentPane().add(BorderLayout.NORTH,p1);
        addLittleFrame.getContentPane().add(BorderLayout.SOUTH,p2);
        addLittleFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        addLittleFrame.setVisible(true);
    }
    class OkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Boolean state;
            if (stateBox.getSelectedIndex()==0)
                state=true;
            else
                state=false;
            ATask a=new LittleTask(nameText.getText(),descriptionText.getText(),state,"Li",ddlText.getText());
            littleTasks.add(a);
            addLittleFrame.setVisible(false);
        }
    }

}
