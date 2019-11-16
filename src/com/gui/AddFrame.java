package com.gui;

import com.task.*;
import com.tools.Tools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class AddFrame {
    public static JDialog addFrame;
    JComboBox selectTaskModel;
    JTextField nameText;
    JTextField descriptionText;
    JTextField ddlText;
    JTextField startdateText;
    JTextField timesText;
    JTextField cycleText;
    JPanel p1;
    JComboBox stateBox;
    JList list;
    int selectIndex;
    public static ArrayList<ATask> littleTasks;
    public void go(){
        addFrame=new JDialog();
        addFrame.setTitle("Add Task");
        addFrame.setBounds(
                new Rectangle(
                        (int) MainFrame.frame.getBounds().getX()+50,
                        (int) MainFrame.frame.getBounds().getY()+50,
                        300,500
                )
        );
        selectTaskModel=new JComboBox();
        selectTaskModel.addItem("--PleaseSelectTaskModel--");
        selectTaskModel.addItem("LongTask");
        selectTaskModel.addItem("PeriodicTask");
        selectTaskModel.addItem("TemporaryTask");
        selectTaskModel.addItemListener(new selectModelListener());
        stateBox=new JComboBox();
        stateBox.addItem("Finish");
        stateBox.addItem("Not Finish");
        nameText=new JTextField();
        descriptionText=new JTextField();

        p1=new JPanel();
        p1.setLayout(new BoxLayout(p1,BoxLayout.Y_AXIS));
        p1.add(Box.createVerticalStrut(5));
        p1.add(selectTaskModel);

        JPanel p2=new JPanel();
        JButton ok=new JButton("OK");
        JButton cancel=new JButton("Cancel");
        ok.addActionListener(new OkListener());
        cancel.addActionListener(e->addFrame.setVisible(false));
        p2.add(ok);
        p2.add(cancel);

        addFrame.getContentPane().add(BorderLayout.NORTH,p1);
        addFrame.getContentPane().add(BorderLayout.SOUTH,p2);
        addFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        addFrame.setVisible(true);
    }

    class selectModelListener implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if(itemEvent.getStateChange()==ItemEvent.SELECTED){
                int index=selectTaskModel.getSelectedIndex();
                p1.removeAll();
                selectTaskModel.setSelectedIndex(index);
                p1.add(Box.createVerticalStrut(5));
                p1.add(selectTaskModel);
                p1.add(new JLabel("Name: "));
                p1.add(nameText);
                p1.add(new JLabel("Description: "));
                p1.add(descriptionText);
                p1.add(new JLabel("State: "));
                p1.add(stateBox);
                if (selectTaskModel.getSelectedIndex()==2){
                    startdateText=new JTextField();
                    timesText=new JTextField();
                    cycleText=new JTextField();
                    p1.add(new JLabel("StartDate: "));
                    p1.add(startdateText);
                    p1.add(new JLabel("Times: "));
                    p1.add(timesText);
                    p1.add(new JLabel("Cycle: "));
                    p1.add(cycleText);

                }
                else if (selectTaskModel.getSelectedIndex()==1){
                    ddlText=new JTextField();
                    list=new JList();
                    list.getSelectionModel().addListSelectionListener(new LittleTaskListSelectionHandle());
                    JScrollPane scrollPane=new JScrollPane();
                    scrollPane.setViewportView(list);

                    p1.add(new JLabel("DeadLine: "));
                    p1.add(ddlText);
                    p1.add(new JLabel("SonTask: "));
                    p1.add(scrollPane);

                    JPanel bp=new JPanel();
                    JButton add=new JButton("Add");
                    JButton del=new JButton("Delete");
                    add.addActionListener(new AddListener());
                    del.addActionListener(new DelListener());
                    bp.add(add);
                    bp.add(del);
                    p1.add(bp);
                }
                else if (selectTaskModel.getSelectedIndex()==3){
                    ddlText=new JTextField();
                    p1.add(new JLabel("DeadLine: "));
                    p1.add(ddlText);
                }
                p1.revalidate();
            }
        }
    }

    class OkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Boolean flag=true;
            if (ddlText!=null){
                flag=Tools.checkInputDDL(ddlText.getText());
            }
            if (!flag){
                JOptionPane.showMessageDialog(addFrame,"DDL Error","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                ATask a=null;
                Boolean state;
                if (stateBox.getSelectedIndex()==0)
                    state=true;
                else
                    state=false;
                if (selectTaskModel.getSelectedIndex()==1){
                    a=new LongTask(nameText.getText(),descriptionText.getText(),state,"L",ddlText.getText(),littleTasks);
                }
                if (selectTaskModel.getSelectedIndex()==2)
                    a=new PeriodicTask(nameText.getText(),descriptionText.getText(),state,"P",startdateText.getText(),
                            Integer.parseInt(timesText.getText()),Integer.parseInt(cycleText.getText()));
                if (selectTaskModel.getSelectedIndex()==3)
                    a=new TemporaryTask(nameText.getText(),descriptionText.getText(),state,"T",ddlText.getText());
                MainFrame.tasks.add(a);
                MainFrame.nameList= Tools.taskToString(MainFrame.tasks);
                addFrame.setVisible(false);
                MainFrame.frame.repaint();
            }
        }
    }

    class LittleTaskListSelectionHandle implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            ListSelectionModel lsm=(ListSelectionModel)listSelectionEvent.getSource();
            selectIndex=lsm.getLeadSelectionIndex();
        }
    }

    class AddListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (littleTasks==null)
                littleTasks=new ArrayList<ATask>();
            new AddLittleTaskFrame(littleTasks,addFrame).go();
            list.setListData(Tools.taskToString(littleTasks));
        }
    }

    class DelListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            littleTasks.remove(selectIndex);
            list.setListData(Tools.taskToString(littleTasks));
        }
    }

}
