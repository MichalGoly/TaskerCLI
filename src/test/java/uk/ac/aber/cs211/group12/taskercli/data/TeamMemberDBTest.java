package uk.ac.aber.cs211.group12.taskercli.data;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;
import uk.ac.aber.cs221.group12.taskercli.business.TaskStatus;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;
import uk.ac.aber.cs221.group12.taskercli.data.ConnectionManager;
import uk.ac.aber.cs221.group12.taskercli.data.TeamMemberDB;

/**
 *
 * @author Michal Goly
 */
public class TeamMemberDBTest {

//   @Test
//   public void assertCanInsertIntoRemoteDB() throws IOException {
//      try {
//       
//         List<TaskElement> te = new ArrayList<>();
//         te.add(new TaskElement(20L, "Go to train station", ""));
//         te.add(new TaskElement(21L, "Get inside the train", ""));
//         List<Task> tasks = new ArrayList<>();
//         Task task = new Task(4L, "Go back for Christmas", new Date(2011, 2, 7), new Date(2010, 2, 1), 
//           TaskStatus.ALLOCATED, te);
//         tasks.add(task);
//         TeamMember mark = new TeamMember("Mark", "Smith", "mark@smith.com", "fish", tasks);
//         
//         TeamMemberDB.insertTeamMember(mark, ConnectionManager.MYSQL);
//      } catch (SQLException e) {
//         for (Throwable t : e) {
//            System.err.println(t);
//         }
//      }
//   }

   @Test
   public void assertTeamMemberHasProperName() throws IOException {
      try {
         String email = "bob@smith.com";
         TeamMember bob = TeamMemberDB.selectTeamMemberByEmail(email,
                 ConnectionManager.MYSQL);
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
         TeamMember mike = TeamMemberDB.selectTeamMemberByEmail(email,
                 ConnectionManager.MYSQL);
         Assert.assertEquals(2, mike.getTaskList().size());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }

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

   @Test
   public void canUpdateTheTaskStatus() throws IOException {
      try {
         String email = "m.goly@goly2.com";
         TeamMember member = TeamMemberDB.selectTeamMemberByEmail(email,
                 ConnectionManager.MYSQL);
         member.getTaskList().get(0).setStatus(TaskStatus.COMPLETED);
         TeamMemberDB.updateTeamMember(member, 1);

         TeamMember sameMember = TeamMemberDB.selectTeamMemberByEmail(email,
                 ConnectionManager.MYSQL);
         Assert.assertEquals(TaskStatus.COMPLETED,
                 sameMember.getTaskList().get(0).getStatus());
      } catch (SQLException e) {
         for (Throwable t : e) {
            System.err.println(t);
         }
      }
   }
}
