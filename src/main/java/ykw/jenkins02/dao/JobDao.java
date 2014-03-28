package ykw.jenkins02.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ykw.jenkins02.entity.Job;

@Repository("jobDao")
public class JobDao {

	@PersistenceContext
	private EntityManager em;
	
	/**
	 *根据某个环境 获取所有Job
	 */
	@SuppressWarnings("unchecked")
	public List<Job> findAllJobs(int envTypeId){
		return (List<Job>)em.createQuery("SELECT j FROM Job j JOIN j.envType e WHERE e.id= :envId")
				.setParameter("envId", envTypeId).getResultList();
	}
	/**
	 *根据某个环境 和job名  获取Job
	 */
	public Job findJobByEnvTypeIdAndJobName(int envTypeId,String jobName){
		return (Job)em.createNamedQuery("Job.findJob")
				.setParameter("envTypeId", envTypeId).setParameter("name", jobName).getSingleResult();
	}
	
	/**
	 *根据job名获取Subversion路径 
	 */
	public String findSvnUrlByJobName(String jobName){
		return em.createNamedQuery("Job.findSvnUrlByJobName", String.class).setParameter("name", jobName).getSingleResult();
	}
	
	/**
	 * 根据job名和envTypeId获取登录loginUrl
	 */
	public String findLoginUrlByJobName(String jobName,int envTypeId){
		return em.createNamedQuery("Job.findLoginUrlByJobName", String.class).
					setParameter("name", jobName).setParameter("envTypeId", envTypeId).getSingleResult();
	}
	
	/**
	 * 添加job
	 */
	public void addOneJob(Job job){
		em.persist(job);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
