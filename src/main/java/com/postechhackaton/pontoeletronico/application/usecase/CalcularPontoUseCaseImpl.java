package com.postechhackaton.pontoeletronico.application.usecase;

import com.postechhackaton.pontoeletronico.application.dto.PontoCalculadoDto;
import com.postechhackaton.pontoeletronico.application.mappers.PontoEletronicoMapper;
import com.postechhackaton.pontoeletronico.domain.gateways.PontoEletronicoDatabaseGateway;
import com.postechhackaton.pontoeletronico.domain.usecase.CalcularPontoUseCase;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
public class CalcularPontoUseCaseImpl implements CalcularPontoUseCase {

    private final PontoEletronicoDatabaseGateway pontoEletronicoDatabaseGateway;
    private final PontoEletronicoMapper pontoEletronicoMapper;

    public CalcularPontoUseCaseImpl(PontoEletronicoDatabaseGateway pontoEletronicoDatabaseGateway, PontoEletronicoMapper pontoEletronicoMapper) {
        this.pontoEletronicoDatabaseGateway = pontoEletronicoDatabaseGateway;
        this.pontoEletronicoMapper = pontoEletronicoMapper;
    }

    @Override
    public PontoCalculadoDto execute(String usuario, LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.plusDays(1).atStartOfDay();
        List<PontoEletronico> registros = pontoEletronicoDatabaseGateway.findByUsuarioAndDataBetween(usuario, dataInicio, dataFim);

        if (registros == null || registros.size() % 2 != 0) {
            var listaRegistros = pontoEletronicoMapper.toDtoList(registros);
            return new PontoCalculadoDto("inconsistente", data, usuario, listaRegistros, 0);
        }

        registros.sort(Comparator.comparing(PontoEletronico::getData));

        long totalHorasTrabalhadas = 0;
        LocalDateTime ultimaSaida = null;

        for (int i = 0; i < registros.size(); i += 2) {
            LocalDateTime entrada = registros.get(i).getData();
            LocalDateTime saida = registros.get(i + 1).getData();

            if (ultimaSaida != null && entrada.isBefore(ultimaSaida)) {
                var listaRegistros = pontoEletronicoMapper.toDtoList(registros);
                return new PontoCalculadoDto("inconsistente", data, usuario, listaRegistros, totalHorasTrabalhadas);
            }

            Duration duracao = Duration.between(entrada, saida);
            totalHorasTrabalhadas += duracao.toHours();

            ultimaSaida = saida;
        }

        var listaRegistros = pontoEletronicoMapper.toDtoList(registros);
        if (totalHorasTrabalhadas < 8) {
            return new PontoCalculadoDto("negativo", data, usuario, listaRegistros, totalHorasTrabalhadas);
        }
        return new PontoCalculadoDto("positivo", data, usuario, listaRegistros, totalHorasTrabalhadas);

    }
}
