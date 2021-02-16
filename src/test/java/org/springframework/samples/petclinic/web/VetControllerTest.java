package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @Mock
    Map<String, Object> model;


    @InjectMocks
    VetController vetController;

    MockMvc mockMvc;

    Set vetSet;

    @BeforeEach
    void setUp() {
        vetSet = new HashSet<>(Set.of(new Vet(), new Vet()));
        given(clinicService.findVets()).willReturn(vetSet);

        //Setting up standalone controller mock - SEE BOTTOM COMMENT FOR MORE INFORMATION
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }


    //MockMvc is able to leverage the dispatcher servlet for testing controllers
    @Test
    void testControllerShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
    }

    @DisplayName("Test should return proper view")
    @Test
    void showVetList() {
        //GIVEN - handled by Setup
        //WHEN
        String view = vetController.showVetList(model);
        //THEN
        assertEquals("vets/vetList", view);
        then(model).should().put(anyString(), any(Vets.class));
        then(clinicService).should().findVets();
        then(clinicService).shouldHaveNoMoreInteractions();
    }

    @DisplayName("Test should return a Vets Object to Client")
    @Test
    void showResourcesVetList() {
        //GIVEN - handled by Setup
        //WHEN
        Vets vets = vetController.showResourcesVetList();
        //THEN
        assertEquals(2, vets.getVetList().size());
        then(clinicService).should().findVets();
        then(clinicService).shouldHaveNoMoreInteractions();
    }
}

/*
Both forms are actually integration tests since you are testing the integration of your code with the Spring DispatcherServlet and supporting infrastructure.
The difference lies in the amount of supporting infrastructure that is used behind the scenes.

The details are documented in the Spring reference manual.

Server-Side Tests
Setup Options
Difference With End-to-End Integration Tests
Noteworthy excerpts:

The "webAppContextSetup" loads the actual Spring MVC configuration resulting in a more complete integration test.
Since the TestContext framework caches the loaded Spring configuration,
it helps to keep tests running fast even as more tests get added.
Furthermore, you can inject mock services into controllers through Spring configuration, in order to remain focused on testing the web layer.

...

The "standaloneSetup" on the other hand is a little closer to a unit test.
It tests one controller at a time, the controller can be injected with mock dependencies manually,
 and it doesn’t involve loading Spring configuration. Such tests are more focused in style and make it easier to see which controller is being tested,
 whether any specific Spring MVC configuration is required to work, and so on.
 The "standaloneSetup" is also a very convenient way to write ad-hoc tests to verify some behavior or to debug an issue.
 */