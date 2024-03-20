package com.postechhackaton.pontoeletronico.domain.gateways;

import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;

import java.time.LocalDateTime;
import java.util.List;

public interface PontoEletronicoDatabaseGateway {
    PontoEletronico save(PontoEletronicoEntity pontoEletronico);

    PontoEletronicoEntity findById(String id);

    List<PontoEletronico> findByUsuarioAndDataBetween(String usuario, LocalDateTime startOfDay, LocalDateTime endOfDay);

}
