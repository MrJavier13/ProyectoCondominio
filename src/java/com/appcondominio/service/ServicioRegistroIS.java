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
            ps = conn.prepareStatement("SELECT idRegistro, cedulaInvitadoTemporal, cedulaInvitadoPermanente, nombreCompletoInvitadoTemp, nombreEmpresa, placaVehicular, detalle, fechaIngreso, fechaSalida, cedulaGuardaSeguridad FROM registro_Ingreso_Salida");
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
                int cedulaGuardaSeguridad = rs.getInt("cedulaGuardaSeguridad");
                
                

                RegistroIngresosSalidasTO registroIngresosSalidas = new RegistroIngresosSalidasTO();
                
                registroIngresosSalidas.setIdRegistro(idRegistro);
                registroIngresosSalidas.setCedulaInvitadoTemporal(cedulaInvitadoTemporal);
                registroIngresosSalidas.setCedulaInvitadoPermanente(cedulaInvitadoPermanente);
                registroIngresosSalidas.setNombreCompletoInvitado(nombreCompletoInvitado);
                registroIngresosSalidas.setNombreEmpresa(nombreEmpresa);
                registroIngresosSalidas.setPlacaVehicular(placaVehicular);
                registroIngresosSalidas.setDetalle(detalle);
                registroIngresosSalidas.setFechaIngreso(fechaIngreso);
                registroIngresosSalidas.setFechaSalida(fechaSalida);
                registroIngresosSalidas.setCedulaGuardaSeguridad(cedulaGuardaSeguridad);

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
    
    public List<RegistroIngresosSalidasTO> mostrarRegistroPorFecha(Timestamp fechaInicio, Timestamp fechaFin) {
    Connection conn = super.getConexion();
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<RegistroIngresosSalidasTO> listaRetornar = new ArrayList<RegistroIngresosSalidasTO>();

    try {
        ps = conn.prepareStatement("SELECT * FROM registro_Ingreso_Salida WHERE fechaIngreso BETWEEN ? AND ?");
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
            int cedulaGuardaSeguridad = rs.getInt("cedulaGuardaSeguridad");

            RegistroIngresosSalidasTO registroIngresosSalidas = new RegistroIngresosSalidasTO();

            registroIngresosSalidas.setIdRegistro(idRegistro);
            registroIngresosSalidas.setCedulaInvitadoTemporal(cedulaInvitadoTemporal);
            registroIngresosSalidas.setCedulaInvitadoPermanente(cedulaInvitadoPermanente);
            registroIngresosSalidas.setNombreCompletoInvitado(nombreCompletoInvitado);
            registroIngresosSalidas.setNombreEmpresa(nombreEmpresa);
            registroIngresosSalidas.setPlacaVehicular(placaVehicular);
            registroIngresosSalidas.setDetalle(detalle);
            registroIngresosSalidas.setFechaIngreso(fechaIngreso);
            registroIngresosSalidas.setFechaSalida(fechaSalida);
            registroIngresosSalidas.setCedulaGuardaSeguridad(cedulaGuardaSeguridad);

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

        
    
}