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
    private int cedulaInvitadoTemporal;
    private int cedulaInvitadoPermanente;
    private String nombreCompletoInvitado;
    private String nombreEmpresa;
    private String placaVehicular;
    private String detalle;
    private Timestamp  fechaIngreso;
    private Timestamp  fechaSalida;
    private int cedulaGuardaSeguridad;
    private String nombreGuardaSeguridad;
    private String primerApellidoGuarda;
    private String segundoApellidoGuarda;

    public RegistroIngresosSalidasTO() {
    }

    public RegistroIngresosSalidasTO(int idRegistro, int cedulaInvitadoTemporal, int cedulaInvitadoPermanente, String nombreCompletoInvitado, String nombreEmpresa, String placaVehicular, String detalle, Timestamp fechaIngreso, Timestamp fechaSalida, String nombreGuardaSeguridad, String primerApellidoGuarda, String segundoApellidoGuarda) {
        this.idRegistro = idRegistro;
        this.cedulaInvitadoTemporal = cedulaInvitadoTemporal;
        this.cedulaInvitadoPermanente = cedulaInvitadoPermanente;
        this.nombreCompletoInvitado = nombreCompletoInvitado;
        this.nombreEmpresa = nombreEmpresa;
        this.placaVehicular = placaVehicular;
        this.detalle = detalle;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.nombreGuardaSeguridad = nombreGuardaSeguridad;
        this.primerApellidoGuarda = primerApellidoGuarda;
        this.segundoApellidoGuarda = segundoApellidoGuarda;
        
    }

    
    public int getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(int idRegistro) {
        this.idRegistro = idRegistro;
    }

    public int getCedulaInvitadoTemporal() {
        return cedulaInvitadoTemporal;
    }

    public void setCedulaInvitadoTemporal(int cedulaInvitadoTemporal) {
        this.cedulaInvitadoTemporal = cedulaInvitadoTemporal;
    }

    public int getCedulaInvitadoPermanente() {
        return cedulaInvitadoPermanente;
    }

    public void setCedulaInvitadoPermanente(int cedulaInvitadoPermanente) {
        this.cedulaInvitadoPermanente = cedulaInvitadoPermanente;
    }

    public String getNombreCompletoInvitado() {
        return nombreCompletoInvitado;
    }

    public void setNombreCompletoInvitado(String nombreCompletoInvitado) {
        this.nombreCompletoInvitado = nombreCompletoInvitado;
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

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Timestamp getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Timestamp fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getCedulaGuardaSeguridad() {
        return cedulaGuardaSeguridad;
    }

    public void setCedulaGuardaSeguridad(int cedulaGuardaSeguridad) {
        this.cedulaGuardaSeguridad = cedulaGuardaSeguridad;
    }

    public String getNombreGuardaSeguridad() {
        return nombreGuardaSeguridad;
    }

    public void setNombreGuardaSeguridad(String nombreGuardaSeguridad) {
        this.nombreGuardaSeguridad = nombreGuardaSeguridad;
    }

    public String getPrimerApellidoGuarda() {
        return primerApellidoGuarda;
    }

    public void setPrimerApellidoGuarda(String primerApellidoGuarda) {
        this.primerApellidoGuarda = primerApellidoGuarda;
    }

    public String getSegundoApellidoGuarda() {
        return segundoApellidoGuarda;
    }

    public void setSegundoApellidoGuarda(String segundoApellidoGuarda) {
        this.segundoApellidoGuarda = segundoApellidoGuarda;
    }
    
    
    
    


}