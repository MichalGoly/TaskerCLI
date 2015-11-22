package uk.ac.aber.cs211.group12.taskercli.data;

import java.io.IOException;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.data.TeamMemberDB;

/**
 *
 * @author Michal Goly
 */
public class TeamMemberDBTest {
   
   @Before
   public void repopulateTheDatabase() {
      
   }
   
   @Test
   public void assertTeamMemberHasProperName() throws IOException {
      try {
         String email = "josh@abber.ac.uk";
         TeamMember bob = TeamMemberDB.selectTeamMemberByEmail(email);        
         Assert.assertEquals("Joshua", bob.getFirstName());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }

   @Test
   public void assertTeamMemberHasProperTask() throws IOException {
      try {
         String email = "michal@goly.com";
         TeamMember mike = TeamMemberDB.selectTeamMemberByEmail(email);
         Assert.assertEquals(1, mike.getTaskList().size());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }
}
