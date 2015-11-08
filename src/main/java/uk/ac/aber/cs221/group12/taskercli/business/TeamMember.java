package uk.ac.aber.cs221.group12.taskercli.business;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Michal Goly
 */
public class TeamMember implements Serializable {

   private String firstName;
   private String lastName;
   private String email;
   private String password;
   private List<Task> taskList;

   public TeamMember() {
   }

   public TeamMember(String firstName, String lastName, String email,
           String password, List<Task> taskList) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.taskList = taskList;
   }

   public String getFirstName() {
      return firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public String getEmail() {
      return email;
   }

   public String getPassword() {
      return password;
   }

   public List<Task> getTaskList() {
      return taskList;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setTaskList(List<Task> taskList) {
      this.taskList = taskList;
   }

   @Override
   public String toString() {
      return "TeamMember{" + "firstName=" + firstName + ", lastName="
              + lastName + ", email=" + email + ", password=" + password
              + ", taskList=" + taskList + '}';
   }

}
