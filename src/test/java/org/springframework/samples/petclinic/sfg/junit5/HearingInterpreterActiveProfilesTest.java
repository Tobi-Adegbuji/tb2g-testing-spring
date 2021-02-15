package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("yanny")
@SpringJUnitConfig(classes = {HearingInterpreterActiveProfilesTest.TestConfig.class})
public class HearingInterpreterActiveProfilesTest {
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg") //Grabs all beans within the package
    static class TestConfig {
    }

    @Autowired //DI automatically handled by IOC container. In this case Yanny Bean will be injected since the class has @Profile("yanny")
    HearingInterpreter hearingInterpreter;

    @Test
    void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Yanny", word);
    }
}
