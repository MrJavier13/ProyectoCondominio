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
    private int idRol;
    private String estado;
    
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

    public UsuarioTO(Integer idUsuario, String usuario, String contrasena, Integer cedulaResidente, Integer cedulaEmpleado, int idRol, String estado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.cedulaResidente = cedulaResidente;
        this.cedulaEmpleado = cedulaEmpleado;
        this.idRol = idRol;
        this.estado = estado;
    }
}
