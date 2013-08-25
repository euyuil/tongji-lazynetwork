package sse.db.pojo;

import sse.db.pojo.gen.TAccount;

public interface OauthEntity {

	public void setTAccount(TAccount acc);
	public void setToken(String tok);
	public void setTokenSecret(String toks);
	public void setRefreshToken(String rft);
}
