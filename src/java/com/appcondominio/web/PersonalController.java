package com.appcondominio.web;

import com.appcondominio.service.PersonalTO;
import com.appcondominio.service.ServicioPersonal;
import com.appcondominio.service.ServicioRol;
import com.appcondominio.service.ServicioUsuario;
import com.appcondominio.service.UsuarioTO;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class PersonalController implements Serializable {

    private PersonalTO personalSeleccionado;
    private UsuarioTO usuarioSeleccionado;
    private String dialogHeader;
    private boolean activo;
    private boolean selectOneMenuDisabled = false;
    private List<PersonalTO> personal = new ArrayList<>();
    private Map<Integer, String> mapaRoles;
    private Boolean isCedulaEditable;
    private Boolean isCreatingNewPersonal;
    private String textoBusqueda;

    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;

    @ManagedProperty("#{personalService}")
    private ServicioPersonal servicioPersonal;

    @ManagedProperty("#{rolService}")
    private ServicioRol servicioRol;

    public PersonalController() {
    }

    @PostConstruct
    public void init() {
        this.personal = servicioPersonal.mostrarPersonal().stream()
                .filter(res -> "Activo".equals(res.getEstado()))
                .collect(Collectors.toList());
        this.activo = true;
        this.mapaRoles = servicioRol.obtenerMapaRoles();
    }

    public Map<Integer, String> getMapaRoles() {
        return mapaRoles;
    }

    public void setMapaRoles(Map<Integer, String> mapaRoles) {
        this.mapaRoles = mapaRoles;
    }

    public ServicioRol getServicioRol() {
        return servicioRol;
    }

    public void setServicioRol(ServicioRol servicioRol) {
        this.servicioRol = servicioRol;
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

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
    }
    
    

    public void disableSelectOneMenu() {
        selectOneMenuDisabled = true;
    }

    public void enableSelectOneMenu() {
        selectOneMenuDisabled = false;
    }

    public void openNew() {
        this.personalSeleccionado = new PersonalTO();
        this.personalSeleccionado.setEstado("Activo");
        setIsCedulaEditable(true);
        setIsCreatingNewPersonal(true);
        disableSelectOneMenu();
        dialogHeader = "Registrar nuevo Empleado";
    }

    public void openNewUsuario() {
        this.usuarioSeleccionado = new UsuarioTO();
        this.usuarioSeleccionado.setEstado("Activo");
        disableSelectOneMenu();
        dialogHeader = "Registrar nuevo usuario para el empleado";
    }

    public void openEdit() {
        this.personalSeleccionado = new PersonalTO();
        setIsCedulaEditable(false);
        setIsCreatingNewPersonal(false);
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

    public UsuarioTO getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(UsuarioTO usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public Boolean getIsCedulaEditable() {
        return isCedulaEditable;
    }

    public void setIsCedulaEditable(Boolean isCedulaEditable) {
        this.isCedulaEditable = isCedulaEditable;
    }

    public Boolean getIsCreatingNewPersonal() {
        return isCreatingNewPersonal;
    }

    public void setIsCreatingNewPersonal(Boolean isCreatingNewPersonal) {
        this.isCreatingNewPersonal = isCreatingNewPersonal;
    }

    public void filtrarResidentes() {
        personal.clear();
        List<PersonalTO> tempList = servicioPersonal.mostrarPersonal().stream()
            .filter(res -> (activo && "Activo".equals(res.getEstado())) || (!activo && "Inactivo".equals(res.getEstado())))
            .filter(res -> {
                if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
                    String filterValue = textoBusqueda.toLowerCase().trim();
                    String cedula = String.valueOf(res.getCedula()).toLowerCase();
                    String nombreCompleto = String.format("%s %s %s",
                            String.valueOf(res.getNombre()).toLowerCase(),
                            String.valueOf(res.getApellido1()).toLowerCase(),
                            String.valueOf(res.getApellido2()).toLowerCase());

                    return cedula.contains(filterValue) || nombreCompleto.contains(filterValue);
                } else {
                    return true; // No hay texto de búsqueda, mostrar todos los registros
                }
            })
            .collect(Collectors.toList());
        personal.addAll(tempList);
    }

    public void guardarPersonal() {

        if (getIsCreatingNewPersonal() == true) {

            if (personalSeleccionado.getCedula() == null) {
                FacesContext.getCurrentInstance().addMessage("form:cedula",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el número de cédula",
                                "Por favor, ingrese el número de cédula"));
                return;
            }

            if (validarCamposPersonal()) {
                if (!correoValido(personalSeleccionado.getCorreoElectronico())) {
                    FacesContext.getCurrentInstance().addMessage("form:correoElectronico",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo electrónico inválido",
                                    "Por favor, ingrese un correo electrónico válido. Ejemplo: juan@gmail.com"));
                    return;
                }
                if (!servicioPersonal.buscarCedulaPersonal(this.personalSeleccionado.getCedula())) {
                    // Si es false inserta
                    servicioPersonal.insertarPersonal(personalSeleccionado);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado agregado"));

                } else {

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cédula ingresada ya existe en el sistema"));
                }

                this.init();
                PrimeFaces.current().executeScript("PF('nuevoPersonalDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-empleados");
            }
        } else {

            if (personalSeleccionado.getCedula() == null) {
                FacesContext.getCurrentInstance().addMessage("form:cedula",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el número de cédula",
                                "Por favor, ingrese el número de cédula"));
                return;
            }

            if (validarCamposPersonal()) {
                if (!correoValido(personalSeleccionado.getCorreoElectronico())) {
                    FacesContext.getCurrentInstance().addMessage("form:correoElectronico",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo electrónico inválido",
                                    "Por favor, ingrese un correo electrónico válido. Ejemplo: juan@gmail.com"));
                    return;
                }
                servicioPersonal.actualizarPersonal(personalSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Empleado Actualizado"));

                this.init();
                PrimeFaces.current().executeScript("PF('nuevoPersonalDialog').hide()");
                PrimeFaces.current().ajax().update("form:messages", "form:dt-empleados");
            }
        }

    }

    public void guardarUsuario() {
        if (validarCamposUsuario()) {
            if (!validarContrasena(usuarioSeleccionado.getContrasena())) {
                FacesContext.getCurrentInstance().addMessage("form:contrasena",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de contraseña inválido",
                                "Debe contener una mayúscula, una minúscula, un número y un carácter especial y una longitud de 8 caracteres."));
                return;
            }
            if (usuarioSeleccionado.getIdRol() == null) {
                FacesContext.getCurrentInstance().addMessage("form:rolUsuario",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el rol del usuario",
                                "Por favor, ingrese el rol del usuario"));
                return;
            }
            if (servicioUsuario.buscarCedulaUsuario(this.personalSeleccionado.getCedula())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "El empleado ya tiene un usuario"));
            } else {
                if (servicioUsuario.buscarUsuario(this.personalSeleccionado.getCorreoElectronico())) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Usuario ya existe"));

                } else {
                    this.usuarioSeleccionado.setCedulaEmpleado(this.personalSeleccionado.getCedula());
                    servicioUsuario.insertarUsuarioPersonal(usuarioSeleccionado);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario agregado"));
                }
            }
            this.init();
            PrimeFaces.current().executeScript("PF('nuevoUsuarioDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-empleados");
        }
    }

    private boolean validarCampo(String valor, String nombreCampo, String nombreError) {
        if (valor == null || valor.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("form:" + nombreCampo,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo " + nombreCampo + " requerido",
                            "Por favor, ingrese el " + nombreError.toLowerCase()));
            return false;
        }
        return true;
    }

    private boolean validarCamposPersonal() {
        if (personalSeleccionado.getCedula() != null) {
        }
        return validarCampo(personalSeleccionado.getNombre(), "nombre", "nombre")
                && validarCampo(personalSeleccionado.getApellido1(), "primerApellido", "primer apellido")
                && validarCampo(personalSeleccionado.getApellido2(), "segundoApellido", "segundo apellido")
                && validarCampo(Integer.toString(personalSeleccionado.getTelefono()), "telefono", "teléfono")
                && validarCampo(personalSeleccionado.getCorreoElectronico(), "correoElectronico", "correo electrónico");
    }

    private boolean validarCamposUsuario() {

        if (usuarioSeleccionado.getUsuario() == null || usuarioSeleccionado.getUsuario().isEmpty()) {
        }
        return validarCampo(usuarioSeleccionado.getUsuario(), "usuario", "usuario")
                && validarCampo(usuarioSeleccionado.getContrasena(), "contrasena", "contraseña");

    }

    private boolean correoValido(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    private boolean validarContrasena(String contrasena) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!-~])[A-Za-z\\d!-~]{8,}$";
        return contrasena.matches(regex);
    }

}
