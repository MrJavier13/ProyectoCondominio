package com.appcondominio.web;


import com.appcondominio.service.AmenidadTO;
import com.appcondominio.service.ReservaAmenidadTO;
import com.appcondominio.service.ServicioReservaAmenidad;
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

@ManagedBean(name = "reservasAmenidadesController")
@ViewScoped
public class ReservasAmenidadesController implements Serializable{
    private ReservaAmenidadTO reservaAmenidadSeleccionada;
    private boolean activo;
    private boolean selectOneMenuDisabled = false;
    private List<ReservaAmenidadTO> reservaAmenidad = new ArrayList<>();
    private ReservaAmenidadTO nuevaReservaAmenidad = new ReservaAmenidadTO(); // Objeto para almacenar los datos de la nueva amenidad
    private List<ReservaAmenidadTO> filteredReservasAmenidades;
    private String dialogHeader;

    
    @ManagedProperty("#{reservaAmenidadService}")
    private ServicioReservaAmenidad servicioReservaAmenidad;
    
    public ReservasAmenidadesController() {
    }
    
    @PostConstruct
    public void init() {
        this.reservaAmenidad = servicioReservaAmenidad.mostrarReservas();
        this.filteredReservasAmenidades = this.reservaAmenidad.stream()
        .filter(reservaAmenidad -> "Activo".equals(reservaAmenidad.getEstado()))
        .collect(Collectors.toList());
        this.activo = true;
    }
    
    /*public void openNew() {
       this.amenidadSeleccionada = new AmenidadTO();
       disableSelectOneMenu();
       dialogHeader = "Registrar nueva amenidad";
       
    }**/
    
    public void openEdit() {
        this.reservaAmenidadSeleccionada = new ReservaAmenidadTO();
        this.reservaAmenidadSeleccionada.setEstado("Activo");
        enableSelectOneMenu(); 
        dialogHeader = "Editar estado de la reserva";
       
    }
    
    public void filtrarReservasAmenidades() {
        filteredReservasAmenidades.clear();
        for (ReservaAmenidadTO reservaAmenidad : reservaAmenidad) {
            if ((activo && "Activo".equals(reservaAmenidad.getEstado())) || (!activo && "Inactivo".equals(reservaAmenidad.getEstado()))) {
                filteredReservasAmenidades.add(reservaAmenidad);
            }
        }
    }
    
    public void disableSelectOneMenu() {
        selectOneMenuDisabled = true;
    }

    public void enableSelectOneMenu() {
        selectOneMenuDisabled = false;
    }
    
    public void guardarReserva() {

        if (!servicioReservaAmenidad.buscarIdReserva(this.reservaAmenidadSeleccionada.getIdReservaAmenidad())) { 

                servicioReservaAmenidad.insertarReserva(reservaAmenidadSeleccionada);
                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reserva agregada"));
                
            } else {
                servicioReservaAmenidad.actualizarReserva(reservaAmenidadSeleccionada);
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Reserva Actualizada"));
            }
        this.init();
        PrimeFaces.current().executeScript("PF('nuevaReservaDialog').hide()");
        PrimeFaces.current().ajax().update("form:growl", "form:dt-reservasAmenidades");
    }
    
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public List<ReservaAmenidadTO> getReservaAmenidad() {
        return reservaAmenidad;
    }

    public void setReservaAmenidad(List<ReservaAmenidadTO> reservaAmenidad) {
        this.reservaAmenidad = reservaAmenidad;
    }

    public ServicioReservaAmenidad getServicioReservaAmenidad() {
        return servicioReservaAmenidad;
    }

    public void setServicioReservaAmenidad(ServicioReservaAmenidad servicioReservaAmenidad) {
        this.servicioReservaAmenidad = servicioReservaAmenidad;
    }

    public ReservaAmenidadTO getNuevaReservaAmenidad() {
        return nuevaReservaAmenidad;
    }

    public boolean isSelectOneMenuDisabled() {
        return selectOneMenuDisabled;
    }

    public void setSelectOneMenuDisabled(boolean selectOneMenuDisabled) {
        this.selectOneMenuDisabled = selectOneMenuDisabled;
    }

    
    
    public void setNuevaReservaAmenidad(ReservaAmenidadTO nuevaReservaAmenidad) {
        this.nuevaReservaAmenidad = nuevaReservaAmenidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<ReservaAmenidadTO> getFilteredReservasAmenidades() {
        return filteredReservasAmenidades;
    }

    public void setFilteredReservasAmenidades(List<ReservaAmenidadTO> filteredReservasAmenidades) {
        this.filteredReservasAmenidades = filteredReservasAmenidades;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    

    
    public ReservaAmenidadTO getReservaAmenidadSeleccionada() {
        return reservaAmenidadSeleccionada;
    }

    public void setReservaAmenidadSeleccionada(ReservaAmenidadTO reservaAmenidadSeleccionada) {
        this.reservaAmenidadSeleccionada = reservaAmenidadSeleccionada;
    }
}