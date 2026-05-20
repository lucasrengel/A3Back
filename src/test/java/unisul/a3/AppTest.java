package unisul.a3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {

    @Test
    void deveExistirClasseApp() {
        assertNotNull(new App());
    }
}