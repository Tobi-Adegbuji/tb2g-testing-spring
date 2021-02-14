package org.springframework.samples.petclinic.sfg.junit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/*
@RunWith(SpringJUnit4ClassRunner.class): Indicates that the class should use Spring's JUnit facilities.

@ContextConfiguration(locations = {...}): Indicates which XML or java Config files contain the ApplicationContext.
 */

//Its better to mock instead of bringing up the spring context which can be a pretty expensive operation

@RunWith(SpringRunner.class) //Adds support for spring annotation
@ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
public class HearingInterpreterLaurelTest {

    @Autowired
    HearingInterpreter hearingInterpreter;


    @Test
    public void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }



}