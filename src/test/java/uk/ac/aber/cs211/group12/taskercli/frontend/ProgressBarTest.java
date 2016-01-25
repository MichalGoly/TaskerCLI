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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgressBar.showGui("Changing");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgressBar.hideGui();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgressBar.showGui("Changed");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProgressBar.hideGui();
    }

}
