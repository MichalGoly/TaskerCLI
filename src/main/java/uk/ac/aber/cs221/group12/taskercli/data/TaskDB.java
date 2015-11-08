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

   public static final String SELECT_TASKS
           = "SELECT * FROM Task WHERE TeamMember_email = ?";

   public static final String UPDATE_TASK
           = "UPDATE Task "
           + "SET title = ?, startDate = ?, endDate = ?, taskStatus = ? "
           + "WHERE taskId = ?";

   public static List<Task> selectTasks(String email) throws SQLException,
           IOException {
      List<Task> taskList = new ArrayList<>();
      Properties props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.MYSQL);

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
                          = TaskElementDB.selectTaskElementsByTaskId(taskId);
                  taskList.add(new Task(taskId, title, startDate, endDate, status,
                          taskElementList));
               }
            }
         }
      }
      return taskList;
   }

   public static void updateTasks(List<Task> taskList) throws SQLException, 
           IOException {
      Properties props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.MYSQL);
      
      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(UPDATE_TASK)) {
            for (Task t : taskList) {
               ps.setString(1, t.getTitle());
               ps.setDate(2, t.getStartDate());
               ps.setDate(3, t.getEndDate());
               ps.setInt(4, t.getStatus().toInt());
               ps.setLong(5, t.getTaskId());
               ps.executeUpdate();
               TaskElementDB.updateTaskElements(t.getTaskElementList());
            }
         }
      }
   }

}
