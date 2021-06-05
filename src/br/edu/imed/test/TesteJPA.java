package br.edu.imed.test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import br.edu.imed.model.Usuario;

public class TesteJPA {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Projeto");
	
	@Test
	public void cadastrar() {
		
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		EntityTransaction transaction = null;
		
		try {
			transaction = em.getTransaction();
			transaction.begin();
			
			Usuario u = new Usuario();
			
			u.setNome("Ana Maria");
			
			em.persist(u);
			
			transaction.commit();
			
		} catch (Exception e) {
			
			if(transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}finally {
			
			em.close();
		}
		
	}
}
