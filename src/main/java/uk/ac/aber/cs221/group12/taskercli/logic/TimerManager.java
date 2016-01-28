/*
 * @(#) TimeManager.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.logic;

import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import uk.ac.aber.cs221.group12.taskercli.frontend.MainFrame;

/**
 * The TimerManager class is responsible for syncing every 5 min as required in the
 * assignment specification of the system.
 *
 * @author Michal Goly 
 * @author Joshua Mir 
 * @author Tom Mills 
 * @author Adam Neaves
 * @version 1.0 Initial Release
 * 
 * @see MainFrame
 * @see Syncer
 * @see Timer
 */
public class TimerManager {

    public static final int MINUTES_TO_WAIT = 5;
   /**
    * {@value #MINUTES_TO_WAIT} min delay expressed in milliseconds.
    */
   public static final long DELAY = TimeUnit.MINUTES.toMillis(MINUTES_TO_WAIT);
   
   /**
    * Constructor
    * 
    * Creates the {@link Timer} and schedules the {@link SyncTask} to occur
    * every few minutes, dependant on the value of the constant 
    * {@link #DELAY DELAY}.
    */
   public TimerManager() {
      Timer timer = new Timer();
      TimerTask syncTask = new SyncTask();

      timer.schedule(syncTask, DELAY, DELAY);
   }

   /**
    * Private inner class activates the {@link Syncer#doUpdate(TeamMember)}
    * method whenever the timer counts the amount of time required to pass.
    */
   private class SyncTask extends TimerTask {

      @Override
      public void run() {
         
         Syncer.doUpdate(MainFrame.getMainFrame().getTeamMember());
      }
   }

}
