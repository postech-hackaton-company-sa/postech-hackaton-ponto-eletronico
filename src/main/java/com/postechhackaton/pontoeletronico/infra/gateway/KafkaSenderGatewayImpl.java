package com.postechhackaton.pontoeletronico.infra.gateway;

import com.postechhackaton.pontoeletronico.domain.gateways.KafkaSenderGateway;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSenderGatewayImpl implements KafkaSenderGateway {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSenderGatewayImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviar(String topico, String mensagem) {
        kafkaTemplate.send(topico, mensagem);
    }
}
