package dominio.presupuestos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.licitacion.criterioSeleccion.CriterioMenorPrecio;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import dominio.operaciones.DocumentoComercial;
import dominio.operaciones.ETipoDoc;
import dominio.operaciones.ETipoItem;
import dominio.operaciones.EntidadOperacion;
import dominio.operaciones.Item;
import dominio.operaciones.OperacionEgreso;
import dominio.operaciones.OperacionEgresoBuilder;
import dominio.operaciones.medioDePago.Efectivo;

public class LicitacionTest {
	OperacionEgresoBuilder builderCompra;
	OperacionEgreso compra; 
	Licitacion licitacion;
	Presupuesto presup1;
	Presupuesto presup2;
	Presupuesto presup3;
	Presupuesto presup4;
	EntidadOperacion proveedor1;
	EntidadOperacion proveedor2;

	// AUDITORIA
	static MongoClient mongoClient;
	static Datastore datastore;
	
	@Before
	public void init() {

		Efectivo pesos = new Efectivo(200000, "Rapipago", "Efectivo", "ticket");
		DocumentoComercial documento = new DocumentoComercial(ETipoDoc.FACTURA, 2000);
		Date fecha = new Date();
		EntidadOperacion origen = new EntidadOperacion("Operacion compra 1", "20-40678950-4", "Av.Libertador 800");
		EntidadOperacion destino = new EntidadOperacion("Operacion compra 1", "20-40678950-4", "Av.Corrientes 550");

		ArrayList<Item> listaItemsCompra = new ArrayList<Item>();
		listaItemsCompra.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
		listaItemsCompra.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

		builderCompra = new OperacionEgresoBuilder();

		compra = builderCompra.agregarItems(listaItemsCompra)
				.agregarMedioDePago(pesos)
				.agregarDocComercial(documento)
				.agregarFecha(fecha)
				.agregarEntidadOrigen(origen)
				.agregarEntidadDestino(destino)
				.agregarPresupuestosNecesarios(2)
				.build();

		licitacion = new Licitacion(compra, NotificadorSuscriptores.getInstance());

		licitacion.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());

		ArrayList<Item> listaItems1 = new ArrayList<Item>();
		listaItems1.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
		listaItems1.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

		ArrayList<Item> listaItems2 = new ArrayList<Item>();
		listaItems2.add(new Item(200, ETipoItem.ARTICULO, "Item3"));
		listaItems2.add(new Item(150, ETipoItem.ARTICULO, "Item4"));

		ArrayList<Item> listaItems3 = new ArrayList<Item>();
		listaItems3.add(new Item(500, ETipoItem.ARTICULO, "Item1"));
		listaItems3.add(new Item(1020, ETipoItem.ARTICULO, "Item2"));

		ArrayList<Item> listaItems4 = new ArrayList<Item>();
		listaItems4.add(new Item(10, ETipoItem.ARTICULO, "Item1"));
		listaItems4.add(new Item(30, ETipoItem.ARTICULO, "Item2"));

		proveedor1 = new EntidadOperacion("Operacion compra 1", "20-40678950-4", "Av.Libertador 800");
		proveedor2 = new EntidadOperacion("Operacion compra 2", "20-40678950-3", "Av.Libertador 100");

		presup1 = new Presupuesto(proveedor1, listaItems1);
		presup2 = new Presupuesto(proveedor2, listaItems2);
		presup3 = new Presupuesto(proveedor1, listaItems3);
		presup4 = new Presupuesto(proveedor2, listaItems4);
	}

	@BeforeClass
	static public void initAudit() {
		MongoClientURI uri = new MongoClientURI(
				"mongodb+srv://mongodb:0AnE83904LltBfkF@cluster0.8pqel.mongodb.net/operations_audit?retryWrites=true&w=majority");

		mongoClient = new MongoClient(uri);
		MongoDatabase database = mongoClient.getDatabase("test");

		Morphia morphia = new Morphia();
		morphia.mapPackage("auditoria");

		datastore = morphia.createDatastore(mongoClient, "TESTS_BD");
		datastore.ensureIndexes();
	}
	
	@Test
	public void agregarPresupuestoValido() {
		assertTrue(presup1.esValido(compra));
	}
	
	@Test
	public void agregarPresupuestoInvalido() {
		assertFalse(presup2.esValido(compra));
	}
	
    @Test
    public void licitarExitoso() {    	
		licitacion.agregarPresupuesto(presup1, "LicitacionTest", datastore);
		licitacion.agregarPresupuesto(presup3, "LicitacionTest", datastore);
		assertTrue(licitacion.puedeLicitar());
    }
    
    @Test
    public void licitarNoExitosoPorqueNoCumpleCriterioCantidadPresupuestos() {
    	licitacion.agregarPresupuesto(presup1, "LicitacionTest", datastore);
		assertFalse(licitacion.puedeLicitar());
    }
    
    @Test
    public void licitarNoExitosoPorqueNoCumpleCriterioCorrespondeYEsMenorPrecio() {
    	licitacion.agregarPresupuesto(presup1, "LicitacionTest", datastore);
    	licitacion.agregarPresupuesto(presup4, "LicitacionTest", datastore);
    	assertFalse(licitacion.puedeLicitar());
    }

    @Test
	public void usuarioNoPuedeSuscribirse(){
		CuentaUsuario usuario = new CuentaUsuario("Prueba","1234",new Rol("TESTER",null));
		licitacion.agregarPresupuesto(presup1, "LicitacionTest", datastore);
		licitacion.agregarPresupuesto(presup3, "LicitacionTest", datastore);
		licitacion.licitar();
		Assert.assertThrows(Exception.class,()->{licitacion.suscribir(usuario);});
	}

}