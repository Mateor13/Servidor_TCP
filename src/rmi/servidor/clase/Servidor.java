package rmi.servidor.clase;

import java.rmi.Remote;

public interface Servidor extends Remote {
    // Consultar id del empleado
    public String consultar(int id) throws Exception;
}
