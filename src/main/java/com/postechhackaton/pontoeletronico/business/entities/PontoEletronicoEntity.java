package com.postechhackaton.pontoeletronico.business.entities;

import com.postechhackaton.pontoeletronico.business.enums.TipoRegistroPontoEletronico;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PontoEletronicoEntity {
    private UUID id;

    private String usuario;

    private LocalDateTime data;

    private TipoRegistroPontoEletronico tipo;
}
