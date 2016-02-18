import models.Encuentro;
import models.Tema;
import models.Usuario;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class EncuentroTest extends UnitTest {

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");
    }

    @Test
    public void crearUnEncuentroGuardaCorrectamenteElEncuentro() {
    	Tema tema = (Tema) Tema.findAll().get(0);
    	Usuario usuario = Usuario.find("byUsername", "usuario2").first();
    	Encuentro encuentro = Encuentro.crearEncuentro(tema.id, usuario);

        assertNotNull(encuentro);
        assertEquals(encuentro,Encuentro.findById(encuentro.id));
        assertEquals(tema, encuentro.tema);
        assertEquals(usuario, encuentro.interesado);
    }
}
