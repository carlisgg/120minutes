package notifiers;

import models.Encuentro;
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
    
    public static void solicitud_encuentro(Encuentro encuentro) {
        setSubject("Solicitud de encuentro del usuario %s", encuentro.interesado.username);
        addRecipient(encuentro.tema.experto.email);
        setFrom("120minutes <carlisgg@gmail.com>");
        send(encuentro);
    }
    
    public static void encuentro_aceptado(Encuentro encuentro) {
        setSubject("Encuentro aceptado");
        addRecipient(encuentro.tema.experto.email);
        addRecipient(encuentro.interesado.email);
        setFrom("120minutes <carlisgg@gmail.com>");
        send(encuentro);
    }
    
    public static void encuentro_rechazado(Encuentro encuentro) {
        setSubject("Encuentro rechazado");
        addRecipient(encuentro.interesado.email);
        setFrom("120minutes <carlisgg@gmail.com>");
        send(encuentro);
    }
    
    public static void encuentro_cancelado(Encuentro encuentro) {
        setSubject("Encuentro cancelado");
        addRecipient(encuentro.tema.experto.email);
        setFrom("120minutes <carlisgg@gmail.com>");
        send(encuentro);
    }
}
