/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate.HibernateStudentService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.StudentServiceI;
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
public class StudentServiceTest {
	// private StudentServiceI ssi=new MemoryStudentService();
	private StudentServiceI ssi = new HibernateStudentService();

	public StudentServiceTest() {
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

		String firstName = "firstName";
		String lastName = "lastName";
		String studentNumber = "1000";
		int count = 10;
		for (int i = 0; i < count; i++) {
			this.ssi.createStudent(new Student(firstName + i, lastName + i, studentNumber + i));
		}
		assertEquals(count, this.ssi.getStudents().size());

		for (int i = 0; i < count; i++) {
				this.ssi.createStudent(new Student("" + i, "" + i, studentNumber + i));
		}
		assertEquals(count, this.ssi.getStudents().size());

		Student test = new Student(firstName + 0, lastName + 0, studentNumber + 0);

		assertEquals(test, this.ssi.getStudentByStudentNumber(test.getStudentNumber()));

		 for(int i=0;i<count;i++){
		 this.ssi.deleteStudent(this.ssi.getStudentByStudentNumber(studentNumber+i));
		 }
		
		 assertEquals(0,this.ssi.getStudents().size());
	}
}
