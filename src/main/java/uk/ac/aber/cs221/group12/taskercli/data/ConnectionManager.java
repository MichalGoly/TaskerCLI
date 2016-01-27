/*
 * @(#) ConnectionManager.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ConnectionManager class can be used to actually connect to both databases. It
 * enables the caller to acquire a Connection object to either the remote MySQL
 * (TaskerSRV), or locally run SQLite. ConnectionManager does not have to register
 * the JDBC drivers anymore, as it is done automatically.
 *
 * // TODO maybe change this behaviour? Right now caller has to acquire the
 * Properties object of the required database, before asking for the Connection
 * object.
 *
 * @author Michal Goly
 * @version 1.0
 */
public class ConnectionManager {

   /**
    * MYSQL constant indicates that the MySQL database should be used
    */
   public static final int MYSQL = 1;

   /**
    * SQLITE constant indicates that the SQLite database should be used
    */
   public static final int SQLITE = 2;

   private static final int MYSQL_PROPERTIES_SIZE = 3;

   /**
    * gets the properties from the database we are connecting to, from the
    * properties files we have stored in {@code src/main/resorces/META-INF}
    * These properties include the location of the database, it's login username
    * and password
    * 
    * @param database The value of the database that the .properties will be, 
    * selected from the static values above.
    * @return The location and login details of the database selected
    * @throws IOException Throws if the .properties file was not located
    */
   public static Properties getDatabaseProperties(int database) 
   throws IOException {
      if (database < 1 || database > 2) {
         return null;
      }

      String propertiesURI;
      if (database == MYSQL) {
         propertiesURI = "src/main/resources/META-INF/mysql.properties";
      } else {
         propertiesURI = "src/main/resources/META-INF/sqlite.properties";
      }

      Properties prop = new Properties();
      try (InputStream in = Files.newInputStream(Paths.get(propertiesURI))) {
         prop.load(in);
      }
      return prop;
   }

   /**
    * This method can be used to acquire the Connection object to the required
    * database by passing in the corresponding Properties object.
    *
    * @param props The Properties object required to get the connection
    * @return The Connection to the required database
    * @throws SQLException Thrown if there was a problem with connecting to a db
    */
   public static Connection getConnection(Properties props) throws SQLException {
      if (props.size() == MYSQL_PROPERTIES_SIZE) {
         String username = props.getProperty("jdbc.username");
         String password = props.getProperty("jdbc.password");
         String url = props.getProperty("jdbc.url");

         return DriverManager.getConnection(url, username, password);
      } else {
         String filename = props.getProperty("jdbc.filename");
         return DriverManager.getConnection(filename);
      }
   }

}
