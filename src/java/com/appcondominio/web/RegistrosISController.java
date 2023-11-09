/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.web;

import com.appcondominio.service.RegistroIngresosSalidasTO;
import com.appcondominio.service.ServicioRegistroIS;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "registrosISController")
@ViewScoped
public class RegistrosISController implements Serializable {

    private List<RegistroIngresosSalidasTO> registroIS = new ArrayList<>();
    private RegistroIngresosSalidasTO registroISSeleccionado;
    private RegistroIngresosSalidasTO nuevoRegistroIS = new RegistroIngresosSalidasTO();
    private Date fechaInicial;
    private Date fechaFinal;
    private String busqueda;
    private Date fechaIngreso;
    private Date fechaSalida;
    private String dialogHeader;

    @ManagedProperty("#{registroISService}")
    private ServicioRegistroIS servicioRegistroIS;

    public RegistrosISController() {
    }

    @PostConstruct
    public void init() {
        this.registroIS = new ArrayList<>();
    }
    
    public void openNew() {
       this.registroISSeleccionado = new RegistroIngresosSalidasTO();
     //  disableSelectOneMenu();
       dialogHeader = "Crear nuevo registro";
       
    }
    
    public void openEdit() {
        this.registroISSeleccionado = new RegistroIngresosSalidasTO();
       
        dialogHeader = "Editar registro";
       
    }

    public void mostrarRegistrosPorFechas() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (fechaInicial != null && fechaFinal != null && !fechaInicial.after(fechaFinal)) {
            // Ajusta las fechas para incluir todas las horas del día
            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(fechaInicial);
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.SECOND, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            Timestamp timestampInicio = new Timestamp(calInicio.getTimeInMillis());

            Calendar calFin = Calendar.getInstance();
            calFin.setTime(fechaFinal);
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.SECOND, 59);
            calFin.set(Calendar.MILLISECOND, 999);
            Timestamp timestampFin = new Timestamp(calFin.getTimeInMillis());

            // Llama al método del servicio para buscar registros por fechas
            registroIS = servicioRegistroIS.buscarRegistrosPorFechas(timestampInicio, timestampFin);
        } else {
            // Muestra un mensaje de error
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas ingresadas no son válidas.");
            context.addMessage(null, message);
        }
    }

    public void mostrarRegistroXInvitados() {
        int resultado = 0;
        FacesContext context = FacesContext.getCurrentInstance();
        if (busqueda.equals("")) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Escriba un nombre a buscar. Ejemplo: Juan Mora Mora");
            context.addMessage(null, message);
        } else {
            resultado = servicioRegistroIS.buscarCedulaInvitado(busqueda);
            if (resultado != 0) {
                registroIS = servicioRegistroIS.buscarRegistrosPorInvitadosPer(resultado);
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Persona no encontrada");
                context.addMessage(null, message);
            }
        }

    }
    
    public void guardarRegistro() {
        if (validarCampos()) {
            registroISSeleccionado.setFechaIngreso(new Timestamp(fechaIngreso.getTime()));
                registroISSeleccionado.setFechaSalida(new Timestamp(fechaSalida.getTime()));
            if (!servicioRegistroIS.buscarIdRegistro(this.registroISSeleccionado.getIdRegistro())) { 
                
                servicioRegistroIS.insertarRegistro(registroISSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro agregado"));

            } else {
                
                servicioRegistroIS.actualizarRegistro(registroISSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro Actualizado"));
            }
            this.init();
            PrimeFaces.current().executeScript("PF('nuevoRegistroISDialog').hide()");
            PrimeFaces.current().ajax().update("form:growl", "form:dt-registros");
        }
    }

    private boolean validarCampo(String valor, String nombreCampo, String nombreError) {
        if (valor == null || valor.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("form:" + nombreCampo,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo " + nombreError + " requerido",
                    "Por favor, ingrese el " + nombreError.toLowerCase() + "de la amenidad."));
            return false;
        }
        return true;
    }
    
    private boolean validarCampos() {
        if (registroISSeleccionado.getIdRegistro() != null) {
        }
        return validarCampo(Integer.toString(registroISSeleccionado.getCedulaInvitadoPermanente()), "cedulaInvitadoPermanente", "cedula invitado permanente") 
                && validarCampo(registroISSeleccionado.getNombreCompletoInvitado(), "nombreCompletoInvitado", "nombre completo invitado")
          //  && validarCampo(registroISSeleccionado.getFechaIngreso().toString(), "fechaIngreso", "fecha de ingreso")
            && validarCampo(Integer.toString(registroISSeleccionado.getCedulaGuardaSeguridad()), "cedulaGuardaSeguridad", "cedula de guarda de seguridad");
    }
    
public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<RegistroIngresosSalidasTO> getRegistroIS() {
        return registroIS;
    }

    public void setUsuario(List<RegistroIngresosSalidasTO> registroIS) {
        this.registroIS = registroIS;
    }

    public ServicioRegistroIS getServicioRegistroIS() {
        return servicioRegistroIS;
    }

    public void setServicioRegistroIS(ServicioRegistroIS servicioRegistroIS) {
        this.servicioRegistroIS = servicioRegistroIS;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public RegistroIngresosSalidasTO getRegistroISSeleccionado() {
        return registroISSeleccionado;
    }

    public void setRegistroISSeleccionado(RegistroIngresosSalidasTO registroISSeleccionado) {
        this.registroISSeleccionado = registroISSeleccionado;
    }

    public RegistroIngresosSalidasTO getNuevoRegistroIS() {
        return nuevoRegistroIS;
    }

    public void setNuevoRegistroIS(RegistroIngresosSalidasTO nuevoRegistroIS) {
        this.nuevoRegistroIS = nuevoRegistroIS;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    
}
