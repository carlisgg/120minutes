import models.Agrupacion;
import models.Tema;
import org.junit.Before;
import org.junit.Test;
import play.Play;
import play.modules.search.Search;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

public class BusquedasTest extends UnitTest {

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");

        try {
            Search.rebuildAllIndexes();
        } catch (Exception e) {
            fail("Cannot rebuild search index");
        }
    }


    @Test
    public void buscarPorUnTerminoDevuelveTodosLosQueLoIncluyenEnTitulo() throws Exception {
        List<Tema> resultado = Tema.findOfertados("tema");

        assertTrue(resultado.size() > 1);
    }

    @Test
    public void buscarPorTerminosQueNoCoincidenNoDevuelveNada() {
        List<Tema> resultado = Tema.findOfertados("tu rollo");

        assertEquals(0, resultado.size());
    }

    @Test
    public void salenPrimeroLosTemasMasRelevantes() throws Exception {
        List<Tema> resultado = Tema.findOfertados("segundo tema");

        assertTrue(resultado.size() > 1);
        Tema primerResultado = resultado.get(0);
        assertEquals("mi segundo tema", primerResultado.titulo);

        Tema segundoResultado = resultado.get(1);
        assertEquals("mi segundo tema", segundoResultado.titulo);

        Tema tercerResultado = resultado.get(2);
        assertEquals("mi primer tema", tercerResultado.titulo);
    }
}
