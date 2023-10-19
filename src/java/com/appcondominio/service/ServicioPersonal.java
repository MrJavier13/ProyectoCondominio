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

@ManagedBean(name = "personalService")
@ApplicationScoped
public class ServicioPersonal extends Servicios implements Serializable{

    public List<PersonalTO> mostrarPersonal() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<PersonalTO> listaRetornar = new ArrayList<PersonalTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT cedulaEmpleado, nombreEmpleado, primerApellido, SegundoApellido, telefono, placaVehiculo, correoElectronico, estado FROM Empleado");
            rs = ps.executeQuery();

            while (rs.next()) {

                PersonalTO personal = new PersonalTO();
                personal.setCedula(rs.getInt("cedulaEmpleado"));
                personal.setNombre(rs.getString("nombreEmpleado"));
                personal.setApellido1(rs.getString("primerApellido"));
                personal.setApellido2(rs.getString("segundoApellido"));
                personal.setTelefono(rs.getInt("telefono"));
                personal.setCorreo(rs.getString("correoElectronico"));
                personal.setPlacaVehiculo(rs.getString("placaVehiculo"));
                personal.setEstado(rs.getString("estado"));

                listaRetornar.add(personal);

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

    public boolean buscarCedulaPersonal(int buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT cedulaEmpleado FROM Empleado WHERE cedulaEmpleado = ?");
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

    public void insertarPersonal(PersonalTO personal) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("INSERT INTO Empleado (cedulaEmpleado, nombreEmpleado, primerApellido, SegundoApellido, telefono, placaVehiculo, correoElectronico, estado) VALUES (?,?,?,?,?,?,?,?)");
            ps.setInt(1, personal.getCedula());
            ps.setString(2, personal.getNombre());
            ps.setString(3, personal.getApellido1());
            ps.setString(4, personal.getApellido2());
            ps.setInt(5, personal.getTelefono());
            ps.setString(6, personal.getPlacaVehiculo());
            ps.setString(7, personal.getCorreo());
            ps.setString(8, personal.getEstado());
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

    public void actualizarPersonal(PersonalTO personal) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("UPDATE Empleado SET nombreEmpleado=?, primerApellido=?, segundoApellido=?, telefono=?, placaVehiculo=?, correoElectronico=?, estado=?  WHERE cedulaEmpleado =?");
            ps.setString(1, personal.getNombre());
            ps.setString(2, personal.getApellido1());
            ps.setString(3, personal.getApellido2());
            ps.setInt(4, personal.getTelefono());
            ps.setString(5, personal.getPlacaVehiculo());
            ps.setString(6, personal.getCorreo());
            ps.setString(7, personal.getEstado());
            ps.setInt(8, personal.getCedula());
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



