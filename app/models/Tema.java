package models;

import play.Play;
import play.data.validation.Required;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.modules.search.Field;
import play.modules.search.Indexed;
import play.modules.search.Search;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Indexed
public class Tema extends Model {

    @Required(message = "debes poner un título al tema que puedes enseñar")
    @Field
    public String titulo;

    public String descripcion;

    @ManyToOne
    public Usuario experto;

    public static List<Agrupacion> getMasOfertados() {

        int numMasOfertados = Integer.valueOf(Play.configuration.getProperty("application.num_mas_ofertados"));

        Query query = JPA.em().createNativeQuery(
                "select titulo, count(titulo) as repeticion from tema where experto_id is not null group by titulo order by repeticion desc");
        
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

    public static List<Tema> findOfertados(String terms) {
        play.modules.search.Query query = Search.search("titulo:(" + terms + ")", Tema.class);
        return query.fetch();
    }

    public List<Tema> posiblesIntercambios(String emailInteresado) {
        Usuario interesado = Usuario.find("byEmail", emailInteresado).first();

        List<Tema> interesaAExperto = new ArrayList<Tema>(experto.findTemasDeInteres());
        if (interesado != null) {
            List<Tema> ofreceInteresado = interesado.temas;

            interesaAExperto.retainAll(ofreceInteresado);
            return interesaAExperto;
        } else {
            return new ArrayList<Tema>();
        }
    }
}
