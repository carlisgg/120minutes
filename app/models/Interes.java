package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Interes extends Model {

    @Required(message = "tienes que describir el tema del que te interesa aprender")
    public String descripcion;

    @ManyToOne
    public Usuario interesado;

}
