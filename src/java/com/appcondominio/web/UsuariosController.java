/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.web;

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

@ManagedBean(name = "usuariosController")
@ViewScoped
public class UsuariosController implements Serializable{
    private List<UsuarioTO> usuario = new ArrayList<>();
    
    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;

    public UsuariosController() {
    }
    
    @PostConstruct
    public void init() {
        this.usuario = servicioUsuario.mostrarUsuarios();
    }

    public List<UsuarioTO> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<UsuarioTO> usuario) {
        this.usuario = usuario;
    }

    public ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
    }

    public void setServicioUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }
    
    
    
}
