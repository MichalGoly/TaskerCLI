package uk.ac.aber.cs211.group12.taskercli.frontend;

import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.frontend.ProgressBar;

/**
 * Created by jam on 1/23/16.
 */
public class ProgressBarTest {
    @Test
    public void testProgressBar(){
        ProgressBar.showGui("Testing");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgressBar.hideGui();
    }

}
