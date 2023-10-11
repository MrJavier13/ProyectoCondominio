/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@ManagedBean(name="usuarioService")
@ApplicationScoped
public class ServicioUsuario extends Servicios{
    
    public List<UsuarioTO> mostrarUsuarios() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<UsuarioTO> listaRetornar = new ArrayList<UsuarioTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT correoElectronico, contrasenna, rol FROM usuario");
            rs = ps.executeQuery();

            while (rs.next()) {

                String correoElectronico = rs.getString("correoElectronico");
                String contrasenna = rs.getString("contrasenna");
                int rol = rs.getInt("contrasenna");

                UsuarioTO usuarios = new UsuarioTO();
                usuarios.setCorreoElectronico(correoElectronico);
                usuarios.setContrasenna(contrasenna);
                usuarios.setRol(rol);
                listaRetornar.add(usuarios);

                
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
    
    public UsuarioTO UsuarioContrasenna(String usuario, String contrasenna) {

        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioTO usuarios = null;
        try {

            ps = conn.prepareStatement("SELECT correoElectronico, contrasenna, rol FROM usuario WHERE correoElectronico = ? AND contrasenna = ?");
            ps.setString(1, usuario);
            ps.setString(2, contrasenna);
            rs = ps.executeQuery();

            while (rs.next()) {

                usuarios = new UsuarioTO();

                usuarios.setCorreoElectronico(rs.getString("correoElectronico"));

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //Paso 5 (Cerrar)  
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
        return usuarios;
    }
    
    
}
