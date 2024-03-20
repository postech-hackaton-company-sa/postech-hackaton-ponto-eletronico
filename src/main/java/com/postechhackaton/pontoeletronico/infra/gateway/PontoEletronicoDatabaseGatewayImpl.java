package com.postechhackaton.pontoeletronico.infra.gateway;

import com.postechhackaton.pontoeletronico.application.mappers.PontoEletronicoMapper;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.business.exceptions.NotFoundException;
import com.postechhackaton.pontoeletronico.domain.gateways.PontoEletronicoDatabaseGateway;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import com.postechhackaton.pontoeletronico.infra.database.repositories.PontoEletronicoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class PontoEletronicoDatabaseGatewayImpl implements PontoEletronicoDatabaseGateway {

    private final PontoEletronicoRepository pontoEletronicoRepository;
    private final PontoEletronicoMapper pontoEletronicoMapper;

    public PontoEletronicoDatabaseGatewayImpl(PontoEletronicoRepository pontoEletronicoRepository, PontoEletronicoMapper pontoEletronicoMapper) {
        this.pontoEletronicoRepository = pontoEletronicoRepository;
        this.pontoEletronicoMapper = pontoEletronicoMapper;
    }

    @Override
    public PontoEletronico save(PontoEletronicoEntity pontoEletronico) {
        var document = pontoEletronicoMapper.toDocument(pontoEletronico);
        return pontoEletronicoRepository.save(document);
    }

    @Override
    public PontoEletronicoEntity findById(String id) {
        return pontoEletronicoRepository.findById(UUID.fromString(id))
                .map(pontoEletronicoMapper::toEntity)
                .orElseThrow(() -> new NotFoundException("Ponto eletrônico não encontrado"));
    }

    @Override
    public List<PontoEletronico> findByUsuarioAndDataBetween(String usuario, LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return pontoEletronicoRepository.findByUsuarioAndDataBetween(usuario, startOfDay, endOfDay);
    }
}
