package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.Assert.assertEquals;

@TestPropertySource("classpath:yanny.properties") //tells spring to look on class path for the specific property file
@ActiveProfiles("externalized")
@SpringJUnitConfig(classes = HearingInterpreterPropertiesTest.TestConfig.class)
public class HearingInterpreterPropertiesTest {
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg") //Grabs all beans within the package
    static class TestConfig {
    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("YaNNy", word);
    }
}
