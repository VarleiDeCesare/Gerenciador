package br.edu.imed.restjersey;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.imed.model.Tarefa;

@Path("/tarefas")
public class TarefasService {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Projeto");

	@GET
	@Produces("application/json")
	public Response pegartodasTarefas() throws JSONException{
		
		Tarefa t = new Tarefa();
		
		List<Tarefa> tarefas = null;

        EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction =null;

        try {

            transaction = em.getTransaction();
            transaction.begin();

            TypedQuery<Tarefa> tq = em.createQuery("SELECT t FROM tarefa t", Tarefa.class);
            tarefas = tq.getResultList();

            transaction.commit();
        } catch (Exception e) {
        	
            e.printStackTrace();
        }
        finally {
            em.close();
        }
        
        assertNotNull(t);
        JSONObject JsonO = new JSONObject();
        JsonO.put("tarefas",tarefas);
		String resultado = JsonO.toString();
		return Response.status(200).entity(resultado).build();	
    }		
}
		
		
		
		
	