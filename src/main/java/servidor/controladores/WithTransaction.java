package servidor.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface WithTransaction<E> {
    E method(Request req, Response res, EntityManager em);
}