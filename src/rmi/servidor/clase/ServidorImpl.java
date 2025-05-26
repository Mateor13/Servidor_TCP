package rmi.servidor.clase;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServidorImpl extends UnicastRemoteObject implements Servidor {

    private static ArrayList<Persona> listPersonas(){
        ArrayList<Persona> lista = new ArrayList<Persona>();
        lista.add(new Persona(1, "Mateo", "mateo@epn.edu.ec", "Estudiante", 175));
        lista.add(new Persona(2, "Lucía", "lucia@epn.edu.ec", "Profesora", 160));
        lista.add(new Persona(3, "Carlos", "carlos@epn.edu.ec", "Estudiante", 180));
        lista.add(new Persona(4, "Diana", "diana@epn.edu.ec", "Administrativa", 165));
        lista.add(new Persona(5, "Andrés", "andres@epn.edu.ec", "Estudiante", 172));
        lista.add(new Persona(6, "María", "maria@epn.edu.ec", "Profesora", 158));
        lista.add(new Persona(7, "José", "jose@epn.edu.ec", "Estudiante", 178));
        lista.add(new Persona(8, "Fernanda", "fernanda@epn.edu.ec", "Investigadora", 170));
        return lista;
    }

    private static String getPersona(int id) {
        return "Nombre: "+listPersonas().get(id-1).getNombre()+"\n,"+
                "Correo: "+listPersonas().get(id-1).getCorreo()+"\n,"+
                "Cargo: "+listPersonas().get(id-1).getCargo()+"\n,"+
                "Sueldo: "+listPersonas().get(id-1).getSueldo()+"\n,";
    }
    public ServidorImpl() throws RemoteException{ super();}

    @Override
    public String consultar(int id) throws Exception{
        if (id<listPersonas().size()+1){
            return getPersona((id));
        }else{
            return "No existen los datos del empleado";
        }
    }

}
