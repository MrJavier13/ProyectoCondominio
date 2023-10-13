/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appcondominio.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aacas
 */
public class Servicios {
    
    private Connection conexion = null;

    public void conectar() {

        try {
            //parte 1

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            //Parte 2 
            this.conexion = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=condominio;user=sa;password=adminadmin;");

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    protected Connection getConexion() {
        try {
            if (this.conexion == null || !conexion.isClosed()) {
                
                this.conectar();
    
            }
        } catch (SQLException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.conexion;
    }

    public void desconectar() {

        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /*public Connection getConexion(){
            return this.conexion;
            }*/
    
}
