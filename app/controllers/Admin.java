package controllers;

import java.util.List;

import models.Encuentro;
import models.Tema;
import models.Usuario;
import notifiers.Mails;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Admin extends Controller {

    public static void index() {
        String username = Security.connected();
        System.out.println(username);
        Usuario user = Usuario.find("byEmail", username).first();

        List<Tema> temasDeInteres = user.findTemasDeInteres();
        render(temasDeInteres);
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
    		Mails.encuentro_aceptado(encuentro);
    	}
        render("User/ficha.html", user);
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
    	render("User/ficha.html", user);
    }
    
    public static void cancelarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Cancelado;
    		encuentro.estadoInteresado=Encuentro.Estado.Cancelado;
    		encuentro.save();
    		Mails.encuentro_cancelado(encuentro);
    	}
    	render("User/ficha.html", user);
    }
    
    public static void finalizarEncuentroOfertante(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Finalizado;
    		encuentro.save();
    	}
    	render("User/ficha.html", user);
    }
    
    public static void finalizarEncuentroSolicitante(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario user = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(Long.valueOf(idEncuentro));
    	if (user.id==encuentro.tema.experto.id){
    		encuentro.estadoInteresado=Encuentro.Estado.Finalizado;
    		encuentro.save();
    		Mails.encuentro_cancelado(encuentro);
    	}
    	render("User/ficha.html", user);
    }

}
