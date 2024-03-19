package com.postechhackaton.pontoeletronico.application.controller;

import com.postechhackaton.pontoeletronico.infra.database.repositories.PontoEletronicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PontoEletronicoControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PontoEletronicoRepository pontoEletronicoRepository;

    @BeforeEach
    void setUp() {
        // Limpar dados de teste antes de cada execução de teste
        pontoEletronicoRepository.deleteAll();
    }

    @Test
    void registrarPonto_deveRetornarPontoEletronicoDto_quandoReceberUsuario() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/ponto-eletronico")
                .header("usuario", "usuario-teste")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());

    }
}