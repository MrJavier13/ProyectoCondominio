/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.web;

import com.appcondominio.service.ServicioUsuario;
import com.appcondominio.service.UsuarioTO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    private UsuarioTO usuario = new UsuarioTO();
    private String correo;
    private String contrasenna;
    
    
    public LoginController() {
    }

    public void ingresar() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();
        
        this.usuario = servicioUsuario.UsuarioContrasenna(correo, contrasenna);
        
        if(this.usuario != null){
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario);
            this.redireccionar("/faces/principal.xhtml");

        } else {
            FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "El usuario o contraseña no son correctos"));
        }
        

    }
    
    public void verificarSesion(){
        try{
           UsuarioTO usuario = (UsuarioTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
           if(usuario == null){
               FacesContext.getCurrentInstance().getExternalContext().redirect("./faces/index.xhtml");
           }
        } catch(Exception e){
            
        }
    }
    
    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.redireccionar("/faces/index.xhtml");
    }
    
    public void registrar() {

        this.redireccionar("/faces/registrar.xhtml");

    }
    
    public void usuarios() {

        this.redireccionar("/faces/usuarios.xhtml");

    }
    
    public void amenidades() {

        this.redireccionar("/faces/amenidades.xhtml");

    }
    
    public void residentes() {

        this.redireccionar("/faces/residentes.xhtml");

    }
    
    public void personal() {

        this.redireccionar("/faces/personal.xhtml");

    }
    
    public void roles() {

        this.redireccionar("/faces/roles.xhtml");

    }
    
    public void bitacora() {

        this.redireccionar("/faces/bitacora.xhtml");

    }
    
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public UsuarioTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioTO usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

   
    
    
}
