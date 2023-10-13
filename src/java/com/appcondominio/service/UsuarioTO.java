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
    
    private int idUsuario;
    private String usuario;
    private String contrasena;
    private int cedulaResidente;
    private int cedulaEmpleado;
    private int idRol;
    private String estado;
    
    public UsuarioTO() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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

    public int getCedulaResidente() {
        return cedulaResidente;
    }

    public void setCedulaResidente(int cedulaResidente) {
        this.cedulaResidente = cedulaResidente;
    }

    public int getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(int cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public UsuarioTO(int idUsuario, String usuario, String contrasena, int cedulaResidente, int cedulaEmpleado, int idRol, String estado) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.cedulaResidente = cedulaResidente;
        this.cedulaEmpleado = cedulaEmpleado;
        this.idRol = idRol;
        this.estado = estado;
    }


}
