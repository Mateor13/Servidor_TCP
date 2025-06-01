package rmi.servidor.clase;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
public class ServidorImpl extends UnicastRemoteObject implements Servidor {
    private static ArrayList<Persona> personas = new ArrayList<>();
    public ServidorImpl() throws RemoteException {
        super();
        personas = getPersonas();
    }
    @Override
    public String eliminar(int id) {
        Persona persona = getPersona(id);
        try {
            if (persona != null) {
                personas.remove(persona);
                Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Det-Pc\\IdeaProjects\\Servidor_TCP\\src\\rmi\\servidor\\data\\empleados.db");
                // Eliminar el empleado con el ID proporcionado
                String sql = "DELETE FROM empleado WHERE id = " + id;
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                // Actualizar los ID de los empleados restantes
                sql = "UPDATE empleado SET id = id - 1 WHERE id > " + id;
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
                // Actualizar la lista de personas en memoria
                personas = getPersonas();
                return "Empleado eliminado correctamente y IDs actualizados";
            } else {
                return "No existe el empleado con el ID proporcionado";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al eliminar el empleado: " + e.getMessage();
        }
    }
    @Override
    public String actualizar(int id, String nombre, String correo, String cargo, double sueldo) {
        Persona persona = getPersona(id);
        try {
            if (persona != null) {
                persona.setNombre(nombre);
                persona.setCorreo(correo);
                persona.setCargo(cargo);
                persona.setSueldo(sueldo);
                Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Det-Pc\\IdeaProjects\\Servidor_TCP\\src\\rmi\\servidor\\data\\empleados.db");
                String sql = "UPDATE empleado SET nombre = '" + nombre + "', correo = '" + correo + "', cargo = '" + cargo + "', sueldo = " + sueldo + " WHERE id = " + id;
                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
                conn.close();
                return "Empleado actualizado correctamente";
            } else {
                return "No existe el empleado con el ID proporcionado";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al actualizar el empleado: " + e.getMessage();
        }
    }
    @Override
    public ArrayList<Persona> getTodasPersonas() {
        return personas;
    }
    @Override
    public String agregar(String nombre, String correo, String cargo, double sueldo) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Det-Pc\\IdeaProjects\\Servidor_TCP\\src\\rmi\\servidor\\data\\empleados.db");
            String sql = "INSERT INTO empleado (nombre, correo, cargo, sueldo) VALUES ('" + nombre + "', '" + correo + "', '" + cargo + "', " + sueldo + ")";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
            int id = personas.size() + 1; // Asignar un ID secuencial
            Persona nuevaPersona = new Persona(id, nombre, correo, cargo, sueldo);
            personas.add(nuevaPersona);
            return "Empleado agregado correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al agregar el empleado: " + e.getMessage();
        }
    }
    public static ArrayList<Persona> getPersonas() {
        ArrayList<Persona> lista = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String ruta = "C:\\Users\\Det-Pc\\IdeaProjects\\Servidor_TCP\\src\\rmi\\servidor\\data\\empleados.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + ruta);
            String sql = "SELECT id, nombre, correo, cargo, sueldo FROM empleado";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int clave = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String cargo = rs.getString("cargo");
                double sueldo = rs.getDouble("sueldo");
                Persona p = new Persona(clave, nombre, correo, cargo, sueldo);
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception ignored) {
            }
            try {
                if (stmt != null) stmt.close();
            } catch (Exception ignored) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }
        return lista;
    }
    private Persona getPersona(int id) {
        for (Persona p : personas) {
            if (p.getClave() == id) {
                return p;
            }
        }
        return null;
    }
    @Override
    public String consultar(int id) {
        Persona persona = getPersona(id);
        if (persona != null) {
            return persona.toString();
        } else {
            return "No existen los datos del empleado";
        }
    }
}