package sse.db.pojo.gen;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TOauthSina entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_oauth_sina", catalog = "j2ee")
public class TOauthSina implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3026551687616024787L;
	private String token;
	private TAccount TAccount;
	private String tokenSecret;
	private Timestamp createTime;
	private Timestamp expireTime;

	// Constructors

	/** default constructor */
	public TOauthSina() {
	}

	/** minimal constructor */
	public TOauthSina(String token, TAccount TAccount, String tokenSecret) {
		this.token = token;
		this.TAccount = TAccount;
		this.tokenSecret = tokenSecret;
	}

	/** full constructor */
	public TOauthSina(String token, TAccount TAccount, String tokenSecret,
			Timestamp createTime, Timestamp expireTime) {
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
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "expire_time", length = 19)
	public Timestamp getExpireTime() {
		return this.expireTime;
	}

	public void setExpireTime(Timestamp expireTime) {
		this.expireTime = expireTime;
	}

}