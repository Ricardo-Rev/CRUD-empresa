/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author DELL
 */
public class Empleado extends Persona {
    private String codigo;
    private int id;
    private int idPuesto;

    // Constructor por defecto
    public Empleado() {}

    // Constructor con parámetros
    public Empleado(int id, String codigo, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento, int idPuesto) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento);
        this.id = id;
        this.codigo = codigo;
        this.idPuesto = idPuesto;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(int idPuesto) {
        this.idPuesto = idPuesto;
    }
    
    // Método para agregar un nuevo empleado
    @Override
    public void agregar() {
        try {
            Conexion cn = new Conexion();
            cn.abrir_conexion();
            String query = "INSERT INTO empleados(codigo, nombres, apellidos, direccion, telefono, fecha_nacimiento, id_puesto) VALUES(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement parametro = cn.conexionDB.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getIdPuesto()); // Usar ID del puesto
            int ejecutar = parametro.executeUpdate();
            System.out.println("Ingreso exitoso, han afectado: " + ejecutar);
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Algo salió mal: " + ex.getMessage());
        }
    }

    // Método para leer empleados y mostrarlos en una tabla
    @Override
    public DefaultTableModel leer() {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            Conexion cn = new Conexion();
            cn.abrir_conexion();
            String query = "SELECT e.id_empleados, e.codigo, e.nombres, e.apellidos, e.direccion, e.telefono, e.fecha_nacimiento, p.puesto " +
                           "FROM empleados e " +
                           "INNER JOIN puestos p ON e.id_puesto = p.id_puestos;";
            ResultSet consulta = cn.conexionDB.createStatement().executeQuery(query);
            String encabezado[] = {"id", "codigo", "nombres", "apellidos", "direccion", "telefono", "nacimiento", "puesto"};
            tabla.setColumnIdentifiers(encabezado);
            String datos[] = new String[8];
            while (consulta.next()) {
                datos[0] = consulta.getString("id_empleados");
                datos[1] = consulta.getString("codigo");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("puesto");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error en leer: " + ex.getMessage());
        }
        return tabla;
    }

    public void actualizar() {
        try {
            Conexion cn = new Conexion();
            cn.abrir_conexion();
            String query = "UPDATE empleados SET codigo = ?, nombres = ?, apellidos = ?, direccion = ?, telefono = ?, fecha_nacimiento = ?, id_puesto = ? WHERE id_empleados = ?;";
            PreparedStatement parametro = cn.conexionDB.prepareStatement(query);
            parametro.setString(1, getCodigo());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setInt(7, getIdPuesto()); // Usar ID del puesto
            parametro.setInt(8, getId());

            // Línea de depuración
            System.out.println("ID del puesto para actualización: " + getIdPuesto());

            int ejecutar = parametro.executeUpdate();
            System.out.println("Modificación exitosa: " + ejecutar);
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error en modificación: " + ex.getMessage());
        }
    }


    // Método para borrar un empleado
    public void borrar() {
        try {
            Conexion cn = new Conexion();
            cn.abrir_conexion();
            String query = "DELETE FROM empleados WHERE id_empleados = ?;";
            PreparedStatement parametro = cn.conexionDB.prepareStatement(query);
            parametro.setInt(1, getId());
            int ejecutar = parametro.executeUpdate();
            System.out.println("Eliminación exitosa: " + ejecutar);
            cn.cerrar_conexion();
        } catch (SQLException ex) {
            System.out.println("Error en borrar: " + ex.getMessage());
        }
    }
    
    public void rellenarComboBox(String tabla, String dato, JComboBox<String> combo) {
        combo.removeAllItems(); // Limpiar los elementos anteriores del JComboBox
        combo.addItem("0) Elija puesto"); // Agregar item de opción por defecto

        String sql = "SELECT id_puestos, " + dato + " FROM " + tabla;
        Conexion cn = new Conexion();
        ResultSet consulta = null;

        try {
            cn.abrir_conexion();
            consulta = cn.conexionDB.createStatement().executeQuery(sql);

            while (consulta.next()) {
                int idPuesto = consulta.getInt("id_puestos");
                String nombrePuesto = consulta.getString(dato);
                String item = idPuesto + ") " + nombrePuesto;
                combo.addItem(item);
            }
        } catch (SQLException ex) {
            System.out.println("Error en el ComboBox: " + ex.getMessage());
        } finally {
            try {
                if (consulta != null) consulta.close();
                cn.cerrar_conexion();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }


}