/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs221.group12.taskercli.business;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Michal Goly
 */
public class Task implements Serializable {

   private Long taskId;
   private String title;
   private Date startDate;
   private Date endDate;
   private TaskStatus status;
   private List<TaskElement> taskElementList;

   public Task() {
   }

   public Task(Long taskId, String title, Date startDate, Date endDate, 
           TaskStatus status, List<TaskElement> taskElementList) {
      this.taskId = taskId;
      this.title = title;
      this.startDate = startDate;
      this.endDate = endDate;
      this.status = status;
      this.taskElementList = taskElementList;
   }

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
      if (!Objects.equals(this.taskElementList, other.taskElementList)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "Task{" + "taskId=" + taskId + ", title=" + title
              + ", startDate=" + startDate + ", endDate=" + endDate
              + ", status=" + status + ", taskElementList=" + taskElementList + '}';
   }

}
