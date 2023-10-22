package com.appcondominio.service;

import java.io.Serializable;
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
public class ServicioAmenidad extends Servicios implements Serializable{
    
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
    
    public boolean buscarIdAmenidad(Integer buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT idAmenidad FROM amenidad WHERE idAmenidad = ?");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();

            if (rs.next()) {
                busqueda = true;

            } else {
                busqueda = false;
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

        return busqueda;
    }
    
    public void insertarAmenidad(AmenidadTO amenidad) {

        PreparedStatement ps = null;
        try {
            ps = super.getConexion().prepareStatement("INSERT INTO amenidad (idAmenidad,nombreAmenidad, descripcion, estado) VALUES (?,?,?,?)");
            ps.setInt(1, amenidad.getIdAmenidad());
            ps.setString(2, amenidad.getNombreAmenidad());
            ps.setString(3, amenidad.getDescripcion());
            ps.setString(4, amenidad.getEstado());
            ps.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null && ps.isClosed()) {
                    ps.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }
    public void actualizarAmenidad(AmenidadTO amenidad) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("UPDATE amenidad SET nombreAmenidad=?, descripcion=?, estado=? WHERE idAmenidad =?");
            ps.setString(1, amenidad.getNombreAmenidad());
            ps.setString(2, amenidad.getDescripcion());
            ps.setString(3, amenidad.getEstado());
            ps.setInt(4, amenidad.getIdAmenidad());
            ps.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null && ps.isClosed()) {
                    ps.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }

    }
    
    
}

