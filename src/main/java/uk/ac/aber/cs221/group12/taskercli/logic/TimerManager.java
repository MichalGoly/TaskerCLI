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
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.frontend.MainFrame;
import uk.ac.aber.cs221.group12.taskercli.frontend.SidebarPanel;

/**
 * The TimerManager class is responsible for syncing every 5 min as required in the
 * assignment specification of the system.
 *
 * @author Michal Goly
 */
public class TimerManager {

   /**
    * 5 min delay expressed in milliseconds
    */
   public static final long DELAY = TimeUnit.MINUTES.toMillis(1);

   private TeamMember teamMember;
   private MainFrame.TaskTableModel taskTableModel;
   private SidebarPanel sidebarPanel;
   
   public TimerManager(TeamMember teamMember, MainFrame.TaskTableModel taskTableModel,
           SidebarPanel sidebarPanel) {
      this.teamMember = teamMember;
      this.taskTableModel = taskTableModel;
      this.sidebarPanel = sidebarPanel;
      
      Timer timer = new Timer();
      TimerTask syncTask = new SyncTask();

      timer.schedule(syncTask, DELAY, DELAY);
   }

   private class SyncTask extends TimerTask {

      @Override
      public void run() {
         Syncer.doUpdate(teamMember);
      }
   }

}
