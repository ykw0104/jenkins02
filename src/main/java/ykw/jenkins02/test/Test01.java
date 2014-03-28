package ykw.jenkins02.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ykw.jenkins02.dao.JobDao;
import ykw.jenkins02.selenium.JobSelenium;

public class Test01 {
	public static void main(String[] args) {
//		EntityManager manager = Persistence.createEntityManagerFactory("Jenkins-unit").createEntityManager();
//		System.out.println(manager.createQuery("SELECT j  FROM Job j JOIN j.envType e WHERE e.id = 2 ").getResultList());
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
//		JobDao jobDao = (JobDao)ac.getBean("jobDao");
//		jobDao.getEm().getTransaction().begin();
//		jobDao.addOneJob(new Job("ddd"));
		JobSelenium jobSelenium =(JobSelenium) ac.getBean("jobSelenium");
		JobDao jobDao = (JobDao)ac.getBean("jobDao");
//		EntityManager em = Persistence.createEntityManagerFactory("Jenkins-unit").createEntityManager();
		
//		envtypeDao.setManager( Persistence.createEntityManagerFactory("Jenkins-unit").createEntityManager());
//		envtypeDao.getManager().getTransaction().begin();
		
//		em.getTransaction().begin();
//		envtypeDao.addEnvType(new EnvType(new SimpleDateFormat("hh_mm_ss").format(new Date())));
//		em.persist(envType);
//		em.getTransaction().commit();
//		envtypeDao.getManager().getTransaction().commit();
		
//		System.out.println(jobSelenium.getJobNameListByEnvTypeId(3,"View1"));
//		System.out.println(jobDao.findJobByEnvTypeIdAndJobName(3, "ilsp-jt-310").getSsh().getRemovePrefix());
//		System.out.println(jobDao.findSvnUrlByJobName("df"));
//		System.out.println(jobSelenium.getSvnUrl(3, "ilsp-jt-310"));
		System.out.println(jobSelenium.getSsh(3, "ilsp-jt-310"));
	}
}
