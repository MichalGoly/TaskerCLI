package uk.ac.aber.cs211.group12.taskercli.data;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.data.ConnectionManager;
import uk.ac.aber.cs221.group12.taskercli.data.TeamMemberDB;

/**
 *
 * @author Michal Goly
 */
public class TeamMemberDBTest {

//   @Test
//   public void assertTeamMemberHasProperName() throws IOException {
//      try {
//         String email = "bob@smith.com";
//         TeamMember bob = TeamMemberDB.selectTeamMemberByEmail(email,
//                 ConnectionManager.MYSQL);
//         Assert.assertEquals("Bob", bob.getFirstName());
//      } catch (SQLException e) {
//         for (Throwable t : e) {
//            System.err.println(t);
//         }
//      }
//   }
//
//   @Test
//   public void assertTeamMemberHasProperTask() throws IOException {
//      try {
//         String email = "m.goly@goly2.com";
//         TeamMember mike = TeamMemberDB.selectTeamMemberByEmail(email,
//                 ConnectionManager.MYSQL);
//         Assert.assertEquals(2, mike.getTaskList().size());
//      } catch (SQLException e) {
//         for (Throwable t : e) {
//            System.err.println(t);
//         }
//      }
//   }

   @Test
   public void canAccessSQLite() throws IOException {
      try {
         String email = "m.goly@goly2.com";
         TeamMember member = TeamMemberDB.selectTeamMemberByEmail(email,
                 ConnectionManager.SQLITE);
         Assert.assertEquals("Goly", member.getLastName());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }

//   @Test
//   public void canUpdateTheTaskStatus() throws IOException {
//      try {
//         String email = "m.goly@goly2.com";
//         TeamMember member = TeamMemberDB.selectTeamMemberByEmail(email,
//                 ConnectionManager.MYSQL);
//         member.getTaskList().get(0).setStatus(TaskStatus.COMPLETED);
//         TeamMemberDB.updateTeamMember(member,1);
//         
//         TeamMember sameMember = TeamMemberDB.selectTeamMemberByEmail(email,
//                 ConnectionManager.MYSQL);
//         Assert.assertEquals(TaskStatus.COMPLETED, 
//                 sameMember.getTaskList().get(0).getStatus());
//      } catch (SQLException e) {
//         for (Throwable t : e) {
//            System.err.println(t);
//         }
//      }
//   }
}
