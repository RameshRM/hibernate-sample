package com.mahadevanr.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class AppContext {

	private AppContext() {

	}

	private static AppContext _appContext = new AppContext();

	private Session context() {
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		return session;

	}

	public synchronized static Session getContext() {
		return _appContext.context();
	}

}
