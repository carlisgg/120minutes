package controllers;

import models.Tema;
import models.Usuario;
import play.mvc.Controller;

import java.util.List;

public class Admin extends Controller {

    public static void index() {
        String username = Security.connected();
        System.out.println(username);
        Usuario user = Usuario.find("byEmail", username).first();

        List<Tema> temasDeInteres = user.findTemasDeInteres();
        render(temasDeInteres);
    }

}
