package com.postechhackaton.pontoeletronico.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PontoCalculadoDto {
    private String status;

    private LocalDate data;

    private String usuario;

    private List<PontoEletronicoDto> registros;
}
