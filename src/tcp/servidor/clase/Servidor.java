package tcp.servidor.clase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {
    private static final String nombreArchivo = "asistencia.csv";
    // Metodo para capturar la fecha
    public static String getHora(String nombre, String tipo){
        DateFormat formato = new SimpleDateFormat("HH:mm:ss");
        return formato.format(new Date());
    }

    private static String getFecha(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    //Metodo para iniciar el udp.servidor
    public static void procesarSolicitud(int puerto) throws Exception {
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor de fechas corriendo...");
        while (true) {
           Socket client = servidor.accept();
            System.out.println("Cliente conectado");
            InputStream in = client.getInputStream();
            OutputStream out = client.getOutputStream();
            //Leer el nombre del empleado
            DataInputStream dis = new DataInputStream(in);
            String nombre = dis.readUTF();
            String tipo = dis.readUTF();
            if(nombre.equals("x") || tipo.equals("x")) break;
            String resultado= Servidor.getFecha();
            System.out.println("Mensaje de " + nombre + " recibido exitosamente");
            // Devolver la respuesta al cliente
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeUTF(resultado);
            client.close();
        }
        servidor.close();
    }
}
