package sse.db.pojo;

import java.util.Iterator;
import java.util.Set;

import sse.Spring;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthRenren;
import sse.db.pojo.gen.TOauthRenrenDAO;

public class OauthRenrenUtil extends TOauthRenrenDAO {

	private static OauthRenrenUtil springInstance = null;

	public static OauthRenrenUtil instance() {
		if (springInstance != null)
			return springInstance;
		return springInstance = (OauthRenrenUtil) Spring.getBean("OauthRenrenUtil");
	}

	public TOauthRenren findBestByAccount(TAccount account) {
		Set<TOauthRenren> results = account.getTOauthRenrens();
		if (results == null || results.isEmpty())
			return null;
		Iterator<TOauthRenren> iter = results.iterator();
		return iter.next();
	}
}
