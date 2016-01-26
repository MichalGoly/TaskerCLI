/*
 * @(#) TaskStatus.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.business;

/**
 * TaskStatus Enum represents one of the 3 possible states of tasks in the program.
 * Each task can be either allocated, abandoned or completed. Each Enum has its
 * integer representation accessible with the toInt() method.
 * @author Michal Goly
 */
public enum TaskStatus {

   ALLOCATED(1), ABANDONED(2), COMPLETED(3);

   private int status;

   TaskStatus(int status) {
      this.status = status;
   }

   /**
    * Static method which creates and returns an Enum based on the provided integer
    * value.
    *
    * @param integer The integer representation of the TaskStatus (1 : allocated, 2 :
    * abandoned, 3 : completed).
    * @return An appropriate TaskStatus Enum or null for the invalid integer value
    */
   public static TaskStatus fromInt(int integer) {
      TaskStatus result = null;
      for (TaskStatus ts : TaskStatus.values()) {
         if (ts.status == integer) {
            result = ts;
            break;
         }
      }
      return result;
   }
   
   public int toInt() {
      return status;
   }
   
}
