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
        Admin.index();
    }
}
