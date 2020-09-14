package servidor.controladores;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Controller {
	private Map<String, Object> viewModel;
	
	public Controller() {
		viewModel = new HashMap<String, Object>();
	}
	
	protected String render(String template) {
		ModelAndView modelAndView = new ModelAndView(viewModel, template);
		String render = new HandlebarsTemplateEngine().render(modelAndView);
		this.clearModel();
		return render;
	}
	
	public void addAttribute(String key, Object value) {
		this.viewModel.put(key, value);
	}
	
	private void clearModel() {
		this.viewModel.clear();
	}
}
