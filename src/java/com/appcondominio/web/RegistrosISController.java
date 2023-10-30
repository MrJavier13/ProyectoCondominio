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
