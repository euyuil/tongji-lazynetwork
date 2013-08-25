package com.oauth.demo.model;

/**
 * 专门给人人网AccessToken创建了一个model类，也可不建，主要是方便存取
 */

public class AccessToken {

	private int renren_id;

	public int getRenren_id() {
		return renren_id;
	}

	public void setRenren_id(int renrenId) {
		renren_id = renrenId;
	}

	private String access_token;
	private long expires_in;
	private String refresh_token;
	private String scope;
	private String create_time;

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String createTime) {
		create_time = createTime;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String accessToken) {
		access_token = accessToken;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expiresIn) {
		expires_in = expiresIn;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refreshToken) {
		refresh_token = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
