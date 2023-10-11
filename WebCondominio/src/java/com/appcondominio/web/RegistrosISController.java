/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.web;

import com.appcondominio.service.RegistroIngresosSalidasTO;
import com.appcondominio.service.ServicioRegistroIS;
import com.appcondominio.service.ServicioUsuario;
import com.appcondominio.service.UsuarioTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author aacas
 */

@ManagedBean(name = "registrosISController")
@ViewScoped
public class RegistrosISController implements Serializable{
    private List<RegistroIngresosSalidasTO> registroIS = new ArrayList<>();
    
    @ManagedProperty("#{registroISService}")
    private ServicioRegistroIS servicioRegistroIS;

    public RegistrosISController() {
    }
    
    @PostConstruct
    public void init() {
        this.registroIS = servicioRegistroIS.mostrarRegistro();
    }

    public List<RegistroIngresosSalidasTO> getRegistroIS() {
        return registroIS;
    }

    public void setUsuario(List<RegistroIngresosSalidasTO> registroIS) {
        this.registroIS = registroIS;
    }

    public ServicioRegistroIS getServicioRegistroIS() {
        return servicioRegistroIS;
    }

    public void setServicioRegistroIS(ServicioRegistroIS servicioRegistroIS) {
        this.servicioRegistroIS = servicioRegistroIS;
    }
    
    
    
}

