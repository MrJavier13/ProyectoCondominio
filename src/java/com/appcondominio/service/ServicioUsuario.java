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
    List<UsuarioTO> listaRetornar = new ArrayList<UsuarioTO>();

    try {
        ps = conn.prepareStatement("SELECT u.idUsuario, u.usuario, u.cedulaResidente, u.cedulaEmpleado, CASE WHEN u.cedulaEmpleado IS NOT NULL THEN e.nombreEmpleado + ' ' + e.primerApellido + ' ' + e.segundoApellido WHEN u.cedulaResidente IS NOT NULL THEN r.nombre + ' ' + r.primerApellido + ' ' + r.segundoApellido END AS nombreCompleto, ro.nombreRol, u.idRol, u.estado FROM Usuario u LEFT JOIN Rol ro ON u.idRol = ro.idRol LEFT JOIN Empleado e ON u.cedulaEmpleado = e.cedulaEmpleado LEFT JOIN Residente r ON u.cedulaResidente = r.cedulaResidente");
        rs = ps.executeQuery();

        while (rs.next()) {
            Integer idUsuario = rs.getInt("idUsuario");
            String nombreUsuario = rs.getString("usuario");
            Integer cedulaResidente = (Integer) rs.getObject("cedulaResidente");
            Integer cedulaEmpleado = (Integer) rs.getObject("cedulaEmpleado");
            String nombreCompleto = rs.getString("nombreCompleto");
            String nombreRol = rs.getString("nombreRol");
            Integer idRol = rs.getInt("idRol");
            String estado = rs.getString("estado");

            Integer cedulaAMostrar = (cedulaResidente != null) ? cedulaResidente : cedulaEmpleado;
           
            UsuarioTO usuario = new UsuarioTO();
           usuario.setIdUsuario(idUsuario);
            usuario.setUsuario(nombreUsuario);
            usuario.setCedulaAMostrar(cedulaAMostrar);
            usuario.setNombreCompleto(nombreCompleto);
            usuario.setNombreRol(nombreRol);
            usuario.setIdRol(idRol);
           usuario.setEstado(estado);
            listaRetornar.add(usuario);
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
                    if (usuarios.getCedulaEmpleado() != null){
                        int cedulaEmpleado = usuarios.getCedulaEmpleado();
                        String nombreCompleto = obtenerNombreCompleto(cedulaEmpleado);
                        usuarios.setNombreCompleto(nombreCompleto);
                    }
                    
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
    private String obtenerNombreCompleto(int cedulaEmpleado) {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String nombreCompleto = null;

        try {
            ps = conn.prepareStatement("SELECT nombreEmpleado, primerApellido, segundoApellido FROM empleado WHERE cedulaEmpleado = ?");
            ps.setInt(1, cedulaEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                String nombre = rs.getString("nombreEmpleado");
                String apellido1 = rs.getString("primerApellido");
                String apellido2 = rs.getString("segundoApellido");

                // Construir el nombre completo
                nombreCompleto = nombre + " " + apellido1 + " " + apellido2;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar recursos
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

        return nombreCompleto;
    }

    public int obtenerIdRolPorNombre(String nombreRol) {
        int idRol = 0;
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT idRol FROM Rol WHERE nombreRol = ?");
            ps.setString(1, nombreRol);
            rs = ps.executeQuery();

            if (rs.next()) {
                idRol = rs.getInt("idRol");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cierra recursos (ps y rs) y la conexión
        }

        return idRol;
    }
    
    public String obtenerNombreRolPorId(int idRol) {
        Connection conn = super.getConexion();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String nombreRol = null;

        try {
            ps = conn.prepareStatement("SELECT nombreRol FROM Rol WHERE idRol = ?");
            ps.setInt(1, idRol);
            rs = ps.executeQuery();

            if (rs.next()) {
                nombreRol = rs.getString("nombreRol");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cierra recursos (ps y rs) y la conexión
        }

        return nombreRol;
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
