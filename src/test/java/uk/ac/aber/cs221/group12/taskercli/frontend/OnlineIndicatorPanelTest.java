/*
 * @(#) OnlineIndicatorPanelTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.frontend;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

/**
 * @author adn2
 */
public class OnlineIndicatorPanelTest {

    OnlineIndicatorPanel onlineIndicatorPanel;

    @Before
    public void setUp() throws Exception {
        onlineIndicatorPanel = new OnlineIndicatorPanel();
        JFrame test = new JFrame();
        onlineIndicatorPanel.initialise();
        test.setContentPane(onlineIndicatorPanel);
        test.setVisible(true);
    }


    @Test
    public void testSetOnline() throws Exception {
        OnlineIndicatorPanel.setOnline();
        Assert.assertTrue("Indicator not visible", onlineIndicatorPanel.isVisible());
    }

    @Test
    public void testSetOffline() throws Exception {
        OnlineIndicatorPanel.setOffline();
        Assert.assertTrue("Indicator not visible", onlineIndicatorPanel.isVisible());
    }
}

