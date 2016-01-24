package uk.ac.aber.cs221.group12.taskercli.business;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * TeamMember class abstracts the TeamMember table in both databases. Its instance
 * variables correspond to columns inside databases and can be accessed and modified
 * using the getters and setters provided. Instance of this class will be used to
 * populate the user interface with data in the program.
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
   
   /**
    * @return The first name of the team member
    */
   public String getFirstName() {
      return firstName;
   }
   
   /**
    * @return The last name of the team member
    */
   public String getLastName() {
      return lastName;
   }
   
   /**
    * @return The email address of the team member 
    */
   public String getEmail() {
      return email;
   }
   
   /**
    * @return The password of the team member
    */
   public String getPassword() {
      return password;
   }
   
   /**
    * @return The list of tasks of the team member
    */
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
   public int hashCode() {
      int hash = 3;
      hash = 29 * hash + Objects.hashCode(this.firstName);
      hash = 29 * hash + Objects.hashCode(this.lastName);
      hash = 29 * hash + Objects.hashCode(this.email);
      hash = 29 * hash + Objects.hashCode(this.password);
      hash = 29 * hash + Objects.hashCode(this.taskList);
      return hash;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final TeamMember other = (TeamMember) obj;
      if (!Objects.equals(this.firstName, other.firstName)) {
         return false;
      }
      if (!Objects.equals(this.lastName, other.lastName)) {
         return false;
      }
      if (!Objects.equals(this.email, other.email)) {
         return false;
      }
      if (!Objects.equals(this.password, other.password)) {
         return false;
      }
      if (!Objects.equals(this.taskList, other.taskList)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "TeamMember{" + "firstName=" + firstName + ", lastName="
              + lastName + ", email=" + email + ", password=" + password
              + ", taskList=" + taskList + '}';
   }

}
