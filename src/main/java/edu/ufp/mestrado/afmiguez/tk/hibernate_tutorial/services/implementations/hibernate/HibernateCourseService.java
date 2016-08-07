package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Course;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.CourseServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils.HibernateUtil;

public class HibernateCourseService implements CourseServiceI {

	@Override
	public Course createCourse(Course course) {
		// Session session = HibernateUtil.getSessionFactory().openSession();
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		try {			
			session.save(course);
			session.getTransaction().commit();
			session.close();
			return course;
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			session.close();
			return null;
		}
	}

	@Override
	public ArrayList<Course> getCourses() {
		Session session = HibernateUtil.getNewSession();
		// session.beginTransaction();
		
//		System.out.println("context:getCourses():start");
//		System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		@SuppressWarnings("unchecked")
		ArrayList<Course> result = (ArrayList<Course>) session.createQuery("from Course").list();
		session.close();
//		System.out.println("context:getCourses():end");
//		System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		return result;
	}

	@Override
	public Course getCourseById(int id) {
		Session session = null;
		Course course = null;
		try {
			session = HibernateUtil.getNewSession();
			course = (Course) session.load(Course.class, id);
			Hibernate.initialize(course);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return course;
	}

	@Override
	public Course getCourseByName(String name) {
		Session session = HibernateUtil.getNewSession();
		// session.beginTransaction();
		String hql = "from Course C where C.name= :name";
		Query query = session.createQuery(hql);

		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<Course> result = query.list();

		session.close();
		if (result.size() > 0) {
			return (Course) result.get(0);
		}
		return null;
	}

	@Override
	public Course updateCourse(Course course) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		// updates the information of the student
		session.update(course);
		session.getTransaction().commit();
		return course;
	}

	@Override
	public Course deleteCourse(Course course) {
		Session session = HibernateUtil.getNewSession();
		int id = course.getId();
		try {
			session.beginTransaction();
			Course deg = (Course) session.get(Course.class, id);
			session.delete(deg);
			session.getTransaction().commit();
			return deg;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	@Override
	public ArrayList<Student> getStudentsByCourse(String name) {
		return (ArrayList<Student>) getCourseByName(name).getStudents();		
	}

}
