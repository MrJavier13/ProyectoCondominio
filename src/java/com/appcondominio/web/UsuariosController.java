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
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;

/**
 *
 * @author aacas
 */

@ManagedBean(name = "usuariosController")
@ViewScoped
public class UsuariosController implements Serializable{
    private List<UsuarioTO> usuario = new ArrayList<>();
    private UsuarioTO usuarioSeleccionado;
    private List<UsuarioTO> filteredUsuarios;
    private boolean activo;
    
    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;
    
    public UsuariosController() {
    }
    
    @PostConstruct
    public void init() {
        this.usuario = servicioUsuario.mostrarUsuarios();
        this.filteredUsuarios = this.usuario.stream()
            .filter(usuario -> "Activo".equals(usuario.getEstado()))
            .collect(Collectors.toList());
        this.activo = true;
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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    
    public void setServicioUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    public List<UsuarioTO> getFilteredUsuarios() {
        return filteredUsuarios;
    }

    public void setFilteredUsuarios(List<UsuarioTO> filteredUsuarios) {
        this.filteredUsuarios = filteredUsuarios;
    }

    
    
    public UsuarioTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    
    
    public void residentes() {

        this.redireccionar("/faces/residentes.xhtml");

    }
    public void actualizarUsuario() {
                servicioUsuario.actualizarUsuario(usuarioSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario Actualizado"));
                PrimeFaces.current().executeScript("PF('nuevoUsuarioDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-usuarios");
    }
    
    public void filtrarUsuarios() {
        filteredUsuarios.clear();
        for (UsuarioTO usuario : usuario) {
            if ((activo && "Activo".equals(usuario.getEstado())) || (!activo && "Inactivo".equals(usuario.getEstado()))) {
                filteredUsuarios.add(usuario);
            }
        }
    }
    
    public void openEdit() {
        this.usuarioSeleccionado = new UsuarioTO();
        this.usuarioSeleccionado.setEstado("Activo");
       
    }
    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redireccionar("/faces/index.xhtml");
    }
    public void principal() {

    this.redireccionar("/faces/principal.xhtml");

    }
    
    public void personal() {

    this.redireccionar("/faces/personal.xhtml");

    }
    
    public void amenidades() {

    this.redireccionar("/faces/amenidades.xhtml");

    }
    
    public void reservaAmenidades() {

    this.redireccionar("/faces/reservas_amenidades.xhtml");

    }
    
    public void roles() {

    this.redireccionar("/faces/roles.xhtml");

    }
    
    public void redireccionar(String ruta) {
    HttpServletRequest request;
    try {
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
    } catch (Exception e) {

    }
    }
    
}
