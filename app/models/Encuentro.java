package models;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import play.libs.Codec;

@Entity
public class Encuentro extends Model {

	@ManyToOne
    public Tema tema;

    @ManyToOne
    public Usuario interesado;

	public enum Estado {
		//Solicitado---|--> Aceptado (por el experto) ---> Finalizado --->  Votado  
		//             |--> Rechazado (por el experto) 
		//             |--> Cancelado (por el interesado)
	    Solicitado, Rechazado, Aceptado, Cancelado, Finalizado, Votado;
	    }

	public Estado estadoExperto;
    public Estado estadoInteresado;

    public static Encuentro crearEncuentro(Long idTema, Usuario interesado) {
    	Encuentro encuentro = new Encuentro();
    	encuentro.tema = Tema.findById(idTema);
    	encuentro.interesado = interesado;
    	encuentro.estadoExperto = Estado.Solicitado;
    	encuentro.estadoInteresado = Estado.Solicitado;
    	encuentro.save();
    	return encuentro;
    }
    
}