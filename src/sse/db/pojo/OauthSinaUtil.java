package sse.db.pojo;

import java.util.Iterator;
import java.util.Set;

import sse.Spring;
import sse.db.pojo.gen.TAccount;
import sse.db.pojo.gen.TOauthSina;
import sse.db.pojo.gen.TOauthSinaDAO;

public class OauthSinaUtil extends TOauthSinaDAO {

	private static OauthSinaUtil springInstance = null;

	public static OauthSinaUtil instance() {
		if (springInstance != null)
			return springInstance;
		return springInstance = (OauthSinaUtil) Spring.getBean("OauthSinaUtil");
	}

	public TOauthSina findBestByAccount(TAccount account) {
		Set<TOauthSina> results = account.getTOauthSinas();
		if (results == null || results.isEmpty())
			return null;
		Iterator<TOauthSina> iter = results.iterator();
		return iter.next();
	}
}
