package servidor.servicio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    public void servicio() {
        int puerto = 5000;
        try{
            DatagramSocket socket = new DatagramSocket(puerto);
            System.out.println("Servidor UDP corriendo en el puerto "+ puerto);
            byte[] bufferEntrada = new byte[1024];
            while(true) {
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(paqueteEntrada);
                String mensajeRecibido = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength());
                System.out.println("Mensaje Recibido " + mensajeRecibido);
                String[] numeros = mensajeRecibido.split(",");
                if (numeros.length == 2) {
                    try {
                        int num1 = Integer.parseInt(numeros[0].trim());
                        int num2 = Integer.parseInt(numeros[1].trim());
                        int suma = num1 + num2;
                        String respuesta = "Suma " + suma;
                        byte[] bufferSalida = respuesta.getBytes();
                        DatagramPacket paqueteSalida = new DatagramPacket(
                                bufferSalida,
                                bufferSalida.length,
                                paqueteEntrada.getAddress(),
                                paqueteEntrada.getPort()
                        );
                        socket.send(paqueteSalida);
                        System.out.println("Respuesta enviada "+ respuesta);

                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir los números.");
                    }
                } else {
                    System.out.println("Formato de mensaje incorrecto.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
