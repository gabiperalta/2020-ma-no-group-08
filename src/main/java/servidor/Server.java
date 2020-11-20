package servidor;

import mock.ServerDataMock;
import spark.Spark;
import spark.debug.DebugScreen;

import static spark.Spark.port;

public class Server {

	public static void main(String[] args) throws Exception {
		port(9000);
		//port(getHerokuAssignedPort());

		Router.init();
		DebugScreen.enableDebugScreen();
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
			return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567;
	}
}
