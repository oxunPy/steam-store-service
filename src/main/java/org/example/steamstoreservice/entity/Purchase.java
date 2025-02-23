package org.example.steamstoreservice.entity;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Table("purchases")
public class Purchase {
    @Id
    private Long id;

    private Long userId;

    private Long gameId;

    private LocalDateTime purchaseDate;

    public Purchase(Long id, Long userId, Long gameId, LocalDateTime purchaseDate) {
        this.id = id;
        this.userId = userId;
        this.gameId = gameId;
        this.purchaseDate = purchaseDate;
    }

    public Purchase() {}
}
