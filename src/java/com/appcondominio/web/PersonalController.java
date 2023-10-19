package com.appcondominio.web;

import com.appcondominio.service.PersonalTO;
import com.appcondominio.service.ServicioPersonal;
import com.appcondominio.service.ServicioUsuario;
import com.appcondominio.service.UsuarioTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "personalController")
@ViewScoped
public class PersonalController {

    private PersonalTO personalSeleccionado;
    private String dialogHeader;
    private boolean activo;
    private boolean selectOneMenuDisabled = false;
    private List<PersonalTO> filteredPersonal;
    private List<PersonalTO> personal = new ArrayList<>();
    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;

    @ManagedProperty("#{personalService}")
    private ServicioPersonal servicioPersonal;

    public PersonalController() {
    }

    @PostConstruct
    public void init() {
        this.personal = servicioPersonal.mostrarPersonal();
        this.filteredPersonal = this.personal.stream()
                .filter(personal -> "Activo".equals(personal.getEstado()))
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
        this.personalSeleccionado = new PersonalTO();
        //this.personalSeleccionado.setEstado("Activo");
        //disableSelectOneMenu();
        dialogHeader = "Registrar nuevo Empleado";

    }

    public void openEdit() {
        this.personalSeleccionado = new PersonalTO();
        enableSelectOneMenu();
        dialogHeader = "Editar Personal";

    }

    public List<PersonalTO> getPersonal() {
        return personal;
    }

    public void setPersonal(List<PersonalTO> personal) {
        this.personal = personal;
    }

    public ServicioPersonal getServicioResidente() {
        return servicioPersonal;
    }

    public void setServicioPersonal(ServicioPersonal servicioPersonal) {
        this.servicioPersonal = servicioPersonal;
    }

    public PersonalTO getPersonalSeleccionado() {
        return personalSeleccionado;
    }

    public void setPersonalSeleccionado(PersonalTO personalSeleccionado) {
        this.personalSeleccionado = personalSeleccionado;
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

    public List<PersonalTO> getFilteredPersonal() {
        return filteredPersonal;
    }

    public void setFilteredPersonal(List<PersonalTO> filteredPersonal) {
        this.filteredPersonal = filteredPersonal;
    }

    public void filtrarPersonal() {
        filteredPersonal.clear();
        for (PersonalTO personal : personal) {
            if ((activo && "Activo".equals(personal.getEstado())) || (!activo && "Inactivo".equals(personal.getEstado()))) {
                filteredPersonal.add(personal);
            }
        }
    }

    public void guardarPersonal() {
        if (personalSeleccionado.getCorreo() == null || personalSeleccionado.getCorreo().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("form:correoElectronico",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Correo electrónico es requerido",
                            "Por favor, ingrese un correo electrónico válido."));
            return;
        }

        if (!servicioPersonal.buscarCedulaPersonal(this.personalSeleccionado.getCedula())) {
            // Si es false inserta
            if (!correoValido(personalSeleccionado.getCorreo())) {
                FacesContext.getCurrentInstance().addMessage("form:correoElectronico",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo electrónico inválido",
                                "Por favor, ingrese un correo electrónico válido. Ejemplo: usuario@gmail.com"));
                return;
            } else {
                servicioPersonal.insertarPersonal(personalSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado agregado"));

            }
        } else {
            if (!correoValido(personalSeleccionado.getCorreo())) {
                FacesContext.getCurrentInstance().addMessage("form:correoElectronico",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo electrónico inválido",
                                "Por favor, ingrese un correo electrónico válido."));
                return;
            } else {
                servicioPersonal.actualizarPersonal(personalSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado Actualizado"));
            }
        }
        this.init();
        PrimeFaces.current().executeScript("PF('nuevoPersonalDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empleados");
    }

    public void guardarUsuario() {
        if (servicioUsuario.buscarUsuario(this.personalSeleccionado.getCorreo())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Usuario ya existe"));

        } else {
            UsuarioTO usuario = new UsuarioTO();

            usuario.setUsuario(personalSeleccionado.getCorreo());
            usuario.setContrasena(String.valueOf(personalSeleccionado.getCedula()));
            usuario.setCedulaEmpleado(personalSeleccionado.getCedula());
            usuario.setEstado(personalSeleccionado.getEstado());
            usuario.setIdRol(1);
            servicioUsuario.insertarUsuarioPersonal(usuario);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario agregado"));
        }
        this.init();
        PrimeFaces.current().executeScript("PF('nuevoPersonalDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-empleados");
    }

    private boolean correoValido(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

}
