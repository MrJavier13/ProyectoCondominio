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
    
    private String correoElectronico;
    private String contrasenna;
    private int rol;
    private String estado;

    public UsuarioTO() {
    }

    public UsuarioTO(String correoElectronico, String contrasenna) {
        this.correoElectronico = correoElectronico;
        this.contrasenna = contrasenna;
    }
    

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
    
    
}
