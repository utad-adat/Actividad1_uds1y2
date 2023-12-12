package com.utad;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String pass = "postgres";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);

            String createTable = "CREATE TABLE IF NOT EXISTS empleados (id SERIAL PRIMARY KEY, nombre VARCHAR(255), apellidos VARCHAR(255), dni VARCHAR(10) UNIQUE, depto VARCHAR(255));";
            Statement statement = conn.createStatement();
            statement.executeUpdate(createTable);
            System.out.println("Tabla creada correctamente.");

            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
