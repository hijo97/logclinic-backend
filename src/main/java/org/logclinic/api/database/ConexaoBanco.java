package org.logclinic.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://mysql-8151ca4-logclinic757-7018.l.aivencloud.com:24889/defaultdb?sslMode=REQUIRED";
    private static final String USUARIO = "avnadmin"; 
    private static final String SENHA = "AVNS_DwbY9hFqZRMUytT8q63"; 

   public static Connection conectar() {
        try {
            // Garante que o driver do MySQL está carregado
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao conectar ao banco da nuvem: " + e.getMessage());
            return null;
        }
    }
}