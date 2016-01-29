/*
 * @(#) TaskElementDBTest.java 1.0 26/01/16
 *
 * Copyright (c) 2016 Aberystwyth University.
 * All rights reserved.
 *
 */

package uk.ac.aber.cs221.group12.taskercli.data;


import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.ac.aber.cs221.group12.taskercli.business.TaskElement;

/**
 * @author Tom Mills
 */
public class TaskElementDBTest {
    

    private List<TaskElement> selectedElements;
    private List<TaskElement> elements;
    private TaskElement element;
    private TaskElement selectedElement;
    private TaskElementDB taskElementDB;
    
    @Before
    public void createTask(){
        
        taskElementDB=new TaskElementDB();
        elements = new ArrayList<TaskElement>();
        element = new TaskElement(new Long(1),"get out of the house","");
        elements.add(element);
        TaskElement element2 = new TaskElement(new Long(2),"drive to the store","");
        elements.add(element2);
        TaskElement element3 = new TaskElement(new Long(3),"put items to your basket","");
        elements.add(element3);
        TaskElement element4 = new TaskElement(new Long(4),"pay for the goods","");
        elements.add(element4);
        TaskElement element5 = new TaskElement(new Long(5),"drive back home","");
        elements.add(element5);
        
    }
    
//    @Test
//    public void assertCanSelectElementsByTaskID(){
//        try{
//        selectedElements = taskElementDB.selectTaskElementsByTaskId(new Long(1),1);
//        }catch(Exception e){
//            System.err.println("Error selecting task");
//        }
//        Assert.assertEquals(elements,selectedElements);
//    }
//    
//    @Test public void assertCanSelectElementByID(){
//        try{
//        selectedElement = taskElementDB.selectTaskElementById(new Long(1),1);
//        }catch(Exception e){
//            System.err.println("Error selecting Element");
//        }
//        Assert.assertEquals(element,selectedElement);
//    }
//    
//    @Test public void assertCanInsert(){
//        
//    }
    

}