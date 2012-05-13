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

	/**
	 * 
	 */
	private static final long serialVersionUID = 8848495429442313461L;
	private Long id;
	private TUser TUser;
	private String provider;
	private Long externalId;
	private Timestamp createTime;
	private Set<TOauthSina> TOauthSinas = new HashSet<TOauthSina>(0);

	// Constructors

	/** default constructor */
	public TAccount() {
	}

	/** minimal constructor */
	public TAccount(String provider, Long externalId) {
		this.provider = provider;
		this.externalId = externalId;
	}

	/** full constructor */
	public TAccount(TUser TUser, String provider, Long externalId,
			Timestamp createTime, Set<TOauthSina> TOauthSinas) {
		this.TUser = TUser;
		this.provider = provider;
		this.externalId = externalId;
		this.createTime = createTime;
		this.TOauthSinas = TOauthSinas;
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

	@ManyToOne(fetch = FetchType.EAGER)
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

	@Column(name = "external_id", nullable = false)
	public Long getExternalId() {
		return this.externalId;
	}

	public void setExternalId(Long externalId) {
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

}