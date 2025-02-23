package org.example.steamstoreservice.controller;

import org.example.steamstoreservice.entity.Purchase;
import org.example.steamstoreservice.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/steam-store")
public class StoreController {
    private final PurchaseService purchaseService;

    public StoreController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    // ✅ Get User Purchases
    @GetMapping("/purchases/{userId}")
    public Flux<Purchase> getPurchases(@PathVariable Long userId) {
        return purchaseService.getUserPurchases(userId);
    }

    // ✅ List All Purchases
    @GetMapping("/purchases/all")
    public Flux<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    // ✅ Purchase a Game (JDBC for Transactions)
    @PostMapping("/purchase")
    public Mono<Void> purchaseGame(@RequestParam Long userId, @RequestParam Long gameId) {
        return purchaseService.purchaseGame(userId, gameId);
    }

    // ✅ Refund a Purchase
    @PostMapping("/purchases/refund")
    public Mono<Void> refundPurchase(@RequestParam Long userId, @RequestParam Long purchaseId) {
        return purchaseService.refundPurchase(userId, purchaseId);
    }
}
