package com.musichouse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.domain.ElectricGuitar;
import com.musichouse.model.domain.Product;
import com.musichouse.model.domain.ProductType;
import com.musichouse.model.repository.ProductRepository;
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
class IntegrationTest {

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
                                new ElectricGuitar("VX100", ProductType.GUITAR, "Stravix", 899.99f, 5, null, 6, false),
                                new ElectricGuitar("NOVA7", ProductType.GUITAR, "Auralite", 1199.50f, 3, null, 7, true),
                                new ElectricGuitar("AX350", ProductType.GUITAR, "Harmonia", 749.00f, 4, null, 6, true),
                                new ElectricGuitar("ORBIT8", ProductType.GUITAR, "Lunaris", 1349.75f, 2, null, 8, true),
                                new ElectricGuitar("VINTAGE6", ProductType.GUITAR, "RetroSound", 679.99f, 6, null, 6,
                                                false),
                                new ElectricGuitar("CRIMSON", ProductType.GUITAR, "Solaris", 1599.00f, 2, null, 7,
                                                false),
                                new ElectricGuitar("OCEANIC", ProductType.GUITAR, "BlueWave", 820.49f, 5, null, 6,
                                                false),
                                new ElectricGuitar("PHOENIX", ProductType.GUITAR, "Ignis", 1999.99f, 1, null, 8, true),
                                new ElectricGuitar("SPARK6", ProductType.GUITAR, "Stravix", 799.00f, 4, null, 6, true),
                                new ElectricGuitar("AURORA", ProductType.GUITAR, "Auralite", 1450.99f, 2, null, 7,
                                                true),
                                new ElectricGuitar("ZENITH", ProductType.GUITAR, "Lunaris", 1099.00f, 3, null, 6,
                                                false),
                                new ElectricGuitar("BLAZE7", ProductType.GUITAR, "Ignis", 1750.00f, 2, null, 7, true),
                                new Amplifier("AMP100", ProductType.AMPLIFIER, "SoundMax", 499.99f, 10, null, 50, 8),
                                new Amplifier("AMP200", ProductType.AMPLIFIER, "TonePro", 699.50f, 6, null, 60, 10),
                                new Amplifier("AMP300", ProductType.AMPLIFIER, "RockWave", 899.00f, 4, null, 100, 12),
                                new Amplifier("AMP400", ProductType.AMPLIFIER, "EchoDrive", 1099.99f, 3, null, 150, 15),
                                new Amplifier("AMP-VINTAGE", ProductType.AMPLIFIER, "ClassicTone", 649.99f, 5, null, 40,
                                                10),
                                new Amplifier("AMP500", ProductType.AMPLIFIER, "BassForge", 1299.00f, 2, null, 200, 18),
                                new Amplifier("AMP600", ProductType.AMPLIFIER, "ThunderPeak", 749.49f, 7, null, 60, 12),
                                new Amplifier("AMP700", ProductType.AMPLIFIER, "PowerChord", 1599.99f, 1, null, 300,
                                                20),
                                new Amplifier("MAX200", ProductType.AMPLIFIER, "SoundMax", 599.00f, 8, null, 60, 10),
                                new Amplifier("PRO400", ProductType.AMPLIFIER, "TonePro", 899.99f, 5, null, 100, 12),
                                new Amplifier("WAVE50", ProductType.AMPLIFIER, "RockWave", 450.75f, 12, null, 50, 8),
                                new Amplifier("DRIVE300", ProductType.AMPLIFIER, "EchoDrive", 950.00f, 4, null, 120, 15)

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
        void emptyStringShouldReturnBadRequest() throws Exception {
                String input = "{\"q\": \"\"}";
                String input2 = "{\"q\": \"...'!~\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(input))
                                .andExpect(status().isBadRequest());

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(input2))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void noMatchStringShouldReturnBadRequest() throws Exception {
                String input = "{\"q\": \"Non-existent product\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(input))
                                .andExpect(status().isNotFound());
        }

        @Test
        void shouldFilterByType() throws Exception {
                String guitarText = "{\"q\": \"Show me Guitars\"}";
                String guitarText2 = "{\"q\": \"I want to see Guitar... and...\"}";

                String ampText = "{\"q\": \"Show me Amps\"}";
                String ampText2 = "{\"q\": \"I want a amp that...\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(guitarText))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(12)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(guitarText2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(12)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ampText))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(12)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(ampText2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(12)));
        }

        @Test
        void souldFilterByModel() throws Exception {
                String text = "{\"q\": \"Guitar AX350\"}";
                String text2 = "{\"q\" :\"ORBIT8 Guitar\"}";
                String text3 = "{\"q\" :\"I want a VINTAGE6.\"}";
                String text4 = "{\"q\" :\"I want a RetroSound VINTAGE6.\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text3))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text4))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));
        }

        @Test
        void souldFilterByBrand() throws Exception {

                String text = "{\"q\": \"Guitar Auralite\"}";
                String text2 = "{\"q\" :\"Lunaris\"}";
                String text3 = "{\"q\" :\"The BlueWave.\"}";
                String text4 = "{\"q\" :\"The Solaris CRIMSON.\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text3))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text4))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));
        }

        @Test
        void shouldFilterByStrings() throws Exception {
                String text = "{\"q\": \"Guitar with 6 strings\"}";
                String text2 = "{\"q\" :\"7 strings Guitar\"}";
                String text3 = "{\"q\" :\"I want a Guitar with the number of strings is 8.\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(6)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(4)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text3))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        void shouldFilterByActivePickup() throws Exception {
                String text = "{\"q\": \"Guitar with active pickups.\"}";
                String text2 = "{\"q\" :\"7 strings Guitar active\"}";
                String text3 = "{\"q\" :\"passive pickups, 8 strings.\"}";
                String text4 = "{\"q\" :\"7 strings and passive pickups Guitar\"}";
                String text5 = "{\"q\" :\"passive pickups Amplifier\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(7)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(3)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text3))
                                .andExpect(status().isNotFound());

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text4))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text5))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(12)));
        }

        @Test
        void shouldFilterBySpeakerInch() throws Exception {

                String text = "{\"q\" :\"Speaker 8 inches \"}";
                String text2 = "{\"q\" :\"Speaker 8 Guitar\"}";
                String text3 = "{\"q\" :\"10in speaker amp\"}";
                String text4 = "{\"q\": \"Amplifier 12\\\".\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(12)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text3))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(3)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text4))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(3)));
        }

        @Test
        void shouldFilterByWatts() throws Exception {

                String text = "{\"q\" :\"Amp 100w \"}";
                String text2 = "{\"q\" :\"Amplifiers 50 watts\"}";
                String text3 = "{\"q\" :\"150watts amp\"}";

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text2))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(2)));

                mockMvc.perform(post("/product/search")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(text3))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$", hasSize(1)));

        }

}