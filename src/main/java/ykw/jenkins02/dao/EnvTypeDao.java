package ykw.jenkins02.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ykw.jenkins02.entity.EnvType;

@Repository("envTypeDao")
public class EnvTypeDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public EnvTypeDao() {

	}

	public List<EnvType> findAllEnvType(){
		return em.createNamedQuery("EvnType.findAllEnvType", EnvType.class).getResultList();
	}
	
	public EnvType findEnvTypeById(int id){
		return em.createNamedQuery("EnvType.findEnvTypeById",  EnvType.class).setParameter("id",id).getSingleResult();
	}

	public void addEnvType(EnvType envType){
		em.persist(envType);
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
