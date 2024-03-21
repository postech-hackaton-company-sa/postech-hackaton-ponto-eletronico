package com.postechhackaton.pontoeletronico.application.subscribers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postechhackaton.pontoeletronico.application.dto.QueryEspelhoPontoDto;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.domain.gateways.KafkaSenderGateway;
import com.postechhackaton.pontoeletronico.domain.usecase.BuscarPontoUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BuscarEspelhoPontoSubscriber {
    public static final String TOPIC_BUSCAR_ESPELHO_PONTO_INPUT = "postech-hackaton-buscar-espelho-ponto-in";
    public static final String TOPIC_BUSCAR_ESPELHO_PONTO_OUTPUT = "postech-hackaton-buscar-espelho-ponto-out";
    public static final String TOPIC_BUSCAR_ESPELHO_PONTO_INPUT_DLQ = "postech-hackaton-buscar-espelho-ponto-in-dlq";

    private final BuscarPontoUseCase buscarPontoUseCase;
    private final ObjectMapper objectMapper;
    private final KafkaSenderGateway kafkaSenderGateway;

    public BuscarEspelhoPontoSubscriber(BuscarPontoUseCase buscarPontoUseCase, ObjectMapper objectMapper,
                                        KafkaSenderGateway kafkaSenderGateway) {
        this.buscarPontoUseCase = buscarPontoUseCase;
        this.objectMapper = objectMapper;
        this.kafkaSenderGateway = kafkaSenderGateway;
    }

    @KafkaListener(topics = TOPIC_BUSCAR_ESPELHO_PONTO_INPUT, groupId = "hackaton-group-ponto-eletronico")
    public void subscribe(String json) {
         try {
             log.info("Received Message: " + json);
             QueryEspelhoPontoDto request = objectMapper.readValue(json, QueryEspelhoPontoDto.class);
             List<PontoEletronicoEntity> registros = buscarPontoUseCase.execute(request.getUsuario(), request.getDataInicio(), request.getDataFim());
             request.setResposta(registros);
             String response = objectMapper.writeValueAsString(request);
             kafkaSenderGateway.enviar(TOPIC_BUSCAR_ESPELHO_PONTO_OUTPUT, response);
         } catch (Exception e) {
            log.error("Erro ao processar a mensagem JSON: " + e.getMessage());
            kafkaSenderGateway.enviar(TOPIC_BUSCAR_ESPELHO_PONTO_INPUT_DLQ, json);
        }
    }
}
