package models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Query;

import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Model;

@Entity
public class Busqueda extends Model {

    public String texto;

    @ManyToOne
    public Usuario interesado;

    public static List<Agrupacion> getMasBuscados() {

        int numMasBuscados = Integer.valueOf(Play.configuration.getProperty("application.num_mas_buscados"));

        Query query = JPA.em().createNativeQuery(
                "select texto, count(texto) as repeticion from busqueda group by texto order by repeticion desc");

        List<Object[]> results = query.setMaxResults(numMasBuscados).getResultList();
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