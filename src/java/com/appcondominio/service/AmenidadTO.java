
package com.appcondominio.service;

import java.io.Serializable;

/**
 *
 * @author meryan
 */
public class AmenidadTO implements Serializable{
    
    private int idAmenidad;
    private String nombreAmenidad;
    private String descripcion;
    private String estado;

    public AmenidadTO() {
    }

    public AmenidadTO(Integer idAmenidad, String nombreAmenidad, String descripcion, String estado) {
        this.idAmenidad = idAmenidad;
        this.nombreAmenidad = nombreAmenidad;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public Integer getIdAmenidad() {
        return idAmenidad;
    }

    public void setIdAmenidad(Integer idAmenidad) {
        this.idAmenidad = idAmenidad;
    }

    public String getNombreAmenidad() {
        return nombreAmenidad;
    }

    public void setNombreAmenidad(String nombreAmenidad) {
        this.nombreAmenidad = nombreAmenidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    

}
