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
public class ResidenteTO implements Serializable{
    
     private String cedula;
    private String nombre;
    private String apellido;
    private int telefono;
    private int numeroCasa;
    private String correoElectronico;
    private int tipoUsuario;
    private String placaVehiculo;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public ResidenteTO() {
    }

    public ResidenteTO(String cedula, String nombre, String apellido, int telefono, int numeroCasa, String correoElectronico, int tipoUsuario, String placaVehiculo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.numeroCasa = numeroCasa;
        this.correoElectronico = correoElectronico;
        this.tipoUsuario = tipoUsuario;
        this.placaVehiculo = placaVehiculo;
    }

    
    
    
}
