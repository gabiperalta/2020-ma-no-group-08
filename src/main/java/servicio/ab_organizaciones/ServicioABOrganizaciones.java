package servicio.ab_organizaciones;

import dominio.entidades.EntidadJuridica;
import dominio.entidades.RepoOrganizaciones;

import java.util.ArrayList;

public class ServicioABOrganizaciones {
	
	public void altaOrganizacion(String nombreOrganizacion, ArrayList<EntidadJuridica> entidades) {
		RepoOrganizaciones.getInstance().agregarOrganizacion(nombreOrganizacion, entidades);
	}
	
	public void bajaOrganizacino(String nombreOrganizacion) {
		RepoOrganizaciones.getInstance().eliminarOrganizacion(nombreOrganizacion);
	}
	
}
