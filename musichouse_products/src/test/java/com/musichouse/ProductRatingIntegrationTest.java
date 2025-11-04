package com.musichouse;

import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musichouse.adapter.client.SaleClient;
import com.musichouse.domain.product.Amplifier;
import com.musichouse.domain.product.ElectricGuitar;
import com.musichouse.domain.product.Product;
import com.musichouse.domain.product.ProductType;
import com.musichouse.domain.rating.Rating;
import com.musichouse.dto.DeliveryResponseDTO;
import com.musichouse.dto.RatingDTO;
import com.musichouse.repository.ProductRepository;
import com.musichouse.repository.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = {
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false",
        "spring.rabbitmq.enabled=false"
})
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class ProductRatingIntegrationTest {

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("db_products")
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
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
        registry.add("spring.jpa.show-sql", () -> "false");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.sql.init.mode", () -> "always");
        registry.add("spring.jpa.defer-datasource-initialization", () -> "false");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @MockBean
    private SaleClient saleClient;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        ratingRepository.deleteAll();

        when(saleClient.deliveryStatus(anyString(), anyString())).thenAnswer(
                invocation -> new DeliveryResponseDTO(
                        invocation.getArgument(0), invocation.getArgument(1), true
                )
        );

        when(saleClient.deliveryStatus("99", "ORBIT8")).thenAnswer(
                invocation -> new DeliveryResponseDTO(
                        "99", "OCEANIC", true
                )
        );

        when(saleClient.deliveryStatus("99", "WAVE50")).thenAnswer(
                invocation -> new DeliveryResponseDTO(
                        "99", "WAVE50", false
                )
        );

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
        productRepository.saveAll(products);
    }

    @Test
    public void shouldCreateRating() throws Exception {
        RatingDTO dto = new RatingDTO("07", "AMP100", Rating.FOUR);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnAverageRating() throws Exception {
        RatingDTO dto = new RatingDTO("07", "AMP100", Rating.FOUR);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());

        RatingDTO dto2 = new RatingDTO("08", "AMP100", Rating.ONE);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto2)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/product/AMP100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productRatings", hasSize(2)))
                .andExpect(jsonPath("$.productRatingMetrics.averageRating", equalTo(2.5)));
    }

    @Test
    public void shouldUpdateRating() throws Exception {
        RatingDTO dto = new RatingDTO("07", "AMP100", Rating.FOUR);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());

        RatingDTO dto2 = new RatingDTO("07", "AMP100", Rating.ONE);
        mockMvc.perform(put("/rating/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/product/AMP100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productRatings", hasSize(1)))
                .andExpect(jsonPath("$.productRatingMetrics.averageRating", equalTo(1.0)));
    }

    @Test
    public void shouldNotCreateTwoRatingsWithSameId() throws Exception {
        RatingDTO dto = new RatingDTO("07", "AMP100", Rating.FOUR);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());

        RatingDTO dto2 = new RatingDTO("07", "AMP100", Rating.ONE);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto2)))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnStatus409WhenADifferentIdIsReceived() throws Exception {
        RatingDTO dto = new RatingDTO("99", "ORBIT8", Rating.ONE);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnStatus404WhenTheProductNotExists() throws Exception {
        RatingDTO dto = new RatingDTO("99", "Nonexistent", Rating.ONE);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnStatus403WhenTheProductIsNotDelivered() throws Exception {
        RatingDTO dto = new RatingDTO("99", "WAVE50", Rating.ONE);
        mockMvc.perform(post("/rating/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn404IfTryUpdateANonexistentRating() throws Exception {
        RatingDTO dto = new RatingDTO("10", "PHOENIX", Rating.ONE);
        mockMvc.perform(put("/rating/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

}
