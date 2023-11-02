package com.appcondominio.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author meryan
 */
@ManagedBean(name="reservaAmenidadService")
@ApplicationScoped
public class ServicioReservaAmenidad extends Servicios implements Serializable{
    
    /*public List<ReservaAmenidadTO> mostrarReservas() {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    //Lista
    List<ReservaAmenidadTO> listaRetornar = new ArrayList<ReservaAmenidadTO>();

    try {

        //super.conectar();
        ps = conn.prepareStatement("SELECT r.idReservaAmenidad, a.nombreAmenidad, a.descripcion, r.fechaInicio, r.fechaFin, res.nombre, res.primerApellido, res.segundoApellido, res.numeroCasa, r.estado FROM reserva_Amenidad r JOIN Amenidad a ON r.idAmenidad = a.idAmenidad JOIN Residente res ON r.cedulaResidente = res.cedulaResidente");
        rs = ps.executeQuery();

        while (rs.next()) {

            int idReservaAmenidad = rs.getInt("idReservaAmenidad");
            String nombreAmenidad = rs.getString("nombreAmenidad");
            String descripcionAmenidad = rs.getString ("descripcion");
            Timestamp fechaInicio = rs.getTimestamp("fechaInicio");
            Timestamp fechaFin = rs.getTimestamp("fechaFin");
            String nombre = rs.getString("nombre");
            String primerApellido = rs.getString("primerApellido");
            String segundoApellido = rs.getString("segundoApellido");
            int numeroCasa = rs.getInt("numeroCasa");
            String estado =rs.getString("estado");

            ReservaAmenidadTO reservaAmenidad = new ReservaAmenidadTO(idReservaAmenidad, nombreAmenidad, descripcionAmenidad, fechaInicio, fechaFin, nombre, primerApellido, segundoApellido, numeroCasa, estado);

            listaRetornar.add(reservaAmenidad);

            
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
}**/
    
   public List<ReservaAmenidadTO> buscarReservasPorFechas(Timestamp fechaInicial, Timestamp fechaFinal) {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<ReservaAmenidadTO> listaRetornar = new ArrayList<ReservaAmenidadTO>();

    try {
        ps = conn.prepareStatement("SELECT r.idReservaAmenidad, a.nombreAmenidad, a.descripcion, r.fechaInicio, r.fechaFin, res.nombre, res.primerApellido, res.segundoApellido, res.numeroCasa, r.estado FROM reserva_Amenidad r JOIN Amenidad a ON r.idAmenidad = a.idAmenidad JOIN Residente res ON r.cedulaResidente = res.cedulaResidente WHERE r.fechaInicio BETWEEN ? AND ?");
        ps.setTimestamp(1, fechaInicial);
        ps.setTimestamp(2, fechaFinal);
        rs = ps.executeQuery();

        while (rs.next()) {
            int idReservaAmenidad = rs.getInt("idReservaAmenidad");
            String nombreAmenidad = rs.getString("nombreAmenidad");
            String descripcionAmenidad = rs.getString ("descripcion");
            Timestamp fechaInicio = rs.getTimestamp("fechaInicio");
            Timestamp fechaFin = rs.getTimestamp("fechaFin");
            String nombre = rs.getString("nombre");
            String primerApellido = rs.getString("primerApellido");
            String segundoApellido = rs.getString("segundoApellido");
            int numeroCasa = rs.getInt("numeroCasa");
            String estado =rs.getString("estado");
            
            

            ReservaAmenidadTO reservaAmenidad = new ReservaAmenidadTO(idReservaAmenidad, nombreAmenidad, descripcionAmenidad, fechaInicio, fechaFin, nombre, primerApellido, segundoApellido, numeroCasa, estado);

            listaRetornar.add(reservaAmenidad);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (ps != null && !ps.isClosed()) {
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
    
    public boolean buscarIdReserva(Integer buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT idReservaAmenidad FROM reserva_Amenidad WHERE idReservaAmenidad = ?");
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
    
    public void insertarReserva(ReservaAmenidadTO reservaAmenidad) {

        PreparedStatement ps = null;
        try {
            ps = super.getConexion().prepareStatement("INSERT INTO reserva_Amenidad (idAmenidad, fechaInicio, fechaFin, cedulaResidente, estado) VALUES (?,?,?,?,?)");
            
            ps.setInt(1, reservaAmenidad.getIdAmenidad());
            ps.setTimestamp(2, reservaAmenidad.getFechaInicio());
            ps.setTimestamp(3, reservaAmenidad.getFechaFin());
            ps.setInt(4, reservaAmenidad.getCedulaResidente());
            ps.setString(5, reservaAmenidad.getEstado());
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
    public void actualizarReserva(ReservaAmenidadTO reservaAmenidad) {

    PreparedStatement ps = null;
    try {
        ps = super.getConexion().prepareStatement("UPDATE Reserva_Amenidad SET fechaInicio=?, fechaFin=?, estado=? WHERE idReservaAmenidad=?");
        
      
        ps.setTimestamp(1, reservaAmenidad.getFechaInicio());
        ps.setTimestamp(2, reservaAmenidad.getFechaFin());
       
        ps.setString(3, reservaAmenidad.getEstado());
        ps.setInt(4, reservaAmenidad.getIdReservaAmenidad()); // No se modifica el campo autoincremento
        ps.executeUpdate();

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
    
   /* public List<ReservaAmenidadTO> mostrarReservasConNombres() {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<ReservaAmenidadTO> listaRetornar = new ArrayList<ReservaAmenidadTO>();

    try {
        String sql = "SELECT RA.idReservaAmenidad, A.nombreAmenidad, RA.fechaInicio, RA.fechaFin, R.nombre + ' ' + R.primerApellido + ' ' + R.segundoApellido AS nombreCompletoResidente, RA.estado " +
                     "FROM Reserva_Amenidad RA " +
                     "INNER JOIN Amenidad A ON RA.idAmenidad = A.idAmenidad " +
                     "INNER JOIN Residente R ON RA.cedulaResidente = R.cedulaResidente";

        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            int idReservaAmenidad = rs.getInt("idReservaAmenidad");
            String nombreAmenidad = rs.getString("nombreAmenidad");
            Timestamp fechaInicio = rs.getTimestamp("fechaInicio");
            Timestamp fechaFin = rs.getTimestamp("fechaFin");
            String nombreCompletoResidente = rs.getString("nombreCompletoResidente");
            String estado = rs.getString("estado");

            ReservaAmenidadTO reservaAmenidad = new ReservaAmenidadTO();
            reservaAmenidad.setIdReservaAmenidad(idReservaAmenidad);
            reservaAmenidad.setNombreAmenidad(nombreAmenidad); // Asegúrate de tener este método en tu clase ReservaAmenidadTO
            reservaAmenidad.setFechaInicio(fechaInicio);
            reservaAmenidad.setFechaFin(fechaFin);
            reservaAmenidad.setNombreCompletoResidente(nombreCompletoResidente); // Asegúrate de tener este método en tu clase ReservaAmenidadTO
            reservaAmenidad.setEstado(estado);

            listaRetornar.add(reservaAmenidad);
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

    
   **/ 
}

