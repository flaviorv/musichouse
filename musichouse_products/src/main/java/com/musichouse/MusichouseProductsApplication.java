package com.musichouse;

import com.musichouse.domain.product.Product;
import com.musichouse.domain.product.ProductFactory;
import com.musichouse.domain.rating.Rating;
import com.musichouse.dto.RatingRequestDTO;
import com.musichouse.exceptions.RatingAlreadyExistsException;
import com.musichouse.service.ProductServiceImp;
import com.musichouse.service.RatingServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class MusichouseProductsApplication {
        public static void main(String[] args) {
                SpringApplication.run(MusichouseProductsApplication.class, args);
        }

        @Bean
        @Profile("!test")
        CommandLineRunner init(ProductServiceImp productServiceImp, RatingServiceImp ratingServiceImp) {
                return args -> {
                    List<Map<String, Object>> guitarAttrsList = List.of(
                            Map.of("type", "guitar", "model", "VX100", "brand", "Stravix", "price", 899.99,
                                    "quantity", 5, "strings", 6, "activePickup", false, "image", "/images/guitar1.png"),
                            Map.of("type", "guitar", "model", "NOVA7", "brand", "Auralite", "price", 1199.50,
                                    "quantity", 3, "strings", 7, "activePickup", true, "image", "/images/guitar2.png"),
                            Map.of("type", "guitar", "model", "AX350", "brand", "Harmonia", "price", 749.00,
                                    "quantity", 4, "strings", 6, "activePickup", true, "image", "/images/guitar3.png"),
                            Map.of("type", "guitar", "model", "ORBIT8", "brand", "Lunaris", "price", 1349.75,
                                    "quantity", 2, "strings", 8, "activePickup", true, "image","/images/guitar4.png"),
                            Map.of("type", "guitar", "model", "VINTAGE6", "brand", "RetroSound", "price", 679.99,
                                    "quantity", 6, "strings", 6, "activePickup", false, "image","/images/guitar5.png"),
                            Map.of("type", "guitar", "model", "CRIMSON", "brand", "Solaris", "price", 1599.00,
                                    "quantity", 2, "strings", 7, "activePickup", true, "image","/images/guitar6.png"),
                            Map.of("type", "guitar", "model", "OCEANIC", "brand", "BlueWave", "price", 820.49,
                                    "quantity", 5, "strings", 6, "activePickup", false, "image", "/images/guitar7.png"),
                            Map.of("type", "guitar", "model", "PHOENIX", "brand", "Ignis", "price", 1999.99,
                                    "quantity", 1, "strings", 8, "activePickup", true, "image", "/images/guitar8.png"));

                        List<Map<String, Object>> ampAttrsList = List.of(
                                        Map.of("type", "amplifier", "model", "AMP100", "brand", "SoundMax", "price", 499.99,
                                                "quantity", 10, "watts", 50, "speakerInch", 8, "image", "/images/amplifier1.png"),
                                        Map.of("type", "amplifier", "model", "AMP200", "brand", "TonePro", "price", 699.50,
                                                        "quantity", 6, "watts", 75, "speakerInch", 10, "image", "/images/amplifier2.png"),
                                        Map.of("type", "amplifier", "model", "AMP300", "brand", "RockWave", "price", 899.00,
                                                        "quantity", 4, "watts", 100, "speakerInch", 12, "image","/images/amplifier3.png"),
                                        Map.of("type", "amplifier", "model", "AMP400", "brand", "EchoDrive", "price", 1099.99,
                                                "quantity", 3, "watts", 150, "speakerInch", 15, "image", "/images/amplifier4.png"),
                                        Map.of("type", "amplifier", "model", "AMP-VINTAGE", "brand", "ClassicTone", "price", 649.99,
                                                "quantity", 5, "watts", 40, "speakerInch", 10, "image", "/images/amplifier5.png"),
                                        Map.of("type", "amplifier", "model", "AMP500", "brand", "BassForge", "price", 1299.00,
                                                "quantity", 2, "watts", 200, "speakerInch", 18, "image", "/images/amplifier6.png"),
                                        Map.of("type", "amplifier", "model", "AMP600", "brand", "ThunderPeak", "price", 749.49,
                                                "quantity", 7, "watts", 60, "speakerInch", 12, "image", "/images/amplifier7.png"),
                                        Map.of("type", "amplifier", "model", "AMP700", "brand", "PowerChord", "price", 1599.99,
                                                "quantity", 1, "watts", 300, "speakerInch", 20, "image", "/images/amplifier8.png"));

                        for (Map<String, Object> attrs : guitarAttrsList) {
                            Product guitar = ProductFactory.createProduct(attrs);
                            try {
                                    productServiceImp.save(guitar);
                            } catch (DataIntegrityViolationException e) {
                                    log.error(e.getMessage());
                            }
                        }

                        for (Map<String, Object> attrs : ampAttrsList) {
                            Product guitar = ProductFactory.createProduct(attrs);
                            try {
                                    productServiceImp.save(guitar);
                            } catch (DataIntegrityViolationException e) {
                                    log.error(e.getMessage());
                            }
                        }

                        List<RatingRequestDTO> ratingDTOS =  List.of(
                                new RatingRequestDTO("Cust001","VX100", Rating.FIVE),
                                new RatingRequestDTO("Cust002","VX100", Rating.FOUR),
                                new RatingRequestDTO("Cust003","AMP100", Rating.FIVE),
                                new RatingRequestDTO("Cust004", "AMP100", Rating.FIVE),
                                new RatingRequestDTO("Cust005", "AMP100", Rating.THREE),
                                new RatingRequestDTO("Cust006", "PHOENIX", Rating.FIVE),
                                new RatingRequestDTO("Cust004", "AX350", Rating.TWO),
                                new RatingRequestDTO("Cust004", "CRIMSON", Rating.FOUR),
                                new RatingRequestDTO("Cust007", "AMP-VINTAGE", Rating.THREE),
                                new RatingRequestDTO("Cust008", "ORBIT8", Rating.FOUR),
                                new RatingRequestDTO("Cust009", "ORBIT8", Rating.THREE),
                                new RatingRequestDTO("Cust010", "ORBIT8", Rating.THREE),
                                new RatingRequestDTO("Cust011", "ORBIT8", Rating.ONE),
                                new RatingRequestDTO("Cust012", "ORBIT8", Rating.FIVE)
                        );

                        for (RatingRequestDTO ratingDTO : ratingDTOS) {
                            try {
                                ratingServiceImp.addRating(ratingDTO);
                            } catch(RatingAlreadyExistsException e) {
                                log.error(e.getMessage());
                            }
                        }

                };
        }

}