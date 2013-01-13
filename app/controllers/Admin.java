package controllers;

import notifiers.Mails;
import models.Encuentro;
import models.Tema;
import models.Usuario;
import play.mvc.Controller;

public class Admin extends Controller {

    public static void index() {
        render();
    }
    
    public static void solicitarEncuentro(String idTema) {
    	String userEmail = Security.connected();
    	Usuario usuario = Usuario.find("byEmail", userEmail).first();
    	if (usuario.creditos<1){
    		flash.error("No tienes suficientes creditos para solicitar el encuentro.");
    	}
    	Encuentro encuentro = Encuentro.crearEncuentro(Long.valueOf(idTema), usuario);
    	Mails.solicitud_encuentro(encuentro);
        render(encuentro);
    }
    
    public static void confirmarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario usuario = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(idEncuentro);
    	if (usuario.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Aceptado;
    		encuentro.estadoInteresado=Encuentro.Estado.Aceptado;
    		Mails.encuentro_aceptado(encuentro);
    	}
        render(encuentro);
    }
    
    public static void rechazarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario usuario = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(idEncuentro);
    	if (usuario.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Rechazado;
    		encuentro.estadoInteresado=Encuentro.Estado.Rechazado;
    		Mails.encuentro_rechazado(encuentro);
    	}
        render(encuentro);
    }
    
    public static void cancelarEncuentro(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario usuario = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(idEncuentro);
    	if (usuario.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Cancelado;
    		encuentro.estadoInteresado=Encuentro.Estado.Cancelado;
    		Mails.encuentro_cancelado(encuentro);
    	}
        render(encuentro);
    }
    

}
