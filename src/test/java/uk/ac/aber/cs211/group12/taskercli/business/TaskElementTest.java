/*
 * @(#) TaskElementTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs211.group12.taskercli.business;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;

/**
 *
 * @author adn2
 */
public class TaskElementTest {

    TaskElement taskElement;

    @Before
    public void createTaskElement() {
        taskElement = new TaskElement(20L, "Go to train station", "");
    }

    @Test
    public void verifyTaskElement() {
        Assert.assertEquals("TaskElement must have correct description", taskElement.getDescription(), "Go to train station");
        Assert.assertEquals("TaskElement must have correct comment", taskElement.getComments(), "");
        Assert.assertEquals("TaskElement id must be correct", taskElement.getTaskElementId(), new Long(20));
    }
    
}
