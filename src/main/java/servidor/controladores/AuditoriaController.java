package servidor.controladores;

import auditoria.RegistroDeAuditoria;
import auditoria.RepoAuditorias;
import com.google.gson.JsonObject;
import com.mercadolibre.json.Json;
import servidor.Router;
import spark.Request;
import spark.Response;

import java.util.List;
import java.util.stream.Collectors;

public class AuditoriaController {

    public Object showAuditorias(Request request, Response response){
        String identificadorEntidadAuditada = request.queryParams("entidad_auditada");
        String operacionRealizada = request.queryParams("operacion");

        RepoAuditorias repoAuditorias = new RepoAuditorias(Router.getDatastore());

        List<RegistroDeAuditoria> registros = repoAuditorias.buscarRegistros(identificadorEntidadAuditada, operacionRealizada);

        if( registros.size() == 0 ) {
            response.status(404);
            return "Licitacion no encontrada";
        }

        List<JsonObject> resultado = registros.stream().map(registro -> registro.toJsonObject()).collect(Collectors.toList());

        response.type("application/json");
        response.status(200);

        return resultado;
    }

}
