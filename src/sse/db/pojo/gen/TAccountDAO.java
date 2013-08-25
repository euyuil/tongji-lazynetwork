package sse.db.pojo.gen;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TAccount entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see sse.db.pojo.gen.TAccount
 * @author MyEclipse Persistence Tools
 */

public class TAccountDAO extends HibernateDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(TAccountDAO.class);
	// property constants
	public static final String PROVIDER = "provider";
	public static final String EXTERNAL_ID = "externalId";

	protected void initDao() {
		// do nothing
	}

	public void save(TAccount transientInstance) {
		log.debug("saving TAccount instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TAccount persistentInstance) {
		log.debug("deleting TAccount instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TAccount findById(Long id) {
		log.debug("getting TAccount instance with id: " + id);
		try {
			TAccount instance = (TAccount) getHibernateTemplate().get(
					"sse.db.pojo.gen.TAccount", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TAccount> findByExample(TAccount instance) {
		log.debug("finding TAccount instance by example");
		try {
			List<TAccount> results = (List<TAccount>) getHibernateTemplate()
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
		log.debug("finding TAccount instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TAccount as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List<TAccount> findByProvider(Object provider) {
		return findByProperty(PROVIDER, provider);
	}

	public List<TAccount> findByExternalId(Object externalId) {
		return findByProperty(EXTERNAL_ID, externalId);
	}

	public List findAll() {
		log.debug("finding all TAccount instances");
		try {
			String queryString = "from TAccount";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TAccount merge(TAccount detachedInstance) {
		log.debug("merging TAccount instance");
		try {
			TAccount result = (TAccount) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TAccount instance) {
		log.debug("attaching dirty TAccount instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TAccount instance) {
		log.debug("attaching clean TAccount instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TAccountDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TAccountDAO) ctx.getBean("TAccountDAO");
	}
}