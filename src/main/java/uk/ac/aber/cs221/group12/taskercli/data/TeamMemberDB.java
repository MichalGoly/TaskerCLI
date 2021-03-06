/*
 * @(#) TeamMemberDB.java 1.2 28/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import uk.ac.aber.cs221.group12.taskercli.business.Task;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

/**
 * TeamMemberDB class is one of the data access classes in the DAO pattern. It
 * provides useful methods which can be used to retrieve and update the
 * {@link TeamMember} objects in both databases.
 * <p>
 * For example the
 * {@link #selectTeamMemberByEmail(String email, int database) selectTeamMemberByEmail}
 * method will as expected acquire the connection to either the MySQL or SQLite
 * database, prepare a suitable query statement, construct the {@link TeamMember}
 * object and return it back to the caller. This is a really powerful concept which
 * abstracts the raw SQL from the graphical user interface. After we make some
 * changes to the object, we can use a single method
 * {@link #updateTeamMember(TeamMember teamMember, int database) updateTeamMember} to
 * put it back to the database.
 *
 * @author Michal Goly 
 * @author Joshua Mir 
 * @author Tom Mills 
 * @author Adam Neaves
 * @version 1.2 bugs fixed
 * @since 1.0 added delete member method, to fix sync bugs
 * 
 * @see Task
 * @see TeamMember
 * @see ConnectionManager
 */
public class TeamMemberDB {

   private static final String SELECT_MEMBER
           = "SELECT * FROM TeamMember "
           + "WHERE email = ?";

   private static final String DELETE_MEMBER
           = "DELETE FROM TeamMember "
           + "WHERE email = ?";

   private static final String INSERT_MEMBER
           = "INSERT INTO TeamMember (email, firstName, lastName, password) "
           + "VALUES (?, ?, ?, ?)";

   //TODO ConnectionManager should take care of getting Properties not each method
   /**
    * Selects a {@link TeamMember} from the databases via their unique email, using
    * the {@link #SELECT_MEMBER SELECT_MEMBER} prepared statement.
    *
    * @param email the email of the TeamMember to be selected
    * @param database The numeric value of the database to select with the
    * TeamMember, based on values set in {@link ConnectionManager}.
    * @return The TeamMember from the database.
    * @throws SQLException Thrown if there is an issue connecting to the database.
    * @throws IOException throws if the properties file loaded by
    * {@link ConnectionManager#getConnection getConnection()} is not found.
    */
   public static TeamMember selectTeamMemberByEmail(String email, int database)
   throws SQLException, IOException {
      TeamMember teamMember = null;
      Properties props
              = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = conn.prepareStatement(SELECT_MEMBER)) {
            statement.setString(1, email);
            try (ResultSet results = statement.executeQuery()) {
               while (results.next()) {
                  String firstName = results.getString("firstName");
                  String lastName = results.getString("lastName");
                  String password = results.getString("password");
                  List<Task> taskList = TaskDB.selectTasks(email, database);
                  teamMember = new TeamMember(firstName, lastName, email,
                          password, taskList);
               }
            }
         }
      }
      return teamMember;
   }

   //if we are updating the database, with the teammember object, assume it is the merged version after syncing
   /**
    * Updates the selected TeamMember by deleting and re-inserting the team 
    * member via the {@link #deleteTeamMember(TeamMember, int) deleteTeamMember()} 
    * and {@link #insertTeamMember(TeamMember, int) insertTeamMember()} methods.
    *
    * @param teamMember The TeamMember to be updated in the database.
    * @param database The numeric value of the database to update with the
    * TeamMember, based on values set in {@link ConnectionManager}.
    * @throws SQLException Thrown if there is an issue connecting to the database.
    * @throws IOException throws if the properties file loaded by
    * {@link ConnectionManager#getConnection getConnection()} is not found.
    */
   public static void updateTeamMember(TeamMember teamMember, int database)
   throws SQLException, IOException {
      
      for (Task t : teamMember.getTaskList()) {
         TaskDB.deleteTaskById(t.getTaskId(), database);
      }
      
      deleteTeamMember(teamMember, database);
      insertTeamMember(teamMember, database);
   }

   /**
    * Inserts a {@link TeamMember} into the database using the
    * {@link #INSERT_MEMBER INSERT_MEMBER} prepared statement.
    *
    * @param teamMember The TeamMember to be inserted into the database.
    * @param database The numeric value of the database that the TeamMember is
    * inserted to, based on values set in {@link ConnectionManager}.
    * @throws SQLException Thrown if there is an issue connecting to the database.
    * @throws IOException throws if the properties file loaded by
    * {@link ConnectionManager#getConnection getConnection()} is not found.
    */
   public static void insertTeamMember(TeamMember teamMember, int database)
   throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         try (PreparedStatement statement = conn.prepareStatement(INSERT_MEMBER)) {
            statement.setString(1, teamMember.getEmail());
            statement.setString(2, teamMember.getFirstName());
            statement.setString(3, teamMember.getLastName());
            statement.setString(4, teamMember.getPassword());
            statement.executeUpdate();

            TaskDB.insertTasks(teamMember.getTaskList(), database, teamMember.getEmail());
         }
      }
   }

   /**
    * Deletes a {@link TeamMember} from the selected database using the 
    * {@link #DELETE_MEMBER DELETE_MEMBER} prepared statement.
    * 
    * @param teamMember The Team Member to be deleted
    * @param database The numeric value of the database that the TeamMember is
    * deleted from, based on values set in {@link ConnectionManager}.
    * @throws SQLException Thrown if there is an issue connecting to the database.
    * @throws IOException throws if the properties file loaded by
    * {@link ConnectionManager#getConnection getConnection()} is not found.
    */
   public static void deleteTeamMember(TeamMember teamMember, int database)
   throws SQLException, IOException {
      Properties props = ConnectionManager.getDatabaseProperties(database);

      try (Connection conn = ConnectionManager.getConnection(props)) {
         if (database == ConnectionManager.SQLITE) {
             //Enable foreign keys if using local database
            conn.createStatement().execute("PRAGMA foreign_keys = ON");
         }

         try (PreparedStatement statement = conn.prepareStatement(DELETE_MEMBER)) {
            statement.setString(1, teamMember.getEmail());
            statement.executeUpdate();

         }
      }

   }

}
