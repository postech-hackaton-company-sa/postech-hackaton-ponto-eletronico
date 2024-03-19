package com.postechhackaton.pontoeletronico.infra.gateway;

import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.domain.gateways.PontoEletronicoDatabaseGateway;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import com.postechhackaton.pontoeletronico.infra.database.repositories.PontoEletronicoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PontoEletronicoDatabaseGatewayImpl implements PontoEletronicoDatabaseGateway {

    private final PontoEletronicoRepository pontoEletronicoRepository;

    public PontoEletronicoDatabaseGatewayImpl(PontoEletronicoRepository pontoEletronicoRepository) {
        this.pontoEletronicoRepository = pontoEletronicoRepository;
    }

    @Override
    public PontoEletronico save(PontoEletronicoEntity pontoEletronico) {
        return null;
    }

    @Override
    public PontoEletronico findById(String id) {
        return null;
    }

    @Override
    public List<PontoEletronico> findByUsuarioAndDataGreaterThan(String usuario, LocalDateTime startOfDay) {
        return null;
    }
}
