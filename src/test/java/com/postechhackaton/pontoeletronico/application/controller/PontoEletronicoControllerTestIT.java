package com.postechhackaton.pontoeletronico.application.controller;

import com.postechhackaton.pontoeletronico.business.enums.TipoRegistroPontoEletronico;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import com.postechhackaton.pontoeletronico.infra.database.repositories.PontoEletronicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PontoEletronicoControllerTestIT {

    @Autowired
    MongoTemplate mongoTemplate;

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

    @Test
    void registrarPonto_quandoRegistradoDuasVezesComUmPeriodoDeDuasHoras_deveRegistrarSaida() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/ponto-eletronico")
                        .header("usuario", "usuario-teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipo").value("ENTRADA"));

        var registro = pontoEletronicoRepository.findAll().get(0);
        registro.setData(registro.getData().minusHours(4));
        pontoEletronicoRepository.save(registro);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/ponto-eletronico")
                        .header("usuario", "usuario-teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipo").value("SAIDA"));
    }

    @Test
    void registrarPonto_quandoRegistradoDuasVezes_deveDarErro() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/ponto-eletronico")
                        .header("usuario", "usuario-teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.tipo").value("ENTRADA"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/ponto-eletronico")
                        .header("usuario", "usuario-teste")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errorType").value("VALIDATION_FAILURE"))
                .andExpect(jsonPath("$.errorMessage").value("Não é permitido registrar ponto com menos de 5 minutos do último registro"));
    }

    @Test
    void calcularPonto_deveRetornarPontoCalculadoDto_quandoReceberUsuario() throws Exception {
        pontoEletronicoRepository.save(stubPontoEletronico(LocalDateTime.now().minusHours(8), TipoRegistroPontoEletronico.ENTRADA));
        pontoEletronicoRepository.save(stubPontoEletronico(LocalDateTime.now().minusHours(4), TipoRegistroPontoEletronico.SAIDA));
        pontoEletronicoRepository.save(stubPontoEletronico(LocalDateTime.now().minusHours(3), TipoRegistroPontoEletronico.ENTRADA));
        pontoEletronicoRepository.save(stubPontoEletronico(LocalDateTime.now(), TipoRegistroPontoEletronico.SAIDA));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/ponto-eletronico")
                .header("usuario", "usuario-teste")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.horasTrabalhadas").doesNotExist());
    }

    private static PontoEletronico stubPontoEletronico(LocalDateTime data, TipoRegistroPontoEletronico tipo) {
        return PontoEletronico.builder()
                .id(UUID.randomUUID())
                .usuario("usuario-teste")
                .data(data)
                .tipo(tipo)
                .build();
    }
}