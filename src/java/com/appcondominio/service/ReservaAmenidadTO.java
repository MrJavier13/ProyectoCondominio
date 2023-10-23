
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
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Integer cedulaResidente;
    private String estado;

    public ReservaAmenidadTO(){
        
    }
    public ReservaAmenidadTO(Integer idReservaAmenidad, Integer idAmenidad, Timestamp fechaInicio, Timestamp fechaFin, Integer cedulaResidente, String estado) {
        this.idReservaAmenidad = idReservaAmenidad;
        this.idAmenidad = idAmenidad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cedulaResidente = cedulaResidente;
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

    
    

}
