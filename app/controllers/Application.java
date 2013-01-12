package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        List<Agrupacion> masOfertados = Tema.getMasOfertados();
        List<Agrupacion> masBuscados = Busqueda.getMasBuscados();
        
        render(masOfertados,masBuscados);
    }

    public static void search(String terms) {
        System.out.println("TERMS: " + terms);

        List<Tema> temas = Tema.findOfertados(terms);
        render(temas);
    }

}