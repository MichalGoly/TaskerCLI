package uk.ac.aber.cs221.group12.taskercli.logic;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

/**
 * The TimerManager class is responsible for syncing every 5 min as required
 * in the assignment specification of the system.
 *
 * @author Michal Goly
 */
public class TimerManager {
   
   /**
    * 5 min delay expressed in milliseconds
    */
   public static final long DELAY = TimeUnit.MINUTES.toMillis(5);
   
   private TeamMember teamMember;
   private Timer timer;
   private TimerTask syncTask;
   
   public TimerManager(TeamMember teamMember) {
      this.teamMember = teamMember;
      timer = new Timer();
      syncTask = new SyncTask();
      
      timer.schedule(syncTask, DELAY, DELAY);
   }
   
   private class SyncTask extends TimerTask {
      
      @Override
      public void run() {
         Syncer.doUpdate(teamMember);
      }
   }
   
}
