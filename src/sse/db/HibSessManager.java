package sse.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sse.Spring;

public class HibSessManager {

	private static HibSessManager manager = null;

	private static HibSessManager singleton() {
		if (manager != null)
			return manager;
		return manager = (HibSessManager) Spring.getBean("HibSessManager");
	}

	public static Session current() {
		return singleton().getSessionFactory().getCurrentSession();
	}

	private SessionFactory sessionFactory = null;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
