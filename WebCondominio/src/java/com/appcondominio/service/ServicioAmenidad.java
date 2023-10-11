package com.appcondominio.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author meryan
 */
@ManagedBean(name="amenidadService")
@ApplicationScoped
public class ServicioAmenidad extends Servicios{
    
    public List<AmenidadTO> mostrarAmenidades() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<AmenidadTO> listaRetornar = new ArrayList<AmenidadTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT idAmenidad, nombreAmenidad, descripcion, estado FROM amenidad");
            rs = ps.executeQuery();

            while (rs.next()) {

                int idAmenidad = rs.getInt("idAmenidad");
                String nombreAmenidad = rs.getString("nombreAmenidad");
                String descripcion = rs.getString("descripcion");
                String estado = rs.getString("estado");

                AmenidadTO amenidades = new AmenidadTO();
                amenidades.setIdAmenidad(idAmenidad);
                amenidades.setNombreAmenidad(nombreAmenidad);
                amenidades.setDescripcion(descripcion);
                amenidades.setEstado(estado);

                listaRetornar.add(amenidades);

                
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null && ps.isClosed()) {
                    ps.close();
                }
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

        return listaRetornar;
    }
    
    
}

