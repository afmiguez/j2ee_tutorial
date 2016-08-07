package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.implementations.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.models.Degree;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.services.interfaces.DegreeServiceI;
import edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils.HibernateUtil;

public class HibernateDegreeService implements DegreeServiceI {

	@Override
	public Degree createDegree(Degree degree) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		try {
			session.save(degree);
			session.getTransaction().commit();
			session.close();
			return degree;
		} catch (ConstraintViolationException cve) {
			// cve.printStackTrace();
			session.close();
			return null;
		}
	}

	@Override
	public ArrayList<Degree> getDegrees() {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
//		System.out.println("context:getDegrees():start");
//		System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		@SuppressWarnings("unchecked")
		ArrayList<Degree> result = (ArrayList<Degree>) session.createQuery("from Degree").list();		
		session.close();
//		System.out.println("context:getDegrees():end");
//		System.out.println(HibernateUtil.getSessionFactory().getStatistics());
		return result;
	}

	@Override
	public Degree getDegreeById(int id) {
		Session session = null;
		Degree degree = null;
		try {
			session = HibernateUtil.getNewSession();
			degree = (Degree) session.load(Degree.class, id);
			Hibernate.initialize(degree);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return degree;
	}

	@Override
	public Degree getDegreeByName(String name) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		String hql = "from Degree D where D.name= :name";
		Query query = session.createQuery(hql);

		query.setParameter("name", name);
		@SuppressWarnings("unchecked")
		List<Degree> result = query.list();
		session.close();
		if (result.size() > 0) {
			return (Degree) result.get(0);
		}
		return null;
	}

	@Override
	public Degree updateDegree(Degree degree) {
		Session session = HibernateUtil.getNewSession();
		session.beginTransaction();
		// updates the information of the student
		session.update(degree);
		session.getTransaction().commit();
		session.close();
		return degree;
	}

	@Override
	public Degree deleteDegree(Degree degree) {
		Session session = HibernateUtil.getNewSession();
		int id = degree.getId();
		try {
			session.beginTransaction();
			Degree deg = (Degree) session.get(Degree.class, id);
			session.delete(deg);
			session.getTransaction().commit();
			session.close();
			return deg;
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			session.close();
			return null;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}
