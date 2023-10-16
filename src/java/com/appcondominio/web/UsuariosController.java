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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author aacas
 */

@ManagedBean(name = "usuariosController")
@ViewScoped
public class UsuariosController implements Serializable{
    private UsuarioTO usuarioSeleccionado;
    private List<UsuarioTO> usuario = new ArrayList<>();
    
    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;

    public UsuariosController() {
    }
    
    @PostConstruct
    public void init() {
        this.usuario = servicioUsuario.mostrarUsuarios();
    }
    
    public void openNew() {
       this.usuarioSeleccionado = new UsuarioTO();
       
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
    
    public UsuarioTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    public void guardarUsuario() {
        if (!servicioUsuario.buscarUsuario(this.usuarioSeleccionado.getUsuario())) { // Si es false inserta
            servicioUsuario.insertarUsuario(usuarioSeleccionado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Agregado"));
        } else {
            servicioUsuario.actualizarUsuario(usuarioSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Actualizado"));
        }
        this.init();
        PrimeFaces.current().executeScript("PF('nuevoResidenteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-residentes");
    }
    
    
    
}
