package mock;

import dominio.categorizacion.CriterioDeCategorizacion;
import dominio.categorizacion.RepositorioCategorizacion;
import dominio.categorizacion.exceptions.CategorizacionException;
import dominio.cuentasUsuarios.CuentaUsuario;
import dominio.entidades.Organizacion;
import dominio.entidades.RepoOrganizaciones;
import dominio.operaciones.*;
import dominio.operaciones.medioDePago.Efectivo;
import temporal.seguridad.repositorioUsuarios.RepositorioUsuarios;

import java.util.ArrayList;
import java.util.Date;

public class ServerDataMock {

    public void cargarMock() throws Exception {
        cargarIngresos();
        cargarEgregos();
        cargarCategorias();
        cargarOrganizaciones();
        cargarEntidades();
        cargarUsuarios();
    }

    private void cargarIngresos() throws Exception {
        OperacionIngreso ingreso1 = new OperacionIngreso();
        ingreso1.setFecha(new Date());
        ingreso1.setMontoTotal(1000);

        OperacionIngreso ingreso2 = new OperacionIngreso();
        ingreso1.setFecha(new Date());
        ingreso1.setMontoTotal(2000);

        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso1);
        RepoOperacionesIngreso.getInstance().agregarIngreso(ingreso2);
    }

    private void cargarEgregos() throws Exception {
        OperacionEgresoBuilder builderEgreso = new OperacionEgresoBuilder();
        ArrayList<Item> items1 = new ArrayList<>();
        ArrayList<Item> items2 = new ArrayList<>();
        ArrayList<Item> items3 = new ArrayList<>();
        ArrayList<Item> items4 = new ArrayList<>();

        Item resma = new Item(300,ETipoItem.ARTICULO,"Resma de hojas");
        Item flete = new Item(350, ETipoItem.SERVICIO, "Servicio de transporte de productos");
        Item tinta = new Item(500,ETipoItem.ARTICULO,"Cartucho de tinta");
        Item pc = new Item(50000,ETipoItem.ARTICULO,"computadora");

        items1.add(resma);
        items2.add(flete);
        items3.add(tinta);
        items4.add(pc);

        Efectivo pesos = new Efectivo(200000,"Rapipago");
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

        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso1);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso2);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso3);
        RepoOperacionesEgreso.getInstance().agregarOperacionEgreso(egreso4);
    }

    private void cargarCategorias() throws CategorizacionException {
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

    private void cargarOrganizaciones(){
        RepoOrganizaciones.getInstance().agregarOrganizacion("Organizacion1");
        RepoOrganizaciones.getInstance().agregarOrganizacion("Organizacion2");
    }

    private void cargarEntidades(){

    }

    private void cargarUsuarios(){
        ArrayList<String> listaDeRolesCliente = new ArrayList<String>();
        listaDeRolesCliente.add("ROL_ESTANDAR");
        ArrayList<String> listaDeRolesClienteMaestro = new ArrayList<String>();
        listaDeRolesClienteMaestro.add("ROL_ADMINISTRADOR_ORGANIZACION");
        listaDeRolesClienteMaestro.add("ROL_ESTANDAR");
        listaDeRolesClienteMaestro.add("ROL_REVISOR");

        Organizacion organizacion1 = RepoOrganizaciones.buscarOrganizacion("Organizacion1");
        Organizacion organizacion2 = RepoOrganizaciones.buscarOrganizacion("Organizacion2");

        CuentaUsuario usuarioClientePruebasWeb = new CuentaUsuario("UsuarioWeb1", organizacion1, listaDeRolesCliente, "1234");
        CuentaUsuario usuarioClienteMaestroPruebasWeb = new CuentaUsuario("UsuarioWeb2", organizacion2, listaDeRolesClienteMaestro, "1234");
        RepositorioUsuarios.getInstance().agregarUsuarioEstandar(usuarioClientePruebasWeb);
        RepositorioUsuarios.getInstance().agregarUsuarioEstandar(usuarioClienteMaestroPruebasWeb);
    }
}