package com.postechhackaton.pontoeletronico.application.mappers;

import com.postechhackaton.pontoeletronico.application.dto.PontoEletronicoDto;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.business.enums.TipoRegistroPontoEletronico;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = { UUID.class, LocalDateTime.class })
public interface PontoEletronicoMapper {

    PontoEletronico toDocument(PontoEletronicoEntity pontoEletronico);

    PontoEletronicoEntity toEntity(PontoEletronico pontoEletronico);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "data", expression = "java(java.time.LocalDateTime.now())")
    PontoEletronicoEntity toEntity(String usuario, TipoRegistroPontoEletronico tipo);

    List<PontoEletronicoDto> toDtoList(List<PontoEletronico> registros);

    PontoEletronicoDto toDto(PontoEletronicoEntity pontoEletronico);

    List<PontoEletronicoEntity> toEntityList(List<PontoEletronico> registros);
}
