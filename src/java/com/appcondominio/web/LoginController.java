/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.web;

import com.appcondominio.service.ServicioUsuario;
import com.appcondominio.service.UsuarioTO;
import com.captcha.botdetect.web.jsf.JsfCaptcha;
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
    private JsfCaptcha captcha;
    private String captchaCode;

    public LoginController() {
    }

    public void ingresar() {
        ServicioUsuario servicioUsuario = new ServicioUsuario();

        this.usuario = servicioUsuario.UsuarioContrasenna(correo, contrasenna);
        boolean isHuman = captcha.validate(captchaCode);
        
        if (isHuman) {
            if (this.usuario != null) {
                if (this.usuario.getEstado().equals("Activo")) {
                    String nombreRol = servicioUsuario.obtenerNombreRolPorId(this.usuario.getIdRol());

                    if ("Administrador".equals(nombreRol) || "Admin".equals(nombreRol)) {
                        // si el nombre del rol es "Administrador", lleva a la página principal
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario);
                        this.redireccionar("/faces/principal.xhtml");
                    } else if ("Guarda".equals(nombreRol)) {
                        // si el nombre del rol es "Guarda", lleva a la bitácora para guardas como página inicial
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", this.usuario);
                        this.redireccionar("/faces/bitacoraVistaGuarda.xhtml");
                    } else {
                        // Abierto a un futuro rol
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cuenta del usuario ingresado actualmente se encuentra inactiva"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage("sticky-key", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campos inválidos", "El usuario o contraseña no son correctos"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("captchaError", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error del captcha", "Por favor vuelve a escribirlo."));
        }
    }

    public void verificarSesion() {
        try {
            UsuarioTO usuario = (UsuarioTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if (usuario == null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./faces/index.xhtml");
            }
        } catch (Exception e) {

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

    public JsfCaptcha getCaptcha() {
        return captcha;
    }

    public void setCaptcha(JsfCaptcha captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
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
