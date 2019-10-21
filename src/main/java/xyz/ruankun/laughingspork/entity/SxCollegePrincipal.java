package xyz.ruankun.laughingspork.entity;

import javax.persistence.*;

/**
 * 学院负责人
 */
@Entity
@Table(name = "sx_college_principal")
public class SxCollegePrincipal {

	/**
	 * null
	 * default value: null
	 */
	@Id
	@Column(name = "id", nullable = false)
	private Long id;

	/**
	 * 账号
	 * default value: null
	 */
	@Column(name = "account", nullable = false)
	private String account;

	/**
	 * 密码
	 * default value: null
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * 学院
	 * default value: null
	 */
	@Column(name = "college", nullable = false)
	private String college;

	/**
	 * 学院代码
	 * default value: null
	 */
	@Column(name = "college_code", nullable = true)
	private String collegeCode;

	/**
	 * 创建时间
	 * default value: CURRENT_TIMESTAMP
	 */
	@Column(name = "gmt_create", nullable = false)
	private java.util.Date gmtCreate;

	/**
	 * 修改时间
	 * default value: CURRENT_TIMESTAMP
	 */
	@Column(name = "gmt_modified", nullable = false)
	private java.util.Date gmtModified;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAccount() {
		return this.account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCollege() {
		return this.college;
	}
	
	public void setCollege(String college) {
		this.college = college;
	}
	
	public String getCollegeCode() {
		return this.collegeCode;
	}
	
	public void setCollegeCode(String collegeCode) {
		this.collegeCode = collegeCode;
	}
	
	public java.util.Date getGmtCreate() {
		return this.gmtCreate;
	}
	
	public void setGmtCreate(java.util.Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	public java.util.Date getGmtModified() {
		return this.gmtModified;
	}
	
	public void setGmtModified(java.util.Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	@Override
	public String toString() {
		return "SxCollegePrincipal{" +
				"id=" + id +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", college='" + college + '\'' +
				", collegeCode='" + collegeCode + '\'' +
				", gmtCreate=" + gmtCreate +
				", gmtModified=" + gmtModified +
				'}';
	}
}
