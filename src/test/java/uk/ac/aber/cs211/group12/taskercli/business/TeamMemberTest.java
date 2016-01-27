/*
 * @(#) TaskMemberTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs211.group12.taskercli.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adn2
 */
public class TeamMemberTest {

    TeamMember teamMember;

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
    public void assertTeamMemberElementsCorrect() {
        Assert.assertEquals("teamMember email must be mark@smith.com", teamMember.getEmail(), "mark@smith.com");
        Assert.assertEquals("teamMember first name must be Mark", teamMember.getFirstName(), "Mark");
        Assert.assertEquals("teamMember last name must be Smith", teamMember.getLastName(), "Smith");
        Assert.assertEquals("teamMember password must be fish", teamMember.getPassword(), "fish");

        List<Task> tasks = teamMember.getTaskList();
        List<TaskElement> taskElements = tasks.get(0).getTaskElementList();
        Assert.assertEquals("teamMember first task description correct", tasks.get(0).getTitle(),
                "Go back for Christmas");
        Assert.assertEquals("first task has two taskElements", taskElements.size(), 2);
        Assert.assertEquals("first task element description is correct", taskElements.get(0).getDescription(),
                "Go to train station");

    }




}
