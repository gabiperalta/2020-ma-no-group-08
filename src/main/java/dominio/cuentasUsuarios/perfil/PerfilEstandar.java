package dominio.cuentasUsuarios.perfil;

import dominio.entidades.Organizacion;
import dominio.operaciones.EntidadOperacion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PerfilEstandar extends Perfil {
	@ManyToOne
	private Organizacion organizacion;

	public PerfilEstandar(){}

	public PerfilEstandar(String unNombre, Organizacion unaOrganizacion) {
		super(unNombre);
		organizacion = unaOrganizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	@Override
	public Organizacion getOrganizacion() {
		return organizacion;
	}

	@Override
	public boolean esUsuarioAdministrador() {
		return false;
	}
	
}
