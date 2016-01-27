/*
 * @(#) ConnectionManagerTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */
package uk.ac.aber.cs221.group12.taskercli.data;

import java.sql.Connection;
import java.util.Properties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tom Mills
 */
public class ConnectionManagerTest {

    private Properties mysqlProp;
    private Properties sqliteProp;
    private Connection con1;
    private Connection con2;
   
    
    @Before
    public void getProperties(){
       try{
       mysqlProp = ConnectionManager.getDatabaseProperties(1);
       sqliteProp = ConnectionManager.getDatabaseProperties(2);
       }catch(Exception e){
           System.err.println("Error obtaining database properties");
       }
    }
    
    @Test
    public void assertMySQLPropertiesCorrect(){
        String url = mysqlProp.getProperty("jdbc.url");
        String actualUrl = "jdbc:mysql://db.dcs.aber.ac.uk:3306/csgp_12_15_16";
        Assert.assertEquals("Url Incorrect",actualUrl, url);
        
        String userName = mysqlProp.getProperty("jdbc.username");
        String actualUserName = "csgpadm_12";
        Assert.assertEquals("UserName Incorrect",actualUserName,userName);
        
        String password = mysqlProp.getProperty("jdbc.password");
        String actualPassword = "RCORoND6";
        Assert.assertEquals("Password Incorrect",actualPassword,password);
    }
    
    @Test
    public void assertSQLitePropertiesCorrect(){
        String fileName = sqliteProp.getProperty("jdbc.filename");
        String actualFileName = "jdbc:sqlite:src/main/resources/META-INF/SQLite.db";
        Assert.assertEquals("Url Incorrect",actualFileName, fileName);
        
    }
    
    @Test
    public void assertConnectionToMysql(){
        try{
        con1 = ConnectionManager.getConnection(mysqlProp);
        }catch(Exception e){
            System.err.println("Could not connect to MySQL Database");
        }
        Assert.assertNotNull(con1);
    }
    
    @Test
    public void assertConnectionToSqlite(){
        try{
            con2 = ConnectionManager.getConnection(sqliteProp);
        }catch(Exception e){
            System.err.println("Could not connect to SQLite Database");
        } 
        Assert.assertNotNull(con2);
    }
    
}
