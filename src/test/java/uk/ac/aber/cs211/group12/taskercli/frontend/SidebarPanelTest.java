/*
 * @(#) SidebarPanelTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs211.group12.taskercli.frontend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.frontend.MainFrame;
import uk.ac.aber.cs221.group12.taskercli.frontend.SidebarPanel;

/**
 *
 * @author Tom Mills
 */

public class SidebarPanelTest {
    private TeamMember teamMember;
    private JFrame testFrame;
    private JTable table = new JTable();
    
    @Before
    public void createTeamMember() {
        List<TaskElement> taskElements = new ArrayList<>();
        taskElements.add(new TaskElement(20L, "Go to train station", ""));
        taskElements.add(new TaskElement(21L, "Get inside the train", ""));
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(7L, "Go back for Christmas",
                new Date(2011, 2, 7),
                new Date(2010, 2, 1),
                TaskStatus.ALLOCATED, taskElements);

        tasks.add(task);
        teamMember =
                new TeamMember("Mark", "Smith", "mark@smith.com", "fish", tasks);
    }
    
    @Test
    public void assertIsSidebarVisible(){
        testFrame = new JFrame();
        testFrame.setVisible(true);
        SidebarPanel sidebar = new SidebarPanel(table,teamMember);
        testFrame.add(sidebar);
        sidebar.setVisible(true);
        Assert.assertEquals("Sidebar not visible", true, sidebar.isShowing());
    }
    

    
    
    
}
