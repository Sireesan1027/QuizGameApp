package com.gameapp.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.UUID;

@Document(collection = "scores")
@AllArgsConstructor
@Data
public class Score {

    @Id
    private String gameId;
    private String email;  // Player's email
    private String username; // Player's username (added field)
    private Integer score;
    private Date timestamp;

    // Default constructor
    public Score() {
        this.gameId = UUID.randomUUID().toString();  // Auto-generate gameId using UUID
        this.timestamp = new Date();  // Set the current timestamp
    }
}