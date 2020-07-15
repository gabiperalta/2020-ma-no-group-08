package servicio.ab_organizaciones;

import dominio.entidades.RepoOrganizaciones;

public class ServicioABOrganizaciones {
	
	public void altaOrganizacion(String nombreOrganizacion) {
		RepoOrganizaciones.getInstance().agregarOrganizacion(nombreOrganizacion);
	}
	
	public void bajaOrganizacino(String nombreOrganizacion) {
		RepoOrganizaciones.getInstance().eliminarOrganizacion(nombreOrganizacion);
	}
	
}
