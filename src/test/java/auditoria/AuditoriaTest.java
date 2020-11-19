package auditoria;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AuditoriaTest {
    MongoClient mongoClient;
    Morphia morphia;
    Datastore datastore;

    @Before
    public void setUp(){
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://mongodb:0AnE83904LltBfkF@cluster0.8pqel.mongodb.net/operations_audit?retryWrites=true&w=majority");

        mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("test");

        morphia = new Morphia();
        morphia.mapPackage("auditoria");

        datastore = morphia.createDatastore(mongoClient, "operations_audit");
        datastore.ensureIndexes();
    }

    @Test
    public void cargarAltaTest(){
//        Datastore datastore = morphia.createDatastore(mongoClient, "operations_audit");
//        datastore.ensureIndexes();

        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00001", "Alta", "Usuario1");

        datastore.save(registro);
    }

    @Test
    public void cargarBajaTest(){
//        Datastore datastore = morphia.createDatastore(mongoClient, "operations_audit");
//        datastore.ensureIndexes();

        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00001", "Baja", "Usuario1");

        datastore.save(registro);
    }

    @Test
    public void cargarModificacionTest(){
//        Datastore datastore = morphia.createDatastore(mongoClient, "operations_audit");
//        datastore.ensureIndexes();

        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00001", "Modificacion", "Usuario1");

        datastore.save(registro);
    }

    @Test
    public void buscarRegistroTest(){
//        Datastore datastore = morphia.createDatastore(mongoClient, "operations_audit");
//        datastore.ensureIndexes();
        RegistroDeAuditoria registro = new RegistroDeAuditoria("OE-00002", "Modificacion", "Usuario1");

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
