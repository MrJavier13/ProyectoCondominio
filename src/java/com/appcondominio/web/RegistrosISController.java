/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.appcondominio.web;

import com.appcondominio.service.RegistroIngresosSalidasTO;
import com.appcondominio.service.ServicioRegistroIS;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.faces.context.FacesContext;



@ManagedBean(name = "registrosISController")
@ViewScoped
public class RegistrosISController implements Serializable{
    private List<RegistroIngresosSalidasTO> registroIS = new ArrayList<>();
    private RegistroIngresosSalidasTO registroISSeleccionado;
    private RegistroIngresosSalidasTO nuevoRegistroIS = new RegistroIngresosSalidasTO();
    private String dialogHeader;
    private Date fechaInicial;
    private Date fechaFinal;
    private Date fechaIngresoForm;
    private Date fechaSalidaForm;
    private boolean invitadoPermanenteEncontrado = false;
    private boolean validarFechas = true;
    private boolean habilitarCampos = false;
    private boolean habilitarFechasGuarda = false;
    private boolean botonVerificar = false;
   

    @ManagedProperty("#{registroISService}")
    private ServicioRegistroIS servicioRegistroIS;

    public RegistrosISController() {
    }

    @PostConstruct
    public void init() {
        this.registroIS = new ArrayList<>();
         
    }
    
    public void openNew() {
       this.registroISSeleccionado = new RegistroIngresosSalidasTO();
     //  disableSelectOneMenu();
       dialogHeader = "Crear nuevo registro";
       fechaIngresoForm = null; // Reinicia la fecha de ingreso(esto no sirve)
        fechaSalidaForm = null; // Reinicia la fecha de salida
        invitadoPermanenteEncontrado = false;
        this.habilitarCampos = false;
        this.habilitarFechasGuarda = false;
        botonVerificar = false;
       
    }
    
    public void openEdit() {
        this.registroISSeleccionado = new RegistroIngresosSalidasTO();
       
       dialogHeader = "Editar registro";
       invitadoPermanenteEncontrado = false;
       habilitarCampos = true;
       habilitarFechasGuarda= true;
       botonVerificar = true;
       if (registroISSeleccionado.getFechaIngreso() != null) {
        fechaIngresoForm = new Date(registroISSeleccionado.getFechaIngreso().getTime());
    } else {
        fechaIngresoForm = null;
    }

    // Verificar si la fecha de salida no es nula antes de realizar la conversión
    if (registroISSeleccionado.getFechaSalida() != null) {
        fechaSalidaForm = new Date(registroISSeleccionado.getFechaSalida().getTime());
    } else {
        fechaSalidaForm = null;
    }
    }

    public void mostrarRegistrosPorFechas() {
    FacesContext context = FacesContext.getCurrentInstance();

    if (validarFechas) { // Comprueba la variable de control
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
            registroIS = servicioRegistroIS.buscarRegistrosPorFechas(timestampInicio, timestampFin);
        } else {
            // Muestra un mensaje de error
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas ingresadas no son válidas.");
            context.addMessage(null, message);
        }
    }

    // Restablece la variable de control
    validarFechas = true;
}
    

