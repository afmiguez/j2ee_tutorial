package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Student;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.*;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils.HibernateUtil;

public class HibernateStudentService implements StudentServiceI {

	@Override
	public Student createStudent(Student student) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		try {
			session.save(student);
			session.getTransaction().commit();
			session.close();
			return student;
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			session.close();
			return null;
		} 	
	}

	@Override
	public ArrayList<Student> getStudents() {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		@SuppressWarnings("unchecked")
		ArrayList<Student> result = (ArrayList<Student>)session.createQuery("from Student").list();
		session.close();
		return result;
	}

	@Override
	public Student getStudentById(int id) {
		Session session = null;
		Student student = null;
		try {
			session = HibernateUtil.getNewSession();
			student = (Student) session.load(Student.class, id);
			Hibernate.initialize(student);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return student;
	}

	@Override
	public Student getStudentByStudentNumber(String number) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		String hql = "from Student S where S.studentNumber= :number";
		Query query = session.createQuery(hql);

		query.setParameter("number", number);
		@SuppressWarnings("unchecked")
		List<Student> result = query.list();
		session.close();
		if (result.size() > 0) {
			return (Student) result.get(0);
		}
		return null;
	}

	@Override
	public Student updateStudent(Student student) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		// updates the information of the student
		session.update(student);
		session.getTransaction().commit();
		return student;
	}

	@Override
	public Student deleteStudent(Student student) {
		Session session = HibernateUtil.getNewSession();
		int id = student.getId();
		try {
			session.beginTransaction();
			Student st = (Student) session.get(Student.class, id);
			session.delete(st);
			session.getTransaction().commit();
			return st;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			return null;
		}finally{
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}
