package sse.db.pojo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.crite

-- Dumping structure for table j2ee.t_oauth_renren
DROP TABLE IF EXISTS `t_oauth_renren`;
CREATE TABLE IF NOT EXISTS `t_oauth_renren` (
  `token` char(64) NOT NULL,
  `refresh_token` char(64) NOT NULL,
  `account_id` bigint(20) unsigned NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `renren_id` bigint(10) NOT NULL,
  PRIMARY KEY (`token`),
  KEY `ix_name` (`token`),
  KEY `ix_account_id` (`account_id`),
  KEY `ix_create_time` (`create_time`),
  KEY `ix_expire_time` (`expire_time`),
  CONSTRAINT `fk_oauth_renren_account` FOREIGN KEY (`account_id`) REFERENCES `t_account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
rion.Restrictions;

import sse.Spring;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TAccountDAO;
import sse.db.pojo.gen.TOauthQq;
import sse.db.pojo.gen.TOauthSina;

public class AccountUtil extends TAccountDAO {

	private static AccountUtil springInstance = null;

	public static AccountUtil instance() {
		if (springInstance != null)
			return springInstance;
		return springInstance = (AccountUtil) Spring.getBean("AccountUtil");
	}

	public static TOauthSina getTOauthSina(TAccount entity) {
		if (!entity.getProvider().equalsIgnoreCase("sina"))
			return null;
		Set<TOauthSina> tokens = entity.getTOauthSinas();
		if (tokens == null || tokens.isEmpty())
			return null;
		Iterator<TOauthSina> iter = tokens.iterator();
		return iter.next();
	}

	public static TOauthQq getTOauthQq(TAccount entity) {
		if (!entity.getProvider().equalsIgnoreCase("qq"))
			return null;
		Set<TOauthQq> tokens = entity.getTOauthQqs();
		if (tokens == null || tokens.isEmpty())
			return null;
		Iterator<TOauthQq> iter = tokens.iterator();
		return iter.next();
	}

	public TAccount findByProviderExternalId(String provider, String externalId) {
		Session session = getSession();
		@SuppressWarnings("unchecked")
		List<TAccount> results = session.createCriteria(TAccount.class)
			.add(Restrictions.eq(AccountUtil.PROVIDER, provider))
			.add(Restrictions.eq(AccountUtil.EXTERNAL_ID, externalId))
			.list();
		if (results == null || results.isEmpty())
			return null;
		return results.get(0);
	}
}
