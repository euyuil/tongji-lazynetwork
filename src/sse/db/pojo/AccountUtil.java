package sse.db.pojo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import sse.Spring;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TAccountDAO;
import sse.db.pojo.gen.TOauthQq;
import sse.db.pojo.gen.TOauthSina;
import sse.provider.IHandler;

public class AccountUtil extends TAccountDAO {

	private static AccountUtil springInstance = null;

	public static AccountUtil instance() {
		if (springInstance != null)
			return springInstance;
		return springInstance = (AccountUtil) Spring.getBean("AccountUtil");
	}

	public static IHandler buildAccountFromEntity(TAccount accountEntity) {
		IHandler account = null;
		try {
			if ("sina".equalsIgnoreCase(accountEntity.getProvider())) {
				account = new sse.provider.sina.Handler(accountEntity);
				// build sina account.
			} else if ("qq".equalsIgnoreCase(accountEntity.getProvider())) {
				account = new sse.provider.qq.Handler(accountEntity);
			}
		} catch (Exception e) {
			return null;
		}
		return account;
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
