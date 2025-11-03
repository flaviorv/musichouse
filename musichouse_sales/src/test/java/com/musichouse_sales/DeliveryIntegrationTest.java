package com.musichouse_sales;

import com.musichouse_sales.adapter.client.ProductClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@SpringBootTest(properties = {
        "spring.cloud.discovery.enabled=false",
        "eureka.client.enabled=false",
        "spring.rabbitmq.enabled=false"
})
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
public class DeliveryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductClient productClient;

    @Test
    void deliveryTest() throws Exception {
        mockMvc.perform(get("/deliveries/check?customerId=5&model=AMP100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("5"))
                .andExpect(jsonPath("$.model").value("AMP100"))
                .andExpect(jsonPath("$.delivered", equalTo(true)));
    }

}
