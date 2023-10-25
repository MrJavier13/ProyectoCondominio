
package com.appcondominio.service;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author meryan
 */
public class ReservaAmenidadTO implements Serializable{
    
    private Integer idReservaAmenidad;
    private Integer idAmenidad; 
    private String nombreAmenidad;
    private String descripcionAmenidad;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Integer cedulaResidente;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private int numeroCasa;
    private String estado;
    private ResidenteTO residente;
    private AmenidadTO amenidad;

    public ReservaAmenidadTO(){
        
    }
    public ReservaAmenidadTO(Integer idReservaAmenidad, String nombreAmenidad, String descripcionAmenidad, Timestamp fechaInicio, Timestamp fechaFin, String nombre, String primerApellido, String segundoApellido, int numeroCasa, String estado) {
    this.idReservaAmenidad = idReservaAmenidad;
    this.nombreAmenidad = nombreAmenidad;
    this.descripcionAmenidad = descripcionAmenidad;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.nombre = nombre;
    this.primerApellido = primerApellido;
    this.segundoApellido = segundoApellido;
    this.numeroCasa = numeroCasa;
    this.estado = estado;
}

    public Integer getIdReservaAmenidad() {
        return idReservaAmenidad;
    }

    public void setIdReservaAmenidad(Integer idReservaAmenidad) {
        this.idReservaAmenidad = idReservaAmenidad;
    }

    public Integer getIdAmenidad() {
        return idAmenidad;
    }

    public void setIdAmenidad(Integer idAmenidad) {
        this.idAmenidad = idAmenidad;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getCedulaResidente() {
        return cedulaResidente;
    }

    public void setCedulaResidente(Integer cedulaResidente) {
        this.cedulaResidente = cedulaResidente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreAmenidad() {
        return nombreAmenidad;
    }

    public void setNombreAmenidad(String nombreAmenidad) {
        this.nombreAmenidad = nombreAmenidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public int getNumeroCasa() {
        return numeroCasa;
    }

    public void setNumeroCasa(int numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    public String getDescripcionAmenidad() {
        return descripcionAmenidad;
    }

    public void setDescripcionAmenidad(String descripcionAmenidad) {
        this.descripcionAmenidad = descripcionAmenidad;
    }
    
    

  
    
    
    
    public ResidenteTO getResidente() {
        return residente;
    }

    public void setResidente(ResidenteTO residente) {
        this.residente = residente;
    }

    public AmenidadTO getAmenidad() {
        return amenidad;
    }

    public void setAmenidad(AmenidadTO amenidad) {
        this.amenidad = amenidad;
    }
}
    
    

    
    


