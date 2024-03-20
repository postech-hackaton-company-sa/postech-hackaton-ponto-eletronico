package com.postechhackaton.pontoeletronico.application.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.postechhackaton.pontoeletronico.infra.database.entities.PontoEletronico;
import com.postechhackaton.pontoeletronico.infra.database.repositories.PontoEletronicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Component
public class MongoDBDataLoader implements CommandLineRunner {

    private final PontoEletronicoRepository pontoEletronicoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MongoDBDataLoader(PontoEletronicoRepository pontoEletronicoRepository) {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        this.pontoEletronicoRepository = pontoEletronicoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pontoEletronicoRepository.findAll().isEmpty()) {
            loadInitialData();
        }
    }

    private void loadInitialData() throws IOException {
        File file = ResourceUtils.getFile("classpath:data.json");
        String content = new String(Files.readAllBytes(file.toPath()));

        List<PontoEletronico> pontos = objectMapper.readValue(content, new TypeReference<>() {});
        pontos.forEach(p -> p.setId(UUID.randomUUID()));
        pontoEletronicoRepository.insert(pontos);
    }
}
