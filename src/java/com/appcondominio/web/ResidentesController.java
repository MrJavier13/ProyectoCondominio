package com.appcondominio.web;

import com.appcondominio.service.ResidenteTO;
import com.appcondominio.service.ServicioResidente;
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

@ManagedBean(name = "residentesController")
@ViewScoped
public class ResidentesController implements Serializable{
    private ResidenteTO residenteSeleccionado;
    private boolean activo;
    private boolean selectOneMenuDisabled = false;
    private List<ResidenteTO> residente = new ArrayList<>();
    private List<ResidenteTO> filteredResidentes;
    private String dialogHeader;
    
    @ManagedProperty("#{residenteService}")
    private ServicioResidente servicioResidente;
    
    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;

    public ResidentesController() {
    }
    
    @PostConstruct
    public void init() {
        this.residente = servicioResidente.mostrarResidentes();
        this.filteredResidentes = this.residente.stream()
        .filter(residente -> "Activo".equals(residente.getEstado()))
        .collect(Collectors.toList());
        this.activo = true;
    }
    
    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }
    
    public boolean isSelectOneMenuDisabled() {
        return selectOneMenuDisabled;
    }

    public void disableSelectOneMenu() {
        selectOneMenuDisabled = true;
    }

    public void enableSelectOneMenu() {
        selectOneMenuDisabled = false;
    }
    
    public void openNew() {
       this.residenteSeleccionado = new ResidenteTO();
       this.residenteSeleccionado.setEstado("Activo");
       disableSelectOneMenu();
       dialogHeader = "Registrar nuevo residente";
       
    }
    
    public void openEdit() {
        this.residenteSeleccionado = new ResidenteTO();
        enableSelectOneMenu(); 
        dialogHeader = "Editar residente";
       
}

    public List<ResidenteTO> getResidente() {
        return residente;
    }

    public void setResidente(List<ResidenteTO> residente) {
        this.residente = residente;
    }

    public ServicioResidente getServicioResidente() {
        return servicioResidente;
    }

    public void setServicioResidente(ServicioResidente servicioResidente) {
        this.servicioResidente = servicioResidente;
    }

    public ResidenteTO getResidenteSeleccionado() {
        return residenteSeleccionado;
    }

    public void setResidenteSeleccionado(ResidenteTO residenteSeleccionado) {
        this.residenteSeleccionado = residenteSeleccionado;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ServicioUsuario getServicioUsuario() {
        return servicioUsuario;
    }

    public void setServicioUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    public List<ResidenteTO> getFilteredResidentes() {
        return filteredResidentes;
    }

    public void setFilteredResidentes(List<ResidenteTO> filteredResidentes) {
        this.filteredResidentes = filteredResidentes;
    }
    
    
    public void filtrarResidentes() {
        filteredResidentes.clear();
        for (ResidenteTO residente : residente) {
            if ((activo && "Activo".equals(residente.getEstado())) || (!activo && "Inactivo".equals(residente.getEstado()))) {
                filteredResidentes.add(residente);
            }
        }
    }
        public void principal() {

        this.redireccionar("/faces/principal.xhtml");

    }
    
    public void usuarios() {

        this.redireccionar("/faces/usuarios.xhtml");

    }
    
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }
    
    
    public void guardarResidenteYUsuario() {
        if (!correoValido(residenteSeleccionado.getCorreoElectronico())) {
            FacesContext.getCurrentInstance().addMessage("form:correoElectronico", 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo electrónico inválido", 
                    "Por favor, ingrese un correo electrónico válido. Ejemplo: juan@gmail.com"));
            return;
        }

        if (!servicioResidente.buscarCedulaResidente(this.residenteSeleccionado.getCedula())) { 
            // Si es false inserta
                servicioResidente.insertarResidente(residenteSeleccionado);
                UsuarioTO usuario = new UsuarioTO();
                usuario.setCedulaResidente(residenteSeleccionado.getCedula());
                usuario.setUsuario(residenteSeleccionado.getCorreoElectronico());
                usuario.setContrasena(String.valueOf(residenteSeleccionado.getCedula()));
                usuario.setEstado(residenteSeleccionado.getEstado());
                usuario.setIdRol(1);
                servicioUsuario.insertarUsuarioResidente(usuario);
                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Residente y usuario agregado"));
        } else {

                servicioResidente.actualizarResidente(residenteSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Residente Actualizado"));
        }
        this.init();
        PrimeFaces.current().executeScript("PF('nuevoResidenteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-residentes");
    }

        private boolean correoValido(String email) {
            String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

}
