package models;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Encuentro extends Model {

	@ManyToOne
    public Tema tema;

    @ManyToOne
    public Usuario interesado;

	enum Estado {
		//Solicitado---|--> Aceptado (por el ofertante) ---> Realizado ---> Votado ---> Finalizado
		//             |--> Rechazado (por el ofertante) 
		//             |--> Cancelado (por el solicitante)
	    Solicitado, Rechazado, Aceptado, Cancelado, Realizado, Votado, Finalizado;
	    }

	public Estado estadoExperto;
    public Estado estadoInteresado;

    public static Encuentro crearEncuentro(Long idTema, Usuario interesado) {
    	Encuentro encuentro = new Encuentro();
    	encuentro.tema = Tema.findById(idTema);
    	encuentro.interesado = interesado;
    	
    	encuentro.save();
    	return encuentro;
    }
}