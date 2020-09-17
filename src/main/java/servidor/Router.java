package servidor;
import servidor.controladores.LoginController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

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
        Spark.staticFileLocation("/public");
        Router.configure();
	   }
	 
	 public static void configure() {
		 LoginController loginc = new LoginController();
		 
		 Spark.get("/login", loginc::loguear);
		 Spark.post("/login", loginc::loguear);
		 Spark.get("/logout", loginc::logout);
	 }
	 
}
