package sse.db.pojo.gen;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTOauthQq entity provides the base persistence definition of the
 * TOauthQq entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTOauthQq implements java.io.Serializable {

	// Fields

	private String token;
	private TAccount TAccount;
	private String tokenSecret;
	private Date createTime;
	private Date expireTime;

	// Constructors

	/** default constructor */
	public AbstractTOauthQq() {
	}

	/** minimal constructor */
	public AbstractTOauthQq(String token, TAccount TAccount, String tokenSecret) {
		this.token = token;
		this.TAccount = TAccount;
		this.tokenSecret = tokenSecret;
	}

	/** full constructor */
	public AbstractTOauthQq(String token, TAccount TAccount,
			String tokenSecret, Date createTime, Date expireTime) {
		this.token = token;
		this.TAccount = TAccount;
		this.tokenSecret = tokenSecret;
		this.createTime = createTime;
		this.expireTime = expireTime;
	}

	// Property accessors
	@Id
	@Column(name = "token", unique = true, nullable = false, length = 64)
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

	@Column(name = "token_secret", nullable = false, length = 64)
	public String getTokenSecret() {
		return this.tokenSecret;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
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

}