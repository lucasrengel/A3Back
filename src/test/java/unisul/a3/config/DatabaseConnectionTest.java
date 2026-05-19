package unisul.a3.config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void deveConectarAoBanco() {

        Connection conn =
                DatabaseConnection.getConnection();

        assertNotNull(conn);
    }
}