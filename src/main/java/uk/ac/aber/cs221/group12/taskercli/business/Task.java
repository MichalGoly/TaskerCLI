/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs221.group12.taskercli.business;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

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
   public String toString() {
      return "Task{" + "taskId=" + taskId + ", title=" + title
              + ", startDate=" + startDate + ", endDate=" + endDate
              + ", status=" + status + ", taskElementList=" + taskElementList + '}';
   }

}
