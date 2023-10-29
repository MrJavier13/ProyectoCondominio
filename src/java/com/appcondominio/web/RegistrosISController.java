/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.web;

import com.appcondominio.service.RegistroIngresosSalidasTO;
import com.appcondominio.service.ServicioRegistroIS;
import java.io.Serializable;
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


@ManagedBean(name = "registrosISController")
@ViewScoped
public class RegistrosISController implements Serializable{
    private List<RegistroIngresosSalidasTO> registroIS = new ArrayList<>();
    
    private Date fechaInicial;
    private Date fechaFinal;

    @ManagedProperty("#{registroISService}")
    private ServicioRegistroIS servicioRegistroIS;

    public RegistrosISController() {
    }

    @PostConstruct
    public void init() {
        this.registroIS = new ArrayList<>();
    }

    public void buscarRegistrosPorFechas() {
    if (fechaInicial == null || fechaFinal == null || fechaInicial.after(fechaFinal)) {
        // Mensaje de error si las fechas son nulas o la fecha inicial es posterior a la fecha final.
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas son nulas o la fecha inicial es posterior a la final");
        FacesContext.getCurrentInstance().addMessage(null, message);
    } else {
        // Realiza la conversión de java.util.Date a java.sql.Date
        java.sql.Date fechaInicialSQL = new java.sql.Date(fechaInicial.getTime());
        java.sql.Date fechaFinalSQL = new java.sql.Date(fechaFinal.getTime());

        // Llama al servicio para buscar registros entre las fechas.
        registroIS = servicioRegistroIS.buscarRegistrosPorFechas(fechaInicialSQL, fechaFinalSQL);
    }
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
}
