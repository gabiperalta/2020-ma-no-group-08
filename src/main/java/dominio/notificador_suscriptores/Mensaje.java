package dominio.notificador_suscriptores;

public class Mensaje {
	String cuerpo;
	boolean leido;
	
	public Mensaje(String cuerpo, boolean leido) {
		this.cuerpo = cuerpo;
		this.leido = leido;
	}

	public String getCuerpo(){
		return cuerpo;
	}

	public boolean getLeido(){
		return leido;
	}

	public void marcarComoLeido() {
		this.leido = true;
	}
}
