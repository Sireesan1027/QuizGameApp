package com.gameapp.quiz.repository;
import com.gameapp.quiz.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
public interface ScoreRepository extends MongoRepository<Score, String> {

    // Find all scores by email (for storing each score)
    List<Score> findByEmail(String email);

    // Query for retrieving the highest score for each user (group by email, get the max score)
    @Query(value = "{}", fields = "{'email': 1, 'username': 1, 'score': 1}")
    List<Score> findTopScores();  // Get top scores by email (we will process this in service)
}