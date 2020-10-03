package servidor;

import mock.ServerDataMock;
import spark.Spark;
import spark.debug.DebugScreen;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerDataMock mock = new ServerDataMock();
		mock.cargarMock();

		Spark.port(9000);
		Router.init();
		DebugScreen.enableDebugScreen();
	}

}
