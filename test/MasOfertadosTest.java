import java.util.List;

import models.Agrupacion;
import models.Tema;

import org.junit.Before;
import org.junit.Test;

import play.Play;
import play.test.Fixtures;
import play.test.UnitTest;

public class MasOfertadosTest extends UnitTest {

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");
    }


    @Test
    public void obtenerTemasMasOfertadosDevuelveUnaListaDeAgrupaciones() {
        List<Agrupacion> masOfertados = Tema.getMasOfertados();

        assertTrue(masOfertados.size() > 0);
    }

    @Test
    public void obtenerTemasMasOfertadosDevuelveUnaListaDeTemasOrdenadaPorNumeroDeOfertas() {
        List<Agrupacion> masOfertados = Tema.getMasOfertados();

        assertTrue(masOfertados.size() > 1);
        assertEquals("mi segundo tema", masOfertados.get(0).titulo);
        assertEquals(2, masOfertados.get(0).repeticiones);

        assertEquals("mi primer tema", masOfertados.get(1).titulo);
        assertEquals(1, masOfertados.get(1).repeticiones);

    }

    @Test
    public void obtenerTemasMasOfertadosRespetaMaximoNumeroDeConfiguracion() {
    	String value = Play.configuration.getProperty("application.num_mas_ofertados");
        Play.configuration.setProperty("application.num_mas_ofertados", "1");
        List<Agrupacion> masOfertados = Tema.getMasOfertados();

        assertTrue(masOfertados.size() == 1);
        
        Play.configuration.setProperty("application.num_mas_ofertados", value);
    }


}
