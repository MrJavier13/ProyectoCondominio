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
 * @author aacas
 */
@ManagedBean(name="residenteService")
@ApplicationScoped
public class ServicioResidente extends Servicios{
    
    public List<ResidenteTO> mostrarResidentes() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<ResidenteTO> listaRetornar = new ArrayList<ResidenteTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT nombre, cedula, apellido, telefono, correoElectronico,  numeroCasa, placaVehiculo, tipoUsuario FROM residente");
            rs = ps.executeQuery();

            while (rs.next()) {

                ResidenteTO residentes = new ResidenteTO();
                residentes.setNombre(rs.getString("nombre"));
                residentes.setCedula(rs.getString("cedula"));
                residentes.setApellido(rs.getString("apellido"));
                residentes.setTelefono(rs.getInt("telefono"));
                residentes.setCorreoElectronico(rs.getString("correoElectronico"));
                residentes.setNumeroCasa(rs.getInt("numeroCasa"));
                residentes.setPlacaVehiculo(rs.getString("placaVehiculo"));
                residentes.setTipoUsuario(rs.getInt("tipoUsuario"));

                listaRetornar.add(residentes);

                
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

