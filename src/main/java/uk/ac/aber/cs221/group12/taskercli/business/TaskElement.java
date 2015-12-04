package uk.ac.aber.cs221.group12.taskercli.business;

import java.util.Objects;

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
      if (!Objects.equals(this.comments, other.comments)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "TaskElement{" + "taskElementId=" + taskElementId + ", description=" + description + ", comments=" + comments + '}';
   }

}
