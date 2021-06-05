package br.edu.imed.restjersey;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONException;

import br.edu.imed.model.Tarefa;

@Path("/tarefas/create")
public class CriarTarefaService {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Projeto");
	
	@Path("{tarefa}")
	@POST
	public void cadastrar(@PathParam("tarefa") String nome){


        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {

            transaction = em.getTransaction();
            transaction.begin();

            Tarefa t = new Tarefa();
      
           
            t.setNome(nome);
            
            
            em.persist(t);

            transaction.commit();

        } catch (Exception e) {

            if (transaction != null) {

                transaction.rollback();

        }

            e.printStackTrace();
        }

        finally {
            em.close();
        }
    }
	
}
