package com.appcondominio.web;

import com.appcondominio.service.ResidenteTO;
import com.appcondominio.service.ServicioResidente;
import com.appcondominio.service.ServicioUsuario;
import com.appcondominio.service.UsuarioTO;
import java.io.Serializable;
import java.security.SecureRandom;
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
public class ResidentesController implements Serializable {

    private ResidenteTO residenteSeleccionado;
    private boolean activo;
    private boolean selectOneMenuDisabled = false;
    private List<ResidenteTO> residente = new ArrayList<>();
    private String dialogHeader;
    private Boolean isCedulaEditable;
    private Boolean isCreatingNewResidente;
    private String textoBusqueda;

    @ManagedProperty("#{residenteService}")
    private ServicioResidente servicioResidente;

    @ManagedProperty("#{usuarioService}")
    private ServicioUsuario servicioUsuario;

    public ResidentesController() {
    }

    @PostConstruct
    public void init() {
        this.residente = servicioResidente.mostrarResidentes().stream()
                .filter(res -> "Activo".equals(res.getEstado()))
                .collect(Collectors.toList());
        this.activo = true;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }

    public void setTextoBusqueda(String textoBusqueda) {
        this.textoBusqueda = textoBusqueda;
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
        setIsCedulaEditable(true);
        setIsCreatingNewResidente(true);
        disableSelectOneMenu();
        dialogHeader = "Registrar nuevo residente";

    }

    public void openEdit() {
        this.residenteSeleccionado = new ResidenteTO();
        setIsCedulaEditable(false);
        setIsCreatingNewResidente(false);
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

    // Getter y Setter para isCedulaEditable
    public Boolean getIsCedulaEditable() {
        return isCedulaEditable;
    }

    public void setIsCedulaEditable(Boolean isCedulaEditable) {
        this.isCedulaEditable = isCedulaEditable;
    }

    public Boolean getIsCreatingNewResidente() {
        return isCreatingNewResidente;
    }

    public void setIsCreatingNewResidente(Boolean isCreatingNewResidente) {
        this.isCreatingNewResidente = isCreatingNewResidente;
    }

    public void filtrarResidentes() {
        residente.clear();
        List<ResidenteTO> tempList = servicioResidente.mostrarResidentes().stream()
                .filter(res -> (activo && "Activo".equals(res.getEstado())) || (!activo && "Inactivo".equals(res.getEstado())))
                .filter(res -> {
                if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
                    String filterValue = textoBusqueda.toLowerCase().trim();
                    String cedula = String.valueOf(res.getCedula()).toLowerCase();
                    String nombreCompleto = String.format("%s %s %s",
                            String.valueOf(res.getNombre()).toLowerCase(),
                            String.valueOf(res.getPrimerApellido()).toLowerCase(),
                            String.valueOf(res.getSegundoApellido()).toLowerCase());

                    return cedula.contains(filterValue) || nombreCompleto.contains(filterValue);
                } else {
                    return true; // No hay texto de búsqueda, mostrar todos los registros
                }
            })
                .collect(Collectors.toList());
        residente.addAll(tempList);
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

        if (residenteSeleccionado.getCedula() == null) {
            FacesContext.getCurrentInstance().addMessage("form:cedula",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el número de cédula",
                            "Por favor, ingrese el número de cédula"));
            return;
        }
        if (validarCampos()) {
            if (!correoValido(residenteSeleccionado.getCorreoElectronico())) {
                FacesContext.getCurrentInstance().addMessage("form:correoElectronico",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de correo electrónico inválido",
                                "Por favor, ingrese un correo electrónico válido. Ejemplo: juan@gmail.com"));
                return;
            }

            if (getIsCreatingNewResidente() == true) {
                if (!servicioResidente.buscarCedulaResidente(this.residenteSeleccionado.getCedula())) {
                    // Si es false inserta
                    servicioResidente.insertarResidente(residenteSeleccionado);

                    String contrasena = generarContrasenaAleatoria(residenteSeleccionado.getNombre());

                    UsuarioTO usuario = new UsuarioTO();
                    usuario.setCedulaResidente(residenteSeleccionado.getCedula());
                    usuario.setUsuario(residenteSeleccionado.getCorreoElectronico());
                    usuario.setContrasena(contrasena);
                    usuario.setEstado(residenteSeleccionado.getEstado());

                    // Obtener el ID del rol "Residente" por nombre
                    String nombreRolResidente = "Residente";
                    int idRolResidente = servicioUsuario.obtenerIdRolPorNombre(nombreRolResidente);

                    usuario.setIdRol(idRolResidente); // Establecer el ID del rol "Residente"

                    servicioUsuario.insertarUsuarioResidente(usuario);

                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Residente y usuario agregado"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "La cédula ingresada ya existe en el sistema."));

                }
            } else {

                servicioResidente.actualizarResidente(residenteSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Residente Actualizado"));

            }
            this.init();

            PrimeFaces.current().executeScript("PF('nuevoResidenteDialog').hide()");
            PrimeFaces.current().ajax().update("form:messages", "form:dt-residentes");
        }
    }

    private String generarContrasenaAleatoria(String nombre) {
        SecureRandom random = new SecureRandom();
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder contrasena = new StringBuilder();
        contrasena.append(caracteres.charAt(random.nextInt(26)));

        contrasena.append(caracteres.charAt(random.nextInt(26) + 26));

        contrasena.append(caracteres.charAt(random.nextInt(10) + 52));

        String ultimosDigitosCedula = String.valueOf(residenteSeleccionado.getCedula()).substring(Math.max(0, String.valueOf(residenteSeleccionado.getCedula()).length() - 2));
        contrasena.append(ultimosDigitosCedula);

        contrasena.append(caracteres.charAt(random.nextInt(8) + 62));

        if (nombre.length() >= 3) {
            contrasena.append(nombre.substring(0, 3).toLowerCase());
        } else {
            contrasena.append(nombre.toLowerCase());
        }

        char[] contrasenaArray = contrasena.toString().toCharArray();
        for (int i = 0; i < contrasena.length(); i++) {
            int index = random.nextInt(contrasena.length());
            char temp = contrasenaArray[i];
            contrasenaArray[i] = contrasenaArray[index];
            contrasenaArray[index] = temp;
        }

        return new String(contrasenaArray);
    }

    private boolean correoValido(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
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

    private boolean validarCampos() {
        if (residenteSeleccionado.getCedula() != null) {
        }
        return validarCampo(residenteSeleccionado.getNombre(), "nombre", "nombre")
                && validarCampo(residenteSeleccionado.getPrimerApellido(), "primerApellido", "primer apellido")
                && validarCampo(residenteSeleccionado.getSegundoApellido(), "segundoApellido", "segundo apellido")
                && validarCampo(Integer.toString(residenteSeleccionado.getTelefono()), "telefono", "teléfono")
                && validarCampo(Integer.toString(residenteSeleccionado.getNumeroCasa()), "numeroCasa", "número de la casa")
                && validarCampo(residenteSeleccionado.getCorreoElectronico(), "correoElectronico", "correo electrónico");
    }

}
