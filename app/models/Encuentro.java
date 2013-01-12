package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Encuentro extends Model {

	@ManyToOne
    public Tema tema;
	
	@ManyToOne
	public Usuario ofertante;
    
	@ManyToOne
	public Usuario solicitante;

	enum Estado {
		//Solicitado---|--> Aceptado (por el ofertante) ---> Realizado ---> Votado ---> Finalizado
		//             |--> Rechazado (por el ofertante) 
		//             |--> Cancelado (por el solicitante)
	    Solicitado, Rechazado, Aceptado, Cancelado, Realizado, Votado, Finalizado;
	    }
	
	public Estado estadoOfertante;
    public Estado estadoSolicitante;

    public static Encuentro crearEncuentro(Long idTema, Usuario solicitante) {
    	Encuentro encuentro = new Encuentro();
    	encuentro.tema = Tema.findById(idTema);
    	encuentro.ofertante = encuentro.tema.ofertante;
    	encuentro.solicitante = solicitante;
    	
    	encuentro.save();
    	return encuentro;
    }
}