package sse.db.pojo.gen;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * AbstractTUser entity provides the base persistence definition of the TUser
 * entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTUser implements java.io.Serializable {

	// Fields

	private long id;
	private Date createTime;
	private Set<TSession> TSessions = new HashSet<TSession>(0);
	private Set<TAccount> TAccounts = new HashSet<TAccount>(0);

	// Constructors

	/** default constructor */
	public AbstractTUser() {
	}

	/** full constructor */
	public AbstractTUser(Date createTime, Set<TSession> TSessions,
			Set<TAccount> TAccounts) {
		this.createTime = createTime;
		this.TSessions = TSessions;
		this.TAccounts = TAccounts;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "create_time", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	public Set<TSession> getTSessions() {
		return this.TSessions;
	}

	public void setTSessions(Set<TSession> TSessions) {
		this.TSessions = TSessions;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TUser")
	public Set<TAccount> getTAccounts() {
		return this.TAccounts;
	}

	public void setTAccounts(Set<TAccount> TAccounts) {
		this.TAccounts = TAccounts;
	}

}