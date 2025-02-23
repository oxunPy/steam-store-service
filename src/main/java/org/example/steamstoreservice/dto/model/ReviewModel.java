package org.example.steamstoreservice.dto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewModel {
    private String userId;
    private String comment;
    private int rating;
}
