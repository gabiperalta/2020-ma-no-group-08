package auditoria;

import com.google.gson.JsonObject;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class RegistroDeAuditoria {
    @Id
    private String id;
    @Property("id_entidad_auditada")
    private String idEntidadAuditada;
    private String operacion;
    private String nombreUsuario;
    private LocalDateTime fecha;

    public RegistroDeAuditoria(){}

    public RegistroDeAuditoria(String idEntidadAuditada, String operacion, String nombreUsuario) {
        this.idEntidadAuditada = idEntidadAuditada;
        this.operacion = operacion;
        this.nombreUsuario = nombreUsuario;
        this.fecha = LocalDateTime.now();
    }

    public JsonObject toJsonObject(){
        JsonObject jsonObject= new JsonObject();
        jsonObject.addProperty("id_entidad_auditada",idEntidadAuditada);
        jsonObject.addProperty("operacion",operacion);
        jsonObject.addProperty("nombre_usuario",nombreUsuario);
        jsonObject.addProperty("fecha",fecha.toString());

        return jsonObject;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEntidadAuditada() {
        return idEntidadAuditada;
    }

    public void setIdEntidadAuditada(String idEntidadAuditada) {
        this.idEntidadAuditada = idEntidadAuditada;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
