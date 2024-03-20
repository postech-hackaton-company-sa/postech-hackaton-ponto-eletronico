package com.postechhackaton.pontoeletronico.application.controller;

import com.postechhackaton.pontoeletronico.application.dto.ExceptionResponse;
import com.postechhackaton.pontoeletronico.application.dto.PontoCalculadoDto;
import com.postechhackaton.pontoeletronico.application.dto.PontoEletronicoDto;
import com.postechhackaton.pontoeletronico.application.mappers.PontoEletronicoMapper;
import com.postechhackaton.pontoeletronico.business.entities.PontoEletronicoEntity;
import com.postechhackaton.pontoeletronico.domain.usecase.CalcularPontoUseCase;
import com.postechhackaton.pontoeletronico.domain.usecase.RegistrarPontoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/v1/ponto-eletronico")
public class PontoEletronicoController {

    private final RegistrarPontoUseCase registrarPontoUseCase;
    private final CalcularPontoUseCase calcularPontoUseCase;
    private final PontoEletronicoMapper pontoEletronicoMapper;

    public PontoEletronicoController(RegistrarPontoUseCase registrarPontoUseCase,
                                     CalcularPontoUseCase calcularPontoUseCase,
                                     PontoEletronicoMapper pontoEletronicoMapper) {
        this.registrarPontoUseCase = registrarPontoUseCase;
        this.calcularPontoUseCase = calcularPontoUseCase;
        this.pontoEletronicoMapper = pontoEletronicoMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Altera o status de um pagamento",
            description = "Realiza uma alteracao do status do pagamento utilizando o id do mesmo."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",
                    description = "Ponto registrado com sucesso",
                    content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = PontoEletronicoDto.class)
                    )
            }),
            @ApiResponse(responseCode = "400",
                    description = "Erro ao registrar ponto",
                    content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = ExceptionResponse.class)
                    )
            }),
    })
    public PontoEletronicoDto registrarPonto(@RequestHeader("usuario") String usuario) {
        PontoEletronicoEntity pontoEletronico = registrarPontoUseCase.execute(usuario);
        return pontoEletronicoMapper.toDto(pontoEletronico);
    }

    @GetMapping
    @Operation(
            summary = "Calcula o ponto do usuario",
            description = "Calcula o ponto do usuario baseado na data informada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ponto calculado com sucesso",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PontoCalculadoDto.class)
                            )
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Parametros invalidos para calcular o ponto",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
    })
    public PontoCalculadoDto calcularPonto(@RequestHeader("usuario") String usuario, @RequestParam("data") LocalDate data) {
        return calcularPontoUseCase.execute(usuario, data);
    }

}
