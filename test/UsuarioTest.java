import models.Usuario;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class UsuarioTest extends UnitTest {

    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("data.yml");
    }

    @Test
    public void obtenerUnUsuarioDevuelveLosDatosDelUsuario() {
        Usuario usuario = Usuario.getUsuarioByName("Juancho");

        assertNotNull(usuario);
        assertEquals("Juancho",usuario.username);
        assertEquals("Soy Juancho y me encantan el html, los apalabrados, la fotografía y los cocteles. Vivo en a coruña pero me desplazo todas las semanas a lugo",usuario.descripcion);
        assertEquals(Long.valueOf(2L),usuario.creditos);
        assertEquals(2,usuario.temas.size());
        assertEquals(3,usuario.intereses.size());
        assertEquals(1,usuario.findEncuentrosOfrecidos().size());

    }
}
