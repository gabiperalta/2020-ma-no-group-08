package servicio.ab_organizaciones;

import dominio.entidades.Empresa;
import dominio.entidades.EntidadJuridica;
import datos.RepoOrganizaciones;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class ServicioABOrganizaciones {

	RepoOrganizaciones repoOrganizaciones;

	public ServicioABOrganizaciones(EntityManager em){
		repoOrganizaciones = new RepoOrganizaciones(em);
	}
	
	public void altaOrganizacion(String nombreOrganizacion, ArrayList<Empresa> entidades) {
		repoOrganizaciones.agregarOrganizacion(nombreOrganizacion, entidades);
	}
	
	public void bajaOrganizacino(String nombreOrganizacion) {
		repoOrganizaciones.eliminarOrganizacion(nombreOrganizacion);
	}
	
}