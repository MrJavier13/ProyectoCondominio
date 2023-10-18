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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "registrosISController")
@ViewScoped
public class RegistrosISController implements Serializable{
    private List<RegistroIngresosSalidasTO> registroIS = new ArrayList<>();
    private Date fechaInicio;
    private Date fechaFin;

    @ManagedProperty("#{registroISService}")
    private ServicioRegistroIS servicioRegistroIS;

    public RegistrosISController() {
    }

    @PostConstruct
    public void init() {
        this.registroIS = servicioRegistroIS.mostrarRegistro();
    }

    public void buscarPorFecha() {
    Calendar cal = Calendar.getInstance();

    cal.setTime(fechaInicio);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    java.sql.Timestamp inicio = new java.sql.Timestamp(cal.getTimeInMillis());

    cal.setTime(fechaFin);
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    cal.set(Calendar.MILLISECOND, 999);
    java.sql.Timestamp fin = new java.sql.Timestamp(cal.getTimeInMillis());

    this.registroIS = servicioRegistroIS.mostrarRegistroPorFecha(inicio, fin);
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
