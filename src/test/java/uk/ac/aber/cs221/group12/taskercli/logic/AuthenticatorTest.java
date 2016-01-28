/*
* @(#) AuthenticatorTest.java 1.0 26/01/16
*
* Copyright (c) 2016 Aberystwyth University.
* All rights reserved.
*
*/

package uk.ac.aber.cs221.group12.taskercli.logic;

import org.junit.Assert;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.TeamMember;

/**
 * Created by jam on 1/26/16.
 */
public class AuthenticatorTest {
   
   @Test
   public void canAuthenticate() {
      String hash = "$2y$10$QT3etHPuD9Azt2JGBsLra.4zPTobVf2S17unxeNIUzF6GlZHRad72";
      char[] password = new char[] {'p', 'a', 's', 's', 'w', 'o', 'r', 'd'};
      
      TeamMember teamMember = new TeamMember();
      teamMember.setPassword(hash);
      
      Assert.assertTrue(Authenticator.authenticate(teamMember, password));
   }
   
}
