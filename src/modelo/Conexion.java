/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author DELL
 */
public class Conexion {
    private final String puerto = "3306";
    private final String db = "db_empresa3";
    private final String urlConexion = String.format( "jdbc:mysql://localhost:%s/%s?serverTimezone=UTC",puerto, db);
    private final String usuario = "root";
    private final String contra = "1234";
    private final String jdbc = "com.mysql.cj.jdbc.Driver";
    
    public Connection conexionDB;
    
    public void abrir_conexion(){
        try {
            Class.forName(jdbc);
            conexionDB = DriverManager.getConnection(urlConexion, usuario, contra);
            System.out.println("Conexión exitosa :)");
        } catch(ClassNotFoundException | SQLException ex){
            System.out.println("Algo salio mal :( " + ex.getMessage());
        }
    }
    
    public void cerrar_conexion(){
        try {
            conexionDB.close();
        } catch(SQLException ex){
            System.out.println("Algo salio mal :( " + ex.getMessage());
        }
    }
}
