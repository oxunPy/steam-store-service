package org.example.steamstoreservice.service;

import org.example.steamstoreservice.dto.model.GameModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GameClientService {
    @Value("${steam.game.microservice.url}")
    private String gameServiceUrl;

    private final WebClient webClient;

    public GameClientService(WebClient.Builder webClientBuilder) {
        webClient = webClientBuilder.baseUrl(gameServiceUrl).build();
    }

    public Mono<GameModel> getGameById(String gameId) {
        return webClient.get()
                .uri("/steam-game/{id}", gameId)
                .retrieve()
                .bodyToMono(GameModel.class);
    }
}
