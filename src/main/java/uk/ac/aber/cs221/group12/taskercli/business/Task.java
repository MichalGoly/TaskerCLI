/*
 * @(#) Task.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.business;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * Task class abstracts the Task table in both databases. Its instance variables
 * correspond to columns inside databases and can be accessed and modified using the
 * getters and setters provided. Each task has an associated list of task elements.
 * The status of a task is stored as an integer in the database and is then
 * transformed to an appropriate TaskStatus Enum inside the program.
 *
 * @author Michal Goly
 * @version 1.0
 * @see TeamMember
 * @see TaskStatus
 * @see TaskElement
 */
public class Task 
implements Serializable {

   private Long taskId;
   private String title;
   
   private Date startDate;
   private Date endDate;
   
   private TaskStatus status;
   private List<TaskElement> taskElementList;
   
   /**
    * empty constructor for maintenence reasons
    */
   public Task(){
       //EMPTY CONSTRUCTOR
   }
   /**
    * Constructor.
    * 
    * @param taskId Unique ID number for the task.
    * @param title The task title
    * @param startDate The date the task is assigned to start
    * @param endDate The date the task is supposed to finish
    * @param status The status of the task, see {@link TaskStatus} for the
    * potential statuses
    * @param taskElementList The list of {@link TaskElement}s
    */
   public Task(Long taskId, String title, Date startDate, Date endDate,
           TaskStatus status, List<TaskElement> taskElementList) {
      this.taskId = taskId;
      this.title = title;
      this.startDate = startDate;
      this.endDate = endDate;
      this.status = status;
      this.taskElementList = taskElementList;
   }
   
   //Get Methods
   public Long getTaskId() {
      return taskId;
   }

   public String getTitle() {
      return title;
   }

   public Date getStartDate() {
      return startDate;
   }

   public Date getEndDate() {
      return endDate;
   }

   public TaskStatus getStatus() {
      return status;
   }

   public List<TaskElement> getTaskElementList() {
      return taskElementList;
   }
   
   //Set Methods
   public void setTaskId(Long taskId) {
      this.taskId = taskId;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public void setStartDate(Date startDate) {
      this.startDate = startDate;
   }

   public void setEndDate(Date endDate) {
      this.endDate = endDate;
   }

   public void setStatus(TaskStatus status) {
      this.status = status;
   }

   public void setTaskElementList(List<TaskElement> taskElementList) {
      this.taskElementList = taskElementList;
   }
   
   @Override
   public int hashCode() {
      int hash = 7;
      hash = 59 * hash + Objects.hashCode(this.taskId);
      hash = 59 * hash + Objects.hashCode(this.title);
      hash = 59 * hash + Objects.hashCode(this.startDate);
      hash = 59 * hash + Objects.hashCode(this.endDate);
      hash = 59 * hash + Objects.hashCode(this.status);
      hash = 59 * hash + Objects.hashCode(this.taskElementList);
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
      final Task other = (Task) obj;
      if (!Objects.equals(this.taskId, other.taskId)) {
         return false;
      }
      if (!Objects.equals(this.title, other.title)) {
         return false;
      }
      if (!Objects.equals(this.startDate, other.startDate)) {
         return false;
      }
      if (!Objects.equals(this.endDate, other.endDate)) {
         return false;
      }
      if (this.status != other.status) {
         return false;
      }
      return Objects.equals(this.taskElementList, other.taskElementList);
   }

   @Override
   public String toString() {
      return "Task{" + "taskId=" + taskId + ", title=" + title
              + ", startDate=" + startDate + ", endDate=" + endDate
              + ", status=" + status + ", taskElementList=" + taskElementList + '}';
   }

}
