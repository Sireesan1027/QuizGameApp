package com.gameapp.quiz.repository;
import com.gameapp.quiz.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends MongoRepository<Score, String> {

    // Find a score by email (to check for existing scores)
    Optional<Score> findByEmail(String email);

    // Query for the top 10 scores, ordered by score descending
    List<Score> findTop10ByOrderByScoreDesc();
}