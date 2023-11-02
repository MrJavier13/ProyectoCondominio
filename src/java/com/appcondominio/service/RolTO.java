
package com.appcondominio.service;

import java.io.Serializable;

/**
 *
 * @author meryan
 */
public class RolTO implements Serializable{
    
    private int idRol;
    private String nombreRol;
 
    public RolTO() {
    }

    public RolTO(int idRol, String nombreRol) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
  
    
}

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }


}
    
    
    