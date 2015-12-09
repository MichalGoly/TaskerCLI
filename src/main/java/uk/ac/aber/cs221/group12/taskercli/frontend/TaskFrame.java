/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.util.GBC;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Vector;

/**
 *
 * @author Michal Goly
 */
public class TaskFrame extends JDialog {

    private JTable table;
    private Task task;



    public TaskFrame() {
        this.task = new Task();
        initComponents();
        initFrame();
    }

    public void setTask(Task newtask){
        this.task = newtask;
        this.revalidate();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());

        JScrollPane scrollPane = new JScrollPane(new JTable(createModel(task)));
        add(scrollPane, new GBC(1, 5, 6, 7).setFill(GBC.BOTH).setWeight(100, 100));

    }


    private void initFrame() {
        pack();
        setLocationRelativeTo(null);
        setTitle("Tasker - " + task.getTitle());
        setVisible(true);
    }

    private TableModel createModel(Task task) {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("#");
        columnNames.add("Sub Task");
        columnNames.add("Comment");

        Vector<Vector<Object>> data = new Vector<>();
        for (TaskElement t : task.getTaskElementList()) {
            Vector<Object> row = new Vector<>();
            row.add(t.getTaskElementId());
            row.add(t.getDescription());
            row.add(t.getComments());
            data.add(row);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
