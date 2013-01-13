package controllers;

import models.Busqueda;
import models.Tema;
import models.Usuario;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;

import java.util.HashMap;
import java.util.Map;

@With(Secure.class)
public class User extends Controller {

    public static void ficha(Long id) {
        Usuario user;
        if (id != null) {
            user = Usuario.findById(Long.valueOf(id));
        } else {
            String userEmail = Security.connected();
            user = Usuario.find("byEmail", userEmail).first();
        }

        render(user);
    }

    public static void add_interes(Busqueda busqueda) {

        String userEmail = Security.connected();
        Usuario user = Usuario.find("byEmail", userEmail).first();

        flash.clear();

        if (validation.hasErrors()) {
            params.flash();
            flash.keep();
            validation.keep();
        } else {
            busqueda.interesado = user;
            busqueda.save();
        }

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
        String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#quiero");
    }

    public static void add_tema(@Valid Tema tema) {

        String userEmail = Security.connected();
        Usuario user = Usuario.find("byEmail", userEmail).first();

        flash.clear();

        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            flash.keep();
        } else {
            tema.experto = user;
            tema.save();
        }

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", user.id);
        String url = Router.getFullUrl("User.ficha", args);
        redirect(url + "#puedo");
    }

}