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
 *
 * @author Michal Goly
 */
public class ConnectionManager {
   
   public static final int MYSQL = 1;
   public static final int SQLITE = 2;
   
   public static Properties getDatabaseProperties(int database) throws IOException {
      if (database < 1 || database > 2) {
         return null;
      }
      
      String propertiesURI = "";
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
   
   public static Connection getConnection(Properties props) throws SQLException {

      // register the JDBC driver
      String drivers = props.getProperty("jdbc.drivers");
      // TODO driver should be register only once
      if (drivers != null) {
         System.setProperty("jdbc.drivers", drivers);
      }

      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");
      String url = props.getProperty("jdbc.url");

      return DriverManager.getConnection(url, username, password);
   }



}
