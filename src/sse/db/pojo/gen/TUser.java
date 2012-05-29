package sse.db.pojo.gen;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user", catalog = "j2ee")
public class TUser implements java.io.Serializable {

	// Fields

	private Long id;
	private Timestamp createTime;
	private Set<TSession> TSessions = new HashSet<TSession>(0);
	private Set<TAccount> TAccounts = new HashSet<TAccount>(0);

	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** full constructor */
	public TUser(Timestamp createTime, Set<TSession> TSessions,
			Set<TAccount> TAccounts) {
		this.createTime = createTime;
		this.TSessions = TSessions;
		this.TAccounts = TAccounts;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
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