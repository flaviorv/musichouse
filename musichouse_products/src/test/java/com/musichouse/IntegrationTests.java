package com.musichouse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Product;
import com.musichouse.model.repository.ProductRepository;
import com.musichouse.model.repository.specification.ProductSpecification;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false",
        "spring.rabbitmq.enabled=false"
})
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class IntegrationTests {

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("test_db")
            .withUsername("test")
            .withPassword("test")
            .withReuse(true)
            .withUrlParam("createDatabaseIfNotExist", "true")
            .withUrlParam("useSSL", "false");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.sql.init.mode", () -> "never");
        registry.add("spring.jpa.defer-datasource-initialization", () -> "false");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {

        productRepository.deleteAll();

        List<Product> products = List.of(
                new ElectricGuitar("VX100", "guitar", "Stravix", 899.99f, 5, null, 6, false),
                new ElectricGuitar("NOVA7", "guitar", "Auralite", 1199.50f, 3, null, 7, true),
                new ElectricGuitar("AX350", "guitar", "Harmonia", 749.00f, 4, null, 6, true),
                new ElectricGuitar("ORBIT8", "guitar", "Lunaris", 1349.75f, 2, null, 8, true),
                new ElectricGuitar("VINTAGE6", "guitar", "RetroSound", 679.99f, 6, null, 6, false),
                new ElectricGuitar("CRIMSON", "guitar", "Solaris", 1599.00f, 2, null, 7, true),
                new ElectricGuitar("OCEANIC", "guitar", "BlueWave", 820.49f, 5, null, 6, false),
                new ElectricGuitar("PHOENIX", "guitar", "Ignis", 1999.99f, 1, null, 8, true),
                new Amplifier("AMP100", "amplifier", "SoundMax", 499.99f, 10, null, 50, 8),
                new Amplifier("AMP200", "amplifier", "TonePro", 699.50f, 6, null, 60, 10),
                new Amplifier("AMP300", "amplifier", "RockWave", 899.00f, 4, null, 100, 12),
                new Amplifier("AMP400", "amplifier", "EchoDrive", 1099.99f, 3, null, 150, 15),
                new Amplifier("AMP-VINTAGE", "amplifier", "ClassicTone", 649.99f, 5, null, 40, 10),
                new Amplifier("AMP500", "amplifier", "BassForge", 1299.00f, 2, null, 200, 18),
                new Amplifier("AMP600", "amplifier", "ThunderPeak", 749.49f, 7, null, 60, 12),
                new Amplifier("AMP700", "amplifier", "PowerChord", 1599.99f, 1, null, 300, 20)

        );

        for (Product product : products) {
            try {
                productRepository.save(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void shouldFilterByStrings() throws Exception {

        ProductSpecification spec = new ProductSpecification();
        spec.setStrings(6);

        mockMvc.perform(post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(spec)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void shouldFilterByType() throws Exception {

        ProductSpecification guitarSpec = new ProductSpecification();
        ProductSpecification ampSpec = new ProductSpecification();
        guitarSpec.setType("guitar");
        ampSpec.setType("amplifier");

        mockMvc.perform(post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(guitarSpec)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));

        mockMvc.perform(post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ampSpec)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    void shouldFilterByStringsAndActivePickup() throws Exception {

        ProductSpecification spec = new ProductSpecification();
        spec.setStrings(6);
        spec.setActivePickup(false);

        mockMvc.perform(post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(spec)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldFilterByWattsAndSpeakerInch() throws Exception {

        ProductSpecification spec = new ProductSpecification();
        spec.setWatts(60);
        spec.setSpeakerInch(10);

        mockMvc.perform(post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(spec)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldFilterByWatts() throws Exception {

        ProductSpecification spec = new ProductSpecification();
        spec.setWatts(60);

        mockMvc.perform(post("/product/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(spec)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}