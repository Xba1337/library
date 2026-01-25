package ru.sorokin.course.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import java.security.SecureRandom;

@AutoConfigureMockMvc
@SpringBootTest
public class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    protected final ObjectMapper objectMapper = new ObjectMapper();

    protected final SecureRandom secureRandom = new SecureRandom();

    private static volatile boolean isSharedSetupDone = false;

    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("1234");

    static {
        if (!isSharedSetupDone) {
            postgres.start();
            isSharedSetupDone = true;
        }
    }

    @DynamicPropertySource
    static void datasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("test.postgres.port", postgres::getFirstMappedPort);
    }

    @EventListener
    public void stopContainer(ContextStoppedEvent event){
        postgres.stop();
    }

    public int getRandomInt(){
        return secureRandom.nextInt();
    }

}
