package servidor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dominio.cuentasUsuarios.CuentaUsuario;
import servidor.controladores.*;
import servicio.*;

import spark.ModelAndView;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;

import static spark.Spark.*;

public class Router {

	private static HandlebarsTemplateEngine engine;
	private static EntityManagerFactory entityManagerFactory;
	static MongoClient mongoClient;
	static Datastore datastore;

	public static void init() throws Exception {
		Router.initAudit();
		Router.initEngine();
		Router.initPersistence();

		boolean localhost = false;
		if(localhost){
			String projectDir = System.getProperty("user.dir");
			String staticDir = "/src/main/resources/public";
			Spark.externalStaticFileLocation(projectDir + staticDir);
		}
		else{
			Spark.staticFileLocation("/public");
		}

		Router.configure();
	}

	private static void initEngine() {
		Router.engine = HandlebarsTemplateEngineBuilder
			.create()
			.withDefaultHelpers()
			.withHelper("isTrue", BooleanHelper.isTrue)
			.build();
	}

	private static void initPersistence(){ entityManagerFactory = Persistence.createEntityManagerFactory("db"); }

	private static void initAudit(){
		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://mongodb:0AnE83904LltBfkF@cluster0.8pqel.mongodb.net/operations_audit?retryWrites=true&w=majority");

		mongoClient = new MongoClient(uri);

		Morphia morphia = new Morphia();
		morphia.mapPackage("auditoria");

		datastore = morphia.createDatastore(mongoClient, "operations_audit");
		datastore.ensureIndexes();
	}

	public static void configure() throws Exception {
		LoginController loginc = new LoginController();
		HomeController homec = new HomeController();
		LicitacionController licitacionc = new LicitacionController();
		VinculacionesController vinculacionesC = new VinculacionesController();
		EgresoController egresoC = new EgresoController();
		IngresoController ingresoC = new IngresoController();
		AuditoriaController auditoriaController = new AuditoriaController();
		ServiceMercadoLibre serviceMeli = ServiceMercadoLibre.INSTANCE;

		CategorizacionesController categorizacionesC = new CategorizacionesController();



		get("/login", loginc::login, engine);
		post("/login", RouteWithTransaction(loginc::loguear));
		get("/logout", loginc::logout);
		get("/", homec::showHomePage, engine);
		get("/presupuestos",TemplWithTransaction(licitacionc::mostrarPresupuestos),engine);
		post("/presupuesto",RouteWithTransaction(licitacionc::agregarPresupuesto));
		post("/licitacion",RouteWithTransaction(licitacionc::realizarLicitacion));
		get("/licitacion",RouteWithTransaction(licitacionc::obtenerLicitacionPorEgreso));
		get("/licitacion/:licitacion_id",RouteWithTransaction(licitacionc::resultadoLicitacion),licitacionc.getGson()::toJson);
		get("/egreso", egresoC::showEgreso, engine);
		post("/egreso", TemplWithTransaction(egresoC::crearEgreso), engine);
		get("/egreso/:id", TemplWithTransaction(egresoC::showModificarEgreso), engine);
		post("/egreso/:id", TemplWithTransaction(egresoC::modificarEgreso), engine);
		get("/egresos/:egreso", egresoC::showEgreso, engine);
		delete("/egreso/:identificador", RouteWithTransaction(egresoC::deleteEgreso));

		get("/egresos", TemplWithTransaction(egresoC::mostrarEgresos), engine);

		get("/ingreso", ingresoC::showIngreso, engine);
		get("/ingresos", TemplWithTransaction(ingresoC::mostrarIngresos), engine);



		get("/categorizar", TemplWithTransaction(categorizacionesC::showCategorizacionesPage), engine);
		post("/categorizar", TemplWithTransaction(categorizacionesC::categorizar), engine);
		get("/descategorizar", TemplWithTransaction(categorizacionesC::showDescategorizacionesPage), engine);
		post("/descategorizar", TemplWithTransaction(categorizacionesC::descategorizar), engine);
		
		get("/vinculaciones",TemplWithTransaction(vinculacionesC::seleccionarOperaciones),engine);
		post("/vinculaciones",vinculacionesC::vincular);

		get("/auditoria", auditoriaController::showAuditorias);

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
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				ModelAndView result = fn.method(req, res, em);
				em.getTransaction().commit();
				return result;
			} catch (Exception ex) {
				em.getTransaction().rollback();
				throw ex;
			} finally {
				em.clear();
			}
		};
		return r;
	}

	private static Route RouteWithTransaction(WithTransaction<Object> fn) {
		Route r = (req, res) -> {
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
			try {
				Object result = fn.method(req, res, em);
				em.getTransaction().commit();
				return result;
			} catch (Exception ex) {
				em.getTransaction().rollback();
				throw ex;
			} finally {
				em.clear();
			}
		};
		return r;
	}

	public static Datastore getDatastore(){
		return datastore;
    }

}
