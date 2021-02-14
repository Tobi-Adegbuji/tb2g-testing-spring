package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/*
@RunWith(SpringJUnit4ClassRunner.class): Indicates that the class should use Spring's JUnit facilities.

@ContextConfiguration(locations = {...}): Indicates which XML files contain the ApplicationContext.
 */

//Its better to mock instead of bringing up the spring context which can be a pretty expensive operation

@RunWith(SpringRunner.class) //Adds support for spring annotation
@ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
public class HearingInterpreterTest {

    @Autowired
    HearingInterpreter hearingInterpreter;


    @Test
    public void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}