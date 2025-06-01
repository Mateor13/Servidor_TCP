package rmi.servidor.clase;

import java.rmi.Remote;
import java.util.ArrayList;

public interface Servidor extends Remote {
    // Consultar id del empleado
    public String consultar(int id) throws Exception;
    // Obtener todas las personas
    public ArrayList<Persona> getTodasPersonas() throws Exception;
    // Eliminar persona por su id
    public String eliminar(int id) throws Exception;
    // Actualizar persona por su id
    public String actualizar(int id, String nombre, String correo, String cargo, double sueldo) throws Exception;
    // Agregar una nueva persona
    public String agregar(String nombre, String correo, String cargo, double sueldo) throws Exception;
}
