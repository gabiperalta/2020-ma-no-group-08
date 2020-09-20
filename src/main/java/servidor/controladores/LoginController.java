package servidor.controladores;

import dominio.cuentasUsuarios.CuentaUsuario;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import servicio.Sesiones.ServicioSesiones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public class LoginController extends Controller{
	
	ServicioSesiones loginService;
	
	public ModelAndView login(Request req, Response res) {
		if (usuarioAutenticado(req) != null)
			res.redirect("/home");

		return new ModelAndView(null, "login.hbs");
		// agregarle password y username
	}

	public Object loguear(Request req, Response res) {
		String password = req.queryParams("password");
		String username = req.queryParams("username");

		// revisar credenciales del repositorio
		CuentaUsuario usuario = new CuentaUsuario();

		req.session().attribute("user", usuario);

		res.redirect("/", 302);

		return null;
	}

	private String usuarioAutenticado(Request req) {
		return req.session().attribute("user");
	}
	
	public String logout(Request req, Response res) {
		req.session().removeAttribute("user");
		res.redirect("/login");
		return "";
	}
}
