package models;

import play.Play;
import play.data.validation.Required;
import play.db.jpa.JPA;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tema extends Model {

    @Required
    public String titulo;

    public String descripcion;

    @ManyToOne
    public Usuario ofertante;
    
    @ManyToOne
    public Usuario solicitante;

    public static List<Agrupacion> getMasOfertados() {

        int numMasOfertados = Integer.valueOf(Play.configuration.getProperty("application.num_mas_ofertados"));

        Query query = JPA.em().createNativeQuery(
                "select titulo, count(titulo) as repeticion from tema where ofertante_id is not null group by titulo order by repeticion desc");
        
        List<Object[]> results = query.setMaxResults(numMasOfertados).getResultList();
        List<Agrupacion> agrupaciones = new ArrayList<Agrupacion>();

        for (Object[] result : results) {
            Agrupacion agrupacion = new Agrupacion();
            agrupacion.titulo = (String) result[0];
            agrupacion.repeticiones = ((BigInteger) result[1]).longValue();
            agrupaciones.add(agrupacion);
        }

        return agrupaciones;
    }
}
