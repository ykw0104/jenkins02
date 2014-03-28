package ykw.jenkins02.selenium;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ykw.jenkins02.dao.JobDao;
import ykw.jenkins02.entity.Job;

@Service("jobServer")
@Transactional
public class JobServer {
	
	@Autowired
	private JobDao jobDao;
	
	public void saveAllJob(List<Job> jobList){
		for(Job job:jobList){
			jobDao.addOneJob(job);
		}
	}
	

	public JobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}
	
}
