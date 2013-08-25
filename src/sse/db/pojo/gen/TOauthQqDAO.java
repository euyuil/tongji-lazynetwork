package sse.db.pojo.gen;

import java.util.Date;
import java.util.List;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TOauthQq entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see sse.db.pojo.gen.TOauthQq
 * @author MyEclipse Persistence Tools
 */

public class TOauthQqDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TOauthQqDAO.class);
	// property constants
	public static final String TOKEN_SECRET = "tokenSecret";

	protected void initDao() {
		// do nothing
	}

	public void save(TOauthQq transientInstance) {
		log.debug("saving TOauthQq instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TOauthQq persistentInstance) {
		log.debug("deleting TOauthQq instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TOauthQq findById(java.lang.String id) {
		log.debug("getting TOauthQq instance with id: " + id);
		try {
			TOauthQq instance = (TOauthQq) getHibernateTemplate().get(
					"sse.db.pojo.gen.TOauthQq", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TOauthQq> findByExample(TOauthQq instance) {
		log.debug("finding TOauthQq instance by example");
		try {
			List<TOauthQq> results = (List<TOauthQq>) getHibernateTemplate()
					.findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TOauthQq instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TOauthQq as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TOauthQq> findByTokenSecret(Object tokenSecret) {
		return findByProperty(TOKEN_SECRET, tokenSecret);
	}

	public List findAll() {
		log.debug("finding all TOauthQq instances");
		try {
			String queryString = "from TOauthQq";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TOauthQq merge(TOauthQq detachedInstance) {
		log.debug("merging TOauthQq instance");
		try {
			TOauthQq result = (TOauthQq) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TOauthQq instance) {
		log.debug("attaching dirty TOauthQq instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TOauthQq instance) {
		log.debug("attaching clean TOauthQq instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TOauthQqDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TOauthQqDAO) ctx.getBean("TOauthQqDAO");
	}
}