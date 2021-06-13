package br.edu.imed.restjersey;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.edu.imed.model.Tarefa;

@Path("/delete")
public class DeleteTaskService {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Projeto");
	
	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
	
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void deletar(@FormParam("id") String id,
			@Context HttpServletResponse servletResponse
			) throws IOException {
		
		Response response = null;
		EntityTransaction transaction = null;
		
	        Tarefa t = null;

	        try {
	            transaction = em.getTransaction();
	            transaction.begin();

	            t = em.createQuery("SELECT t FROM tarefa t WHERE t.id ="+id, Tarefa.class).getSingleResult();
	            em.remove(t);

	            transaction.commit();
	            servletResponse.sendRedirect("http://localhost:8080/Projeto/tarefas.html");
	        } catch (Exception e) {

	            if (transaction != null) {
	                transaction.rollback();
	            }

	            e.printStackTrace();

	        } finally {
	            em.close();
	        }
	    }
	}

