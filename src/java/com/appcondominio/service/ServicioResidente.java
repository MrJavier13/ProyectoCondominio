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
 * @author aacas
 */
@ManagedBean(name="residenteService")
@ApplicationScoped
public class ServicioResidente extends Servicios implements Serializable{
    
    public List<ResidenteTO> mostrarResidentes() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<ResidenteTO> listaRetornar = new ArrayList<ResidenteTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT nombre, cedulaResidente, primerApellido, segundoApellido, telefono, correoElectronico,  numeroCasa, placaVehiculo, estado FROM residente");
            rs = ps.executeQuery();

            while (rs.next()) {

                ResidenteTO residentes = new ResidenteTO();
                residentes.setNombre(rs.getString("nombre"));
                residentes.setCedula(rs.getInt("cedulaResidente"));
                residentes.setPrimerApellido(rs.getString("primerApellido"));
                residentes.setSegundoApellido(rs.getString("segundoApellido"));
                residentes.setTelefono(rs.getInt("telefono"));
                residentes.setCorreoElectronico(rs.getString("correoElectronico"));
                residentes.setNumeroCasa(rs.getInt("numeroCasa"));
                residentes.setPlacaVehiculo(rs.getString("placaVehiculo"));
                residentes.setEstado(rs.getString("estado"));

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
    
    public boolean buscarCedulaResidente(int buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT cedulaResidente FROM residente WHERE cedulaResidente = ?");
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
    
    public void insertarResidente(ResidenteTO residente) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("INSERT INTO residente (cedulaResidente, nombre, primerApellido, segundoApellido, telefono, placaVehiculo, numeroCasa, correoElectronico, estado) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, residente.getCedula());
            ps.setString(2, residente.getNombre());
            ps.setString(3, residente.getPrimerApellido());
            ps.setString(4, residente.getSegundoApellido());
            ps.setInt(5, residente.getTelefono());
            ps.setString(6, residente.getPlacaVehiculo());
            ps.setInt(7, residente.getNumeroCasa());
            ps.setString(8, residente.getCorreoElectronico());
            ps.setString(9, residente.getEstado());
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
    
    public void actualizarResidente(ResidenteTO residente) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("UPDATE residente SET Nombre=?, primerApellido=?, segundoApellido=?, telefono=?, placaVehiculo=?, numeroCasa=?, correoElectronico=?, estado=?  WHERE cedulaResidente =?");
            ps.setString(1, residente.getNombre());
            ps.setString(2, residente.getPrimerApellido());
            ps.setString(3, residente.getSegundoApellido());
            ps.setInt(4, residente.getTelefono());
            ps.setString(5, residente.getPlacaVehiculo());
            ps.setInt(6, residente.getNumeroCasa());
            ps.setString(7, residente.getCorreoElectronico());
            ps.setString(8, residente.getEstado());
            ps.setInt(9, residente.getCedula());
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

