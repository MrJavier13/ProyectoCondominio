package com.appcondominio.web;


import com.appcondominio.service.RolTO;
import com.appcondominio.service.ServicioRol;
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

@ManagedBean(name = "rolesController")
@ViewScoped
public class RolesController implements Serializable{
    private RolTO rolSeleccionado;
 //   private boolean activo;
  //  private boolean selectOneMenuDisabled = false;
    private List<RolTO> rol = new ArrayList<>();
    private RolTO nuevoRol = new RolTO(); // Objeto para almacenar los datos del rol
//    private List<RolTO> filteredRoles;
    private String dialogHeader;

    
    @ManagedProperty("#{rolService}")
    private ServicioRol servicioRol;
    
    public RolesController() {
    }
    
    @PostConstruct
    public void init() {
        this.rol = servicioRol.mostrarRoles();
    }
    
    public void openNew() {
       this.rolSeleccionado = new RolTO();
     //  disableSelectOneMenu();
       dialogHeader = "Registrar nuevo rol";
       
    }
    public void openEdit() {
        this.rolSeleccionado = new RolTO();
       // enableSelectOneMenu(); 
        dialogHeader = "Editar rol";
       
    }
   /* 
    public void filtrarAmenidades() {
        filteredAmenidades.clear();
        for (AmenidadTO amenidad : amenidad) {
            if ((activo && "Activo".equals(amenidad.getEstado())) || (!activo && "Inactivo".equals(amenidad.getEstado()))) {
                filteredAmenidades.add(amenidad);
            }
        }
    } 
    
    public void disableSelectOneMenu() {
        selectOneMenuDisabled = true;
    }

    public void enableSelectOneMenu() {
        selectOneMenuDisabled = false;
    }**/
    
    public void guardarRol() {
        if (validarCampos()) {
            if (servicioRol.buscarIdRol(this.rolSeleccionado.getIdRol()) == null) { 

                servicioRol.insertarRol(rolSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Rol agregado"));

            } else {
                servicioRol.actualizarRol(rolSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Rol Actualizado"));
            }
            this.init();
            PrimeFaces.current().executeScript("PF('nuevoRolDialog').hide()");
            PrimeFaces.current().ajax().update("form:growl", "form:dt-roles");
        }
    }
    private boolean validarCampo(String valor, String nombreCampo, String nombreError) {
        if (valor == null || valor.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("form:" + nombreCampo,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo " + nombreError + " requerido",
                    "Por favor, ingrese el " + nombreError.toLowerCase() + "de la amenidad."));
            return false;
        }
        return true;
    }
    
    private boolean validarCampos() {
        if (rolSeleccionado.getIdRol() != null) {
        }
        return validarCampo(rolSeleccionado.getNombreRol(), "nombreRol", "nombre de rol") ;
    }


    
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public List<RolTO> getRol() {
        return rol;
    }

    public void setRol(List<RolTO> rol) {
        this.rol = rol;
    }

    public ServicioRol getServicioRol() {
        return servicioRol;
    }

    public void setServicioRol(ServicioRol servicioRol) {
        this.servicioRol = servicioRol;
    }

    public RolTO getNuevoRol() {
        return nuevoRol;
    }

    /*public boolean isSelectOneMenuDisabled() {
        return selectOneMenuDisabled;
    }

    public void setSelectOneMenuDisabled(boolean selectOneMenuDisabled) {
        this.selectOneMenuDisabled = selectOneMenuDisabled;
    }**/

    
    
    public void setNuevoRol(RolTO nuevaRol) {
        this.nuevoRol = nuevoRol;
    }

   /** public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<AmenidadTO> getFilteredAmenidades() {
        return filteredAmenidades;
    }

    public void setFilteredAmenidades(List<AmenidadTO> filteredAmenidades) {
        this.filteredAmenidades = filteredAmenidades;
    }

* **/
    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    

    
    public RolTO getRolSeleccionado() {
        return rolSeleccionado;
    }

    public void setRolSeleccionado(RolTO rolSeleccionado) {
        this.rolSeleccionado = rolSeleccionado;
    }
}