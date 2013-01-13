package controllers;

import java.util.List;

import models.Encuentro;
import models.Interes;
import models.Tema;
import models.Usuario;
import notifiers.Mails;
import play.data.validation.Valid;
import play.mvc.Controller;

public class Admin extends Controller {

    public static void index() {
        String username = Security.connected();
        System.out.println(username);
        Usuario user = Usuario.find("byEmail", username).first();

        List<Tema> temasDeInteres = user.findTemasDeInteres();
        render(temasDeInteres);
    }

    public static void add_interes(@Valid Interes interes) {

        flash.clear();
        if (validation.hasErrors()) {

        }



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
    
    public static void finalizarEncuentroOfertante(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario usuario = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(idEncuentro);
    	if (usuario.id==encuentro.tema.experto.id){
    		encuentro.estadoExperto=Encuentro.Estado.Finalizado;
    	}
        render(encuentro);
    }
    
    public static void finalizarEncuentroSolicitante(String idEncuentro) {
    	String userEmail = Security.connected();
    	Usuario usuario = Usuario.find("byEmail", userEmail).first();
    	Encuentro encuentro = Encuentro.findById(idEncuentro);
    	if (usuario.id==encuentro.tema.experto.id){
    		encuentro.estadoInteresado=Encuentro.Estado.Finalizado;
    		Mails.encuentro_cancelado(encuentro);
    	}
        render(encuentro);
    }
    

}
