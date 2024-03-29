package com.postechhackaton.pontoeletronico.application.usecase;

import com.postechhackaton.pontoeletronico.application.mappers.PontoEletronicoMapper;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.business.enums.TipoRegistroPontoEletronico;
import com.postechhackaton.pontoeletronico.business.exceptions.RegistroPontoException;
import com.postechhackaton.pontoeletronico.domain.gateways.PontoEletronicoDatabaseGateway;
import com.postechhackaton.pontoeletronico.domain.usecase.RegistrarPontoUseCase;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RegistrarPontoUseCaseImpl implements RegistrarPontoUseCase {

    private final PontoEletronicoDatabaseGateway pontoEletronicoDatabaseGateway;
    private final PontoEletronicoMapper pontoEletronicoMapper;

    public RegistrarPontoUseCaseImpl(PontoEletronicoDatabaseGateway pontoEletronicoDatabaseGateway, PontoEletronicoMapper pontoEletronicoMapper) {
        this.pontoEletronicoDatabaseGateway = pontoEletronicoDatabaseGateway;
        this.pontoEletronicoMapper = pontoEletronicoMapper;
    }

    @Override
    public PontoEletronicoEntity execute(String usuario) {
        LocalDateTime dataAtual = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime dataAtualFimDia = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(59);
        List<PontoEletronico> registros = pontoEletronicoDatabaseGateway.findByUsuarioAndDataBetween(usuario, dataAtual, dataAtualFimDia);

        validarHorarioEntrada(registros);

        TipoRegistroPontoEletronico tipoRegistro = obterTipoRegistro(registros);

        PontoEletronicoEntity novoRegistro = pontoEletronicoMapper.toEntity(usuario, tipoRegistro);
        PontoEletronico pontoEletronico = pontoEletronicoDatabaseGateway.save(novoRegistro);
        return pontoEletronicoMapper.toEntity(pontoEletronico);
    }

    private TipoRegistroPontoEletronico obterTipoRegistro(List<PontoEletronico> registros) {
        if (registros.isEmpty() || registros.get(registros.size() - 1).getTipo() == TipoRegistroPontoEletronico.SAIDA) {
            return TipoRegistroPontoEletronico.ENTRADA;
        } else {
            return TipoRegistroPontoEletronico.SAIDA;
        }
    }

    private void validarHorarioEntrada(List<PontoEletronico> registros) {
        if (!registros.isEmpty()) {
            PontoEletronico ultimoRegistro = registros.get(registros.size() - 1);
            LocalDateTime horaUltimoRegistro = ultimoRegistro.getData();

            LocalDateTime horaAtual = LocalDateTime.now();
            if (horaUltimoRegistro.plusMinutes(5).isAfter(horaAtual)) {
                throw new RegistroPontoException("Não é permitido registrar ponto com menos de 5 minutos do último registro.");
            }
        }
    }

}
