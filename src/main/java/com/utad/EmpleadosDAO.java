package com.utad;

import com.utad.model.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadosDAO {
    private final Connection connection;
    private List<String> dnis;

    public EmpleadosDAO(Connection connection) {
        this.connection = connection;
        this.dnis = new ArrayList<>();
        try {
            readDNIs();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void readDNIs() throws SQLException {
        String selectQuery = "SELECT dni FROM empleados;";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String identification = resultSet.getString("dni");
                dnis.add(identification);
            }
        }
    }

    public boolean dniContains(String dni) {
        return dnis.contains(dni);
    }

    public int createEmpleado(Empleado empleado) throws SQLException {
        String insertQuery = "INSERT INTO empleados (nombre, apellidos, dni, depto) VALUES (?,?,?,?) RETURNING id;";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, empleado.getNombre());
            statement.setString(2, empleado.getApellido());
            statement.setString(3, empleado.getDni());
            statement.setString(4, empleado.getDepto());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Empleado añadido: " + empleado.getNombre() + " - " + empleado.getDni());
                return resultSet.getInt(1);
            }
        }
        return -1;
    }

    public void addIntoDatabase(List<Empleado> empleados) {
        int counter = 0;
        for (Empleado empleado : empleados) {
            if (!dniContains(empleado.getDni())) {
                try {
                   int result = createEmpleado(empleado);
                   if(result != -1) {
                       counter++;
                   }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        System.out.println("Total empleados añadidos: " + counter);
    }
}
