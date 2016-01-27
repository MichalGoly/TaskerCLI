/*
 * @(#) TaskStatusTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.business;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author adn2
 */
public class TaskStatusTest {

    @Test
    public void assertEnumCorrect() {
        Assert.assertEquals("Task allocated correct value", TaskStatus.fromInt(1), TaskStatus.ALLOCATED);

        Assert.assertEquals("Task abandoned correct value", TaskStatus.fromInt(2), TaskStatus.ABANDONED);

        Assert.assertEquals("Task completed correct value", TaskStatus.fromInt(3), TaskStatus.COMPLETED);

    }
}
