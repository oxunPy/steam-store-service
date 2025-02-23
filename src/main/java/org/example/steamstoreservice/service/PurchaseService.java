package org.example.steamstoreservice.service;

import org.example.steamstoreservice.entity.Purchase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
public class PurchaseService {
    private final DatabaseClient databaseClient;
    private final JdbcTemplate jdbcTemplate;

    public PurchaseService(DatabaseClient databaseClient, JdbcTemplate jdbcTemplate) {
        this.databaseClient = databaseClient;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Get all purchases for a user
    public Flux<Purchase> getUserPurchases(Long userId) {
        return databaseClient.sql("SELECT * FROM purchases WHERE user_id = :userId")
                .bind("userId", userId)
                .map((row, metadata) -> new Purchase(
                        row.get("id", Long.class),
                        row.get("user_id", Long.class),
                        row.get("game_id", Long.class),
                        row.get("purchase_date", LocalDateTime.class)
                )).all();
    }

    // ✅ Get all purchases (R2DBC)
    public Flux<Purchase> getAllPurchases() {
        return databaseClient.sql("SELECT * FROM purchases")
                .map((row, metadata) -> new Purchase(
                        row.get("id", Long.class),
                        row.get("user_id", Long.class),
                        row.get("game_id", Long.class),
                        row.get("purchase_date", LocalDateTime.class)
                ))
                .all();
    }

    // ✅ Purchase a game (JDBC - Ensures Transaction)
    public Mono<Void> purchaseGame(Long userId, Long gameId) {
        return Mono.fromRunnable(() -> {
            jdbcTemplate.update("INSERT INTO purchases (user_id, game_id, purchase_date) VALUES (?, ?, ?)",
                    userId, gameId, LocalDateTime.now());
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    // ✅ Refund a purchase (JDBC - Ensures Transaction)
    public Mono<Void> refundPurchase(Long userId, Long purchaseId) {
        return Mono.fromRunnable(() -> {
            jdbcTemplate.update("DELETE FROM purchases WHERE id = ? AND user_id = ?", purchaseId, userId);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

}
