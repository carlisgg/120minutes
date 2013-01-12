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
		//Para los ofertantes las transiciones serán: EnCurso -> Realizado -> Finalizado;
		//Para los solicitantes las transiciones serán: EnCurso -> Realizado -> Votado -> Finalizado;
	    EnCurso, Realizado, Votado, Finalizado;
	}
	
	public Estado estadoExperto;
    public Estado estadoInteresado;

}