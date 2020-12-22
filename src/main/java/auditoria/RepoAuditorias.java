package auditoria;

import dev.morphia.Datastore;
import servidor.Router;

import java.util.List;

public class RepoAuditorias {

    Datastore datastore;

    public RepoAuditorias(Datastore unDatastore){
        this.datastore = unDatastore;
    }

    public void registrarAlta(String identificadorEntidad, String nombreUsuario){
        this.nuevoRegistro(identificadorEntidad, nombreUsuario, "alta");
    }

    public void registrarModificacion(String identificadorEntidad, String nombreUsuario){
        this.nuevoRegistro(identificadorEntidad, nombreUsuario, "modificacion");
    }

    public void registrarBaja(String identificadorEntidad, String nombreUsuario){
        this.nuevoRegistro(identificadorEntidad, nombreUsuario, "baja");
    }

    private void nuevoRegistro(String identificadorEntidad, String nombreUsuario, String operacionRealizada){
        RegistroDeAuditoria registro = new RegistroDeAuditoria(identificadorEntidad, operacionRealizada, nombreUsuario);

        datastore.save(registro);
    }

    public List<RegistroDeAuditoria> buscarRegistros(String identificadorEntidad, String operacionRealizada){
        List<RegistroDeAuditoria> registros = datastore.createQuery(RegistroDeAuditoria.class)
                .field("id_entidad_auditada")
                .contains(identificadorEntidad)
                .field("operacion")
                .contains(operacionRealizada)
                .find()
                .toList();

        return registros;
    }
}
