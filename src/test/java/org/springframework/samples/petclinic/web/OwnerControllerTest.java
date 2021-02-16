package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/*
SpringJUnitWebConfig Annotation includes:
@ExtendWith({SpringExtension.class})
@ContextConfiguration
@WebAppConfiguration
 */

@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml", "classpath:/spring/mvc-core-config.xml"})
class OwnerControllerTest {

    @Autowired //asking spring context to inject a bean from the IOC container
    OwnerController ownerController;

    @Autowired //Bean was created in test config xml with a mock
    ClinicService clinicService;


    MockMvc mockMvc;

    List<Owner> owners;

    private final static String OWNERS_URL = "/owners";
    private final static String FIND_OWNERS_URL = "owners/findOwners";
    private final static String REDIRECT_OWNER_URL = "redirect:/owners/";
    private final static String OWNERS_LIST_URL = "owners/ownersList";



    @BeforeEach
    void setUp() {
        //Sets up Mock Mvc Environment for controller
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
        Owner owner = new Owner();
        owner.setLastName("sandy");
        owner.setId(1);
        Owner owner2 = new Owner();
        owner2.setLastName("sandy");

        owners = new ArrayList<>(List.of(owner, owner2));
    }


    @Test
    void findByNameNotFound() throws Exception {
        mockMvc.perform(get(OWNERS_URL)
        .param("lastName", (String) null)) //configuring request further bt
        .andExpect(status().isOk())
        .andExpect(view().name(FIND_OWNERS_URL));
    }

    @Test
    void processFindFormOneOwnerFound() throws Exception{
        //GIVEN
        given(clinicService.findOwnerByLastName("sandy"))
                .willReturn(new ArrayList<>(List.of(owners.get(0))));

        mockMvc.perform(get(OWNERS_URL)
                .param("lastName", "sandy"))
                .andExpect(status().isFound())
                .andExpect(view()
                        .name(REDIRECT_OWNER_URL + owners.get(0).getId()));
    }

    @Test
    void processFindFormOwnersFound() throws Exception{
        given(clinicService.findOwnerByLastName("sandy"))
                .willReturn(owners);

       mockMvc.perform(get(OWNERS_URL)
       .param("lastName", "sandy"))
               .andExpect(status().isOk())
               .andExpect(view().name(OWNERS_LIST_URL));

    }

    @DisplayName("Test should return proper view name given Model")
    @Test
    void initCreationFormTest() throws Exception {

        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name(ownerController.VIEWS_OWNER_CREATE_OR_UPDATE_FORM));

//        //GIVEN
//        //WHEN
//        String viewResult = ownerController.initCreationForm(model);
//        //THEN
//        assertEquals("owners/createOrUpdateOwnerForm", viewResult, "viewResult does not equal expected for initCreationForm Test");
//        then(clinicService).shouldHaveZeroInteractions();







    }
}