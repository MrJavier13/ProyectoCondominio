/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.service;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author aacas
 */
public class RegistroIngresosSalidasTO implements Serializable{
    
    private int idRegistro;
    private int cedulaInvitado;
    private String nombreCompletoInvitado;
    private String tipoInvitado;
    private String nombreEmpresa;
    private String placaVehicular;
    private String detalle;
    private Timestamp  fechaIngreso;
    private Timestamp  fechaSalida;
    private int cedulaGuardaSeguridad;

    public RegistroIngresosSalidasTO() {
    }

    public RegistroIngresosSalidasTO(int idRegistro, int cedulaInvitado, String nombreCompletoInvitado, String tipoInvitado, String nombreEmpresa, String placaVehicular, String detalle, Timestamp  fechaIngreso, Timestamp  fechaSalida, int cedulaGuardaSeguridad) {
        this.idRegistro = idRegistro;
        this.cedulaInvitado = cedulaInvitado;
        this.nombreCompletoInvitado = nombreCompletoInvitado;
        this.tipoInvitado = tipoInvitado;
        this.nombreEmpresa = nombreEmpresa;
        this.placaVehicular = placaVehicular;
        this.detalle = detalle;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.cedulaGuardaSeguridad = cedulaGuardaSeguridad;
    }

    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getCedulaInvitado() {
        return cedulaInvitado;
    }

    public void setCedulaInvitado(int cedulaInvitado) {
        this.cedulaInvitado = cedulaInvitado;
    }


    public String getNombreCompletoInvitado() {
        return nombreCompletoInvitado;
    }

    public void setNombreCompletoInvitado(String nombreCompletoInvitado) {
        this.nombreCompletoInvitado = nombreCompletoInvitado;
    }

    public String getTipoInvitado() {
        return tipoInvitado;
    }

    public void setTipoInvitado(String tipoInvitado) {
        this.tipoInvitado = tipoInvitado;
    }
    
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getPlacaVehicular() {
        return placaVehicular;
    }

    public void setPlacaVehicular(String placaVehicular) {
        this.placaVehicular = placaVehicular;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Timestamp  getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp  fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Timestamp  getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp  fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCedulaGuardaSeguridad() {
        return cedulaGuardaSeguridad;
    }

    public void setCedulaGuardaSeguridad(int cedulaGuardaSeguridad) {
        this.cedulaGuardaSeguridad = cedulaGuardaSeguridad;
    }
    
    
    


    
    
    
}

