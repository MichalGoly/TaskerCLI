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
 * TeamMemberDB class is one of the data access classes in the DAO pattern. It
 * provides useful methods which can be used to retrieve and update the
 * <code>TeamMember</code> objects in both databases.
 *
 * For example the
 * {@link #selectTeamMemberByEmail(String email, int database) selectTeamMemberByEmail}
 * method will as expected acquire the connection to either the MySQL or SQLite
 * database, prepare a suitable query statement, construct the
 * <code>TeamMember</code> object and return it back to the caller. This is a really
 * powerful concept which abstracts the raw SQL from the graphical user interface.
 * After we make some changes to the object, we can use a single method
 * {@link #updateTeamMember(TeamMember teamMember, int database) updateTeamMember} to
 * put it back to the database.
 *
 * @author Michal Goly
 */
public class TeamMemberDB {

   private static final String SELECT_MEMBER
           = "SELECT * FROM TeamMember "
           + "WHERE email = ?";

   private static final String UPDATE_MEMBER
           = "UPDATE TeamMember "
           + "SET firstName = ?, lastName = ?, password = ? "
           + "WHERE email = ?";

   private static final String INSERT_MEMBER
           = "INSERT INTO TeamMember (email, firstName, lastName, password) "
           + "VALUES (?, ?, ?, ?)";

   // TODO ConnectionManager should take care of getting Properties not each method
   public static TeamMember selectTeamMemberByEmail(String email, int database)
           throws SQLException, IOException {
      TeamMember teamMember = null;
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(SELECT_MEMBER)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
               while (rs.next()) {
                  String firstName = rs.getString("firstName");
                  String lastName = rs.getString("lastName");
                  String password = rs.getString("password");
                  List<Task> taskList = TaskDB.selectTasks(email, database);
                  teamMember = new TeamMember(firstName, lastName, email,
                          password, taskList);
               }
            }
         }
      }
      return teamMember;
   }

   //if we are updating the database, with the teammember object, assume it is the merged version after syncing
   public static void updateTeamMember(TeamMember teamMember, int database) throws
           SQLException, IOException {
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(UPDATE_MEMBER)) {
            ps.setString(1, teamMember.getFirstName());
            ps.setString(2, teamMember.getLastName());
            ps.setString(3, teamMember.getPassword());
            ps.setString(4, teamMember.getEmail());
            ps.executeUpdate();
            TaskDB.updateTasks(teamMember.getTaskList(), database);
         }
      }
   }

   public static void insertTeamMember(TeamMember teamMember, int database) throws
           SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement ps = conn.prepareStatement(INSERT_MEMBER)) {
            ps.setString(1, teamMember.getEmail());
            ps.setString(2, teamMember.getFirstName());
            ps.setString(3, teamMember.getLastName());
            ps.setString(4, teamMember.getPassword());
            ps.executeUpdate();

            TaskDB.insertTasks(teamMember.getTaskList(), database, teamMember.getEmail());
         }
      }
   }

}
