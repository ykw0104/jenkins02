package ykw.jenkins02.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="J_JOB")
@NamedQueries({@NamedQuery(name="Job.findSvnUrlByJobName",query="SELECT j.svnUrl FROM Job j WHERE j.name = :name")
						,@NamedQuery(name="Job.findLoginUrlByJobName",
												query="SELECT e.loginUrl "
														+ " FROM Job j JOIN j.envType e "
														+ " WHERE j.name=:name AND e.id=:envTypeId")
						,@NamedQuery(name="Job.findJobByName",
												query="SELECT j"
														+ " FROM Job j JOIN j.envType e "
														+ "WHERE e.id= :envTypeId AND j.name=:name")
						,@NamedQuery(name="Job.findJobsByView",
												query="SELECT j "
														+ "FROM Job j JOIN j.envType e "
														+ "WHERE e.id= :envTypeId AND j.jenkinsView=:jenkinsView")
})
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;
	@Column(nullable = false)
	private String name;
	@Column
	private String svnUrl;
	@Column
	private String mvnCmd;
	@Column
	private String jenkinsView;
	
	@ManyToOne
	@JoinColumn(name="ENVTYPE_ID")
	private EnvType envType;
	
	@Embedded
	private Ssh ssh;
	
	public Job() {

	}
	
	public Job(String name){
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSvnUrl() {
		return svnUrl;
	}

	public void setSvnUrl(String svnUrl) {
		this.svnUrl = svnUrl;
	}

	public String getMvnCmd() {
		return mvnCmd;
	}

	public void setMvnCmd(String mvnCmd) {
		this.mvnCmd = mvnCmd;
	}
	
	@Override
	public String toString() {
		return id+" "+name+" "+" "+svnUrl+" "+mvnCmd+" "+ssh+"\n";
	}

	public EnvType getEnvType() {
		return envType;
	}

	public void setEnvType(EnvType envType) {
		this.envType = envType;
	}

	public Ssh getSsh() {
		return ssh;
	}

	public void setSsh(Ssh ssh) {
		this.ssh = ssh;
	}

	public String getJenkinsView() {
		return jenkinsView;
	}

	public void setJenkinsView(String jenkinsView) {
		this.jenkinsView = jenkinsView;
	}


}
