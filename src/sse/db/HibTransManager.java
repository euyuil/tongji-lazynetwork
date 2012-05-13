package sse.db;

import org.springframework.orm.hibernate3.HibernateTransactionManager;

import sse.Spring;

public class HibTransManager {

	private static HibTransManager manager = null;

	private static HibTransManager singleton() {
		if (manager != null)
			return manager;
		return manager = (HibTransManager) Spring.getBean("HibTransManager");
	}

	public static HibernateTransactionManager instance() {
		return singleton().getRealManager();
	}

	private HibernateTransactionManager realManager = null;

	public void setRealManager(HibernateTransactionManager realManager) {
		this.realManager = realManager;
	}

	public HibernateTransactionManager getRealManager() {
		return realManager;
	}
}
