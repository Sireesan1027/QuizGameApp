package com.gameapp.quiz.service;

import com.gameapp.quiz.model.Score;
import com.gameapp.quiz.repository.ScoreRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j2
@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    // Save the score (store every score for the user)
    public Score saveScore(String email, String username, Integer score) {
        log.debug("Saving score for user: {} with score: {}", email, score);

        try {
            Score newScore = new Score();
            newScore.setEmail(email);
            newScore.setUsername(username);
            newScore.setScore(score);
            Score savedScore = scoreRepository.save(newScore);
            log.debug("Score saved successfully: {}", savedScore);
            return savedScore;
        } catch (Exception e) {
            log.error("Error saving score for user: {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Error saving score", e);
        }
    }

    // Get leaderboard: find highest score per user and show their name
    public List<Map<String, Object>> getLeaderboard() {
        log.debug("Fetching leaderboard...");

        try {
            List<Score> allScores = scoreRepository.findAll();
            Map<String, Score> highestScoresByUser = new HashMap<>();

            // Iterate through all scores and keep only the highest score for each user
            for (Score score : allScores) {
                String email = score.getEmail();
                if (!highestScoresByUser.containsKey(email) || score.getScore() > highestScoresByUser.get(email).getScore()) {
                    highestScoresByUser.put(email, score);
                }
            }

            // Prepare leaderboard: each entry will be a map of email, username, and highest score
            List<Map<String, Object>> leaderboard = new ArrayList<>();
            for (Score highestScore : highestScoresByUser.values()) {
                Map<String, Object> leaderboardEntry = new HashMap<>();
                leaderboardEntry.put("email", highestScore.getEmail());
                leaderboardEntry.put("username", highestScore.getUsername());
                leaderboardEntry.put("score", highestScore.getScore());
                leaderboard.add(leaderboardEntry);
            }

            // Sort leaderboard by score (highest first)
            leaderboard.sort((entry1, entry2) -> Integer.compare((int) entry2.get("score"), (int) entry1.get("score")));

            log.debug("Leaderboard retrieved successfully with {} entries", leaderboard.size());
            return leaderboard;
        } catch (Exception e) {
            log.error("Error retrieving leaderboard: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving leaderboard", e);
        }
    }
}