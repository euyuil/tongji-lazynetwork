package sse.db.pojo.gen;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TSession entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_session", catalog = "j2ee")
public class TSession implements java.io.Serializable {

	// Fields

	private String id;
	private TUser TUser;

	// Constructors

	/** default constructor */
	public TSession() {
	}

	/** full constructor */
	public TSession(String id, TUser TUser) {
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