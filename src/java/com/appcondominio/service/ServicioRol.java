package com.appcondominio.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author meryan
 */
@ManagedBean(name="rolService")
@ApplicationScoped
public class ServicioRol extends Servicios implements Serializable{
    
    public List<RolTO> mostrarRoles() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<RolTO> listaRetornar = new ArrayList<RolTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT idRol, nombreRol FROM rol");
            rs = ps.executeQuery();

            while (rs.next()) {

                int idRol = rs.getInt("idRol");
                String nombreRol = rs.getString("nombreRol");
                

                RolTO rol = new RolTO();
                rol.setIdRol(idRol);
                rol.setNombreRol(nombreRol);
              

                listaRetornar.add(rol);

                
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
    
    public Map<Integer, String> obtenerMapaRoles() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        // Mapa para almacenar roles
        Map<Integer, String> mapaRoles = new HashMap<>();

        try {
            ps = conn.prepareStatement("SELECT idRol, nombreRol FROM Rol");
            rs = ps.executeQuery();

            while (rs.next()) {
                int idRol = rs.getInt("idRol");
                String nombreRol = rs.getString("nombreRol");
                // Agregar el rol al mapa
                mapaRoles.put(idRol, nombreRol);
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

        return mapaRoles;
    }
    
    public Integer buscarIdRol(Integer buscar) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    Integer idEncontrado = null;

    try {

        ps = super.getConexion().prepareStatement("SELECT idRol FROM rol WHERE idRol = ?");
        ps.setInt(1, buscar);
        rs = ps.executeQuery();

        if (rs.next()) {
            idEncontrado = rs.getInt("idRol");
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

    return idEncontrado;
}

    
    public void insertarRol(RolTO rol) {

    PreparedStatement ps = null;
    try {
        ps = super.getConexion().prepareStatement("INSERT INTO rol (nombreRol) VALUES (?)");
        ps.setString(1, rol.getNombreRol());

        ps.execute();

    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}

    public void actualizarRol(RolTO rol) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("UPDATE rol SET nombreRol=? WHERE idRol =?");
            ps.setString(1, rol.getNombreRol());
            ps.setInt(2, rol.getIdRol());
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


    
    


