/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.service;

import java.io.Serializable;

/**
 *
 * @author aacas
 */
public class UsuarioTO implements Serializable{
    
    private Integer idUsuario;
    private String usuario;
    private String contrasena;
    private Integer cedulaResidente;
    private Integer cedulaEmpleado;
    private Integer cedulaAMostrar;
    private Integer idRol;
    private String estado;
    private String nombreCompleto;
    private String nombreRol;
    
    public UsuarioTO() {
    }

    public UsuarioTO(String usuario, String contrasenna) {
        this.usuario = usuario;
        this.contrasena = contrasenna;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Integer getCedulaResidente() {
        return cedulaResidente;
    }

    public void setCedulaResidente(Integer cedulaResidente) {
        this.cedulaResidente = cedulaResidente;
    }

    public Integer getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(Integer cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Integer getCedulaAMostrar() {
        return cedulaAMostrar;
    }

    public void setCedulaAMostrar(Integer cedulaAMostrar) {
        this.cedulaAMostrar = cedulaAMostrar;
    }
    
    
    
    

    public UsuarioTO(Integer idUsuario, String usuario, Integer cedulaAMostrar, String nombreCompleto, String nombreRol, Integer idRol, String estado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.cedulaAMostrar = cedulaAMostrar;
        this.nombreCompleto = nombreCompleto;
        this.nombreRol = nombreRol;
        this.idRol = idRol;
        this.estado = estado;
    }
}
