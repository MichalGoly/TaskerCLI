/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.aber.cs221.group12.taskercli.business;

/**
 *
 * @author Michal Goly
 */
public class TaskElement {

   private Long taskElementId;
   private String description;
   private String comments;

   public TaskElement() {
   }

   public TaskElement(Long taskElementId, String description, String comments) {
      this.taskElementId = taskElementId;
      this.description = description;
      this.comments = comments;
   }

   public void setTaskElementId(Long taskElementId) {
      this.taskElementId = taskElementId;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public void setComments(String comments) {
      this.comments = comments;
   }

   public Long getTaskElementId() {
      return taskElementId;
   }

   public String getDescription() {
      return description;
   }

   public String getComments() {
      return comments;
   }

}
