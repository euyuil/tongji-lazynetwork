package sse.db.pojo;

import sse.db.HibSessManager;

public class OauthEntityUtil {

	public static void save(OauthEntity ent) {
		HibSessManager.current().save(ent);
	}
}
