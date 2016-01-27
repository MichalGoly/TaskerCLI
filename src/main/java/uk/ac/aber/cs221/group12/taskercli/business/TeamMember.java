/*
 * @(#) TeamMember.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
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
 * @version 1.0
 */
public class TeamMember implements Serializable {

   private String firstName;
   private String lastName;
   
   private String email;
   private String password;
   
   private List<Task> taskList;
   
   /**
    * Empty constructor for maintenence reasons
    */
   public TeamMember() {
       //EMPTY CONSTRUCTOR
   }
   
   /**
    * Constructor 
    * 
    * @param firstName First name of the TeamMember
    * @param lastName Last name of the TeamMember
    * @param email email for the TeamMember, also used as a username for login
    * @param password password used for login. Stored in a hash, verified using
    * BeCript.
    * @param taskList the list of {@link Task}s assigned to this task member
    */
   public TeamMember(String firstName, String lastName, String email,
           String password, List<Task> taskList) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.taskList = taskList;
   }
   //Get Methods
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
   
   //Set Methods
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
      return Objects.equals(this.taskList, other.taskList);
   }

   @Override
   public String toString() {
      return "TeamMember{" + "firstName=" + firstName + ", lastName="
              + lastName + ", email=" + email + ", password=" + password
              + ", taskList=" + taskList + '}';
   }

}
