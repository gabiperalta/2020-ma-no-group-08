package mock;

import datos.*;
import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.cuentasUsuarios.Roles.Privilegio;
import dominio.cuentasUsuarios.Roles.Rol;
import dominio.entidades.*;
import dominio.entidades.calculadorFiscal.ETipoActividad;
import dominio.licitacion.Licitacion;
import dominio.licitacion.Presupuesto;
import datos.RepoLicitaciones;
import dominio.licitacion.criterioSeleccion.CriterioMenorPrecio;
import dominio.notificador_suscriptores.NotificadorSuscriptores;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.Efectivo;
import datos.RepositorioUsuarios;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

public class ServerDataMock {
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) throws Exception {
        entityManagerFactory =  Persistence.createEntityManagerFactory("db");
        cargarMock();
    }

    public static void cargarMock() throws Exception {
        cargarOrganizaciones();
        cargarRoles();
        cargarUsuarios();
//        cargarIngresos();
//        cargarEgregos();
//        cargarCategorias();
//        cargarPresupuestos();
    }

    public ServerDataMock() {
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
    }

    private static void cargarOrganizaciones(){

        ArrayList<EntidadJuridica> entidades1 = new ArrayList<>();
        ArrayList<EntidadJuridica> entidades2 = new ArrayList<>();

        EntidadJuridica entidad1 = new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "Empresa 1", "Empresa 1", "20-40678950-3", "200", "Av.Libertador 801",false);
        EntidadJuridica entidad2 = new Empresa(ETipoEmpresa.MEDIANA_T1, 3, ETipoActividad.COMERCIO, 2000.54, "Empresa 2", "Empresa 2", "20-40678950-4", "203", "Av.Libertador 200",false);

        entidades1.add(entidad1);
        entidades2.add(entidad2);



        EntityManager em = getEntityManager();
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(em);

        em.getTransaction().begin();
        repoOrganizaciones.agregarOrganizacion("Organizacion1", entidades1);
        repoOrganizaciones.agregarOrganizacion("Organizacion2", entidades2);
        em.getTransaction().commit();

    }

    private static void cargarRoles(){
        Privilegio privilegioABOrganizacion = new Privilegio("PRIVILEGIO_AB_ORGANIZACIONES");
        Privilegio privilegioABMUsuarios = new Privilegio("PRIVILEGIO_ABM_USUARIOS");
        Privilegio privilegioABMEntidadesJuridicas = new Privilegio("PRIVILEGIO_ABM_ENTIDADES_JURIDICAS");
        Privilegio privilegioABMEntidadesBase = new Privilegio("PRIVILEGIO_ABM_ENTIDADES_BASE");
        Privilegio privilegioABOperacion = new Privilegio("PRIVILEGIO_AB_OPERACIONES");
        Privilegio privilegioABLicitaciones = new Privilegio("PRIVILEGIO_AB_LICITACIONES");
        Privilegio privilegioRevisor = new Privilegio("PRIVILEGIO_REVISOR");
        Privilegio privilegioRecategorizador = new Privilegio("PRIVILEGIO_RECATEGORIZADOR");
        Privilegio privilegioVinculador = new Privilegio("PRIVILEGIO_VINCULADOR");

        ArrayList<Privilegio> privilegiosRolAdministradorSistema = new ArrayList<Privilegio>();
        ArrayList<Privilegio> privilegiosRolAdministradorOrganizacion = new ArrayList<Privilegio>();
        ArrayList<Privilegio> privilegiosRolEstandar = new ArrayList<Privilegio>();
        ArrayList<Privilegio> privilegiosRolRevisor = new ArrayList<Privilegio>();

        privilegiosRolAdministradorSistema.add(privilegioABOrganizacion);
        privilegiosRolAdministradorSistema.add(privilegioABMUsuarios);
        privilegiosRolAdministradorOrganizacion.add(privilegioABMEntidadesJuridicas);
        privilegiosRolAdministradorOrganizacion.add(privilegioABMEntidadesBase);
        privilegiosRolEstandar.add(privilegioABOperacion);
        privilegiosRolEstandar.add(privilegioABLicitaciones);
        privilegiosRolEstandar.add(privilegioRecategorizador);
        privilegiosRolEstandar.add(privilegioVinculador);

        privilegiosRolRevisor.add(privilegioRevisor);

        Rol rolAdministradorSistema = new Rol("ROL_ADMINISTRADOR_SISTEMA", privilegiosRolAdministradorSistema);
        Rol rolAdministradorOrganizacion = new Rol("ROL_ADMINISTRADOR_ORGANIZACION", privilegiosRolAdministradorOrganizacion);
        Rol rolEstandar = new Rol("ROL_ESTANDAR", privilegiosRolEstandar);
        Rol rolRevisor = new Rol("ROL_REVISOR", privilegiosRolRevisor);

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        em.persist(rolAdministradorSistema);
        em.persist(rolAdministradorOrganizacion);
        em.persist(rolEstandar);
        em.persist(rolRevisor);

        em.getTransaction().commit();
    }

    private static void cargarUsuarios(){
        ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
        listaDeRolesCliente.add("ROL_ESTANDAR");
        ArrayList<String> listaDeRolesClienteMaestro = new ArrayList<String>();
        listaDeRolesClienteMaestro.add("ROL_ADMINISTRADOR_ORGANIZACION");
        listaDeRolesClienteMaestro.add("ROL_ESTANDAR");
        listaDeRolesClienteMaestro.add("ROL_REVISOR");

        EntityManager em = getEntityManager();
        // Usuarios Administradores
        em.getTransaction().begin();
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(em);
        repositorioUsuarios.agregarUsuarioAdministrador("admin1", "1234");
        repositorioUsuarios.agregarUsuarioAdministrador("admin2", "1234");
        repositorioUsuarios.agregarUsuarioAdministrador("admin3", "1234");

        // Usuarios Estandar
        RepoOrganizaciones repoOrganizaciones = new RepoOrganizaciones(em);

        Organizacion organizacion1 = repoOrganizaciones.buscarOrganizacion("Organizacion1");
        Organizacion organizacion2 = repoOrganizaciones.buscarOrganizacion("Organizacion2");

        repositorioUsuarios.agregarUsuarioEstandar("UsuarioWeb1", organizacion1, listaDeRolesCliente, "1234");
        repositorioUsuarios.agregarUsuarioEstandar("UsuarioWeb2", organizacion2, listaDeRolesClienteMaestro, "1234");
        em.getTransaction().commit();
    }

    private static void cargarIngresos() throws Exception {
        OperacionIngreso ingreso1 = new OperacionIngreso();
        ingreso1.setFecha(new Date());
        ingreso1.setMontoTotal(1000);

        OperacionIngreso ingreso2 = new OperacionIngreso();
        ingreso2.setFecha(new Date());
        ingreso2.setMontoTotal(2000);

        OperacionIngreso ingreso3 = new OperacionIngreso();
        ingreso3.setFecha(new Date());
        ingreso3.setMontoTotal(3434.6);

        OperacionIngreso ingreso4 = new OperacionIngreso();
        ingreso4.setFecha(new Date());
        ingreso4.setMontoTotal(565.3);

        OperacionIngreso ingreso5 = new OperacionIngreso();
        ingreso5.setFecha(new Date());
        ingreso5.setMontoTotal(565.3);

        OperacionIngreso ingreso6 = new OperacionIngreso();
        ingreso6.setFecha(new Date());
        ingreso6.setMontoTotal(565.3);

        EntidadOperacion origen = new EntidadOperacion("Empresa 2","20-40678950-3","Av.Libertador 801");
        EntidadOperacion destino = new EntidadOperacion("Empresa 1", "20-40678950-3", "Av.Corrientes 550");

        ingreso1.setEntidadOrigen(origen);
        ingreso1.setEntidadDestino(destino);

        ingreso2.setEntidadOrigen(origen);
        ingreso2.setEntidadDestino(destino);

        ingreso3.setEntidadOrigen(origen);
        ingreso3.setEntidadDestino(destino);

        ingreso4.setEntidadOrigen(origen);
        ingreso4.setEntidadDestino(destino);

        ingreso5.setEntidadOrigen(destino);
        ingreso5.setEntidadDestino(origen);

        ingreso6.setEntidadOrigen(destino);
        ingreso6.setEntidadDestino(origen);

        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso1);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso2);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso3);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso4);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso5);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso6);
    }

    private static void cargarEgregos() throws Exception {
        OperacionEgresoBuilder builderEgreso = new OperacionEgresoBuilder();
        ArrayList<Item> items1 = new ArrayList<>();
        ArrayList<Item> items2 = new ArrayList<>();
        ArrayList<Item> items3 = new ArrayList<>();
        ArrayList<Item> items4 = new ArrayList<>();
        ArrayList<Item> items5 = new ArrayList<>();


        Item resma = new Item(300,ETipoItem.ARTICULO,"Resma de hojas");
        Item flete = new Item(350, ETipoItem.SERVICIO, "Servicio de transporte de productos");
        Item tinta = new Item(500,ETipoItem.ARTICULO,"Cartucho de tinta");
        Item pc = new Item(50000,ETipoItem.ARTICULO,"computadora");
        Item escritorio = new Item(456454,ETipoItem.ARTICULO,"Escritorio");

        items1.add(resma);
        items2.add(flete);
        items3.add(tinta);
        items4.add(pc);
        items5.add(escritorio);
        items5.add(pc);

        Efectivo pesos = new Efectivo(200000,"Rapipago", "Efectivo");
        DocumentoComercial documento = new DocumentoComercial(ETipoDoc.FACTURA, 2000);
        Date fecha = new Date();
        EntidadOperacion origen = new EntidadOperacion("Empresa 1","20-40678950-3","Av.Libertador 801");
        EntidadOperacion destino = new EntidadOperacion("Empresa 2", "20-40678950-3", "Av.Corrientes 550");
        int presupuestosNecesarios = 2;

        OperacionEgreso egreso1 = builderEgreso.agregarItems(items1)
                                                .agregarMedioDePago(pesos)
                                                .agregarDocComercial(documento)
                                                .agregarFecha(fecha)
                                                .agregarEntidadOrigen(origen)
                                                .agregarEntidadDestino(destino)
                                                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();

        OperacionEgreso egreso2 = builderEgreso.agregarItems(items2)
                                                .agregarMedioDePago(pesos)
                                                .agregarDocComercial(documento)
                                                .agregarFecha(fecha)
                                                .agregarEntidadOrigen(origen)
                                                .agregarEntidadDestino(destino)
                                                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();

        OperacionEgreso egreso3 = builderEgreso.agregarItems(items3)
                                                .agregarMedioDePago(pesos)
                                                .agregarDocComercial(documento)
                                                .agregarFecha(fecha)
                                                .agregarEntidadOrigen(origen)
                                                .agregarEntidadDestino(destino)
                                                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();
        OperacionEgreso egreso4 = builderEgreso.agregarItems(items4)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino)
                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();

        OperacionEgreso egreso5 = builderEgreso.agregarItems(items5)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(origen)
                .agregarEntidadDestino(destino)
                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();

        OperacionEgreso egreso6 = builderEgreso.agregarItems(items2)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(destino)
                .agregarEntidadDestino(origen)
                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();

        OperacionEgreso egreso7 = builderEgreso.agregarItems(items3)
                .agregarMedioDePago(pesos)
                .agregarDocComercial(documento)
                .agregarFecha(fecha)
                .agregarEntidadOrigen(destino)
                .agregarEntidadDestino(origen)
                .agregarPresupuestosNecesarios(presupuestosNecesarios).build();

        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso1);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso2);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso3);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso4);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso5);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso6);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso7);
    }

    private static void cargarCategorias() throws CategorizacionException {
        CriterioDeCategorizacion criterioDePrueba1 = new CriterioDeCategorizacion("CriterioDePrueba-1");
        criterioDePrueba1.agregarCategoria("Categoria-1");
        criterioDePrueba1.agregarCategoria("Categoria-1.1", "Categoria-1");
        criterioDePrueba1.agregarCategoria("Categoria-2");

        CriterioDeCategorizacion criterioDePrueba2 = new CriterioDeCategorizacion("CriterioDePrueba-2");
        criterioDePrueba2.agregarCategoria("Categoria-1");
        criterioDePrueba2.agregarCategoria("Categoria-1.1", "Categoria-1");
        criterioDePrueba2.agregarCategoria("Categoria-2");
        criterioDePrueba2.agregarCategoria("Categoria-3");
        criterioDePrueba2.agregarCategoria("Categoria-3.1", "Categoria-3");

        RepositorioCategorizacion.getInstance().agregarCriterioDeCategorizacion(criterioDePrueba1);
        RepositorioCategorizacion.getInstance().agregarCriterioDeCategorizacion(criterioDePrueba2);
    }

    private static void cargarPresupuestos(){
        Licitacion licitacion1;
        Licitacion licitacion2;
        Presupuesto presup1;
        Presupuesto presup2;
        Presupuesto presup3;
        EntidadOperacion proveedor1;

        RepoLicitaciones repoLicitaciones = new RepoLicitaciones(getEntityManager());

        ArrayList<Item> listaItems = new ArrayList<Item>();
        listaItems.add(new Item(50, ETipoItem.ARTICULO, "Item1"));
        listaItems.add(new Item(100, ETipoItem.ARTICULO, "Item2"));

        licitacion1 = new Licitacion(RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdenticadorOperacionEgreso("OE-1"), NotificadorSuscriptores.getInstance());
        licitacion2 = new Licitacion(RepoOperacionesEgreso.getInstance().buscarOperacionEgresoPorIdenticadorOperacionEgreso("OE-5"), NotificadorSuscriptores.getInstance());

        licitacion1.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());
        licitacion2.agregarCriterioSeleccionDeProveedor(new CriterioMenorPrecio());

        ArrayList<Item> listaItems1 = new ArrayList<>();
        listaItems1.add(new Item(350, ETipoItem.ARTICULO, "Resma de hojas"));

        ArrayList<Item> listaItems2 = new ArrayList<>();
        listaItems2.add(new Item(45032, ETipoItem.ARTICULO, "Escritorio"));
        listaItems2.add(new Item(65000, ETipoItem.ARTICULO, "computadora"));

        ArrayList<Item> listaItems3 = new ArrayList<>();
        listaItems3.add(new Item(456454, ETipoItem.ARTICULO, "Escritorio"));
        listaItems3.add(new Item(50000, ETipoItem.ARTICULO, "computadora"));

        proveedor1 = new EntidadOperacion("Empresa 1","20-40678950-3","Av.Libertador 801");

        presup1 = new Presupuesto(proveedor1,listaItems1);
        presup2 = new Presupuesto(proveedor1,listaItems2);
        presup3 = new Presupuesto(proveedor1,listaItems3);

        licitacion1.agregarPresupuesto(presup1);
        licitacion2.agregarPresupuesto(presup2);
        licitacion2.agregarPresupuesto(presup3);

        repoLicitaciones.agregarLicitacion(licitacion1);
        repoLicitaciones.agregarLicitacion(licitacion2);
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}