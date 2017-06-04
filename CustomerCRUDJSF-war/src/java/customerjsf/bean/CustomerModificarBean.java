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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author alumno
 */
@Named(value = "customerModificarBean")
@RequestScoped
public class CustomerModificarBean {

    @EJB
    private DiscountCodeFacade discountCodeFacade;    
    @EJB
    private MicroMarketFacade microMarketFacade;
    @EJB
    private CustomerFacade customerFacade;
        
    @Inject CustomerBean customerBean;            
    
    protected Customer cliente;
    protected String zipCodeSeleccionado;
    protected String discountSeleccionado;
    

    /**
     * Creates a new instance of CustomerModificarBean
     */
    public CustomerModificarBean() {
    }
    
    @PostConstruct
    public void init () {
        if (this.customerBean.getClienteSeleccionado()==null) { // Nuevo cliente
            this.cliente = new Customer();
        } else { // Editar cliente  
            this.cliente = this.customerBean.getClienteSeleccionado();
            this.zipCodeSeleccionado = cliente.getZip().getZipCode();
            this.discountSeleccionado = cliente.getDiscountCode().getDiscountCode();                    
        }
        this.customerBean.cargarListas();        
    }

    public Customer getCliente() {
        return cliente;
    }

    public void setCliente(Customer cliente) {
        this.cliente = cliente;
    }

    public String getZipCodeSeleccionado() {
        return zipCodeSeleccionado;
    }

    public void setZipCodeSeleccionado(String zipCodeSeleccionado) {
        this.zipCodeSeleccionado = zipCodeSeleccionado;
    }

    public String getDiscountSeleccionado() {
        return discountSeleccionado;
    }

    public void setDiscountSeleccionado(String discountSeleccionado) {
        this.discountSeleccionado = discountSeleccionado;
    }
    
        
  public String doGuardar () {
        MicroMarket mm = this.microMarketFacade.find(this.zipCodeSeleccionado);
        this.cliente.setZip(mm);
        DiscountCode dc = this.discountCodeFacade.find(this.discountSeleccionado);
        this.cliente.setDiscountCode(dc);
        if (this.cliente.getCustomerId() == null) {
            Integer entero = this.customerFacade.calcularSiguienteCustomerId();
            this.cliente.setCustomerId(entero);
            this.customerFacade.create(this.cliente);
            this.customerBean.init();
        } else {
            this.customerFacade.edit(this.cliente);
        }
        return "listadoCustomer";
    }       
}
