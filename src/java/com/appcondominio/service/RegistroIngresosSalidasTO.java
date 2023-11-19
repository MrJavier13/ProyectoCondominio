/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.service;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author mer
 */
public class RegistroIngresosSalidasTO implements Serializable {

    private int idRegistro;
    private Integer cedulaInvitadoTemporal;
    private Integer cedulaInvitadoPermanente;
    private Integer cedulaAMostrar;
    private String nombreCompletoInvitado;
    private String nombreEmpresa;
    private String placaVehicular;
    private String detalle;
    private Timestamp fechaIngreso;
    private Timestamp fechaSalida;
    private Date fechaIngresoDate;
    private Date fechaSalidaDate;
    private Integer cedulaGuardaSeguridad;
    private String nombreGuardaSeguridad;
    private String primerApellidoGuarda;
    private String segundoApellidoGuarda;

    public RegistroIngresosSalidasTO() {
    }

    public RegistroIngresosSalidasTO(Integer idRegistro, Integer cedulaInvitadoPermanente, Integer cedulaInvitadoTemporal, Integer cedulaAMostrar, String nombreCompletoInvitado, String nombreEmpresa, String placaVehicular, String detalle, Timestamp fechaIngreso, Timestamp fechaSalida, Date fechaIngresoDate, Date fechaSalidaDate, int cedulaGuardaSeguridad, String nombreGuardaSeguridad, String primerApellidoGuarda, String segundoApellidoGuarda) {
        this.idRegistro = idRegistro;
        this.cedulaInvitadoPermanente = cedulaInvitadoPermanente;
        this.cedulaInvitadoTemporal = cedulaInvitadoTemporal;
        this.cedulaAMostrar = cedulaAMostrar;
        this.nombreCompletoInvitado = nombreCompletoInvitado;
        this.nombreEmpresa = nombreEmpresa;
        this.placaVehicular = placaVehicular;
        this.detalle = detalle;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.fechaIngresoDate = fechaIngresoDate;
        this.fechaSalidaDate = fechaSalidaDate;
        this.cedulaGuardaSeguridad = cedulaGuardaSeguridad;
        this.nombreGuardaSeguridad = nombreGuardaSeguridad;
        this.primerApellidoGuarda = primerApellidoGuarda;
        this.segundoApellidoGuarda = segundoApellidoGuarda;

    }

    public Integer getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Integer getCedulaInvitadoTemporal() {
        return cedulaInvitadoTemporal;
    }

    public void setCedulaInvitadoTemporal(Integer cedulaInvitadoTemporal) {
        this.cedulaInvitadoTemporal = cedulaInvitadoTemporal;
    }

    public Integer getCedulaInvitadoPermanente() {
        return cedulaInvitadoPermanente;
    }

    public void setCedulaInvitadoPermanente(Integer cedulaInvitadoPermanente) {
        this.cedulaInvitadoPermanente = cedulaInvitadoPermanente;
    }

    public Integer getCedulaAMostrar() {
        return cedulaAMostrar;
    }

    public void setCedulaAMostrar(Integer cedulaAMostrar) {
        this.cedulaAMostrar = cedulaAMostrar;
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

    public Integer getCedulaGuardaSeguridad() {
        return cedulaGuardaSeguridad;
    }

    public void setCedulaGuardaSeguridad(Integer cedulaGuardaSeguridad) {
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

    public Date getFechaIngresoDate() {
        return fechaIngresoDate;
    }

    public void setFechaIngresoDate(Date fechaIngresoDate) {
        this.fechaIngresoDate = fechaIngresoDate;
    }

    public Date getFechaSalidaDate() {
        return fechaSalidaDate;
    }

    public void setFechaSalidaDate(Date fechaSalidaDate) {
        this.fechaSalidaDate = fechaSalidaDate;
    }

}
