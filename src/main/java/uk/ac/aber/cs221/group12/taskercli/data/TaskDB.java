/*
 * @(#) TaskDB.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Properties;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;

/**
 * Provides methods to get {@link Task} data from either the local or remote 
 * databases.
 * All methods are static, so this class does not need to be initialised, nor
 * does it require a constructor to be used.
 * 
 * @author Michal Goly
 * @version 1.0
 * @see Task
 * @see TaskElement
 * @see TaskStatus
 * @see ConnectionManager
 */
public class TaskDB {
    
    //SQL query constant strings, or 'prepared statements'
   private static final String SELECT_TASKS_BY_EMAIL
           = "SELECT * FROM Task WHERE TeamMember_email = ?";

   private static final String SELECT_TASK_BY_ID
           = "SELECT * FROM Task WHERE taskId = ?";

   private static final String UPDATE_TASK
           = "UPDATE Task "
           + "SET title = ?, startDate = ?, endDate = ?, taskStatus = ? "
           + "WHERE taskId = ?";

   private static final String INSERT_TASK
           = "INSERT INTO Task (taskId, title, startDate, endDate, taskStatus, TeamMember_email) "
           + "VALUES (?, ?, ?, ?, ?, ?)";

   /**
    * Selects tasks from the databases by the ID of that {@link Task}, using the 
    * {@link #SELECT_TASK_BY_ID SELECT_TASK_BY_ID} prepared statement.
    * 
    * @param taskId the unique ID number of the task to be selected
    * @param database the numeric value of the database to select the task from,
    * based on values set in {@link ConnectionManager}
    * @return the {@link Task} from the database.
    * @throws SQLException Thrown if there is an issue connecting to the database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found
    */
   public static Task selectTaskById(long taskId, int database) 
   throws SQLException, IOException {
      Task task = null;
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = conn.prepareStatement(SELECT_TASK_BY_ID)) {
            statement.setLong(1, taskId);
            try (ResultSet results = statement.executeQuery()) {
               while (results.next()) {
                  String title = results.getString("title");
                  Date startDate = results.getDate("startDate");
                  Date endDate = results.getDate("endDate");
                  TaskStatus status = TaskStatus.fromInt(results.getInt("taskStatus"));
                  List<TaskElement> taskElementList
                          = TaskElementDB.selectTaskElementsByTaskId(taskId, database);
                  task = new Task(taskId, title, startDate, endDate, status,
                          taskElementList);
               }
            }
         }
      }

      return task;
   }

   /**
    * Selects an {@link ArrayList} of {@link Task}s from the databases assigned to a 
    * {@link uk.ac.aber.cs221.group12.taskercli.business.TeamMember} via the team
    * members email, using the {@link #SELECT_TASKS_BY_EMAIL SELECT_TASKS_BY_EMAIL}
    * prepared statement.
    * 
    * @param email the email of the team member whose tasks are to be selected.
    * @param database the numeric value of the database to select the tasks from,
    * based on values set in {@link ConnectionManager}
    * @return an {@link ArayList} of {@link Task}s assigned to the selected Team
    * Member 
    * @throws SQLException throws if an issue occurs when connecting to the 
    * database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found
    */
   public static List<Task> selectTasks(String email, int database) 
   throws SQLException, IOException {
      List<Task> taskList = new ArrayList<>();
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = conn.prepareStatement(SELECT_TASKS_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet results = statement.executeQuery()) {
               while (results.next()) {
                  Long taskId = results.getLong("taskId");
                  String title = results.getString("title");
                  Date startDate = results.getDate("startDate");
                  Date endDate = results.getDate("endDate");
                  TaskStatus status = TaskStatus.fromInt(results.getInt("taskStatus"));
                  List<TaskElement> taskElementList
                          = TaskElementDB.selectTaskElementsByTaskId(taskId, database);
                  taskList.add(new Task(taskId, title, startDate, endDate, status,
                          taskElementList));
               }
            }
         }
      }
      return taskList;
   }

   /**
    * Updates the {@link Task}s in the databases with a list of updated tasks, via
    * the {@link #UPDATE_TASK UPDATE_TASK} prepared statement.
    * 
    * @param taskList the list of tasks to update in the databases
    * @param database the numeric value of the database that will be updated with
    * these tasks, based on values set in {@link ConnectionManager}
    * @throws SQLException throws if an issue occurs when connecting to the 
    * database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found 
    */
   public static void updateTasks(List<Task> taskList, int database) 
   throws SQLException, IOException {
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = conn.prepareStatement(UPDATE_TASK)) {
            for (Task t : taskList) {
               statement.setString(1, t.getTitle());
               statement.setDate(2, t.getStartDate());
               statement.setDate(3, t.getEndDate());
               statement.setInt(4, t.getStatus().toInt());
               statement.setLong(5, t.getTaskId());
               statement.executeUpdate();
               TaskElementDB.updateTaskElements(t.getTaskElementList(), database);
            }
         }
      }
   }
   
   /**
    * Inserts new tasks into the databases, and assigning them to a 
    * {@link uk.ac.aber.cs221.group12.taskercli.business.TeamMember}, via the 
    * {@link #INSERT_TASK INSERT_TASK} prepared statement.
    * 
    * @param taskList the list of tasks to insert into the databases
    * @param database the numeric value of the database that will be updated with
    * these tasks, based on values set in {@link ConnectionManager}
    * @param email the email address of the 
    * {@link uk.ac.aber.cs221.group12.taskercli.business.TeamMember} that 
    * the new tasks will be assigned to.
    * @throws SQLException throws if an issue occurs when connecting to the 
    * database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found
    */
   public static void insertTasks(List<Task> taskList, int database, String email)
   throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);
      System.out.println("BEFO");
      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = conn.prepareStatement(INSERT_TASK)) {
            for (Task t : taskList) {
               System.out.println("IN");

               statement.setLong(1, t.getTaskId());
               statement.setString(2, t.getTitle());
               statement.setDate(3, t.getStartDate());
               statement.setDate(4, t.getEndDate());
               statement.setInt(5, t.getStatus().toInt());
               statement.setString(6, email);
               statement.executeUpdate();

               TaskElementDB.insertTaskElements(t.getTaskElementList(), database,
                       t.getTaskId());
            }
         }
      }
   }

}
