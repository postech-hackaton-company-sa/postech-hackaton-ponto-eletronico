package com.postechhackaton.pontoeletronico.domain.gateways;

public interface KafkaSenderGateway {
    void enviar(String pagamentoJson, String topic);
}
