/*
 * @(#) MainFrameTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs211.group12.taskercli.frontend;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.frontend.MainFrame;

/**
 *
 * @author Tom Mills
 */
public class MainFrameTest {
    
    private TeamMember teamMember;
    
       @Before
        public void createTeamMember() {
        List<TaskElement> te = new ArrayList<>();
        te.add(new TaskElement(20L, "Go to train station", ""));
        te.add(new TaskElement(21L, "Get inside the train", ""));
        List<Task> tasks = new ArrayList<>();
        Task task = new Task(7L, "Go back for Christmas", new Date(2011, 2, 7), new Date(2010, 2, 1),
                TaskStatus.ALLOCATED, te);
        tasks.add(task);
        teamMember = new TeamMember("Mark", "Smith", "mark@smith.com", "fish", tasks);
    }
       @Test
       public void assertIsFrameVisible(){
           MainFrame frame = new MainFrame(teamMember);
           Assert.assertEquals("Frame Not Visible", true, frame.isShowing());
       }
}
