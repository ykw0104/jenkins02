package ykw.jenkins02.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Ssh {
	@Column(name = "SSH_SITE")
	private String sshSite;
	@Column(name = "SSH_SITE_COMMAND")
	private String sshSiteCommand;
	@Column(name = "SSH_SERVER_NAME")
	private String sshServerName;
	@Column(name = "SOURCE_FILES")
	private String sourceFiles;
	@Column(name = "REMOVE_PREFIX")
	private String removePrefix ;
	@Column(name = "REMOTE_DIRECTORY")
	private String remoteDirectory;
	
	public Ssh() {

	}
	
	public Ssh(String sshSite ,String sshSiteCommand ,String sshServerName ,String sourceFiles ,String removePrefix ,String remoteDirectory){
		this.sshSite = sshSite;
		this.sshSiteCommand = sshSiteCommand;
		this.sshServerName = sshServerName;
		this.sourceFiles = sourceFiles;
		this.removePrefix = removePrefix;
		this.remoteDirectory = remoteDirectory;
	}

	public String getSshSite() {
		return sshSite;
	}

	public void setSshSite(String sshSite) {
		this.sshSite = sshSite;
	}

	public String getSshSiteCommand() {
		return sshSiteCommand;
	}

	public void setSshSiteCommand(String sshSiteCommand) {
		this.sshSiteCommand = sshSiteCommand;
	}

	public String getSshServerName() {
		return sshServerName;
	}

	public void setSshServerName(String sshServerName) {
		this.sshServerName = sshServerName;
	}

	public String getSourceFiles() {
		return sourceFiles;
	}

	public void setSourceFiles(String sourceFiles) {
		this.sourceFiles = sourceFiles;
	}

	public String getRemovePrefix() {
		return removePrefix;
	}

	public void setRemovePrefix(String removePrefix) {
		if(removePrefix ==null || removePrefix == ""){
			this.removePrefix = "target";
		}else{
			this.removePrefix = removePrefix;
		}
	}

	public String getRemoteDirectory() {
		return remoteDirectory;
	}

	public void setRemoteDirectory(String remoteDirectory) {
		this.remoteDirectory = remoteDirectory;
	}

	@Override
	public String toString() {
		return sshSite+":"+sshSiteCommand+":"+sshServerName+":"+sourceFiles+":"+removePrefix+':'+remoteDirectory;
	}
}
