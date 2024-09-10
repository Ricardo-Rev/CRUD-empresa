/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author DELL
 */
public class Puesto {
    private int id;
    private String puesto;
    Conexion cn;
    public Puesto() {}

    public Puesto(int idPuesto, String puesto) {
        this.id = idPuesto;
        this.puesto = puesto;
    }

    public int getIdPuesto() {
        return id;
    }

    public void setIdPuesto(int idPuesto) {
        this.id = idPuesto;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    // Método para agregar un nuevo puesto
    public void agregarPuesto() {
        try {
            PreparedStatement parametro;
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "INSERT INTO puestos (puesto) VALUES (?);";
            parametro = (PreparedStatement) cn.conexionDB.prepareStatement(query);
             parametro.setString(1, getPuesto());
            int ejecutar = parametro.executeUpdate();
            System.out.println("Puesto agregado correctamente" + Integer.toString(ejecutar));
            cn.cerrar_conexion();
        } catch (SQLException e) {
            System.out.println("Error al agregar puesto: " + e.getMessage());
        }
    }
    
    // Método para leer los puestos y mostrarlos en una tabla
    public DefaultTableModel leerPuestos() {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "SELECT * FROM puestos;";
            ResultSet consulta = cn.conexionDB.createStatement().executeQuery(query);
            String[] encabezado = {"id_puestos", "puesto"};
            tabla.setColumnIdentifiers(encabezado);
            String[] datos = new String[2];
            while (consulta.next()) {
                datos[0] = consulta.getString("id_puestos");
                datos[1] = consulta.getString("puesto");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error en leer puestos: " + ex.getMessage());
        }
        return tabla;
    }
    
    // Método para actualizar un puesto existente
    public void actualizar() {
        try {
            PreparedStatement parametro;
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "UPDATE puestos SET puesto = ? WHERE id_puestos = ?";
            parametro = (PreparedStatement) cn.conexionDB.prepareStatement(query);
            parametro.setString(1, getPuesto());
            parametro.setInt(2, getIdPuesto());
            int ejecutar = parametro.executeUpdate();
            System.out.println("Puesto actualizado correctamente" + Integer.toString(ejecutar));
            cn.cerrar_conexion();
        } catch (SQLException e) {
            System.out.println("Error al actualizar puesto: " + e.getMessage());
        }
    }

    // Método para borrar un puesto
    public void borrar() {
        try {
            PreparedStatement parametro;
            cn= new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM puestos WHERE id_puestos = ?";
            parametro = (PreparedStatement) cn.conexionDB.prepareStatement(query);
            parametro.setInt(1, getIdPuesto());
            int ejecutar = parametro.executeUpdate();
            System.out.println("Puesto eliminado correctamente" + Integer.toString(ejecutar));
            cn.cerrar_conexion();
        } catch (SQLException e) {
            System.out.println("Error al eliminar puesto: " + e.getMessage());
        }
    }
}
