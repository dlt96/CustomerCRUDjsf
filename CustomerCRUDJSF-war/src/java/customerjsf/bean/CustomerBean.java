/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerjsf.bean;

import customerjsf.ejb.CustomerFacade;
import customerjsf.ejb.DiscountCodeFacade;
import customerjsf.ejb.MicroMarketFacade;
import customerjsf.entity.Customer;
import customerjsf.entity.DiscountCode;
import customerjsf.entity.MicroMarket;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 *
 * @author alumno
 */
@Named
@SessionScoped
public class CustomerBean implements Serializable{

    @EJB
    private DiscountCodeFacade discountCodeFacade;
    @EJB
    private MicroMarketFacade microMarketFacade;
    @EJB
    private CustomerFacade customerFacade;
                
    protected List<Customer> listadoClientes;
    protected Customer clienteSeleccionado;
    protected List<MicroMarket> listaSupermercados = null;
    protected List<DiscountCode> listaDescuentos = null;
    
    /**
     * Creates a new instance of CustomerBean
     */
    public CustomerBean() {
    }
    
    @PostConstruct
    public void init() {
        this.listadoClientes = this.customerFacade.findAll();
    }

    public List<Customer> getListadoClientes() {
        return listadoClientes;
    }

    public void setListadoClientes(List<Customer> listadoClientes) {
        this.listadoClientes = listadoClientes;
    }

    public Customer getClienteSeleccionado() {
        return clienteSeleccionado;
    }

    public void setClienteSeleccionado(Customer clienteSeleccionado) {
        this.clienteSeleccionado = clienteSeleccionado;
    }

    public List<MicroMarket> getListaSupermercados() {
        return listaSupermercados;
    }

    public void setListaSupermercados(List<MicroMarket> listaSupermercados) {
        this.listaSupermercados = listaSupermercados;
    }

    public List<DiscountCode> getListaDescuentos() {
        return listaDescuentos;
    }

    public void setListaDescuentos(List<DiscountCode> listaDescuentos) {
        this.listaDescuentos = listaDescuentos;
    }          

    public String doBorrar (Customer cliente) {
        this.customerFacade.remove(cliente);
        this.init();
        return "listadoCustomer";
    }

    public String doEditar (Customer cliente) {
        this.clienteSeleccionado = cliente;
        return "customer";
    }
    
  /*
    protected void reset () {
        this.clienteSeleccionado = new Customer();
        this.discountSeleccionado = null;
        this.zipCodeSeleccionado = null;
    }
       */
    
    public void cargarListas () {
        if (this.listaSupermercados == null) {
            this.listaSupermercados = this.microMarketFacade.findAll();
            this.listaDescuentos = this.discountCodeFacade.findAll();
        }
    }
    
    public String doNuevo() {
        this.clienteSeleccionado = null;
        return "customer";
    }
    
}