public void guardarRegistro() {
    if (validarCampos()) {
   
      // Verificar y asignar valores nulos a los campos cuando sea necesario
        if (registroISSeleccionado.getCedulaInvitadoPermanente() == null) {
            registroISSeleccionado.setCedulaInvitadoPermanente(null);
        }
        
        if (registroISSeleccionado.getCedulaInvitadoTemporal() == null) {
            registroISSeleccionado.setCedulaInvitadoTemporal(null);
        }
        if (registroISSeleccionado.getNombreEmpresa() != null && registroISSeleccionado.getNombreEmpresa().isEmpty()) {
            registroISSeleccionado.setNombreEmpresa(null);
        }
        if (registroISSeleccionado.getPlacaVehicular() != null && registroISSeleccionado.getPlacaVehicular().isEmpty()) {
            registroISSeleccionado.setPlacaVehicular(null);
        }
        if (registroISSeleccionado.getDetalle() != null && registroISSeleccionado.getDetalle().isEmpty()) {
            registroISSeleccionado.setDetalle(null);
        }
        if (registroISSeleccionado.getFechaSalida() == null){
            registroISSeleccionado.setFechaSalida(null);
        }
        
        registroISSeleccionado.setFechaIngreso(new Timestamp(fechaIngresoForm.getTime()));
        if (fechaSalidaForm != null) {
    registroISSeleccionado.setFechaSalida(new Timestamp(fechaSalidaForm.getTime()));
} else {
    registroISSeleccionado.setFechaSalida(null);
}

        if (!servicioRegistroIS.buscarIdRegistro(this.registroISSeleccionado.getIdRegistro())) {
            servicioRegistroIS.insertarRegistro(registroISSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro agregado"));
        } else {
            servicioRegistroIS.actualizarRegistro(registroISSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Registro Actualizado"));
        }
        
        this.init();
        PrimeFaces.current().executeScript("PF('nuevoRegistroISDialog').hide()");
        PrimeFaces.current().ajax().update("form:growl", "form:dt-registros");
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
        if (registroISSeleccionado.getIdRegistro() != null) {
        }
        return validarCampo(Integer.toString(registroISSeleccionado.getCedulaGuardaSeguridad()), "cedulaGuardaSeguridad", "cedula de guarda de seguridad");
              //  && validarCampo(registroISSeleccionado.getNombreCompletoInvitado(), "nombreCompletoInvitado", "nombre completo invitado")
            //    && validarCampo(Integer.toString(registroISSeleccionado.getCedulaInvitadoPermanente()), "cedulaInvitadoPermanente", "cedula invitado permanente") 
          //  && validarCampo(registroISSeleccionado.getFechaIngreso().toString(), "fechaIngreso", "fecha de ingreso")
         //   && validarCampo(Integer.toString(registroISSeleccionado.getCedulaGuardaSeguridad()), "cedulaGuardaSeguridad", "cedula de guarda de seguridad");
    }
    
public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    public void verificarInvitadoPermanente() {
    // Obtén la cédula ingresada por el usuario
    Integer cedulaIngresada = registroISSeleccionado.getCedulaAMostrar();

    // Realiza la lógica para verificar si la cédula corresponde a un Invitado Permanente
    // Por ejemplo, puedes utilizar el servicio obtenerInvitadoPermanentePorCedula
    RegistroIngresosSalidasTO invitadoPermanente = servicioRegistroIS.obtenerInvitadoPermanentePorCedula(cedulaIngresada);

    if (invitadoPermanente != null) {
        // Si es un Invitado Permanente, completa los campos correspondientes
        registroISSeleccionado.setCedulaInvitadoPermanente(cedulaIngresada);
        registroISSeleccionado.setCedulaInvitadoTemporal(0);
        registroISSeleccionado.setNombreCompletoInvitado(invitadoPermanente.getNombreCompletoInvitado());
        registroISSeleccionado.setPlacaVehicular(invitadoPermanente.getPlacaVehicular());
        registroISSeleccionado.setNombreEmpresa(null);
        registroISSeleccionado.setDetalle(null);
     
        invitadoPermanenteEncontrado = true;
        habilitarCampos = false;
        habilitarFechasGuarda = true;
    } else {
        // Si no es un Invitado Permanente se asigna cedulaIngresada a cedulaInvitadoTemporal y la perm queda nula
        registroISSeleccionado.setCedulaInvitadoTemporal(cedulaIngresada);
        registroISSeleccionado.setCedulaInvitadoPermanente(0);
        
        invitadoPermanenteEncontrado = false;
        habilitarCampos=true;
        habilitarFechasGuarda = true;
     
    }
}
  
    public List<RegistroIngresosSalidasTO> getRegistroIS() {
        return registroIS;
    }

    public void setUsuario(List<RegistroIngresosSalidasTO> registroIS) {
        this.registroIS = registroIS;
    }

    public ServicioRegistroIS getServicioRegistroIS() {
        return servicioRegistroIS;
    }

    public void setServicioRegistroIS(ServicioRegistroIS servicioRegistroIS) {
        this.servicioRegistroIS = servicioRegistroIS;
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

    public RegistroIngresosSalidasTO getRegistroISSeleccionado() {
        return registroISSeleccionado;
    }

    public void setRegistroISSeleccionado(RegistroIngresosSalidasTO registroISSeleccionado) {
        this.registroISSeleccionado = registroISSeleccionado;
    }

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    public RegistroIngresosSalidasTO getNuevoRegistroIS() {
        return nuevoRegistroIS;
    }

    public void setNuevoRegistroIS(RegistroIngresosSalidasTO nuevoRegistroIS) {
        this.nuevoRegistroIS = nuevoRegistroIS;
    }

    public Date getFechaIngresoForm() {
        return fechaIngresoForm;
    }

    public void setFechaIngresoForm(Date fechaIngresoForm) {
        this.fechaIngresoForm = fechaIngresoForm;
    }

    public Date getFechaSalidaForm() {
        return fechaSalidaForm;
    }

    public void setFechaSalidaForm(Date fechaSalidaForm) {
        this.fechaSalidaForm = fechaSalidaForm;
    }

    public boolean isInvitadoPermanenteEncontrado() {
    return invitadoPermanenteEncontrado;
}

    public boolean isValidarFechas() {
        return validarFechas;
    }

    public void setValidarFechas(boolean validarFechas) {
        this.validarFechas = validarFechas;
    }

    public boolean isHabilitarCampos() {
        return habilitarCampos;
    }

    public void setHabilitarCampos(boolean habilitarCampos) {
        this.habilitarCampos = habilitarCampos;
    }

    public boolean isHabilitarFechasGuarda() {
        return habilitarFechasGuarda;
    }

    public void setHabilitarFechasGuarda(boolean habilitarFechasGuarda) {
        this.habilitarFechasGuarda = habilitarFechasGuarda;
    }

    public boolean isBotonVerificar() {
        return botonVerificar;
    }

    public void setBotonVerificar(boolean botonVerificar) {
        this.botonVerificar = botonVerificar;
    }

  
}