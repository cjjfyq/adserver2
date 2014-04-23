package com.adserver.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
	private static String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";
	private static org.hibernate.SessionFactory sessionFactory;

	public static Session openSession() throws HibernateException {
		// currentSession();
		// return sessionFactory.openSession();

		createSessionFactory();

		return sessionFactory.getCurrentSession();
	}

	private static void createSessionFactory() {
		Configuration cfg = new AnnotationConfiguration();
		if (sessionFactory == null) {
			try {
				sessionFactory = cfg.configure(CONFIG_FILE_LOCATION)
						.buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
