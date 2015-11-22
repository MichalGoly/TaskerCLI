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

   public static String SELECT_TASK_ELEMENTS
           = "SELECT * FROM TaskElement "
           + "WHERE Task_taskId = ?";

   public static String UPDATE_TASKELEMENT
           = "UPDATE TaskElement "
           + "SET description = ?, comments = ? "
           + "WHERE taskElementId = ?";

   public static List<TaskElement> selectTaskElementsByTaskId(Long taskId)
           throws SQLException, IOException {
      List<TaskElement> taskElementList = new ArrayList<>();
      Properties props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.MYSQL);

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

   public static void updateTaskElements(List<TaskElement> taskElementList)
           throws SQLException, IOException {
      Properties props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.MYSQL);
      
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

}
