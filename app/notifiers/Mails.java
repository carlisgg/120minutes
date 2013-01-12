package notifiers;

import models.Usuario;
import play.mvc.Mailer;

public class Mails extends Mailer {

    public static void confirm(Usuario user) {
        setSubject("Bienvenido %s, confirma tu nueva cuenta de usuario", user.username);
        addRecipient(user.email);
        setFrom("120minutes <carlisgg@gmail.com>");

        send(user);
    }

    public static void send_password(Usuario user, String newPassword) {
        setSubject("Contraseña reiniciada para el usuario %s", user.username);
        addRecipient(user.email);
        setFrom("120minutes <carlisgg@gmail.com>");
        send(user, newPassword);
    }
}
