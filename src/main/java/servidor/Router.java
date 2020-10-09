package servidor;
import dominio.operaciones.RepoOperacionesEgreso;
import dominio.operaciones.RepoOperacionesIngreso;
import servidor.controladores.*;

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
	public static void init() throws Exception {
		Router.initEngine();
		String projectDir = System.getProperty("user.dir");
		String staticDir = "/src/main/resources/public";
		Spark.externalStaticFileLocation(projectDir + staticDir);
		Router.configure();
	}

	public static void configure() throws Exception {
		LoginController loginc = new LoginController();
		HomeController homec = new HomeController();
		LicitacionController licitacionc = new LicitacionController();
		VinculacionesController vinculacionesC = new VinculacionesController();
		EgresoController egresoC = new EgresoController();
		CategorizacionesController categorizacionesC = new CategorizacionesController();



		get("/login", loginc::login, engine);
		post("/login", loginc::loguear);
		get("/logout", loginc::logout);
		get("/", (request, response) -> { return "hola";});
		get("/home", homec::showHomePage, engine);
		get("/presupuestos",licitacionc::mostrarPresupuestos,engine);
		post("/presupuesto",licitacionc::agregarPresupuesto);
		post("/licitacion",licitacionc::realizarLicitacion);
		get("/licitacion",licitacionc::obtenerLicitacionPorEgreso);
		get("/licitacion/:licitacion_id",licitacionc::resultadoLicitacion,licitacionc.getGson()::toJson);
		get("/egreso", egresoC::showEgreso, engine);
		post("/egreso", egresoC::crearEgreso, engine);
		get("/egresos", egresoC::mostrarEgresos, engine);

		get("/categorizar", categorizacionesC::showCategorizacionesPage, engine);
		post("/categorizar", categorizacionesC::Categorizar);

		//get("/archivo",licitacionc::agregarArchivo,engine);
		get("/vinculaciones",vinculacionesC::seleccionarOperaciones,engine);
		post("/vinculaciones",vinculacionesC::vincular);


		/*before("/*", (request, response) -> {
			if (isProtected(request.uri()) && request.session().attribute("user") == null) {
				response.redirect("/login", 302);
			}
		});*/

		//request.session().attribute("user");
	}

	private static boolean isProtected(String uri) {
		ArrayList<String> urlNoProtegidas = new ArrayList<>();
		urlNoProtegidas.add("/login");
		urlNoProtegidas.add("/licitacion");

		return urlNoProtegidas.stream().noneMatch(uri::startsWith);
	}

}
