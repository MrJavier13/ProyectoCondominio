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
            ps = conn.prepareStatement("SELECT idUsuario, usuario, contrasena, cedulaResidente, cedulaEmpleado, idRol, estado FROM usuario");
            rs = ps.executeQuery();

            while (rs.next()) {
                
                int idUsuario = rs.getInt("idUsuario");
                String usuario = rs.getString("usuario");
                String contrasena = rs.getString("contrasena");
                int cedulaResidente = rs.getInt("cedulaResidente");
                Integer cedulaEmpleado = rs.getInt("cedulaEmpleado");
                int idRol = rs.getInt("idrol");
                String estado = rs.getString("estado");

                UsuarioTO usuarios = new UsuarioTO();
                usuarios.setIdUsuario(idUsuario);
                usuarios.setUsuario(usuario);
                usuarios.setContrasena(contrasena);
                usuarios.setCedulaResidente(cedulaResidente);
                usuarios.setCedulaEmpleado(cedulaEmpleado);
                usuarios.setIdRol(idRol);
                usuarios.setEstado(estado);
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
    
    public UsuarioTO UsuarioContrasenna(String usuario, String contrasena) {

        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        UsuarioTO usuarios = null;
        try {

            ps = conn.prepareStatement("SELECT usuario, contrasena, idRol FROM usuario WHERE usuario = ? AND contrasena = ?");
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            rs = ps.executeQuery();

            while (rs.next()) {

                usuarios = new UsuarioTO();

                usuarios.setUsuario(rs.getString("usuario"));

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
    
    public boolean buscarUsuario(String buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT usuario FROM usuario WHERE usuario = ?");
            ps.setString(1, buscar);
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
    
        public void insertarUsuario(UsuarioTO usuario) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("INSERT INTO usuario (usuario, contrasena, cedulaResidente, cedulaEmpleado, idRol, estado) VALUES (?,?,?,?,?,?,?,?,?)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getCedulaResidente());
            ps.setInt(4, usuario.getCedulaEmpleado());
            ps.setInt(5, usuario.getIdRol());
            ps.setString(6, usuario.getEstado());
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
        
        public void actualizarUsuario(UsuarioTO usuario) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("UPDATE usuario SET usuario=?, contrasena=?, cedulaResidente=?, cedulaEmpleado=?, idRol=?, estado=?  WHERE usuario =?");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setInt(3, usuario.getCedulaResidente());
            ps.setInt(4, usuario.getCedulaEmpleado());
            ps.setInt(5, usuario.getIdRol());
            ps.setString(6, usuario.getEstado());
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
