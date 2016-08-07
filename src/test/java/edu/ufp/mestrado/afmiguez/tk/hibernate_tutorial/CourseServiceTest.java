/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Course;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate.HibernateCourseService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.CourseServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils.HibernateUtil;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AF
 */
public class CourseServiceTest {
    
//    private CourseServiceI csi=new MemoryCourseService();
	private CourseServiceI csi=new HibernateCourseService();
    
    public CourseServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    	HibernateUtil.getSessionFactory();
    }
    
    @AfterClass
    public static void tearDownClass() {
    	HibernateUtil.closeFactory();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {
         String name="name";
         
         int count=10;
         for(int i=0;i<count;i++){
             this.csi.createCourse(new Course(name+i));
         }
         assertEquals(count,this.csi.getCourses().size());
         
         for(int i=0;i<count;i++){
             this.csi.createCourse(new Course(name+i));
         }
         assertEquals(count,this.csi.getCourses().size());
         
         Course test=new Course(name+0);
         
//         assertEquals(test, this.csi.getCourseById(test.getId()));
         
         assertEquals(test, this.csi.getCourseByName(test.getName()));
         
         for(int i=0;i<count;i++){
             this.csi.deleteCourse(this.csi.getCourseByName(name+i));
         }
         
         assertEquals(0,this.csi.getCourses().size());
     }
}
