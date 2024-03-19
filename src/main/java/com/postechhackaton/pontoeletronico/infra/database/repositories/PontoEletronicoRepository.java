package com.postechhackaton.pontoeletronico.infra.database.repositories;

import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PontoEletronicoRepository extends MongoRepository<PontoEletronico, UUID> {

    List<PontoEletronico> findByUsuarioAndDataGreaterThan(String usuario, LocalDateTime startOfDay);

}
