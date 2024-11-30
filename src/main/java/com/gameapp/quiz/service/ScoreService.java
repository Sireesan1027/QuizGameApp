package com.gameapp.quiz.service;

import com.gameapp.quiz.model.Score;
import com.gameapp.quiz.repository.ScoreRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    // Save the score, ensuring the highest score per player is kept
    public Score saveScore(String email, String username, Integer score) {
        log.debug("Saving score for user: {} with score: {}", email, score);

        try {
            Optional<Score> existingScoreOpt = scoreRepository.findByEmail(email);
            Score scoreToSave;

            if (existingScoreOpt.isPresent()) {
                Score existingScore = existingScoreOpt.get();
                // If the new score is higher, update the score
                if (score > existingScore.getScore()) {
                    existingScore.setScore(score);
                    existingScore.setUsername(username);
                    scoreToSave = scoreRepository.save(existingScore);
                    log.debug("Updated score for user: {}", email);
                } else {
                    // Don't update if the new score is lower
                    scoreToSave = existingScore;
                    log.debug("Score not updated for user {} (new score is lower)", email);
                }
            } else {
                // Save as a new score if no existing score for the user
                Score newScore = new Score();
                newScore.setEmail(email);
                newScore.setUsername(username);
                newScore.setScore(score);
                scoreToSave = scoreRepository.save(newScore);
                log.debug("New score saved for user: {}", email);
            }

            return scoreToSave;
        } catch (Exception e) {
            log.error("Error saving score for user: {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Error saving score", e);
        }
    }

    // Get leaderboard: only highest score for each player
    public List<Score> getLeaderboard() {
        log.debug("Fetching leaderboard...");

        try {
            List<Score> leaderboard = scoreRepository.findTop10ByOrderByScoreDesc();
            log.debug("Leaderboard retrieved successfully: {} entries", leaderboard.size());
            return leaderboard;
        } catch (Exception e) {
            log.error("Error retrieving leaderboard: {}", e.getMessage(), e);
            throw new RuntimeException("Error retrieving leaderboard", e);
        }
    }
}