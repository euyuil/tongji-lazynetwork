package sse.db.pojo.gen;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * AbstractTSession entity provides the base persistence definition of the
 * TSession entity. @author MyEclipse Persistence Tools
 */
@MappedSuperclass
public abstract class AbstractTSession implements java.io.Serializable {

	// Fields

	private String id;
	private TUser TUser;

	// Constructors

	/** default constructor */
	public AbstractTSession() {
	}

	/** full constructor */
	public AbstractTSession(String id, TUser TUser) {
		this.id = id;
		this.TUser = TUser;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public TUser getTUser() {
		return this.TUser;
	}

	public void setTUser(TUser TUser) {
		this.TUser = TUser;
	}

}