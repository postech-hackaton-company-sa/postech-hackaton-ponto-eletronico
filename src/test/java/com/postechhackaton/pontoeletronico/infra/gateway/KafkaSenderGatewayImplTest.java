package com.postechhackaton.pontoeletronico.infra.gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaSenderGatewayImplTest {
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private KafkaSenderGatewayImpl kafkaSenderGateway;

    @Test
    void send_deveEnviarMensagem_quandoForChamado() {
        kafkaSenderGateway.enviar("topico", "mensagem");
        verify(kafkaTemplate, times(1)).send("topico", "mensagem");

    }
}