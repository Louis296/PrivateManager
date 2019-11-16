package com.gui;

import com.task.*;
import com.tools.Tools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChangeFrame {
    ATask selectedTask=MainFrame.tasks.get(MainFrame.selectedIndex);
    JDialog changeFrame;
    JTextField nameText;
    JTextField descriptionText;
    JTextField ddlText;
    JTextField startdateText;
    JTextField timesText;
    JTextField cycleText;
    JComboBox stateBox;
    JList list;
    static int selectedIndex=-1;
    ArrayList<ATask> littleTasks;
    public void go(){
        changeFrame=new JDialog();
        changeFrame.setTitle("ChangeInformation");
        changeFrame.setBounds(
                new Rectangle(
                        (int) MainFrame.frame.getBounds().getX()+50,
                        (int) MainFrame.frame.getBounds().getY()+50,
                        300,500
                )
        );

        JPanel p1=new JPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
        nameText=new JTextField(selectedTask.getName());
        descriptionText=new JTextField(selectedTask.getDescription());
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
        if (selectedTask.getKey().equals("P")){
            startdateText=new JTextField(selectedTask.getStartdate());
            timesText=new JTextField(String.valueOf(selectedTask.getTimes()));
            cycleText=new JTextField(String.valueOf(selectedTask.getCycle()));
            p1.add(new JLabel("StartDate: "));
            p1.add(startdateText);
            p1.add(new JLabel("Times: "));
            p1.add(timesText);
            p1.add(new JLabel("Cycle: "));
            p1.add(cycleText);

        }
        else if (selectedTask.getKey().equals("T")){
            ddlText=new JTextField(selectedTask.getDDL());
            p1.add(new JLabel("DeadLine: "));
            p1.add(ddlText);
        }
        else if (selectedTask.getKey().equals("L")){
            ddlText=new JTextField(selectedTask.getDDL());
            list=new JList();
            littleTasks=selectedTask.getTasks();
            list.setListData(Tools.taskToString(littleTasks));
            list.getSelectionModel().addListSelectionListener(new ListSelectionHandle());
            JScrollPane scrollPane=new JScrollPane();
            scrollPane.setViewportView(list);

            p1.add(new JLabel("DeadLine: "));
            p1.add(ddlText);
            p1.add(new JLabel("SonTask: "));
            p1.add(scrollPane);

            JPanel bp=new JPanel();
            JButton add=new JButton("Add");
            JButton change=new JButton("Change");
            JButton del=new JButton("Delete");
            add.addActionListener(new AddListener());
            change.addActionListener(new ChangeListener());
            del.addActionListener(new DelListener());
            bp.add(add);
            bp.add(change);
            bp.add(del);
            p1.add(bp);
        }

        JPanel p2=new JPanel();
        JButton ok=new JButton("OK");
        JButton cancel=new JButton("Cancel");
        ok.addActionListener(new OkListener());
        cancel.addActionListener(e->changeFrame.setVisible(false));
        p2.add(ok);
        p2.add(cancel);

        changeFrame.getContentPane().add(p1,BorderLayout.NORTH);
        changeFrame.getContentPane().add(p2,BorderLayout.SOUTH);
        changeFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        changeFrame.setVisible(true);
    }

    class OkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Boolean flag=true;
            if (ddlText!=null){
                flag=Tools.checkInputDDL(ddlText.getText());
            }
            if (!flag){
                JOptionPane.showMessageDialog(changeFrame,"DDL Error","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                Boolean newState;

                if (stateBox.getSelectedIndex()==0)
                    newState=true;
                else
                    newState=false;

                ATask newTask = null;
                if (selectedTask.getKey().equals("L")){
                    newTask=new LongTask(nameText.getText(),descriptionText.getText(),newState,"L",ddlText.getText(),
                            selectedTask.getTasks());
                }
                if (selectedTask.getKey().equals("P")){
                    newTask=new PeriodicTask(nameText.getText(),descriptionText.getText(),newState,"P",
                            startdateText.getText(),Integer.parseInt(timesText.getText()),
                            Integer.parseInt(cycleText.getText()));
                }
                if (selectedTask.getKey().equals("T")){
                    newTask=new TemporaryTask(nameText.getText(),descriptionText.getText(),newState,"T",
                            ddlText.getText());
                }
                MainFrame.tasks.set(MainFrame.selectedIndex,newTask);
                MainFrame.nameList=Tools.taskToString(MainFrame.tasks);
                changeFrame.setVisible(false);
            }
        }
    }

    class ListSelectionHandle implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            ListSelectionModel lsm=(ListSelectionModel)listSelectionEvent.getSource();
            selectedIndex=lsm.getLeadSelectionIndex();
        }
    }

    class AddListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AddLittleTaskFrame(littleTasks,changeFrame).go();
            list.setListData(Tools.taskToString(littleTasks));
        }
    }

    class ChangeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ChangeLittleTaskFrame(littleTasks,changeFrame).go();
            list.setListData(Tools.taskToString(littleTasks));
        }
    }

    class DelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (selectedIndex>=0){
                littleTasks.remove(selectedIndex);
                list.setListData(Tools.taskToString(littleTasks));
            }
            selectedIndex=-1;
        }
    }
}
