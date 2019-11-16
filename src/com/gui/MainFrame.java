package com.gui;

import com.task.*;
import com.tools.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class MainFrame {
    public static JFrame frame;
    public static TaskList<ATask> tasks;
    public static ArrayList<TaskList<ATask>> taskLists;
    public static String[] nameList;
    public static JList list;
    public static JList list1;
    JPopupMenu popupMenu;
    public static JTextArea infoArea;
    JSplitPane splitPane;
    public static int selectedIndex=-1;
    public static int selectedIndex2=-1;
    public static int tasksIndex=0;
    JPanel tP1;
    JTextField searchField;
    JLabel taskListNameLabel;
    public void go(){
        taskLists=new ArrayList<TaskList<ATask>>();

        frame=new JFrame("Private Manager");
        JMenuBar menuBar=new JMenuBar();
        JMenu tasklistMenu=new JMenu("TaskList");
        JMenu sortMenu=new JMenu("Sort");

        JMenuItem newMenuItem=new JMenuItem("New");
        JMenuItem openMenuItem=new JMenuItem("Open");
        JMenuItem openAllMenuItem=new JMenuItem("Open All");
        JMenuItem saveMenuItem=new JMenuItem("Save");
        JMenuItem saveAllMenuItem=new JMenuItem("Save All");
        JMenuItem managerMenuItem=new JMenuItem("Manager");
        JMenuItem exitMenuItem=new JMenuItem("Exit");
        JMenuItem sortByNameItem=new JMenuItem("By name");
        JMenuItem sortByDescriptionItem=new JMenuItem("By description");
        JMenuItem sortByDDLItem=new JMenuItem("By ddl");
        JMenuItem sortByStateItem=new JMenuItem("By state");

        newMenuItem.addActionListener(new NewMenuListener());
        openMenuItem.addActionListener(new ReadFileMenuListener());
        openAllMenuItem.addActionListener(new ReadAllMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        saveAllMenuItem.addActionListener(new SaveAllMenuListener());
        managerMenuItem.addActionListener(new ManagerMenuListener());
        exitMenuItem.addActionListener(e->System.exit(0));
        sortByNameItem.addActionListener(e ->{
            TasksSorter.sortByName(tasks);
            list.setListData(Tools.taskToString(tasks));
        } );
        sortByDescriptionItem.addActionListener(e->{
            TasksSorter.sortByDescription(tasks);
            list.setListData(Tools.taskToString(tasks));
        });
        sortByDDLItem.addActionListener(e->{
            TasksSorter.sortByDDL(tasks);
            list.setListData(Tools.taskToString(tasks));
        });
        sortByStateItem.addActionListener(e->{
            TasksSorter.sortByState(tasks);
            list.setListData(Tools.taskToString(tasks));
        });
        tasklistMenu.add(newMenuItem);
        tasklistMenu.add(openMenuItem);
        tasklistMenu.add(openAllMenuItem);
        tasklistMenu.add(saveMenuItem);
        tasklistMenu.add(saveAllMenuItem);
        tasklistMenu.add(managerMenuItem);
        tasklistMenu.add(exitMenuItem);
        sortMenu.add(sortByNameItem);
        sortMenu.add(sortByDescriptionItem);
        sortMenu.add(sortByDDLItem);
        sortMenu.add(sortByStateItem);

        menuBar.add(tasklistMenu);
        menuBar.add(sortMenu);
        frame.setJMenuBar(menuBar);

        splitPane=new JSplitPane();
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerSize(5);
        splitPane.setDividerLocation(200);

        tP1=new JPanel();
        tP1.setBorder(new EmptyBorder(5,5,5,5));
        tP1.setLayout(new BorderLayout(0,0));
        JScrollPane scrollPane1=new JScrollPane();
        tP1.add(scrollPane1,BorderLayout.CENTER);
        list=new JList();
        scrollPane1.setViewportView(list);
        list.getSelectionModel().addListSelectionListener(new TaskListSelectionHandle());
        list.addMouseListener(new ListMouseListener());
        setNameList();
        list.setListData(nameList);

        JPanel tP2=new JPanel();
        JScrollPane scrollPane2=new JScrollPane();
        tP2.setBorder(new EmptyBorder(5,5,5,5));
        tP2.setLayout(new BorderLayout(0,0));
        tP2.add(scrollPane2,BorderLayout.CENTER);
        list1=new JList();
        scrollPane2.setViewportView(list1);
        list1.getSelectionModel().addListSelectionListener(new LittleTaskListSelectionHandle());

        splitPane.setLeftComponent(tP1);
        splitPane.setRightComponent(tP2);
        frame.getContentPane().add(BorderLayout.CENTER,splitPane);

        JPanel infoPanel=new JPanel();
        JScrollPane scrollPane3=new JScrollPane();
        infoArea=new JTextArea(10,20);
        infoPanel.setBorder(new EmptyBorder(5,5,5,5));
        infoPanel.setLayout(new BorderLayout(0,0));
        infoPanel.add(scrollPane3,BorderLayout.CENTER);
        scrollPane3.setViewportView(infoArea);
        frame.getContentPane().add(BorderLayout.EAST,infoPanel);

        setSearchPanel();
        setButtonPanel();

        frame.setBounds(300,400,700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void setNameList(){
        tasks=new TaskList<ATask>("1");
        ATask t1=new TemporaryTask("WXL","3Love",true,"T","9999.12.30");
        ATask t2=new PeriodicTask("Kiss","2Kiss my love",false,"P","2019.5.7",10000,100000);
        ATask t3=new TemporaryTask("l","1l",true,"T","2019.11.12");
        ArrayList<ATask> littleTasks=new ArrayList<>();
        littleTasks.add(new LittleTask("1","1",true,"Li","1"));
        littleTasks.add(new LittleTask("2","3",false,"2","4"));
        ATask t4=new LongTask("test","test",true,"L","2019.6.6",littleTasks);
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);

        taskLists.add(tasks);
        nameList=Tools.taskToString(tasks);

    }

    public void setButtonPanel(){
        JPanel buttonPanel=new JPanel();
        JButton add=new JButton("Add");
        JButton del=new JButton("Delete");
        JButton change=new JButton("Change");
        add.addActionListener(new AddListener());
        del.addActionListener(new DeleteListener());
        change.addActionListener(new ChangeListener());

        buttonPanel.add(add);
        buttonPanel.add(change);
        buttonPanel.add(del);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
    }

    public void setSearchPanel(){
        searchField=new JTextField(15);
        JPanel searchPanel=new JPanel();
        taskListNameLabel=new JLabel(tasks.getName());
        JButton searchButton=new JButton("Search");
        searchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.add(taskListNameLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(new SearchListener());

        frame.getContentPane().add(BorderLayout.NORTH,searchPanel);
    }

    public void showListPopupMenu(Component invoker,int x,int y){
        popupMenu=new JPopupMenu();
        JMenuItem copyMenu=new JMenuItem("Copy To");
        JMenuItem moveMenu=new JMenuItem("Move To");
        popupMenu.add(copyMenu);
        popupMenu.add(moveMenu);
        copyMenu.addActionListener(e->{
            new CopyFrame().go();
            list.setListData(Tools.taskToString(tasks));
        });
        moveMenu.addActionListener(e->{
            new MoveFrame().go();
            list.setListData(Tools.taskToString(tasks));
        });
        popupMenu.show(invoker,x,y);

    }

    class TaskListSelectionHandle implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {

            ListSelectionModel lsm=(ListSelectionModel)listSelectionEvent.getSource();
            selectedIndex=lsm.getLeadSelectionIndex();

            if(selectedIndex>=0)
                new TasksOutputer().go();

            if (tasks.get(selectedIndex).getKey().equals("L")){
                list1.setListData(Tools.taskToString(tasks.get(selectedIndex).getTasks()));
            }
            else{

                selectedIndex2=-1;
                list1.setListData(new String[0]);

            }
            frame.repaint();

        }
    }

    class LittleTaskListSelectionHandle implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            ListSelectionModel lsm=(ListSelectionModel)listSelectionEvent.getSource();
            selectedIndex2=lsm.getLeadSelectionIndex();

            if (selectedIndex2>=0){
                infoArea.setText("Name: "+tasks.get(selectedIndex).getName()+"\n");
                infoArea.append("Description: "+tasks.get(selectedIndex).getDescription()+"\n");
                infoArea.append("State: "+Tools.getState(tasks.get(selectedIndex))+"\n");
                infoArea.append("DeadLine: "+tasks.get(selectedIndex).getDDL()+"\n");
                infoArea.append("\n");
                infoArea.append("\n");
                infoArea.append("SonTask: "+"\n");
                ATask littleTask=tasks.get(selectedIndex).getTasks().get(selectedIndex2);
                infoArea.append("Name: "+littleTask.getName()+"\n");
                infoArea.append("Description: "+littleTask.getDescription()+"\n");
                infoArea.append("State: "+Tools.getState(littleTask)+"\n");
                infoArea.append("DeadLine: "+littleTask.getDDL());
            }

            frame.repaint();
            list1.clearSelection();
        }
    }

    class DeleteListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(selectedIndex>=0){
                tasks.remove(selectedIndex);
                infoArea.setText(" ");
                list1.setListData(new String[0]);
                selectedIndex=-1;
                nameList=Tools.taskToString(tasks);
                list.setListData(nameList);

            }
        }
    }

    class ChangeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (selectedIndex>=0){
                new ChangeFrame().go();
                new TasksOutputer().go();
                list.setListData(nameList);
            }
        }
    }

    class AddListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new AddFrame().go();
            list.setListData(nameList);
        }
    }

    class SearchListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Boolean isFind=false;
            for (TaskList<ATask> a:taskLists){
                for(ATask i:a){
                    if (searchField.getText().equals(i.getName())){
                        if (isFind){
                            JOptionPane.showMessageDialog(frame,"存在同名任务，已查找到第一个");
                            break;
                        }
                        tasks=a;
                        list.setListData(Tools.taskToString(tasks));
                        list.setSelectedIndex(tasks.indexOf(i));
                        taskListNameLabel.setText(tasks.getName());
                        isFind=true;
                    }
                }
            }
            if (!isFind)
                JOptionPane.showMessageDialog(frame,"Not Find!");
        }
    }

    class NewMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new NewTaskListFrame().go();

        }
    }

    class SaveMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileSave=new JFileChooser();
            fileSave.showSaveDialog(frame);

            try {
                FileOutputStream fileStream=new FileOutputStream(fileSave.getSelectedFile());
                ObjectOutputStream os=new ObjectOutputStream(fileStream);
                os.writeObject(tasks);
                os.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class SaveAllMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileSave=new JFileChooser();
            fileSave.showSaveDialog(frame);

            try {
                FileOutputStream fileStream=new FileOutputStream(fileSave.getSelectedFile());
                ObjectOutputStream os=new ObjectOutputStream(fileStream);
                os.writeObject(taskLists);
                os.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class ReadFileMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileOpen=new JFileChooser();
            fileOpen.showOpenDialog(frame);

            try {
                FileInputStream fileStream=new FileInputStream(fileOpen.getSelectedFile());
                ObjectInputStream os=new ObjectInputStream(fileStream);
                taskLists.add((TaskList<ATask>)os.readObject());
                tasks=taskLists.get(taskLists.size()-1);
                nameList=Tools.taskToString(tasks);
                list.setListData(nameList);
                taskListNameLabel.setText(tasks.getName());
                os.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class ReadAllMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileOpen=new JFileChooser();
            fileOpen.showOpenDialog(frame);

            try {
                FileInputStream fileStream=new FileInputStream(fileOpen.getSelectedFile());
                ObjectInputStream os=new ObjectInputStream(fileStream);
                taskLists=(ArrayList<TaskList<ATask>>) os.readObject();
                os.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class ManagerMenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            new ManagerFrame().go();
            tasks=taskLists.get(tasksIndex);
            list.setListData(Tools.taskToString(tasks));
            taskListNameLabel.setText(tasks.getName());
        }
    }

    class ListMouseListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            selectedIndex=list.locationToIndex(e.getPoint());
            list.setSelectedIndex(selectedIndex);
            if (e.getButton()==3&&selectedIndex>=0){
                showListPopupMenu(list,e.getX(),e.getY());
            }
        }
    }
}
