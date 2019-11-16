package com.gui;

import com.task.ATask;
import com.tools.TaskList;
import com.tools.Tools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class ManagerFrame {
    JDialog managerFrame;
    JList list;
    JPanel buttonPanel;
    int selectedIndex=-1;
    public void go(){
        managerFrame=new JDialog();
        managerFrame.setTitle("TasksList Manager");
        managerFrame.setBounds(
                new Rectangle(
                        (int) MainFrame.frame.getBounds().getX()+50,
                        (int) MainFrame.frame.getBounds().getY()+50,
                        350,500
                )
        );
        list=new JList();
        list.setListData(Tools.taskslistsToString(MainFrame.taskLists));
        list.getSelectionModel().addListSelectionListener(new ListSelectionHandle());

        buttonPanel=new JPanel();
        JPanel p1=new JPanel();
        JButton sortByName=new JButton("SortByName");
        JButton sortBySize=new JButton("SortBySize");
        p1.add(sortByName);
        p1.add(sortBySize);
        JPanel p2=new JPanel();
        JButton ok =new JButton("OK");
        JButton change=new JButton("Change");
        JButton del=new JButton("Delete");
        JButton cancel =new JButton("Cancel");
        p2.add(ok);
        p2.add(change);
        p2.add(del);
        p2.add(cancel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
        buttonPanel.add(p1);
        buttonPanel.add(p2);

        sortByName.addActionListener(new SortByNameListener());
        sortBySize.addActionListener(new SortBySizeListener());
        ok.addActionListener(new OkListener());
        change.addActionListener(new ChangeListener());
        del.addActionListener(new DeleteListener());
        cancel.addActionListener(e->managerFrame.setVisible(false));

        managerFrame.getContentPane().add(BorderLayout.CENTER,list);
        managerFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        managerFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        managerFrame.setVisible(true);
    }

    class ListSelectionHandle implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            ListSelectionModel lsm=(ListSelectionModel)listSelectionEvent.getSource();
            selectedIndex=lsm.getLeadSelectionIndex();
        }
    }

    class OkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (selectedIndex>=0){
                MainFrame.tasksIndex=selectedIndex;
            }
            managerFrame.setVisible(false);
        }
    }

    class ChangeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (selectedIndex>=0){
                JDialog changeframe=new JDialog();
                changeframe.setTitle("Change Name");
                changeframe.setBounds(
                        new Rectangle(
                                (int) MainFrame.frame.getBounds().getX()+50,
                                (int) MainFrame.frame.getBounds().getY()+50,
                                300,150
                        )
                );
                JPanel namePanel=new JPanel();
                JLabel nameLabel=new JLabel("Name: ");
                JTextField nameText=new JTextField(10);
                nameText.setText(MainFrame.taskLists.get(selectedIndex).getName());
                namePanel.add(nameLabel);
                namePanel.add(nameText);

                JPanel bP=new JPanel();
                JButton ok=new JButton("OK");
                JButton cancel=new JButton("Cancel");
                bP.add(ok);
                bP.add(cancel);

                ok.addActionListener(e->{
                    MainFrame.taskLists.get(selectedIndex).setName(nameText.getText());
                    list.setListData(Tools.taskslistsToString(MainFrame.taskLists));
                    changeframe.setVisible(false);
                });
                cancel.addActionListener(e->changeframe.setVisible(false));

                changeframe.getContentPane().add(BorderLayout.CENTER,namePanel);
                changeframe.getContentPane().add(BorderLayout.SOUTH,bP);
                changeframe.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                changeframe.setVisible(true);
            }
            else
                JOptionPane.showMessageDialog(managerFrame,"Selected invalid item","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    class DeleteListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (MainFrame.taskLists.size()==1){
                JOptionPane.showMessageDialog(managerFrame,"Your can't delete all","Error",JOptionPane.ERROR_MESSAGE);
            }
            else {
                MainFrame.taskLists.remove(selectedIndex);
                list.setListData(Tools.taskslistsToString(MainFrame.taskLists));
                if (selectedIndex==0)
                    selectedIndex=0;
                else
                    selectedIndex=selectedIndex-1;
            }

        }
    }

    class SortByNameListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Comparator<TaskList<ATask>> comp=new Comparator<TaskList<ATask>>() {
                @Override
                public int compare(TaskList<ATask> aTasks, TaskList<ATask> t1) {
                    return aTasks.getName().compareTo(t1.getName());
                }
            };
            MainFrame.taskLists.sort(comp);
            list.setListData(Tools.taskslistsToString(MainFrame.taskLists));
        }
    }

    class SortBySizeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Comparator<TaskList<ATask>> comp=new Comparator<TaskList<ATask>>() {
                @Override
                public int compare(TaskList<ATask> aTasks, TaskList<ATask> t1) {
                    if (aTasks.size()>t1.size())
                        return -1;
                    else if (aTasks.size()<t1.size())
                        return 1;
                    else
                        return 0;
                }
            };
            MainFrame.taskLists.sort(comp);
            list.setListData(Tools.taskslistsToString(MainFrame.taskLists));
        }
    }


}
