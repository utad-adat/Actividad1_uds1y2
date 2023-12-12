package com.utad;

import com.utad.model.Empleado;
import com.utad.model.Empleados;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class Main {

    // Ruta al archivo XML que se va a leer
    static String PATH_FILE = "./src/main/java/com/utad/empleados.xml";

    public static void main(String[] args) {
        EmpleadosDAO empleadosDAO = new EmpleadosDAO(DBConnection.getConnection());

        // Lee los datos del archivo XML y almacénalos en una lista de objetos Empleado
        List<Empleado> empleadosList = readXML();

        // Agrega los empleados leídos de XML a la base de datos
        empleadosDAO.addIntoDatabase(empleadosList);
    }

    // Método para leer y parsear el archivo XML
    static List<Empleado> readXML() {
        try {
            File file = new File(PATH_FILE);
            JAXBContext jaxbContext = JAXBContext.newInstance(Empleados.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            Empleados empleados = (Empleados) jaxbUnmarshaller.unmarshal(file);

            return empleados.getEmpleados();
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}