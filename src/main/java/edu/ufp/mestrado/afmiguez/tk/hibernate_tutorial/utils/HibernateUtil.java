package edu.ufp.mestrado.afmiguez.tk.hibernate_tutorial.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final boolean isHSQLDB = false;

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			if (isHSQLDB) {
				return new Configuration().configure("hibernate-hsqldb.cfg.xml").buildSessionFactory();
			} else {
				return new Configuration().configure("hibernate-postgres.cfg.xml").buildSessionFactory();
			}
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void init() {
		
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getNewSession() {
		return getSessionFactory().openSession();
	}

	public static void closeFactory() {
		sessionFactory.close();
	}

}