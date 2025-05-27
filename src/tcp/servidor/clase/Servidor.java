package tcp.servidor.clase;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class Servidor {
    private static final String nombreArchivo = "ReporteEmpleado.dat";
    // Método para actualizar o crear una persona en la lista de asistencia
    private static Persona actualizarPersona(String nombre, String tipo, String hora) throws IOException, ClassNotFoundException {
        // Cargar la lista de personas desde el archivo
        List<Persona> personas = cargarPersonas();
        Persona persona = null;
        // Buscar persona por nombre
        for (Persona p : personas) {
            // Si la persona ya existe, asignarla
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                // Asignar la persona encontrada
                persona = p;
                break;
            }
        }
        // Si no existe, crear nueva
        if (persona == null) {
            // Crear nueva persona con el nombre proporcionado
            persona = new Persona(nombre);
            // Añadir la nueva persona a la lista
            personas.add(persona);
        }
        // Actualizar hora
        persona.setHora(tipo, hora);
        // Guardar lista actualizada
        guardarPersonas(personas);
        // Retornar la persona actualizada
        return persona;
    }
    // Método para cargar la lista de personas desde el archivo
    private static List<Persona> cargarPersonas() throws IOException, ClassNotFoundException {
        // Tomar el archivo de asistencia
        File archivo = new File(nombreArchivo);
        // Si el archivo no existe, retornar una lista vacía
        if (!archivo.exists()) return new ArrayList<>();
        // Si el archivo existe, leer la lista de personas
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            // Retornar la lista de personas
            return (List<Persona>) ois.readObject();
        }
    }
    // Método para guardar la lista de personas en el archivo
    private static void guardarPersonas(List<Persona> personas) throws IOException {
        // Crear el archivo de asistencia si no existe
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            // Escribir la lista de personas en el archivo
            oos.writeObject(personas);
        }
        // Leer el archivo de asistencia
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            // Leer la lista de personas
            List<Persona> lista = (List<Persona>) ois.readObject();
            // Mostrar la lista de personas
            for (Persona p : lista) {
                System.out.println(p);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Metodo para iniciar el udp.servidor
    public static void procesarSolicitud(int puerto) throws Exception {
        // Crear el servidor en el puerto especificado
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor activo en el puerto " + puerto + "...");
        while (true) {
            // Esperar conexión del cliente
            Socket cliente = servidor.accept();
            // Procesar solicitud del cliente
            DataInputStream dis = new DataInputStream(cliente.getInputStream());
            // Leer datos del cliente
            DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
            // Leer nombre y tipo de registro
            String nombre = dis.readUTF();
            String tipo = dis.readUTF();
            // Si el nombre o tipo es "x", salir del bucle
            if (nombre.equals("x") || tipo.equals("x")) break;
            // Mostrar información recibida
            System.out.println("Registro de horario de: " + nombre);
            // Tomar la hora actual
            String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
            // Actualizar o crear la persona
            Persona personaActualizada = actualizarPersona(nombre, tipo, hora);
            // Enviar respuesta al cliente
            dos.writeUTF("Registro actualizado:\n" + personaActualizada);
            cliente.close();
        }
        servidor.close();
    }
}