/*
 * @(#) TaskTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.business;

import junit.framework.Assert;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tom Mills
 */
public class TaskTest {

    @Test
    public void testConstructor() {

        Long id = new Long(1000);
        String title = "Wash Dishes";
        Date startDate = new Date(116 / 10 / 21);
        Date endDate = new Date(116 / 11 / 21);
        TaskStatus status = TaskStatus.ALLOCATED;
        List<TaskElement> elements = new ArrayList<TaskElement>();
        long taskElementId = 1;
        String description = "Fill up Sink";
        String comments = "Will need soap";
        TaskElement element = new TaskElement(taskElementId, description, comments);
        elements.add(element);

        Task task = new Task(id, title, startDate, endDate, status, elements);

        Assert.assertEquals("Id Incorrect", id, task.getTaskId());
        Assert.assertEquals("Title Incorrect", title, task.getTitle());
        Assert.assertEquals("Start Date Incorrect", startDate, task.getStartDate());
        Assert.assertEquals("End Date Incorrect", endDate, task.getEndDate());
        Assert.assertEquals("Status Incorrect", status, task.getStatus());
        Assert.assertEquals("Element Task List", elements, task.getTaskElementList());

    }


}