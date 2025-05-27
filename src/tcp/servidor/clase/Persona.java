package tcp.servidor.clase;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

// El implements Serializable permite que la clase Persona pueda ser serializada,
// lo cual es necesario para guardar objetos de esta clase en un archivo.
public class Persona implements Serializable {
    // Clase Persona que representa a una persona con un ID único y nombre
    @Serial
    private static final long serialVersionUID = 1L;
    @Setter @Getter private String id;
    @Setter @Getter private String nombre;
    private String entrada = "";
    private String almuerzo = "";
    private String regresoAlmuerzo = "";
    private String salida = "";

    // Constructor que inicializa el ID y el nombre
    public Persona(String nombre) {
        // Genera un ID único en 4 caracteres
        this.id = UUID.randomUUID().toString().substring(0, 4);
        // Inicializa el nombre
        this.nombre = nombre;
    }

    // Método para obtener la hora de un tipo específico
    public void setHora(String tipo, String hora) {
        // Asigna la hora a la variable correspondiente según el tipo
        switch (tipo.toLowerCase()) {
            case "entrada" -> this.entrada = hora;
            case "almuerzo" -> this.almuerzo = hora;
            case "regreso" -> this.regresoAlmuerzo = hora;
            case "salida" -> this.salida = hora;
        }
    }

    // Método toString para representar la persona como una cadena
    @Override
    public String toString() {
        // %s es un marcador de posición para una cadena
        // Un marcador de posición es un espacio reservado en una cadena que se reemplaza por un valor
        return String.format("ID: %s, Nombre: %s, Entrada: %s, Almuerzo: %s, Regreso del almuerzo: %s, Salida: %s",
                id, nombre, entrada, almuerzo, regresoAlmuerzo, salida);
    }
}
