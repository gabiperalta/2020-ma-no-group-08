package servidor;
import servidor.controladores.HomeController;
import servidor.controladores.LicitacionController;
import servidor.controladores.LoginController;
import spark.ModelAndView;
import spark.Spark;
import spark.TemplateEngine;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

import java.util.ArrayList;
import java.util.HashMap;

import static spark.Spark.*;

public class Router {

	private static HandlebarsTemplateEngine engine;

	private static void initEngine() {
		Router.engine = HandlebarsTemplateEngineBuilder
			.create()
			.withDefaultHelpers()
			.withHelper("isTrue", BooleanHelper.isTrue)
			.build();
	}
	public static void init() {
		Router.initEngine();
		String projectDir = System.getProperty("user.dir");
		String staticDir = "/src/main/resources/public";
		Spark.externalStaticFileLocation(projectDir + staticDir);
		Router.configure();
	}

	public static void configure() {
		LoginController loginc = new LoginController();
		HomeController homec = new HomeController();
		LicitacionController licitacionc = new LicitacionController();

		RepositorioUsuarios.getInstance().inicializarClientesParaWeb();

		get("/login", loginc::login, engine);
		post("/login", loginc::loguear);
		get("/logout", loginc::logout);
		get("/", (request, response) -> { return "hola";});
		get("/home", homec::showHomePage, engine);
		get("/presupuestos",licitacionc::mostrarPresupuestos,engine);
		post("/presupuesto",licitacionc::agregarPresupuesto);
		post("/licitacion",licitacionc::realizarLicitacion);
		get("/licitacion/:licitacion_id",licitacionc::resultadoLicitacion,licitacionc.getGson()::toJson);

		get("/archivo",licitacionc::agregarArchivo,engine);

		before("/*", (request, response) -> {
			if (isProtected(request.uri()) && request.session().attribute("user") == null) {
				response.redirect("/login", 302);
			}
		});

		//request.session().attribute("user");
	}

	private static boolean isProtected(String uri) {
		ArrayList<String> urlNoProtegidas = new ArrayList<>();
		urlNoProtegidas.add("/login");
		urlNoProtegidas.add("/presupuestos");
		urlNoProtegidas.add("/licitacion");
		urlNoProtegidas.add("/presupuesto");
		urlNoProtegidas.add("/home"); //SOLO PARA PRUEBA

		return urlNoProtegidas.stream().noneMatch(uri::startsWith);
	}

}
