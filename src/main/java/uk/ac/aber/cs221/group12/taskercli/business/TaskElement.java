/*
 * @(#) TaskElement.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.business;

import java.util.Objects;

/**
 * TaskElement class abstracts the TaskElement table in both databases. Its instance
 * variables correspond to columns inside databases and can be accessed and modified
 * using the getters and setters provided. Each task element consist of both the
 * description written by the manager using TaskerMAN, and the comment which can be
 * edited by the team member to communicate progress to the manager. Each task
 * element is assigned to one task, in a 1 {@link Task} to many TaskElement relationship.
 *
 * @author Michal Goly
 * @version 1.0
 * @see Task
 * @see TaskDB
 * @see uk.ac.aber.cs221.group12.taskercli.data.TaskElementDB
 */
public class TaskElement {

   private Long taskElementId;
   private String description;
   private String comments;
   
   /**
    * empty constructor for maintenence reasons
    */
   public TaskElement(){
       //EMPTY CONSTRUCTOR
   }
   /**
    * Constructor
    * 
    * @param taskElementId Unique ID number for each task element
    * @param description Description of what the task element is about
    * @param comments comments about the task element, written by the team member
    * the {@link Task} is assigned to.
    */
   public TaskElement(Long taskElementId, String description, String comments) {
      this.taskElementId = taskElementId;
      this.description = description;
      this.comments = comments;
   }
   
   //Set Methods
   public void setTaskElementId(Long taskElementId) {
      this.taskElementId = taskElementId;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }
   
   //Get Methods
   public Long getTaskElementId() {
      return taskElementId;
   }

   public String getDescription() {
      return description;
   }

   public String getComments() {
      return comments;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 41 * hash + Objects.hashCode(this.taskElementId);
      hash = 41 * hash + Objects.hashCode(this.description);
      hash = 41 * hash + Objects.hashCode(this.comments);
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
      final TaskElement other = (TaskElement) obj;
      if (!Objects.equals(this.taskElementId, other.taskElementId)) {
         return false;
      }
      if (!Objects.equals(this.description, other.description)) {
         return false;
      }
      return Objects.equals(this.comments, other.comments);
   }

   @Override
   public String toString() {
      return "TaskElement{" + "taskElementId=" + taskElementId + ", description=" + description + ", comments=" + comments + '}';
   }

}
