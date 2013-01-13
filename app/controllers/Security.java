package controllers;

import models.Usuario;

public class Security extends Secure.Security {
    static boolean authenticate(String username, String password) {
        return Usuario.connect(username, password) != null;
    }

    static void onDisconnected() {
        Application.index();
    }

    static void onAuthenticated() {
        Usuario loggedUser = Usuario.find("byEmail", connected()).first();
        session.put("alias", loggedUser.username);
        Admin.index();
    }
}
