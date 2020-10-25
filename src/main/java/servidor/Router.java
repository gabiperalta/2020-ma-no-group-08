package servidor;
import dominio.cuentasUsuarios.CuentaUsuario;
import mock.ServerDataMock;
import servidor.controladores.*;
import servicio.*;

import spark.ModelAndView;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;

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
		IngresoController ingresoC = new IngresoController();
		ServiceMercadoLibre serviceMeli = ServiceMercadoLibre.INSTANCE;

		CategorizacionesController categorizacionesC = new CategorizacionesController();



		get("/login", loginc::login, engine);
		post("/login", loginc::loguear);
		get("/logout", loginc::logout);
		get("/", homec::showHomePage, engine);
		get("/presupuestos",licitacionc::mostrarPresupuestos,engine);
		//post("/presupuesto",licitacionc::agregarPresupuesto);
		post("/presupuesto",RouteWithTransaction(licitacionc::agregarPresupuesto));
		post("/licitacion",licitacionc::realizarLicitacion);
		//get("/licitacion",licitacionc::obtenerLicitacionPorEgreso);
		get("/licitacion",RouteWithTransaction(licitacionc::obtenerLicitacionPorEgreso));
		get("/licitacion/:licitacion_id",RouteWithTransaction(licitacionc::resultadoLicitacion),licitacionc.getGson()::toJson);
		get("/egreso", egresoC::showEgreso, engine);
		post("/egreso", egresoC::crearEgreso, engine);
		get("/egreso/:id", egresoC::showModificarEgreso, engine);
		post("/egreso/:id", egresoC::modificarEgreso, engine);
		get("/egresos/:egreso", egresoC::showEgreso, engine);
		delete("/egreso/:identificador", egresoC::deleteEgreso);

		get("/egresos", egresoC::mostrarEgresos, engine);

		get("/ingreso", ingresoC::showIngreso, engine);
		get("/ingresos", ingresoC::mostrarIngresos, engine);



		get("/categorizar", categorizacionesC::showCategorizacionesPage, engine);
		post("/categorizar", categorizacionesC::categorizar, engine);
		get("/descategorizar", categorizacionesC::showDescategorizacionesPage, engine);
		post("/descategorizar", categorizacionesC::descategorizar, engine);
		
		get("/vinculaciones",vinculacionesC::seleccionarOperaciones,engine);
		post("/vinculaciones",vinculacionesC::vincular);


		before("/*", (request, response) -> {
			CuentaUsuario cuentaUsuario = request.session().attribute("user");
			if(request.uri().startsWith("/licitacion") && cuentaUsuario == null){
				halt(401,"Debe loguearse");
			}
			else if (isProtected(request.uri()) && cuentaUsuario == null) {
				response.redirect("/login", 302);
			}else if(isProtectedFromAdmin(request.uri()) && cuentaUsuario!=null && cuentaUsuario.esAdministrador()){
					halt(401,"No puede acceder aquí");
			}
		});

		//request.session().attribute("user");
	}

	private static boolean isProtected(String uri) {
		ArrayList<String> urlNoProtegidas = new ArrayList<>();
		urlNoProtegidas.add("/login");

		return urlNoProtegidas.stream().noneMatch(uri::startsWith);
	}

	private static boolean isProtectedFromAdmin(String uri) {
		ArrayList<String> urlNoProtegidas = new ArrayList<>();
		urlNoProtegidas.add("/login");
		urlNoProtegidas.add("/logout");
		urlNoProtegidas.add("/css");

		boolean condicion1 = urlNoProtegidas.stream().noneMatch(uri::startsWith);
		boolean condicion2 = !uri.contentEquals("/");


		return condicion1 && condicion2 ;
	}


	private static TemplateViewRoute  TemplWithTransaction(WithTransaction<ModelAndView> fn) {
		TemplateViewRoute r = (req, res) -> {
			EntityManager em = ServerDataMock.getEntityManager();
			em.getTransaction().begin();
			try {
				ModelAndView result = fn.method(req, res, em);
				em.getTransaction().commit();
				return result;
			} catch (Exception ex) {
				em.getTransaction().rollback();
				throw ex;
			}
		};
		return r;
	}

	private static Route RouteWithTransaction(WithTransaction<Object> fn) {
		Route r = (req, res) -> {
			EntityManager em = ServerDataMock.getEntityManager();
			em.getTransaction().begin();
			try {
				Object result = fn.method(req, res, em);
				em.getTransaction().commit();
				return result;
			} catch (Exception ex) {
				em.getTransaction().rollback();
				throw ex;
			}
		};
		return r;
	}
}
