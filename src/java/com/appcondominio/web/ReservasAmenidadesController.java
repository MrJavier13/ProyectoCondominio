package com.appcondominio.web;


import com.appcondominio.service.AmenidadTO;
import com.appcondominio.service.ReservaAmenidadTO;
import com.appcondominio.service.ServicioReservaAmenidad;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    private String dialogHeader;
    private Date fechaInicial;
    private Date fechaFinal;
    
    @ManagedProperty("#{reservaAmenidadService}")
    private ServicioReservaAmenidad servicioReservaAmenidad;
    
    public ReservasAmenidadesController() {
    }
    
    @PostConstruct
    public void init() {
        this.reservaAmenidad = new ArrayList<>();
        //filtrarReservasAmenidades();
       /* this.filteredReservasAmenidades = this.reservaAmenidad.stream()
        .filter(reservaAmenidad -> "Activo".equals(reservaAmenidad.getEstado()))
        .collect(Collectors.toList());
        this.activo = true;**/
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
        reservaAmenidad.clear();
        Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(fechaInicial);
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.SECOND, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            Timestamp timestampInicio = new Timestamp(calInicio.getTimeInMillis());

            Calendar calFin = Calendar.getInstance();
            calFin.setTime(fechaFinal);
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.SECOND, 59);
            calFin.set(Calendar.MILLISECOND, 999);
            Timestamp timestampFin = new Timestamp(calFin.getTimeInMillis());

        List<ReservaAmenidadTO> tempList = servicioReservaAmenidad.buscarReservasPorFechas(timestampInicio, timestampFin);

        reservaAmenidad.addAll(tempList.stream()
                .filter(res -> (activo && "Activo".equals(res.getEstado())) || (!activo && "Inactivo".equals(res.getEstado())))
                .collect(Collectors.toList()));

        PrimeFaces.current().ajax().update("form:dt-reservasAmenidades");
    }
    
    public void mostrarReservasPorFechas() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (fechaInicial != null && fechaFinal != null && !fechaInicial.after(fechaFinal)) {
            // Ajusta las fechas para incluir todas las horas del día
            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(fechaInicial);
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            calInicio.set(Calendar.SECOND, 0);
            calInicio.set(Calendar.MILLISECOND, 0);
            Timestamp timestampInicio = new Timestamp(calInicio.getTimeInMillis());

            Calendar calFin = Calendar.getInstance();
            calFin.setTime(fechaFinal);
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            calFin.set(Calendar.SECOND, 59);
            calFin.set(Calendar.MILLISECOND, 999);
            Timestamp timestampFin = new Timestamp(calFin.getTimeInMillis());

        // Llama al método del servicio para buscar registros por fechas
        reservaAmenidad = servicioReservaAmenidad.buscarReservasPorFechas(timestampInicio, timestampFin);
        
        
    //    filtrarReservasAmenidades();
    } else {
            // Muestra un mensaje de error
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas ingresadas no son válidas.");
            context.addMessage(null, message);
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

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    
}