package servidor;
import servidor.controladores.LoginController;
import spark.Spark;


 public class Router {
	 
	 static Router _instance;
	 
	 private Router () {}
	 
	 public static Router instance() {
			if (_instance == null) {
				_instance = new Router();
			}
			return _instance;
	}
	 
	 public void configurar() {
		 LoginController loginc = new LoginController();
		 
		 Spark.get("/login", loginc::loguear);
		 Spark.post("/login", loginc::loguear);
		 Spark.get("/logout", loginc::logout);
	 }
	 
}
