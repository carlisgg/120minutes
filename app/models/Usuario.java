package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model {

	@Required
	public String username;
	public String descripcion;
	public Long creditos;
	
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ofertante")
    public List<Tema> ofertados;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "solicitante")
    public List<Tema> solicitados;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "ofertante")
    public List<Encuentro> encuentrosComoOfertante;
    
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "solicitante")
    public List<Encuentro> encuentrosComoSolicitante;

}