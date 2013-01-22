package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Encuentro;
import models.Tema;
import models.Usuario;
import notifiers.Mails;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {

    @Before
    static void checkAuthentification() {
        if(Security.connected() != null) {
            renderArgs.put("user", Usuario.find("byEmail", Security.connected()).first());
        }
    }

    public static void index() {
        String username = Security.connected();
        System.out.println(username);
        Usuario user = Usuario.find("byEmail", username).first();

        List<Tema> temas = user.findTemasDeInteres();
        render("Application/search.html", temas);
    }
    
    public static void solicitarEncuentro(String idTema) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	if (user.creditos<1){
    		flash.error("No tienes suficientes creditos para solicitar el encuentro.");
    	}
    	Encuentro encuentro = Encuentro.crearEncuentro(Long.valueOf(idTema), user);
    	Mails.solicitud_encuentro(encuentro);
        render(encuentro);
    }
    
    public static void confirmarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Aceptado;
    		encuentro.estadoInteresado=Encuentro.Estado.Aceptado;
    		encuentro.save();
    		user.creditos++;
    		user.save();
    		encuentro.interesado.creditos--;
    		encuentro.interesado.save();
    		Mails.encuentro_aceptado(encuentro);
    	}
    	
    	Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
    	String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#ensenando");
    }
    
    public static void rechazarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Rechazado;
    		encuentro.estadoInteresado=Encuentro.Estado.Rechazado;
    		encuentro.save();
    		Mails.encuentro_rechazado(encuentro);
    	}
    	Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
    	String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#ensenando");
    }
    
    public static void cancelarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.interesado.id){
    		encuentro.estadoExperto=Encuentro.Estado.Cancelado;
    		encuentro.estadoInteresado=Encuentro.Estado.Cancelado;
    		encuentro.save();
    		Mails.encuentro_cancelado(encuentro);
    	}
    	Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
    	String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#aprendiendo");
    }
    
    public static void finalizarEncuentroOfertante(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Finalizado;
    		encuentro.save();
    	}
    	Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
    	String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#ensenando");
    }
    
    public static void finalizarEncuentroSolicitante(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.interesado.id){
    		encuentro.estadoInteresado=Encuentro.Estado.Finalizado;
    		encuentro.save();
    		Mails.encuentro_cancelado(encuentro);
    	}
    	Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
    	String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#aprendiendo");
    }

    public static void puntuarEncuentroOfertante(String idEncuentro, String puntuacion) {
        String userEmail = Security.connected();
        Usuario user = Usuario.find("byEmail", userEmail).first();
        Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
        if (user.id==encuentro.tema.experto.id){
            encuentro.estadoExperto=Encuentro.Estado.Votado;
            encuentro.puntuacionInteresado = Integer.valueOf(puntuacion);
            encuentro.save();
        }
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
        String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#ensenando");
    }

    public static void puntuarEncuentroSolicitante(String idEncuentro, String puntuacion) {
        String userEmail = Security.connected();
        Usuario user = Usuario.find("byEmail", userEmail).first();
        Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
        if (user.id==encuentro.interesado.id){
            encuentro.estadoInteresado=Encuentro.Estado.Votado;
            encuentro.puntuacionExperto = Integer.valueOf(puntuacion);
            encuentro.save();
        }
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
        String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#aprendiendo");
    }

}
