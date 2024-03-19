package com.postechhackaton.pontoeletronico.domain.usecase;

import com.postechhackaton.pontoeletronico.application.dto.PontoCalculadoDto;

import java.time.LocalDate;

public interface CalcularPontoUseCase {

    PontoCalculadoDto execute(String usuario, LocalDate data);

}
