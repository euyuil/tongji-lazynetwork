package sse.db.pojo.gen;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TOauthSina entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see sse.db.pojo.gen.TOauthSina
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings({"unchecked", "rawtypes"})
public class TOauthSinaDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TOauthSinaDAO.class);
	// property constants
	public static final String TOKEN_SECRET = "tokenSecret";

	protected void initDao() {
		// do nothing
	}

	public void save(TOauthSina transientInstance) {
		log.debug("saving TOauthSina instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TOauthSina persistentInstance) {
		log.debug("deleting TOauthSina instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TOauthSina findById(java.lang.String id) {
		log.debug("getting TOauthSina instance with id: " + id);
		try {
			TOauthSina instance = (TOauthSina) getHibernateTemplate().get(
					"sse.db.pojo.gen.TOauthSina", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TOauthSina> findByExample(TOauthSina instance) {
		log.debug("finding TOauthSina instance by example");
		try {
			List<TOauthSina> results = (List<TOauthSina>) getHibernateTemplate()
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
		log.debug("finding TOauthSina instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TOauthSina as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TOauthSina> findByTokenSecret(Object tokenSecret) {
		return findByProperty(TOKEN_SECRET, tokenSecret);
	}

	public List findAll() {
		log.debug("finding all TOauthSina instances");
		try {
			String queryString = "from TOauthSina";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TOauthSina merge(TOauthSina detachedInstance) {
		log.debug("merging TOauthSina instance");
		try {
			TOauthSina result = (TOauthSina) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TOauthSina instance) {
		log.debug("attaching dirty TOauthSina instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TOauthSina instance) {
		log.debug("attaching clean TOauthSina instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TOauthSinaDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TOauthSinaDAO) ctx.getBean("TOauthSinaDAO");
	}
}