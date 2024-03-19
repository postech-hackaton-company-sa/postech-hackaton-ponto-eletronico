package com.postechhackaton.pontoeletronico.domain.usecase;

import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;

public interface RegistrarPontoUseCase {
    PontoEletronicoEntity execute(String usuario);
}
