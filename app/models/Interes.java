package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Interes extends Model {

    public String descripcion;

    @ManyToOne
    public Usuario interesado;

}
