package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Course;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Degree;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate.HibernateCourseService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate.HibernateDegreeService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate.HibernateStudentService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.memory.MemoryCourseService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.memory.MemoryDegreeService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.memory.MemoryStudentService;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.CourseServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.DegreeServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.StudentServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils.HibernateUtil;

public class App {

	private static CourseServiceI csi;
	private static StudentServiceI ssi;
	private static DegreeServiceI dsi;

	private static boolean memory = false;

	private static void init() {
		if (memory) {
			csi = new MemoryCourseService();
			ssi = new MemoryStudentService();
			dsi = new MemoryDegreeService();
		} else {
			HibernateUtil.init();
			HibernateUtil.getSessionFactory().getStatistics().setStatisticsEnabled(true);
			csi = new HibernateCourseService();
			ssi = new HibernateStudentService();
			dsi = new HibernateDegreeService();
		}
	}

	public static void shutdown() {
		if (!memory) {
			HibernateUtil.closeFactory();
		}
	}

	public static void main(String[] args) {
//		init();
		// test();
		test2();
		try {
//			deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		shutdown();
	}

	public static void test2() {
		init();
		insertFromFile();
		try {
			 readAllEntities();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		shutdown();
	}

	public static void test() {
		init();
		try {
			System.out.println("creating entities");
			createStub();
			System.out.println(
					csi.getCourses().size() + dsi.getDegrees().size() + ssi.getStudents().size() + " entities");

			System.out.println("reading entities");
			readAllEntities();

			System.out.println("**********");
			for (Course c : csi.getCourses()) {
				System.out.println(c.getName() + "\t\t" + csi.getStudentsByCourse(c.getName()));
			}

			System.out.println("**********");

			System.out.println("deleting entities");
			deleteAll();

			readAllEntities();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			shutdown();
		}
	}

	public static void readAllEntities() throws Exception {
		long sessionOpen = HibernateUtil.getSessionFactory().getStatistics().getSessionOpenCount();
		long sessionClosed = HibernateUtil.getSessionFactory().getStatistics().getSessionCloseCount();
		if (sessionOpen != sessionClosed) {
			// System.out.println("PROBLEMA!!!!");
			// System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		}
		// System.out.println("antes students");
		// System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		ArrayList<Student> students = ssi.getStudents();
		for (Student s : students) {
			System.out.println(s);
		}
		// System.out.println("depois students, antes courses");
		// System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		ArrayList<Course> courses = csi.getCourses();
		for (Course c : courses) {
			System.out.println(c);
		}
		// System.out.println("depois courses, antes degrees");
		// System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		ArrayList<Degree> degrees = dsi.getDegrees();
		for (Degree d : degrees) {
			System.out.println(d);
		}
		// System.out.println("depois degrees");
		// System.out.println(HibernateUtil.getSessionFactory().getStatistics());
	}

	public static void createStub() throws Exception {
		Degree d = new Degree("degree0");

		Course course0 = csi.createCourse(new Course("course0"));
		Course course1 = csi.createCourse(new Course("course1"));
		Course course2 = csi.createCourse(new Course("course2"));

		d.addCourse(course0);
		d.addCourse(course1);
		d.addCourse(course2);

		dsi.createDegree(d);

		Student student = new Student("a", "b", "c");
		student.addCourse(csi.getCourseByName("course0"));
		student.addCourse(csi.getCourseByName("course2"));
		student.setDegree(d);

		Student student2 = new Student("a", "b", "d");
		student2.addCourse(csi.getCourseByName("course0"));
		student2.addCourse(csi.getCourseByName("course1"));
		student2.setDegree(d);

		ssi.createStudent(student);
		ssi.createStudent(student2);
	}

	public static void deleteAll() throws Exception {
		ArrayList<Student> students = ssi.getStudents();
		ArrayList<Course> courses = csi.getCourses();
		ArrayList<Degree> degrees = dsi.getDegrees();
		if (memory) {
			if (csi.getCourses().removeAll(courses)) {
				System.out.println("removed all courses");
			}
			if (ssi.getStudents().removeAll(students)) {
				System.out.println("removed all students");
			}
			if (dsi.getDegrees().removeAll(degrees)) {
				System.out.println("removed all degrees");
			}
		} else {
			for (Student s : students) {
				ssi.deleteStudent(s);
			}
			for (Course c : courses) {
				csi.deleteCourse(c);
			}
			for (Degree d : degrees) {
				dsi.deleteDegree(d);
			}
		}
	}

	private static void insertFromFile() {
		String pathFile = System.getProperty("user.dir") + "/src/main/resources/clean_data_explicadores.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(pathFile))) {
			try {

				int numberDegree = Integer.parseInt(br.readLine());
				for (int j = 0; j < numberDegree; j++) {
					String degreeName = br.readLine();
					Degree deg = null;
					deg = new Degree(degreeName);

					int numberCourse = Integer.parseInt(br.readLine());
					for (int k = 0; k < numberCourse; k++) {
						String courseName = br.readLine();
						Course course = null;
						course = new Course(courseName);
						deg.addCourse(course);
					}

					dsi.createDegree(deg);
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
