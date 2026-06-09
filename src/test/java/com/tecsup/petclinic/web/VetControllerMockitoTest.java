package com.tecsup.petclinic.web;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.service.VetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VetController.class)
class VetControllerMockitoTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VetService vetService;

    // =========================
    // Integrante A — Listado y consulta
    // =========================

    @Test
    void testFindAllVets() throws Exception {
        Vet vet = new Vet(1L, "James", "Carter", "james.carter@petclinic.com", "6085551234", true);
        when(vetService.findAllVets()).thenReturn(List.of(vet));

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].lastName").value("Carter"));
    }

    @Test
    void testFindVetOK() throws Exception {
        Vet vet = new Vet(1L, "James", "Carter", "james.carter@petclinic.com", "6085551234", true);
        when(vetService.findVetById(1L)).thenReturn(vet);

        mockMvc.perform(get("/vets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Carter"));
    }

    @Test
    void testFindVetKO() throws Exception {
        when(vetService.findVetById(666L)).thenThrow(new RuntimeException("Vet not found with id: 666"));

        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }

    // =========================
    // Integrante B — Creación y soft delete
    // =========================

    @Test
    void testCreateVet() throws Exception {
        Vet created = new Vet(7L, "Ana", "Lopez", "ana.lopez@test.com", "55510001", true);
        when(vetService.createVet(any(Vet.class))).thenReturn(created);

        String body = """
                {"firstName":"Ana","lastName":"Lopez","email":"ana.lopez@test.com","phone":"55510001","active":true}
                """;

        mockMvc.perform(post("/vets")
                        .contentType("application/json")
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.firstName").value("Ana"));
    }

    @Test
    void testDeactivateVet() throws Exception {
        Vet deactivated = new Vet(1L, "James", "Carter", "james.carter@petclinic.com", "6085551234", false);
        when(vetService.deactivateVet(1L)).thenReturn(deactivated);

        mockMvc.perform(put("/vets/1/deactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void testReactivateVet() throws Exception {
        Vet reactivated = new Vet(6L, "Sharon", "Jenkins", "sharon.jenkins@petclinic.com", "6085556789", true);
        when(vetService.reactivateVet(6L)).thenReturn(reactivated);

        mockMvc.perform(put("/vets/6/reactivate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.active").value(true));
    }

    // =========================
    // Integrante C — Filtros y borrado
    // =========================

    @Test
    void testFindActiveVets() throws Exception {
        Vet vet = new Vet(1L, "James", "Carter", "james.carter@petclinic.com", "6085551234", true);
        when(vetService.findActiveVets()).thenReturn(List.of(vet));

        mockMvc.perform(get("/vets?active=true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].active").value(true));
    }

    @Test
    void testFindInactiveVets() throws Exception {
        Vet vet = new Vet(6L, "Sharon", "Jenkins", "sharon.jenkins@petclinic.com", "6085556789", false);
        when(vetService.findInactiveVets()).thenReturn(List.of(vet));

        mockMvc.perform(get("/vets?active=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].active").value(false));
    }

    @Test
    void testDeleteVetKO() throws Exception {
        doThrow(new RuntimeException("Vet not found")).when(vetService).deleteVet(1000L);

        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());
    }
}
