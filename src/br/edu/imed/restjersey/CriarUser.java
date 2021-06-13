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

import org.json.JSONObject;

import br.edu.imed.model.Tarefa;
import br.edu.imed.model.Usuario;

@Path("/criarUser")
public class CriarUser {

	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Projeto");
	
	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
	
	
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void criarTask(@FormParam("username") String username,
			@FormParam("password") String senha,
			@Context HttpServletResponse servletResponse
			) throws IOException {
		
		
		Response response = null;
		EntityTransaction transaction = null;
		
		
		try {

            transaction = em.getTransaction();
            transaction.begin();
           
            Usuario usuario = new Usuario();
            
            
            usuario.setNome(username);
            usuario.setSenha(senha);
            
            
            JSONObject json = new JSONObject();
            
            json.put("sucesso", true);
            
            response = Response.status(200).entity(json.toString()).build();
            
            em.persist(usuario);

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
		
		servletResponse.sendRedirect("http://localhost:8080/Projeto/RegistroTarefas.html");
		
	}
}
