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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;

    Set vetSet;

    @BeforeEach
    void setUp() {
        vetSet = new HashSet<>(Set.of(new Vet(), new Vet()));
        given(clinicService.findVets()).willReturn(vetSet);
    }

    @DisplayName("Test should return proper view")
    @Test
    void showVetList() {
        //GIVEN
        Map<String, Object> model = new HashMap<>();
        //WHEN
        String view = vetController.showVetList(model);
        //THEN
        assertEquals("vets/vetList", view);
        //then(model).should().put(anyString(), vetSet);
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