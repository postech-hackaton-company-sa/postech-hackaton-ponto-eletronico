package com.postechhackaton.pontoeletronico.application.subscribers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.domain.gateways.KafkaSenderGateway;
import com.postechhackaton.pontoeletronico.domain.usecase.BuscarPontoUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarEspelhoPontoSubscriberTest {
    @Mock
    private BuscarPontoUseCase buscarPontoUseCase;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private KafkaSenderGateway kafkaSenderGateway;

    @InjectMocks
    private BuscarEspelhoPontoSubscriber subscriber;

    @Test
    public void subscribe_deveBuscarRegistros_quandoReceberMensagem() {
        String json = "{ \"usuario\": \"teste\",  \"email\": \"teste@gmail.com\", \"dataInicio\": \"2024-03-01T00:00:00\", \"dataFim\": \"2024-03-15T00:00:00\" }";

        List<PontoEletronicoEntity> registros = new ArrayList<>();
        when(buscarPontoUseCase.execute("teste", LocalDate.of(2024, 3, 1).atStartOfDay(), LocalDate.of(2024, 3, 15).atStartOfDay())).thenReturn(registros);

        subscriber.subscribe(json);

        verify(kafkaSenderGateway).enviar(eq(BuscarEspelhoPontoSubscriber.TOPIC_BUSCAR_ESPELHO_PONTO_OUTPUT), any());
        verifyNoMoreInteractions(kafkaSenderGateway);
    }

    @Test
    public void subscribe_deveEnviarParaDlq_quandoOcorrerAlgumErro() {
        String json = "mensagem inválida";

        subscriber.subscribe(json);

        verify(kafkaSenderGateway).enviar(BuscarEspelhoPontoSubscriber.TOPIC_BUSCAR_ESPELHO_PONTO_INPUT_DLQ, json);
        verifyNoMoreInteractions(kafkaSenderGateway);
    }
}