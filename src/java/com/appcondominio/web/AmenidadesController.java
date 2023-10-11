package com.appcondominio.web;

import com.appcondominio.service.AmenidadTO;
import com.appcondominio.service.ServicioAmenidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;

@ManagedBean(name = "amenidadesController")
@ViewScoped
public class AmenidadesController implements Serializable{
    private AmenidadTO amenidadSeleccionada;
    private List<AmenidadTO> amenidad = new ArrayList<>();
    private AmenidadTO nuevaAmenidad = new AmenidadTO(); // Objeto para almacenar los datos de la nueva amenidad

    @ManagedProperty("#{amenidadService}")
    private ServicioAmenidad servicioAmenidad;

    public AmenidadesController() {
    }
    
    @PostConstruct
    public void init() {
        this.amenidad = servicioAmenidad.mostrarAmenidades();
    }
    
    public void openNew() {
       this.amenidadSeleccionada = new AmenidadTO();
       
    }

    public List<AmenidadTO> getAmenidad() {
        return amenidad;
    }

    public void setAmenidad(List<AmenidadTO> amenidad) {
        this.amenidad = amenidad;
    }

    public ServicioAmenidad getServicioAmenidad() {
        return servicioAmenidad;
    }

    public void setServicioAmenidad(ServicioAmenidad servicioAmenidad) {
        this.servicioAmenidad = servicioAmenidad;
    }

    public AmenidadTO getNuevaAmenidad() {
        return nuevaAmenidad;
    }

    public void setNuevaAmenidad(AmenidadTO nuevaAmenidad) {
        this.nuevaAmenidad = nuevaAmenidad;
    }

    // Método para agregar una nueva amenidad
    public void agregar() {
        // Aquí va la lógica para agregar una nueva amenidad
    }

    // Método para editar una amenidad existente
    public void editar(AmenidadTO amenidad) {
        // Aquí va la lógica para editar una amenidad
    }

    // Método para deshabilitar una amenidad
    public void deshabilitar(AmenidadTO amenidad) {
        // Aquí va la lógica para deshabilitar una amenidad
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Celda cambiada", "Antiguo: " + oldValue + ", Nuevo:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
        public AmenidadTO getAmenidadSeleccionada() {
        return amenidadSeleccionada;
    }

    public void setAmenidadSeleccionada(AmenidadTO amenidadSeleccionada) {
        this.amenidadSeleccionada = amenidadSeleccionada;
    }
}

