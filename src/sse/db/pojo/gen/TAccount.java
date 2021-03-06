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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TAccount entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_account", catalog = "j2ee", uniqueConstraints = @UniqueConstraint(columnNames = {
		"provider", "external_id" }))
public class TAccount implements java.io.Serializable {

	// Fields

	private Long id;
	private TUser TUser;
	private String provider;
	private String externalId;
	private Timestamp createTime;
	private Set<TOauthSina> TOauthSinas = new HashSet<TOauthSina>(0);
	private Set<TOauthQq> TOauthQqs = new HashSet<TOauthQq>(0);
	private Set<TOauthRenren> TOauthRenrens = new HashSet<TOauthRenren>(0);

	// Constructors

	/** default constructor */
	public TAccount() {
	}

	/** minimal constructor */
	public TAccount(String provider, String externalId) {
		this.provider = provider;
		this.externalId = externalId;
	}

	/** full constructor */
	public TAccount(TUser TUser, String provider, String externalId,
			Timestamp createTime, Set<TOauthSina> TOauthSinas,
			Set<TOauthQq> TOauthQqs, Set<TOauthRenren> TOauthRenrens) {
		this.TUser = TUser;
		this.provider = provider;
		this.externalId = externalId;
		this.createTime = createTime;
		this.TOauthSinas = TOauthSinas;
		this.TOauthQqs = TOauthQqs;
		this.TOauthRenrens = TOauthRenrens;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

	@Column(name = "provider", nullable = false, length = 8)
	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Column(name = "external_id", nullable = false, length = 64)
	public String getExternalId() {
		return this.externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@Column(name = "create_time", length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")
	public Set<TOauthSina> getTOauthSinas() {
		return this.TOauthSinas;
	}

	public void setTOauthSinas(Set<TOauthSina> TOauthSinas) {
		this.TOauthSinas = TOauthSinas;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")
	public Set<TOauthQq> getTOauthQqs() {
		return this.TOauthQqs;
	}

	public void setTOauthQqs(Set<TOauthQq> TOauthQqs) {
		this.TOauthQqs = TOauthQqs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TAccount")
	public Set<TOauthRenren> getTOauthRenrens() {
		return this.TOauthRenrens;
	}

	public void setTOauthRenrens(Set<TOauthRenren> TOauthRenrens) {
		this.TOauthRenrens = TOauthRenrens;
	}

}