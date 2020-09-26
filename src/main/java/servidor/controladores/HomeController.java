package servidor.controladores;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController extends Controller{

    public ModelAndView showHomePage(Request request, Response response){
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user", request.session().attribute("user"));

        return new ModelAndView(parameters, "home.hbs");
    }

}
