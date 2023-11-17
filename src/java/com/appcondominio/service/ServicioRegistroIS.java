/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.service;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;

@ManagedBean(name = "registroISService")
@ApplicationScoped
public class ServicioRegistroIS extends Servicios implements Serializable {

    /*public List<RegistroIngresosSalidasTO> mostrarRegistro() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<RegistroIngresosSalidasTO> listaRetornar = new ArrayList<RegistroIngresosSalidasTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT reg.idRegistro, reg.cedulaInvitadoTemporal, reg.cedulaInvitadoPermanente, reg.nombreCompletoInvitado, reg.nombreEmpresa, reg.placaVehiculo, reg.detalle, reg.fechaIngreso, reg.fechaSalida, e.nombreEmpleado, e.primerApellido, e.segundoApellido FROM registro_Ingreso_Salida reg JOIN Empleado e ON reg.cedulaGuardaSeguridad = e.cedulaEmpleado");
            rs = ps.executeQuery();


            while (rs.next()) {

                int idRegistro = rs.getInt("idRegistro");
                int cedulaInvitadoTemporal = rs.getInt("cedulaInvitadoTemporal");
                int cedulaInvitadoPermanente = rs.getInt("cedulaInvitadoPermanente");
                String nombreCompletoInvitado = rs.getString("nombreCompletoInvitado");
                String nombreEmpresa = rs.getString("nombreEmpresa");
                String placaVehicular = rs.getString("placaVehiculo");
                String detalle = rs.getString("detalle");
                Timestamp  fechaIngreso = rs.getTimestamp("fechaIngreso");
                Timestamp  fechaSalida = rs.getTimestamp("fechaSalida");
                String nombreGuardaSeguridad = rs.getString("nombreEmpleado");
                String primerApellidoGuarda = rs.getString("primerApellido");
                String segundoApellidoGuarda = rs.getString("segundoApellido");
                
                

                RegistroIngresosSalidasTO registroIngresosSalidas = new RegistroIngresosSalidasTO(idRegistro, cedulaInvitadoTemporal, cedulaInvitadoPermanente, nombreCompletoInvitado, nombreEmpresa, placaVehicular, detalle, fechaIngreso, fechaSalida, nombreGuardaSeguridad, primerApellidoGuarda, segundoApellidoGuarda);
                
           

                listaRetornar.add(registroIngresosSalidas);

                
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
    public List<RegistroIngresosSalidasTO> buscarRegistrosPorFechas(Timestamp fechaInicial, Timestamp fechaFinal) {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RegistroIngresosSalidasTO> listaRetornar = new ArrayList<RegistroIngresosSalidasTO>();

        try {
            ps = conn.prepareStatement("SELECT reg.idRegistro, reg.cedulaInvitadoTemporal, reg.cedulaInvitadoPermanente, reg.nombreCompletoInvitado, reg.nombreEmpresa, reg.placaVehiculo, reg.detalle, reg.fechaIngreso, reg.fechaSalida, reg.cedulaGuardaSeguridad, e.nombreEmpleado, e.primerApellido, e.segundoApellido FROM registro_Ingreso_Salida reg JOIN Empleado e ON reg.cedulaGuardaSeguridad = e.cedulaEmpleado WHERE reg.fechaIngreso BETWEEN ? AND ?");
            ps.setTimestamp(1, fechaInicial);
            ps.setTimestamp(2, fechaFinal);
            rs = ps.executeQuery();

            while (rs.next()) {
                Integer idRegistro = rs.getInt("idRegistro");
                Integer cedulaInvitadoTemporal = (Integer) rs.getObject("cedulaInvitadoTemporal");
                Integer cedulaInvitadoPermanente = (Integer) rs.getObject("cedulaInvitadoPermanente");
                String nombreCompletoInvitado = rs.getString("nombreCompletoInvitado");
                String nombreEmpresa = rs.getString("nombreEmpresa");
                String placaVehicular = rs.getString("placaVehiculo");
                String detalle = rs.getString("detalle");
                Timestamp fechaIngreso = rs.getTimestamp("fechaIngreso");
                Timestamp fechaSalida = rs.getTimestamp("fechaSalida");
                Integer cedulaGuardaSeguridad = rs.getInt("cedulaGuardaSeguridad");
                String nombreGuardaSeguridad = rs.getString("nombreEmpleado");
                String primerApellidoGuarda = rs.getString("primerApellido");
                String segundoApellidoGuarda = rs.getString("segundoApellido");

                Integer cedulaAMostrar = (cedulaInvitadoTemporal != null) ? cedulaInvitadoTemporal : cedulaInvitadoPermanente;

                RegistroIngresosSalidasTO registroIngresosSalidas = new RegistroIngresosSalidasTO(idRegistro, cedulaInvitadoPermanente, cedulaInvitadoTemporal, cedulaAMostrar, nombreCompletoInvitado, nombreEmpresa, placaVehicular, detalle, fechaIngreso, fechaSalida, cedulaGuardaSeguridad, nombreGuardaSeguridad, primerApellidoGuarda, segundoApellidoGuarda);

                listaRetornar.add(registroIngresosSalidas);
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

    
    public int buscarCedulaInvitado(String buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int resultado = 0;

        try {

            ps = super.getConexion().prepareStatement("SELECT cedulaInvitadoPermanente FROM Invitado_Permanente WHERE nombreInvitadoPermanente + ' ' + primerApellidoPermanente + ' ' + segundoApellidoPermanente = ?");
            ps.setString(1, buscar);
            rs = ps.executeQuery();

            if (rs.next()) {
                resultado = rs.getInt("cedulaInvitadoPermanente");

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

        return resultado;
    }
    
    public List<RegistroIngresosSalidasTO> buscarRegistrosPorInvitadosPer (int buscar) {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<RegistroIngresosSalidasTO> listaRetornar = new ArrayList<RegistroIngresosSalidasTO>();

        try {
            ps = conn.prepareStatement("SELECT reg.idRegistro, reg.cedulaInvitadoTemporal, reg.cedulaInvitadoPermanente, reg.nombreCompletoInvitado, reg.nombreEmpresa, reg.placaVehiculo, reg.detalle, reg.fechaIngreso, reg.fechaSalida, reg.cedulaGuardaSeguridad, e.nombreEmpleado, e.primerApellido, e.segundoApellido FROM registro_Ingreso_Salida reg JOIN Empleado e ON reg.cedulaGuardaSeguridad = e.cedulaEmpleado WHERE reg.cedulaInvitadoPermanente = ?");
            ps.setInt(1, buscar);
            rs = ps.executeQuery();

            while (rs.next()) {
                Integer idRegistro = rs.getInt("idRegistro");
                Integer cedulaInvitadoTemporal = (Integer) rs.getObject("cedulaInvitadoTemporal");
                Integer cedulaInvitadoPermanente = (Integer) rs.getObject("cedulaInvitadoPermanente");
                String nombreCompletoInvitado = rs.getString("nombreCompletoInvitado");
                String nombreEmpresa = rs.getString("nombreEmpresa");
                String placaVehicular = rs.getString("placaVehiculo");
                String detalle = rs.getString("detalle");
                Timestamp fechaIngreso = rs.getTimestamp("fechaIngreso");
                Timestamp fechaSalida = rs.getTimestamp("fechaSalida");
                Integer cedulaGuardaSeguridad = rs.getInt("cedulaGuardaSeguridad");
                String nombreGuardaSeguridad = rs.getString("nombreEmpleado");
                String primerApellidoGuarda = rs.getString("primerApellido");
                String segundoApellidoGuarda = rs.getString("segundoApellido");

                Integer cedulaAMostrar = (cedulaInvitadoTemporal != null) ? cedulaInvitadoTemporal : cedulaInvitadoPermanente;

                RegistroIngresosSalidasTO registroIngresosSalidas = new RegistroIngresosSalidasTO(idRegistro, cedulaInvitadoPermanente, cedulaInvitadoTemporal, cedulaAMostrar, nombreCompletoInvitado, nombreEmpresa, placaVehicular, detalle, fechaIngreso, fechaSalida, cedulaGuardaSeguridad, nombreGuardaSeguridad, primerApellidoGuarda, segundoApellidoGuarda);

                listaRetornar.add(registroIngresosSalidas);
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
    
    public void insertarRegistro(RegistroIngresosSalidasTO registro) {

        PreparedStatement ps = null;
        try {
            ps = super.getConexion().prepareStatement("INSERT INTO Registro_Ingreso_Salida (cedulaInvitadoPermanente, cedulaInvitadoTemporal, nombreCompletoInvitado, nombreEmpresa, placaVehiculo, detalle, fechaIngreso, fechaSalida, cedulaGuardaSeguridad) VALUES (?,?,?,?,?,?,?,?,?)");
            if (registro.getCedulaInvitadoPermanente() == 0) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, registro.getCedulaInvitadoPermanente());
            }
            if (registro.getCedulaInvitadoTemporal() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, registro.getCedulaInvitadoTemporal());
            }
            ps.setString(3, registro.getNombreCompletoInvitado());
            ps.setString(4, registro.getNombreEmpresa());
            ps.setString(5, registro.getPlacaVehicular());
            ps.setString(6, registro.getDetalle());
            ps.setTimestamp(7, registro.getFechaIngreso());
            if (registro.getFechaSalida() == null) {
                ps.setNull(8, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(8, registro.getFechaSalida());
            }
           
            ps.setInt(9, registro.getCedulaGuardaSeguridad());

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

    public void actualizarRegistro(RegistroIngresosSalidasTO registro) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("UPDATE Registro_Ingreso_Salida SET cedulaInvitadoPermanente=?, cedulaInvitadoTemporal=?, nombreCompletoInvitado=?, nombreEmpresa=?, placaVehiculo=?, detalle=?, fechaIngreso=?, fechaSalida=?, cedulaGuardaSeguridad=?  WHERE idRegistro =?");
            if (registro.getCedulaInvitadoPermanente() == 0) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, registro.getCedulaInvitadoPermanente());
            }
            if (registro.getCedulaInvitadoTemporal() == 0) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, registro.getCedulaInvitadoTemporal());
            }
            ps.setString(3, registro.getNombreCompletoInvitado());
            ps.setString(4, registro.getNombreEmpresa());
            ps.setString(5, registro.getPlacaVehicular());
            ps.setString(6, registro.getDetalle());
            ps.setTimestamp(7, registro.getFechaIngreso());
            if (registro.getFechaSalida() == null) {
                ps.setNull(8, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(8, registro.getFechaSalida());
            }
           
            ps.setInt(9, registro.getCedulaGuardaSeguridad());
            ps.setInt(10, registro.getIdRegistro());
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
    
    public boolean buscarIdRegistro(int buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT idRegistro FROM Registro_Ingreso_Salida WHERE idRegistro = ?");
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
    
    public RegistroIngresosSalidasTO obtenerInvitadoPermanentePorCedula(Integer cedula) {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    RegistroIngresosSalidasTO invitadoPermanente = null;

    try {
        ps = conn.prepareStatement("SELECT CONCAT(nombreInvitadoPermanente, ' ', primerApellidoPermanente, ' ', segundoApellidoPermanente) AS nombreCompleto, placaVehiculo FROM Invitado_Permanente WHERE cedulaInvitadoPermanente = ?");
        ps.setInt(1, cedula);
        rs = ps.executeQuery();

        if (rs.next()) {
            invitadoPermanente = new RegistroIngresosSalidasTO();
         //   invitadoPermanente.setCedulaAMostrar(rs.getInt("cedulaInvitadoPermanente"));
            invitadoPermanente.setNombreCompletoInvitado(rs.getString("nombreCompleto"));
            invitadoPermanente.setPlacaVehicular(rs.getString("placaVehiculo"));
            
            
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

    return invitadoPermanente;
}

}