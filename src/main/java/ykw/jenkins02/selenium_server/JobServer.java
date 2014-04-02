package ykw.jenkins02.selenium_server;

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
	
	public List<Job> findJobsByView(String view,int envTypeId){
		return jobDao.findJobsByView(view, envTypeId);
	}
	
	/**
	 * 保存所有job的相关信息
	 * @param jobList job信息集合
	 */
	public void saveAllJob(List<Job> jobList){
		for(Job job:jobList){
			jobDao.addOneJob(job);
		}
	}

	/**
	 * 更新所有job的相关信息
	 * @param jobList job信息集合
	 */
	public void updateAllJob(List<Job> jobList){
		for(Job job:jobList){
			jobDao.updateOneJob(job);
		}
	}
	
	/**
	 * 更新mvn命令
	 * @param mvnCmd maven命令
	 */
	public void updateAllJobMvnCmd(String mvnCmd,int envTypeId,String  jenkinsView){
		List<Job> jobList = jobDao.findJobsByView(jenkinsView, envTypeId); //查询获取job,返回集合
		for(Job job:jobList){
			job.setMvnCmd(mvnCmd);
			jobDao.updateOneJob(job);
		}
	}
	
	/**
	 * Just for test sth.
	 */
	public void test(){
		System.out.println(jobDao.findJobsByView("Dependencies", 9));
	}

	public JobDao getJobDao() {
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}
	
}
