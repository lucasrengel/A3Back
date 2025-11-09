package unisul.a3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import unisul.a3.config.DatabaseInitializer;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        DatabaseInitializer.initialize();
        SpringApplication.run(App.class, args);
        
        System.out.println("===========================================");
        System.out.println("  BACKEND REST API INICIADO!");
        System.out.println("  API rodando em: http://localhost:8080");
        System.out.println("===========================================");
    }
}