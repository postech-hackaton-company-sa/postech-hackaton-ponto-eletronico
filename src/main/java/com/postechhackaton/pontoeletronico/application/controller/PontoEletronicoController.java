package com.postechhackaton.pontoeletronico.application.controller;

import com.postechhackaton.pontoeletronico.application.dto.PontoCalculadoDto;
import com.postechhackaton.pontoeletronico.application.dto.PontoEletronicoDto;
import com.postechhackaton.pontoeletronico.application.mappers.PontoEletronicoMapper;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.domain.usecase.CalcularPontoUseCase;
import com.postechhackaton.pontoeletronico.domain.usecase.RegistrarPontoUseCase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/v1/ponto-eletronico")
public class PontoEletronicoController {

    private final RegistrarPontoUseCase registrarPontoUseCase;
//    private final CalcularPontoUseCase calcularPontoUseCase;
    private final PontoEletronicoMapper pontoEletronicoMapper;

    public PontoEletronicoController(RegistrarPontoUseCase registrarPontoUseCase,
//                                     CalcularPontoUseCase calcularPontoUseCase,
                                     PontoEletronicoMapper pontoEletronicoMapper) {
        this.registrarPontoUseCase = registrarPontoUseCase;
//        this.calcularPontoUseCase = calcularPontoUseCase;
        this.pontoEletronicoMapper = pontoEletronicoMapper;
    }

    @PostMapping
    public PontoEletronicoDto registrarPonto(@RequestHeader("usuario") String usuario) {
        PontoEletronicoEntity pontoEletronico = registrarPontoUseCase.execute(usuario);
        return pontoEletronicoMapper.toDto(pontoEletronico);
    }

//    @GetMapping
//    public PontoCalculadoDto calcularPonto(@RequestHeader("usuario") String usuario) {
//        return calcularPontoUseCase.execute(usuario, LocalDate.now());
//    }

}
