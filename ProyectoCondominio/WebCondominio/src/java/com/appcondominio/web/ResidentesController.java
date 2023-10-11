package com.appcondominio.web;

import com.appcondominio.service.ResidenteTO;
import com.appcondominio.service.ServicioResidente;
import com.appcondominio.service.ServicioUsuario;
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

@ManagedBean(name = "residentesController")
@ViewScoped
public class ResidentesController implements Serializable{
    private ResidenteTO residenteSeleccionado;
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
    
    
    
}
