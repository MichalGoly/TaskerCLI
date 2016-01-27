/*
 * @(#) ProgressBarTest.java 1.0 23/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs211.group12.taskercli.frontend;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.frontend.ProgressBar;

/**
 * Created by jam on 1/23/16.
 */
public class ProgressBarTest {

    private void oneSec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testProgressBar(){
        ProgressBar.showGui("Testing");
        oneSec();
        ProgressBar.showGui("Changing");
        oneSec();
        ProgressBar.showGui("Changed");
        oneSec();
        ProgressBar.showGui("Hiding");
        oneSec();
        ProgressBar.hideGui();
        oneSec();
        ProgressBar.showGui("Finished");
        oneSec();
        Assert.assertTrue("Test Completed", true);

    }

}
