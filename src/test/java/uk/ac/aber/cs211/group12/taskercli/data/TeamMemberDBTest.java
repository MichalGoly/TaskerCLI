package uk.ac.aber.cs211.group12.taskercli.data;

import java.io.IOException;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.data.TeamMemberDB;

/**
 *
 * @author Michal Goly
 */
public class TeamMemberDBTest {

   @Test
   public void assertTeamMemberHasProperName() throws IOException {
      try {
         String email = "bob@smith.com";
         TeamMember bob = TeamMemberDB.selectTeamMemberByEmail(email);
         Assert.assertEquals("Bob", bob.getFirstName());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }

   @Test
   public void assertTeamMemberHasProperTask() throws IOException {
      try {
         String email = "m.goly@goly2.com";
         TeamMember mike = TeamMemberDB.selectTeamMemberByEmail(email);
         Assert.assertEquals(2, mike.getTaskList().size());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }
}
