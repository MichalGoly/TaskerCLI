/*
 * @(#) TaskElementDB.java 1.0 26/01/16
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
import java.util.List;
import java.util.Properties;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;

/**
 * Provides methods to get {@link TaskElement} data from either the local or remote 
 * databases. Uses prepared statements to avoid issue with XSS type attacks and
 * bugs.
 * All methods are static, so this class does not need to be initialised, nor
 * does it require a constructor to be used.
 * 
 * @author Michal Goly
 * @version 1.0
 * @see TaskElement
 * @see ConnectionManager
 */
public class TaskElementDB {
    
    //prepared SQL statements to avoid issues with XSS type attacks and errors
   private static final String SELECT_TASK_ELEMENTS_FROM_TASK
           = "SELECT * FROM TaskElement "
           + "WHERE Task_taskId = ?";

   private static final String SELECT_TASK_ELEMENT_FROM_ID
           = "SELECT * FROM TaskElement "
           + "WHERE taskElementId = ?";

   private static final String UPDATE_TASK_ELEMENT
           = "UPDATE TaskElement "
           + "SET description = ?, comments = ? "
           + "WHERE taskElementId = ?";

   private static final String INSERT_TASK_ELEMENT
           = "INSERT INTO TaskElement (taskElementId, description, comments, Task_taskId) "
           + "VALUES (?, ?, ?, ?)";

   /**
    * Selects A {@link List} of task elements attributed to the {@link Task}
    * with a matching ID to the one sent as a parameter, using the 
    * {@link #SELECT_TASK_ELEMENTS_FROM_TASK SELECT_TASK_ELEMENTS_FROM_TASK} 
    * prepared statement.
    * 
    * @param taskId the ID of the task whose elements we want.
    * @param database the numeric value of the database to select the tasks from,
    * based on values set in {@link ConnectionManager}.
    * @return a {@link List} of {@link TaskElement}s from the database.
    * @throws SQLException Thrown if there is an issue connecting to the database.
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found.
    */
   public static List<TaskElement> selectTaskElementsByTaskId(Long taskId, int database)
   throws SQLException, IOException {
      List<TaskElement> taskElementList = new ArrayList<>();
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = 
                 conn.prepareStatement(SELECT_TASK_ELEMENTS_FROM_TASK)) {
            statement.setLong(1, taskId);
            try (ResultSet results = statement.executeQuery()) {
               while (results.next()) {
                  Long taskElementId = results.getLong("taskElementId");
                  String description = results.getString("description");
                  String comments = results.getString("comments");
                  taskElementList.add(
                          new TaskElement(taskElementId, description, comments));
               }
            }
         }
      }
      return taskElementList;
   }
   
   /**
    *  Selects an individual task element from the selected database via it's
    * unique ID number. Uses the 
    * {@link #SELECT_TASK_ELEMENT_FROM_ID SELECT_TASK_ELEMENT_FROM_ID} prepared
    * statement.
    * 
    * @param taskElementId the Unique ID value of the {@link TaskElement} that
    * we want to select from the database.
    * @param database the numeric value of the database to select the tasks from,
    * based on values set in {@link ConnectionManager}.
    * @return The TaskElement with the matching ID from the database.
    * @throws SQLException Thrown if there is an issue connecting to the database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found 
    */
   public static TaskElement selectTaskElementById(Long taskElementId, int database)
   throws SQLException, IOException {
      TaskElement result = null;
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = 
                 conn.prepareStatement(SELECT_TASK_ELEMENT_FROM_ID)) {
            statement.setLong(1, taskElementId);
            try (ResultSet results = statement.executeQuery()) {
               if (results.next()) {
                  Long id = results.getLong("taskElementId");
                  String description = results.getString("description");
                  String comments = results.getString("comments");
                  result = new TaskElement(id, description, comments);
               }
            }
         }
      }

      return result;
   }

   /**
    * Updates a selection of taskElements that match the ID values of those in
    * the list with new descriptions and comments. Uses the 
    * {@link #UPDATE_TASK_ELEMENT UPDATE_TASK_ELEMENT} prepared statement.
    * 
    * @param taskElementList The list of taskElements to update in the database.
    * @param database The numeric value of the database to select the tasks from,
    * based on values set in {@link ConnectionManager}.
    * @throws SQLException Thrown if there is an issue connecting to the database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found
    */
   public static void updateTaskElements(List<TaskElement> taskElementList, int database)
   throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = 
                 conn.prepareStatement(UPDATE_TASK_ELEMENT)) {
            for (TaskElement element : taskElementList) {
               statement.setString(1, element.getDescription());
               statement.setString(2, element.getComments());
               statement.setLong(3, element.getTaskElementId());
               statement.executeUpdate();
            }
         }
      }
   }

   /**
    * Assigns new TaskElements to the task selected by ID, and inserts them into
    * the selected database, using the 
    * {@link #INSERT_TASK_ELEMENT INSERT_TASK_ELEMENT} prepared statement.
    * 
    * @param taskElementList A list of tasks to add to the database
    * @param database the numeric value of the database to select the tasks from,
    * based on values set in {@link ConnectionManager}.
    * @param taskId
    * @throws SQLException Thrown if there is an issue connecting to the database
    * @throws IOException throws if the properties file loaded by 
    * {@link ConnectionManager#getConnection getConnection()} is not found
    */
   public static void insertTaskElements(List<TaskElement> taskElementList,
           int database, long taskId) 
   throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = 
                 conn.prepareStatement(INSERT_TASK_ELEMENT)) {
            for (TaskElement element : taskElementList) {
               statement.setLong(1, element.getTaskElementId());
               statement.setString(2, element.getDescription());
               statement.setString(3, element.getComments());
               statement.setLong(4, taskId);
               statement.executeUpdate();
            }
         }
      }
   }

}
