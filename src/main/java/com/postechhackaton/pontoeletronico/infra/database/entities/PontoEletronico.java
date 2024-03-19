package com.postechhackaton.pontoeletronico.infra.database.entities;

import com.postechhackaton.pontoeletronico.business.enums.TipoRegistroPontoEletronico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@SuppressWarnings("unused")
@Getter
@Setter
@Builder
@Document(collection = "pontoeletronico")
@NoArgsConstructor
@AllArgsConstructor
public class PontoEletronico {

    @Id
    private UUID id;

    private String usuario;

    private LocalDateTime data;

    private TipoRegistroPontoEletronico tipo;
}
