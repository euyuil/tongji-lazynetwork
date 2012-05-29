package sse.db.pojo;

import java.util.Iterator;
import java.util.Set;

import sse.Spring;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthQq;
import sse.db.pojo.gen.TOauthQqDAO;

public class OauthQqUtil extends TOauthQqDAO {

	private static OauthQqUtil springInstance = null;

	public static OauthQqUtil instance() {
		if (springInstance != null)
			return springInstance;
		return springInstance = (OauthQqUtil) Spring.getBean("OauthQqUtil");
	}

	public TOauthQq findBestByAccount(TAccount account) {
		Set<TOauthQq> results = account.getTOauthQqs();
		if (results == null || results.isEmpty())
			return null;
		Iterator<TOauthQq> iter = results.iterator();
		return iter.next();
	}
}
