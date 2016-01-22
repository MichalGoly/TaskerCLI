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
 *
 * @author Michal Goly
 */
public class TaskElementDB {

   private static final String SELECT_TASK_ELEMENTS
           = "SELECT * FROM TaskElement "
           + "WHERE Task_taskId = ?";
   
   private static final String SELECT_TASK_ELEMENT
           = "SELECT * FROM TaskElement "
           + "WHERE taskElementId = ?";
   
   private static final String UPDATE_TASKELEMENT
           = "UPDATE TaskElement "
           + "SET description = ?, comments = ? "
           + "WHERE taskElementId = ?";

   private static final String INSERT_TASK_ELEMENT
           = "INSERT INTO TaskElement (taskElementId, description, comments, Task_taskId) "
           + "VALUES (?, ?, ?, ?)";
   
   public static List<TaskElement> selectTaskElementsByTaskId(Long taskId, int database)
           throws SQLException, IOException {
      List<TaskElement> taskElementList = new ArrayList<>();
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(SELECT_TASK_ELEMENTS)) {
            ps.setLong(1, taskId);
            try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                  Long taskElementId = rs.getLong("taskElementId");
                  String description = rs.getString("description");
                  String comments = rs.getString("comments");
                  taskElementList.add(
                          new TaskElement(taskElementId, description, comments));
               }
            }
         }
      }
      return taskElementList;
   }
   
   public static TaskElement selectTaskElementById(Long taskElementId, int database) 
           throws SQLException, IOException {
      TaskElement result = null;
      Properties props = ConnectionManager.getDatabaseProperties(database);
      
      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(SELECT_TASK_ELEMENT)) {
            ps.setLong(1, taskElementId);
            try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                  Long id = rs.getLong("taskElementId");
                  String description = rs.getString("description");
                  String comments = rs.getString("comments");
                  result = new TaskElement(id, description, comments);
               }
            }
         }
      }
         
      return result;
   }

   public static void updateTaskElements(List<TaskElement> taskElementList, int database)
           throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(UPDATE_TASKELEMENT)) {
            for (TaskElement element : taskElementList) {
               ps.setString(1, element.getDescription());
               ps.setString(2, element.getComments());
               ps.setLong(3, element.getTaskElementId());
               ps.executeUpdate();
            }
         }
      }
   }

   public static void insertTaskElements(List<TaskElement> taskElementList, 
           int database, long taskId) throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(INSERT_TASK_ELEMENT)) {
            for (TaskElement element : taskElementList) {
               ps.setLong(1, element.getTaskElementId());
               ps.setString(2, element.getDescription());
               ps.setString(3, element.getComments());
               ps.setLong(4, taskId);
               ps.executeUpdate();
            }
         }
      }
   }

}
