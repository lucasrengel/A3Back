package unisul.a3.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    private static final String URL =
            System.getProperty(
                    "db.url",
                    "jdbc:mysql://localhost:3306/estoque_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8"
            );
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() {
        try {
            String url = System.getProperty(
                    "db.url",
                    "jdbc:mysql://localhost:3306/estoque_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8"
            );

            return DriverManager.getConnection(url, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }
}
