package servidor.controladores;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import servicio.Sesiones.ServicioSesiones;
import spark.Request;
import spark.Response;

public class LoginController extends Controller{
	
	ServicioSesiones loginService;
	
	public String loguear(Request req, Response res) {
		if (usuarioAutenticado(req) != null)
			res.redirect("/home");

		String username = req.queryParams("username");
		String password = req.queryParams("password");

		if (username != null) {
			if (loginService.logIn(username, password)) { //Hay error de tipos
				req.session(true);
				req.session().attribute("username", username);
				res.redirect("/calendar");
			} else {
				this.addAttribute("username", username);
				this.addAttribute("errorLogueo", true);
			}
		}

		return this.render("login.hbs");
	}
	
	public String userExists(Request req, Response res) throws JSONException {
		String username = req.params("username");
		JSONObject userJSON = new JSONObject()
				.put("username", username)
				.put("exists", loginService.userExists(username)); //Aca tendriamos que tener algun metodo para chequear si existe el usuario
		return userJSON.toString();
	}

	private String usuarioAutenticado(Request req) {
		return req.session().attribute("username");
	}
	
	public String logout(Request req, Response res) {
		req.session().removeAttribute("username");
		res.redirect("/login");
		return "";
	}
}
