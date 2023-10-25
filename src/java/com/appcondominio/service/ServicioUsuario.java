/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.service;

import encriptar.BCrypt;
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
@ManagedBean(name = "usuarioService")
@ApplicationScoped
public class ServicioUsuario extends Servicios implements Serializable {

    public List<UsuarioTO> mostrarUsuarios() {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        //Lista
        List<UsuarioTO> listaRetornar = new ArrayList<UsuarioTO>();

        try {

            //super.conectar();
            ps = conn.prepareStatement("SELECT idUsuario, usuario, cedulaResidente, cedulaEmpleado, idRol, estado FROM usuario");
            rs = ps.executeQuery();

            while (rs.next()) {

                Integer idUsuario = rs.getInt("idUsuario");
                String usuario = rs.getString("usuario");
                Integer cedulaResidente = rs.getInt("cedulaResidente");
                Integer cedulaEmpleado = rs.getInt("cedulaEmpleado");
                Integer idRol = rs.getInt("idrol");
                String estado = rs.getString("estado");

                UsuarioTO usuarios = new UsuarioTO();
                usuarios.setIdUsuario(idUsuario);
                usuarios.setUsuario(usuario);
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

            ps = conn.prepareStatement("SELECT * FROM usuario WHERE usuario = ?");
            ps.setString(1, usuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                usuarios = new UsuarioTO();

                if (BCrypt.checkpw(contrasena, rs.getString("contrasena"))) {
                    usuarios.setIdUsuario(rs.getInt("idUsuario"));
                    usuarios.setUsuario(rs.getString("usuario"));
                    usuarios.setContrasena(rs.getString("contrasena"));
                    usuarios.setCedulaResidente(rs.getInt("cedulaResidente"));
                    usuarios.setCedulaEmpleado(rs.getInt("cedulaEmpleado"));
                    usuarios.setIdRol(rs.getInt("idRol"));
                    usuarios.setEstado(rs.getString("estado"));
                } else {
                    usuarios = null;
                }

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
    
    public boolean buscarCedulaUsuario(Integer buscar) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean busqueda = true;

        try {

            ps = super.getConexion().prepareStatement("SELECT cedulaEmpleado FROM usuario WHERE cedulaEmpleado = ?");
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

    public void insertarUsuarioResidente(UsuarioTO usuario) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("INSERT INTO usuario (usuario, contrasena, cedulaResidente, idRol, estado) VALUES (?,?,?,?,?)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(10)));
            ps.setInt(3, usuario.getCedulaResidente());
            ps.setInt(4, usuario.getIdRol());
            ps.setString(5, usuario.getEstado());
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
    
    public void insertarUsuarioPersonal(UsuarioTO usuario) {

        PreparedStatement ps = null;

        try {
            ps = super.getConexion().prepareStatement("INSERT INTO usuario (usuario, contrasena, cedulaEmpleado, idRol, estado) VALUES (?,?,?,?,?)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(10)));
            ps.setInt(3, usuario.getCedulaEmpleado());
            ps.setInt(4, usuario.getIdRol());
            ps.setString(5, usuario.getEstado());
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
            ps = super.getConexion().prepareStatement("UPDATE usuario SET usuario=?, cedulaResidente=?, cedulaEmpleado=?, idRol=?, estado=?  WHERE idUsuario =?");
            ps.setString(1, usuario.getUsuario());
            ps.setInt(2, usuario.getCedulaResidente());
            ps.setInt(3, usuario.getCedulaEmpleado());
            ps.setInt(4, usuario.getIdRol());
            ps.setString(5, usuario.getEstado());
            ps.setInt(6, usuario.getIdUsuario());
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
