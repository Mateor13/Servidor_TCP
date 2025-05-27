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