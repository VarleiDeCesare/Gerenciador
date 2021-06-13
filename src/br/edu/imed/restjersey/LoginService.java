package br.edu.imed.restjersey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

import br.edu.imed.model.Usuario;

@Path("/login")
public class LoginService {
	
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
		
		
		List<Usuario> usuarios = null;
		
		Usuario u = new Usuario();
		
		u.setNome(username);
		u.setSenha(senha);
		
		
		try {

            transaction = em.getTransaction();
            transaction.begin();

            TypedQuery<Usuario> tq = em.createQuery("SELECT u FROM usuario u", Usuario.class);
            usuarios = tq.getResultList();
            transaction.commit();
            
            ArrayList<String> nomes = new ArrayList<String>();
            ArrayList<String> senhas = new ArrayList<String>();
            
            for(int i = 0; i < usuarios.size(); i++) {
            	senhas.add(usuarios.get(i).getSenha());
            }
            for(int i = 0; i < usuarios.size(); i++) {
            	nomes.add(usuarios.get(i).getNome()); 
            }
            
            if(senhas.contains(u.getSenha()) && nomes.contains(u.getNome())){
            	servletResponse.sendRedirect("http://localhost:8080/Projeto/RegistroTarefas.html");
            }else {
            	servletResponse.sendRedirect("http://localhost:8080/Projeto/404.html");
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
		
	}
	

}
