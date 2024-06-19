package com.musichouse;

import com.musichouse.model.domain.Amplifier;
import com.musichouse.model.service.AmplifierServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class AmplifierTests {

    @Autowired
    private AmplifierServiceImp amplifierServiceImp;

    @Test
    @DisplayName("Update Amplifier attributes")
    void updateAllParameters() {
        Amplifier amp1 = Amplifier.builder()
                .brand("Warm Music")
                .price(1500)
                .watts(800)
                .model("85kj2")
                .speakerInch(15).build();
        amplifierServiceImp.save(amp1);

        Amplifier amp2 = new Amplifier();
        amp2.setWatts(700);
        amp2.setBrand("Meteoro");
        amp2.setPrice(1300);
        amp2.setSpeakerInch(14);
        amplifierServiceImp.update("85kj2", amp2);

        Optional<Amplifier> updatedAmp = amplifierServiceImp.getByModel("85kj2");

        Assertions.assertEquals(updatedAmp.get().getWatts(), 700);
        Assertions.assertEquals(updatedAmp.get().getBrand(), "Meteoro");
        Assertions.assertEquals(updatedAmp.get().getPrice(), 1300);
        Assertions.assertEquals(updatedAmp.get().getSpeakerInch(), 14);
        Assertions.assertEquals(updatedAmp.get().getModel(), "85kj2");
    }

    @Test
    @DisplayName("The Amplifier list results in a same size after update")
    void update(){
        Amplifier amp1 = Amplifier.builder()
                .brand("Warm Music")
                .price(1500)
                .watts(800)
                .model("20kO32")
                .speakerInch(15).build();
        amplifierServiceImp.save(amp1);

        List<Amplifier> amps = amplifierServiceImp.getAll();
        int initialListSize = amps.size();

        Amplifier amp2 = new Amplifier();
        amp2.setWatts(700);
        amp2.setBrand("Meteoro");
        amp2.setPrice(1300);
        amp2.setSpeakerInch(14);
        amplifierServiceImp.update("20kO32", amp2);

        int updatedListSize = amps.size();

        Assertions.assertEquals(initialListSize, updatedListSize);
    }

    @Test
    @DisplayName("A nonexistent model parameter should throw a exception")
    void updateNonexistentModelParameter(){
        Assertions.assertThrowsExactly(EntityNotFoundException.class, () -> {
            amplifierServiceImp.update("10kO32", new Amplifier());
        });
    }

}
