package com.gui;

import com.tools.Tools;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CopyFrame {
    JDialog copyFrame;
    JList list;
    JPanel buttonPanel;
    int selectedIndex;
    public void go(){
        copyFrame=new JDialog();
        copyFrame.setBounds(
                new Rectangle(
                        (int) MainFrame.frame.getBounds().getX()+50,
                        (int) MainFrame.frame.getBounds().getY()+50,
                        300,500
                )
        );
        list=new JList();
        list.setListData(Tools.taskslistsToString(MainFrame.taskLists));
        list.getSelectionModel().addListSelectionListener(new ListSelectionHandle());

        buttonPanel=new JPanel();
        JButton ok=new JButton("OK");
        JButton cancel=new JButton("Cancel");
        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        ok.addActionListener(new OkListener());
        cancel.addActionListener(e->copyFrame.setVisible(false));

        copyFrame.getContentPane().add(BorderLayout.CENTER,list);
        copyFrame.getContentPane().add(BorderLayout.SOUTH,buttonPanel);
        copyFrame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        copyFrame.setVisible(true);
    }

    class ListSelectionHandle implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            ListSelectionModel lsm=(ListSelectionModel)listSelectionEvent.getSource();
            selectedIndex=lsm.getLeadSelectionIndex();
        }
    }

    class OkListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MainFrame.taskLists.get(selectedIndex).add(MainFrame.tasks.get(MainFrame.selectedIndex));
            copyFrame.setVisible(false);
//            MainFrame.taskLists.get(MainFrame.tasksIndex).remove(MainFrame.selectedIndex);
        }
    }
}
