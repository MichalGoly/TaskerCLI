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
 *
 * @author Michal Goly
 */
public class TaskDB {

   private static final String SELECT_TASKS
           = "SELECT * FROM Task WHERE TeamMember_email = ?";

   private static final String SELECT_TASK
           = "SELECT * FROM Task WHERE taskId = ?";

   private static final String UPDATE_TASK
           = "UPDATE Task "
           + "SET title = ?, startDate = ?, endDate = ?, taskStatus = ? "
           + "WHERE taskId = ?";

   private static final String INSERT_TASK
           = "INSERT INTO Task (taskId, title, startDate, endDate, taskStatus, TeamMember_email) "
           + "VALUES (?, ?, ?, ?, ?, ?)";

   public static Task selectTaskById(long taskId, int database) throws SQLException,
           IOException {
      Task task = null;
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(SELECT_TASK)) {
            ps.setLong(1, taskId);
            try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                  String title = rs.getString("title");
                  Date startDate = rs.getDate("startDate");
                  Date endDate = rs.getDate("endDate");
                  TaskStatus status = TaskStatus.fromInt(rs.getInt("taskStatus"));
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

   public static List<Task> selectTasks(String email, int database) throws SQLException,
           IOException {
      List<Task> taskList = new ArrayList<>();
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(SELECT_TASKS)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                  Long taskId = rs.getLong("taskId");
                  String title = rs.getString("title");
                  Date startDate = rs.getDate("startDate");
                  Date endDate = rs.getDate("endDate");
                  TaskStatus status = TaskStatus.fromInt(rs.getInt("taskStatus"));
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

   public static void updateTasks(List<Task> taskList, int database) throws SQLException,
           IOException {
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(UPDATE_TASK)) {
            for (Task t : taskList) {
               ps.setString(1, t.getTitle());
               ps.setDate(2, t.getStartDate());
               ps.setDate(3, t.getEndDate());
               ps.setInt(4, t.getStatus().toInt());
               ps.setLong(5, t.getTaskId());
               ps.executeUpdate();
               TaskElementDB.updateTaskElements(t.getTaskElementList(), database);
            }
         }
      }
   }

   public static void insertTasks(List<Task> taskList, int database, String email)
           throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);
      System.out.println("BEFO");
      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(INSERT_TASK)) {
            for (Task t : taskList) {
               System.out.println("IN");

               ps.setLong(1, t.getTaskId());
               ps.setString(2, t.getTitle());
               ps.setDate(3, t.getStartDate());
               ps.setDate(4, t.getEndDate());
               ps.setInt(5, t.getStatus().toInt());
               ps.setString(6, email);
               ps.executeUpdate();

               TaskElementDB.insertTaskElements(t.getTaskElementList(), database,
                       t.getTaskId());
            }
         }
      }
   }

}
