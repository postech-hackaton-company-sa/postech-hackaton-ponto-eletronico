package com.postechhackaton.pontoeletronico.application.usecase;

import com.postechhackaton.pontoeletronico.application.mappers.PontoEletronicoMapper;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.domain.gateways.PontoEletronicoDatabaseGateway;
import com.postechhackaton.pontoeletronico.domain.usecase.BuscarPontoUseCase;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BuscarPontoUseCaseImpl implements BuscarPontoUseCase {

    private final PontoEletronicoDatabaseGateway pontoEletronicoDatabaseGateway;

    private final PontoEletronicoMapper pontoEletronicoMapper;

    public BuscarPontoUseCaseImpl(PontoEletronicoDatabaseGateway pontoEletronicoDatabaseGateway,
                                  PontoEletronicoMapper pontoEletronicoMapper) {
        this.pontoEletronicoDatabaseGateway = pontoEletronicoDatabaseGateway;
        this.pontoEletronicoMapper = pontoEletronicoMapper;
    }

    @Override
    public List<PontoEletronicoEntity> execute(String usuario, LocalDateTime inicio, LocalDateTime fim) {
        List<PontoEletronico> registros = pontoEletronicoDatabaseGateway.findByUsuarioAndDataBetween(usuario, inicio, fim);
        return pontoEletronicoMapper.toEntityList(registros);
    }

    @Override
    public List<PontoEletronicoEntity> execute(String usuario, LocalDate inicio, LocalDate fim) {
        LocalDateTime dataInicio = inicio.atStartOfDay();
        LocalDateTime dataFim = fim.plusDays(1).atStartOfDay();
        return this.execute(usuario, dataInicio, dataFim);
    }

    @Override
    public List<PontoEletronicoEntity> execute(String usuario, LocalDate data) {
        LocalDateTime dataInicio = data.atStartOfDay();
        LocalDateTime dataFim = data.plusDays(1).atStartOfDay();
        return this.execute(usuario, dataInicio, dataFim);
    }
}
