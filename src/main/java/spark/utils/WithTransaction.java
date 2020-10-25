package spark.utils;

import spark.Response;
import spark.Request;

import javax.persistence.EntityManager;

@FunctionalInterface
public interface WithTransaction<E> {
    E method(Request req, Response res, EntityManager em);
}
