package dominio.notificador_suscriptores.bandeja_de_mensajes;

import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.notificador_suscriptores.Mensaje;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BandejaDeMensajesTest{
    BandejaDeMensajes bandejaDeMensajes;
    CuentaUsuario usuario;

    @Before
    public void init(){
        usuario = new CuentaUsuario("Prueba","1234",new Rol("TESTER",null));
        bandejaDeMensajes = new BandejaDeMensajes(usuario);
        bandejaDeMensajes.nuevoMensaje(new Mensaje("Hola",false));
        bandejaDeMensajes.nuevoMensaje(new Mensaje("Como estas?",false));
        bandejaDeMensajes.nuevoMensaje(new Mensaje("Silla",false));
    }

    @Test
    public void testObtenerMensajePorIndice() {
        Assert.assertEquals("Como estas?",bandejaDeMensajes.obtenerMensajePorIndice(1).getCuerpo());
    }

    @Test
    public void testBorrarMensajePorIndice() {
        bandejaDeMensajes.borrarMensajePorIndice(0);
        Assert.assertNotEquals("Hola",bandejaDeMensajes.obtenerMensajePorIndice(0).getCuerpo());
    }

    @Test
    public void noHayMensajesParaLeer(){
        bandejaDeMensajes.getMensajes();
        Assert.assertEquals(0,bandejaDeMensajes.obtenerMensajesSinLeer().size());
    }
}