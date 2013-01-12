package controllers;

import models.Usuario;
import play.mvc.Controller;

public class User extends Controller {

    public static void ficha(String id) {
        Usuario user = Usuario.findById(Long.valueOf(id));

        render(user);
    }

}
