package controllers;

import models.Interes;
import models.Tema;
import models.Usuario;
import play.data.validation.Valid;
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

    public static void add_interes(@Valid Interes interes) {

        flash.clear();
        if (validation.hasErrors()) {

        }



    }

}
