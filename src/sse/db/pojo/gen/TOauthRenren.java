package sse.db.pojo.gen;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import sse.db.pojo.OauthEntity;

/**
 * TOauthRenren entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_oauth_renren", catalog = "j2ee")
public class TOauthRenren implements
		java.io.Serializable, OauthEntity {

	// Fields

	private String token;
	private TAccount TAccount;
	private String refreshToken;
	private Date createTime;
	private Date expireTime;

	// Constructors

	/** default constructor */
	public TOauthRenren() {
	}

	/** minimal constructor */
	public TOauthRenren(String token, TAccount TAccount) {
		this.token = token;
		this.TAccount = TAccount;
	}

	/** full constructor */
	public TOauthRenren(String token, TAccount TAccount,
			String refreshToken, Date createTime, Date expireTime) {
		this.token = token;
		this.TAccount = TAccount;
		this.refreshToken = refreshToken;
		this.createTime = createTime;
		this.expireTime = expireTime;
	}

	// Property accessors
	@Id
	@Column(name = "token", unique = true, nullable = false, length = 80)
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	public TAccount getTAccount() {
		return this.TAccount;
	}

	public void setTAccount(TAccount TAccount) {
		this.TAccount = TAccount;
	}

	@Column(name = "refresh_token", length = 80)
	public String getRefreshToken() {
		return this.refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "expire_time", length = 19)
	public Date getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public void setTokenSecret(String toks) {
		// TODO Auto-generated method stub
		
	}

}
