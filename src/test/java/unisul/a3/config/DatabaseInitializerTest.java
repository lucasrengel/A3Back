package unisul.a3.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DatabaseInitializerTest {

    @Test
    void deveInicializarBanco() {

        assertDoesNotThrow(
                DatabaseInitializer::initialize
        );
    }
}