package ykw.jenkins02.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import ykw.jenkins02.entity.Job;
import ykw.jenkins02.selenium.JobSelenium;
import ykw.jenkins02.selenium.JobServer;

@Component("jobAction")
public class JobAction {
	@Autowired
	JobSelenium jobSelenium;
	@Autowired
	JobServer jobServer;
	
	public JobAction() {
	
	}
	
	public void getAllJobInfo(){
		int  envTypeId = 5;//读取的环境
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		jobSelenium =(JobSelenium) ac.getBean("jobSelenium");
		jobServer = (JobServer)ac.getBean("jobServer");
		
		List<String> jobNameList = jobSelenium.getJobNameListByEnvTypeId(envTypeId);
		
		List<Job> jobList = jobSelenium.getAllJobInfo(envTypeId, jobNameList);
		jobServer.saveAllJob(jobList);
	}
	
	public static void main(String[] args) {
		JobAction jobAction = new JobAction();
		jobAction.getAllJobInfo();
	}
}
