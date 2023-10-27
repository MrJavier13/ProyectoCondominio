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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;


@ManagedBean(name="registroISService")
@ApplicationScoped
public class ServicioRegistroIS extends Servicios implements Serializable{
    
    public List<RegistroIngresosSalidasTO> mostrarRegistro() {
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
                
               /* registroIngresosSalidas.setIdRegistro(idRegistro);
                registroIngresosSalidas.setCedulaInvitadoTemporal(cedulaInvitadoTemporal);
                registroIngresosSalidas.setCedulaInvitadoPermanente(cedulaInvitadoPermanente);
                registroIngresosSalidas.setNombreCompletoInvitado(nombreCompletoInvitado);
                registroIngresosSalidas.setNombreEmpresa(nombreEmpresa);
                registroIngresosSalidas.setPlacaVehicular(placaVehicular);
                registroIngresosSalidas.setDetalle(detalle);
                registroIngresosSalidas.setFechaIngreso(fechaIngreso);
                registroIngresosSalidas.setFechaSalida(fechaSalida);
                registroIngresosSalidas.setNombreGuardaSeguridad(nombreGuardaSeguridad);
                registroIngresosSalidas.setPrimerApellidoGuarda(primerApellidoGuarda);
                registroIngresosSalidas.setSegundoApellidoGuarda(segundoApellidoGuarda);**/

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
    }
    
    public List<RegistroIngresosSalidasTO> buscarRegistrosPorFechas(Date fechaInicial, Date fechaFinal) {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<RegistroIngresosSalidasTO> listaRetornar = new ArrayList<>();

    try {
        java.sql.Date fechaInicialSQL = new java.sql.Date(fechaInicial.getTime());
        java.sql.Date fechaFinalSQL = new java.sql.Date(fechaFinal.getTime());
        ps = conn.prepareStatement("SELECT reg.idRegistro, reg.cedulaInvitadoTemporal, reg.cedulaInvitadoPermanente, reg.nombreCompletoInvitado, reg.nombreEmpresa, reg.placaVehiculo, reg.detalle, reg.fechaIngreso, reg.fechaSalida, e.nombreEmpleado, e.primerApellido, e.segundoApellido FROM registro_Ingreso_Salida reg JOIN Empleado e ON reg.cedulaGuardaSeguridad = e.cedulaEmpleado WHERE fechaIngreso >= ? AND fechaSalida <= ?");
        ps.setDate(1, fechaInicialSQL);
        ps.setDate(2, fechaFinalSQL);
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
                
               /* registroIngresosSalidas.setIdRegistro(idRegistro);
                registroIngresosSalidas.setCedulaInvitadoTemporal(cedulaInvitadoTemporal);
                registroIngresosSalidas.setCedulaInvitadoPermanente(cedulaInvitadoPermanente);
                registroIngresosSalidas.setNombreCompletoInvitado(nombreCompletoInvitado);
                registroIngresosSalidas.setNombreEmpresa(nombreEmpresa);
                registroIngresosSalidas.setPlacaVehicular(placaVehicular);
                registroIngresosSalidas.setDetalle(detalle);
                registroIngresosSalidas.setFechaIngreso(fechaIngreso);
                registroIngresosSalidas.setFechaSalida(fechaSalida);
                registroIngresosSalidas.setNombreGuardaSeguridad(nombreGuardaSeguridad);
                registroIngresosSalidas.setPrimerApellidoGuarda(primerApellidoGuarda);
                registroIngresosSalidas.setSegundoApellidoGuarda(segundoApellidoGuarda);**/

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
}

    
    /*public List<RegistroIngresosSalidasTO> mostrarRegistroPorFecha(Timestamp fechaInicio, Timestamp fechaFin) {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<RegistroIngresosSalidasTO> listaRetornar = new ArrayList<RegistroIngresosSalidasTO>();

    try {
        ps = conn.prepareStatement("SELECT \n" +
"    reg.idRegistro, \n" +
"    reg.cedulaInvitadoTemporal, \n" +
"    reg.cedulaInvitadoPermanente, \n" +
"    reg.nombreCompletoInvitado, \n" +
"    reg.nombreEmpresa, \n" +
"    reg.placaVehiculo, \n" +
"    reg.detalle, \n" +
"    reg.fechaIngreso, \n" +
"    reg.fechaSalida, \n" +
"    e.nombreEmpleado, \n" +
"    e.primerApellido, \n" +
"    e.segundoApellido \n" +
"FROM registro_Ingreso_Salida reg \n" +
"JOIN Empleado e ON reg.cedulaGuardaSeguridad = e.cedulaEmpleado\n" +
"WHERE reg.fechaIngreso BETWEEN ? AND ?");
        ps.setTimestamp(1, fechaInicio);
        ps.setTimestamp(2, fechaFin);
        rs = ps.executeQuery();

        while (rs.next()) {
            int idRegistro = rs.getInt("idRegistro");
            int cedulaInvitadoTemporal = rs.getInt("cedulaInvitadoTemporal");
            int cedulaInvitadoPermanente = rs.getInt("cedulaInvitadoPermanente");
            String nombreCompletoInvitado = rs.getString("nombreCompletoInvitadoTemp");
            String nombreEmpresa = rs.getString("nombreEmpresa");
            String placaVehicular = rs.getString("placaVehicular");
            String detalle = rs.getString("detalle");
            Timestamp  fechaIngreso = rs.getTimestamp("fechaIngreso");
            Timestamp  fechaSalida = rs.getTimestamp("fechaSalida");
            String nombreGuardaSeguridad = rs.getString("nombreEmpleado");
                String primerApellidoGuarda = rs.getString("primerApellido");
                String segundoApellidoGuarda = rs.getString("segundoApellido");

            RegistroIngresosSalidasTO registroIngresosSalidas = new RegistroIngresosSalidasTO(idRegistro, cedulaInvitadoTemporal, cedulaInvitadoPermanente, nombreCompletoInvitado, nombreEmpresa, placaVehicular, detalle, fechaIngreso, fechaSalida, nombreGuardaSeguridad, primerApellidoGuarda, segundoApellidoGuarda);

           /* registroIngresosSalidas.setIdRegistro(idRegistro);
            registroIngresosSalidas.setCedulaInvitadoTemporal(cedulaInvitadoTemporal);
            registroIngresosSalidas.setCedulaInvitadoPermanente(cedulaInvitadoPermanente);
            registroIngresosSalidas.setNombreCompletoInvitado(nombreCompletoInvitado);
            registroIngresosSalidas.setNombreEmpresa(nombreEmpresa);
            registroIngresosSalidas.setPlacaVehicular(placaVehicular);
            registroIngresosSalidas.setDetalle(detalle);
            registroIngresosSalidas.setFechaIngreso(fechaIngreso);
            registroIngresosSalidas.setFechaSalida(fechaSalida);
            registroIngresosSalidas.setNombreGuardaSeguridad(nombreGuardaSeguridad);
            registroIngresosSalidas.setPrimerApellidoGuarda(primerApellidoGuarda);
            registroIngresosSalidas.setSegundoApellidoGuarda(segundoApellidoGuarda);

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

        
    
}