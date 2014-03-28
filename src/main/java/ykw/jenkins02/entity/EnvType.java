package ykw.jenkins02.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "J_ENVTYPE")
@NamedQueries({@NamedQuery(name="EvnType.findAllEnvType",query="SELECT e FROM EnvType e ORDER BY e.id"),
							@NamedQuery(name="EnvType.findEnvTypeById",query="SELECT e FROM EnvType e WHERE e.id = :id")})
public class EnvType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	@Column(name = "ENVTYPE")
	private String envType;
	@Column(name="LOGINURL")
	private String loginUrl;
	@Column(name = "LOGINNAME")
	private String loginName;
	@Column(name = "PASSWORD")
	private String password;
	
	@OneToMany(mappedBy="envType")
	private Set<Job> jobSet;
	
	public EnvType() {

	}
	
	public EnvType(int id,String envType) {
		this.id = id;
		this.envType = envType;
	}
	
	public EnvType(String envType){
		this.envType = envType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnvType() {
		return envType;
	}

	public void setEnvType(String envType) {
		this.envType = envType;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Job> getJobSet() {
		return jobSet;
	}

	public void setJobSet(Set<Job> jobSet) {
		this.jobSet = jobSet;
	}
	
	@Override
	public String toString() {
		return id+" "+envType;
	}
}
