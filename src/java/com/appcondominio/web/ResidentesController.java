package com.appcondominio.web;

import com.appcondominio.service.ResidenteTO;
import com.appcondominio.service.ServicioResidente;
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

@ManagedBean(name = "residentesController")
@ViewScoped
public class ResidentesController implements Serializable{
    private ResidenteTO residenteSeleccionado;
    private UsuarioTO usuarioSeleccionado;
    private UsuariosController usuariosController;
    private ServicioUsuario servicioUsuario;
    
    private List<ResidenteTO> residente = new ArrayList<>();
    
    @ManagedProperty("#{residenteService}")
    private ServicioResidente servicioResidente;
    
   

    public ResidentesController() {
    }
    
    @PostConstruct
    public void init() {
        this.residente = servicioResidente.mostrarResidentes();
    }
    
    public void openNew() {
       this.residenteSeleccionado = new ResidenteTO();
      
       
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
    
    public void guardarResidente() {
        if (!servicioResidente.buscarCedulaResidente(this.residenteSeleccionado.getCedula())) { // Si es false inserta
            servicioResidente.insertarResidente(residenteSeleccionado);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Residente Agregado"));
        } else {
            servicioResidente.actualizarResidente(residenteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Residente Actualizado"));
        }
        this.init();
        PrimeFaces.current().executeScript("PF('nuevoResidenteDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-residentes");
    }
    
    public void guardarResidenteYUsuario() {
    if (!servicioResidente.buscarCedulaResidente(this.residenteSeleccionado.getCedula())) { // Si es false inserta
        servicioResidente.insertarResidente(residenteSeleccionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Residente Agregado"));

        // Crear un nuevo usuario cada vez que se crea un nuevo residente
     /*   UsuarioTO nuevoUsuario = new UsuarioTO();
        nuevoUsuario.setUsuario(this.residenteSeleccionado.getCorreoElectronico()); // Usar el email como nombre de usuario
        nuevoUsuario.setContrasena(String.valueOf(this.residenteSeleccionado.getCedula())); //usar cédula como
        nuevoUsuario.setCedulaResidente(this.residenteSeleccionado.getCedula());
        nuevoUsuario.setCedulaEmpleado(null);
        nuevoUsuario.setIdRol(1); // esto hay que revisarlo
        nuevoUsuario.setEstado("Activo"); //esto hay que revisarlo

        servicioUsuario.insertarUsuario(nuevoUsuario);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Usuario Agregado"));**/
    } else {
        servicioResidente.actualizarResidente(residenteSeleccionado);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Residente Actualizado"));
    }
    this.init();
    PrimeFaces.current().executeScript("PF('nuevoResidenteDialog').hide()");
    PrimeFaces.current().ajax().update("form:messages", "form:dt-residentes");
}

    
    
    
}
