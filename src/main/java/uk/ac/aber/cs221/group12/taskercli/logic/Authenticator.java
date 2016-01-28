/*
 * @(#) Authenticator.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.logic;

import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

/**
 * Authenticator provides a static method to provide functionality to the 
 * {@link uk.ac.aber.cs221.group12.taskercli.logic.Syncer#logIn() } method to
 * check passwords. Using {@link BCrypt} the passwords are hashed to keep
 * them secure, and are encrypted in the databases. 
 * 
 * @author Michal Goly
 * @see TeamMember
 * @see BCrypt
 * @see uk.ac.aber.cs221.group12.taskercli.logic.Syncer#logIn()
 */
public class Authenticator {

   /**
    * Used to encrypt the entered password on the login box, and compare it
    * to the matching encrypted password inside the database.
    *
    * @param teamMember The TeamMember whose password needs checking.
    * @param password The password entered into the login box, to be encrypted
    * and compared
    * @return true if the password provided matches the password in the TeamMember
    */
   public static boolean authenticate(TeamMember teamMember, char[] password) {
      String plainTextPassword = String.valueOf(password);

      // PHP uses the old version of BCrypt which has to be translated to Java one
      String hashedPassword = teamMember.getPassword();
      String properHashedPassword = hashedPassword.replaceFirst("y", "a");

      if (BCrypt.checkpw(plainTextPassword, properHashedPassword)) {
         return true;
      } else {
         JOptionPane.showMessageDialog(null, "Invalid email or password", "",
                 JOptionPane.ERROR_MESSAGE);

         return false;
      }
   }

}
