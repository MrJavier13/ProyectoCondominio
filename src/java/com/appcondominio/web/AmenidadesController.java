
import com.appcondominio.service.AmenidadTO;
import com.appcondominio.service.ServicioAmenidad;
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

@ManagedBean(name = "amenidadesController")
@ViewScoped
public class AmenidadesController implements Serializable{
    private AmenidadTO amenidadSeleccionada;
    private boolean activo;
    private boolean selectOneMenuDisabled = false;
    private List<AmenidadTO> amenidad = new ArrayList<>();
    private AmenidadTO nuevaAmenidad = new AmenidadTO(); // Objeto para almacenar los datos de la nueva amenidad
    private List<AmenidadTO> filteredAmenidades;
    private String dialogHeader;

    
    @ManagedProperty("#{amenidadService}")
    private ServicioAmenidad servicioAmenidad;
    
    public AmenidadesController() {
    }
    
    @PostConstruct
    public void init() {
        this.amenidad = servicioAmenidad.mostrarAmenidades();
        this.filteredAmenidades = this.amenidad.stream()
        .filter(amenidad -> "Activo".equals(amenidad.getEstado()))
        .collect(Collectors.toList());
        this.activo = true;
    }
    
    public void openNew() {
       this.amenidadSeleccionada = new AmenidadTO();
       this.amenidadSeleccionada.setEstado("Activo");
       disableSelectOneMenu();
       dialogHeader = "Registrar nueva amenidad";
       
    }
    public void openEdit() {
        this.amenidadSeleccionada = new AmenidadTO();
        this.amenidadSeleccionada.setEstado("Activo");
        enableSelectOneMenu(); 
        dialogHeader = "Editar amenidad";
       
    }
    
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
    }
    
    public void guardarAmenidad() {
        if (validarCampos()) {
            if (!servicioAmenidad.buscarIdAmenidad(this.amenidadSeleccionada.getIdAmenidad())) { 

                    servicioAmenidad.insertarAmenidad(amenidadSeleccionada);

                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Amenidad agregada"));

                } else {
                    servicioAmenidad.actualizarAmenidad(amenidadSeleccionada);
                    FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Amenidad Actualizada"));
                }
            this.init();
            PrimeFaces.current().executeScript("PF('nuevaAmenidadDialog').hide()");
            PrimeFaces.current().ajax().update("form:growl", "form:dt-amenidades");
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
        if (amenidadSeleccionada.getIdAmenidad() != null) {
        }
        return validarCampo(amenidadSeleccionada.getNombreAmenidad(), "nombreAmenidad", "nombre") ;
    }
    
    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
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

    public boolean isSelectOneMenuDisabled() {
        return selectOneMenuDisabled;
    }

    public void setSelectOneMenuDisabled(boolean selectOneMenuDisabled) {
        this.selectOneMenuDisabled = selectOneMenuDisabled;
    }

    
    
    public void setNuevaAmenidad(AmenidadTO nuevaAmenidad) {
        this.nuevaAmenidad = nuevaAmenidad;
    }

    public boolean isActivo() {
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

    public String getDialogHeader() {
        return dialogHeader;
    }

    public void setDialogHeader(String dialogHeader) {
        this.dialogHeader = dialogHeader;
    }

    

    
    public AmenidadTO getAmenidadSeleccionada() {
        return amenidadSeleccionada;
    }

    public void setAmenidadSeleccionada(AmenidadTO amenidadSeleccionada) {
        this.amenidadSeleccionada = amenidadSeleccionada;
    }
}