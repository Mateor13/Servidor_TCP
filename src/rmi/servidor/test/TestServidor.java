package rmi.servidor.test;
import rmi.servidor.clase.Servidor;
import rmi.servidor.clase.ServidorImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class TestServidor {
    public static void main(String[] args) throws Exception {
        String ip = System.getenv("RMI_HOST");
        System.setProperty("java.rmi.server.hostname", ip);
        System.out.println("🔧 IP configurada como: " + ip);

        LocateRegistry.createRegistry(1099);

        ServidorImpl servidor = new ServidorImpl();
        // Exporta explícitamente el objeto en el puerto 1100
        Servidor stub = (Servidor) UnicastRemoteObject.exportObject(servidor, 1100);

        Naming.rebind("Datos", stub);

        System.out.println("✅ Servidor RMI corriendo...");
    }
}
