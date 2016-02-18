import java.util.List;

import models.Agrupacion;
import models.Busqueda;

import org.junit.Before;
import org.junit.Test;

import play.Play;
import play.test.Fixtures;
import play.test.UnitTest;

public class MasBuscadosTest extends UnitTest {

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");
    }


    @Test
    public void obtenerTemasMasBuscadosDevuelveUnaListaDeAgrupaciones() {
        List<Agrupacion> masBuscados = Busqueda.getMasBuscados();

        assertTrue(masBuscados.size() > 0);
    }

    @Test
    public void obtenerTemasMasBuscadosDevuelveUnaListaDeTemasOrdenadaPorNumeroDeBusquedas() {
        List<Agrupacion> masBuscados = Busqueda.getMasBuscados();

        assertTrue(masBuscados.size() > 1);
        assertEquals("html5", masBuscados.get(0).titulo);
        assertEquals(3, masBuscados.get(0).repeticiones);

        assertEquals("php", masBuscados.get(1).titulo);
        assertEquals(2, masBuscados.get(1).repeticiones);
        
        assertEquals("css3", masBuscados.get(2).titulo);
        assertEquals(1, masBuscados.get(2).repeticiones);

    }

    @Test
    public void obtenerTemasMasBuscadosRespetaMaximoNumeroDeConfiguracion() {
    	String value = Play.configuration.getProperty("application.num_mas_buscados");
        Play.configuration.setProperty("application.num_mas_buscados", "1");
        List<Agrupacion> masBuscados = Busqueda.getMasBuscados();

        assertTrue(masBuscados.size() == 1);
        
        Play.configuration.setProperty("application.num_mas_buscados", value);
    }

}
