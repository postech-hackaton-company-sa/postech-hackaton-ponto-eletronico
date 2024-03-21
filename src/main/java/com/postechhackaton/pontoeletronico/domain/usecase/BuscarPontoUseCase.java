package com.postechhackaton.pontoeletronico.domain.usecase;

import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BuscarPontoUseCase {
    List<PontoEletronicoEntity> execute(String usuario, LocalDateTime inicio, LocalDateTime fim);

    List<PontoEletronicoEntity> execute(String usuario, LocalDate inicio, LocalDate fim);

    List<PontoEletronicoEntity> execute(String usuario, LocalDate data);
}
