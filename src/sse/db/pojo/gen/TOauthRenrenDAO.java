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
 * TOauthRenren entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see sse.db.pojo.gen.TOauthRenren
 * @author MyEclipse Persistence Tools
 */

public class TOauthRenrenDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TOauthRenrenDAO.class);
	// property constants
	public static final String REFRESH_TOKEN = "refreshToken";

	protected void initDao() {
		// do nothing
	}

	public void save(TOauthRenren transientInstance) {
		log.debug("saving TOauthRenren instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TOauthRenren persistentInstance) {
		log.debug("deleting TOauthRenren instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TOauthRenren findById(java.lang.String id) {
		log.debug("getting TOauthRenren instance with id: " + id);
		try {
			TOauthRenren instance = (TOauthRenren) getHibernateTemplate().get(
					"sse.db.pojo.gen.TOauthRenren", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TOauthRenren> findByExample(TOauthRenren instance) {
		log.debug("finding TOauthRenren instance by example");
		try {
			List<TOauthRenren> results = (List<TOauthRenren>) getHibernateTemplate()
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
		log.debug("finding TOauthRenren instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TOauthRenren as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TOauthRenren> findByRefreshToken(Object refreshToken) {
		return findByProperty(REFRESH_TOKEN, refreshToken);
	}

	public List findAll() {
		log.debug("finding all TOauthRenren instances");
		try {
			String queryString = "from TOauthRenren";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TOauthRenren merge(TOauthRenren detachedInstance) {
		log.debug("merging TOauthRenren instance");
		try {
			TOauthRenren result = (TOauthRenren) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TOauthRenren instance) {
		log.debug("attaching dirty TOauthRenren instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TOauthRenren instance) {
		log.debug("attaching clean TOauthRenren instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TOauthRenrenDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TOauthRenrenDAO) ctx.getBean("TOauthRenrenDAO");
	}
}