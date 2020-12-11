package auditoria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class AuditoriaTest {
    static MongoClient mongoClient;
    static Datastore datastore;

    @BeforeClass
    static public void setUp(){
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://mongodb:0AnE83904LltBfkF@cluster0.8pqel.mongodb.net/operations_audit?retryWrites=true&w=majority");

        mongoClient = new MongoClient(uri);

        Morphia morphia = new Morphia();
        morphia.mapPackage("auditoria");

        datastore = morphia.createDatastore(mongoClient, "TESTS_BD");
        datastore.ensureIndexes();
    }

    @Test
    public void cargarAltaTest(){
        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00001", "Alta", "AUDITORIA_TEST");

        datastore.save(registro);
    }

    @Test
    public void cargarBajaTest(){

        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00001", "Baja", "AUDITORIA_TEST");

        datastore.save(registro);
    }

    @Test
    public void cargarModificacionTest(){

        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00001", "Modificacion", "AUDITORIA_TEST");

        datastore.save(registro);
    }

    @Test
    public void buscarRegistroTest(){
        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00002", "Modificacion", "AUDITORIA_TEST");

        datastore.save(registro);


        List<RegistroDeAuditoria> registros = datastore.createQuery(RegistroDeAuditoria.class)
                .field("id_entidad_auditada")
                .contains("OE-00002")
                .field("operacion")
                .contains("Modificacion")
                .find()
                .toList();
    }
}
