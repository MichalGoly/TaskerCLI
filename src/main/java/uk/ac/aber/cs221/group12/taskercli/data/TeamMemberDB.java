package uk.ac.aber.cs221.group12.taskercli.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

/**
 *
 * @author Michal Goly
 */
public class TeamMemberDB {

   public static final String SELECT_MEMBER
           = "SELECT * FROM TeamMember "
           + "WHERE email = ?";

   public static final String UPDATE_MEMBER
           = "UPDATE TeamMember "
           + "SET firstName = ?, lastName = ?, password = ? "
           + "WHERE email = ?";

   public static TeamMember selectTeamMemberByEmail(String email)
           throws SQLException, IOException {
      TeamMember teamMember = null;
      Properties props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.MYSQL);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(SELECT_MEMBER)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                  String firstName = rs.getString("firstName");
                  String lastName = rs.getString("lastName");
                  String password = rs.getString("password");
                  List<Task> taskList = TaskDB.selectTasks(email);
                  teamMember = new TeamMember(firstName, lastName, email,
                          password, taskList);
               }
            }
         }
      }
      return teamMember;
   }

   public static void updateTeamMember(TeamMember teamMember) throws
           SQLException, IOException {
      Properties props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.MYSQL);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(UPDATE_MEMBER)) {
            ps.setString(1, teamMember.getFirstName());
            ps.setString(2, teamMember.getLastName());
            ps.setString(3, teamMember.getPassword());
            ps.setString(4, teamMember.getEmail());
            ps.executeUpdate();
            TaskDB.updateTasks(teamMember.getTaskList());
         }
      }
      props
              = ConnectionManager.getDatabaseProperties(ConnectionManager.SQLITE);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(UPDATE_MEMBER)) {
            ps.setString(1, teamMember.getFirstName());
            ps.setString(2, teamMember.getLastName());
            ps.setString(3, teamMember.getPassword());
            ps.setString(4, teamMember.getEmail());
            ps.executeUpdate();
            TaskDB.updateTasks(teamMember.getTaskList());
         }
      }
   }

}