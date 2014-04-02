package ykw.jenkins02.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import ykw.jenkins02.entity.Job;
import ykw.jenkins02.selenium_server.JobSelenium;
import ykw.jenkins02.selenium_server.JobServer;

@Component("jobAction")
public class JobAction {
	@Autowired
	JobSelenium jobSelenium;
	@Autowired
	JobServer jobServer;
	
	public JobAction() {
	
	}
	
	/**
	 * 从页面读取job信息并保存job信息
	 * @param envTypeId 环境Id
	 * @param view jenkins视图
	 */
	public void saveAllJobInfo(int envTypeId,String... view){
		//从容器中手动获取
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");  
		jobSelenium =(JobSelenium) ac.getBean("jobSelenium");
		jobServer = (JobServer)ac.getBean("jobServer");
		
		List<String> jobNameList = jobSelenium.getJobNameListByEnvTypeId(envTypeId,view); //页面中获取job名字的集合
		List<Job> jobList = jobSelenium.getAllJobInfo(envTypeId, jobNameList,view); //页面中获取job信息的集合
		
		jobServer.saveAllJob(jobList);
	}
	
	/**
	 * 批量更新maven命令
	 */
	public void updateAllJobMvnCmd(String mvnCmd,int envTypeId,String view){
		//从容器中手动获取
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");  
		jobServer = (JobServer)ac.getBean("jobServer");
		
		jobServer.updateAllJobMvnCmd(mvnCmd, envTypeId, view);
	}
	
	/**
	 * 添加job信息到jenkins
	 * @param readEnvId	从数据库中读取需要的envTypeId
	 * @param writeEnvId 	写入信息需要的envTypeId
	 */
	public void addJobsToJenkins(String view,int readEnvId,int writeEnvId){
		//从容器中手动获取
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");  
		jobSelenium =(JobSelenium) ac.getBean("jobSelenium");
		jobServer = (JobServer)ac.getBean("jobServer");
		
		List<Job> jobList = jobServer.findJobsByView(view,readEnvId );
		jobSelenium.addJobsToJenkins(jobList, writeEnvId, view);
	}
	
	public static void main(String[] args) {
		JobAction jobAction = new JobAction();
//		jobAction.saveAllJobInfo(9,"Dependencies");
		
//		jobAction.updateAllJobMvnCmd("$maven_pt", 9,"Dependencies");
		
//		jobAction.addJobsToJenkins("Dependencies",9,3);
	}
}
