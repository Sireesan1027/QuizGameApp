package com.gameapp.quiz.controller;
import com.gameapp.quiz.model.Score;
import com.gameapp.quiz.service.ScoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @Operation(summary = "Save a new score", description = "Allows a user to store their game score by providing an email, username, and score.")
    @PostMapping
    public ResponseEntity<Score> saveScore(@Valid @RequestBody Score score) {
        log.info("Received request to save score: email={} username={} score={}", score.getEmail(), score.getUsername(), score.getScore());

        if (score.getEmail() == null || score.getUsername() == null || score.getScore() == null) {
            log.warn("Request is missing required fields: email, username, or score.");
            return ResponseEntity.badRequest().build();  // 400 Bad Request if fields are missing
        }

        try {
            Score savedScore = scoreService.saveScore(score.getEmail(), score.getUsername(), score.getScore());
            log.info("Score saved successfully: {}", savedScore);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedScore);
        } catch (Exception e) {
            log.error("Error saving score: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 Internal Server Error
        }
    }

    @Operation(summary = "Get the leaderboard", description = "Retrieves the top 10 highest scores ordered by score.")
    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        log.info("Retrieving leaderboard...");

        try {
            var leaderboard = scoreService.getLeaderboard();
            log.info("Leaderboard retrieved successfully with {} entries.", leaderboard.size());
            return ResponseEntity.ok(leaderboard);
        } catch (Exception e) {
            log.error("Error retrieving leaderboard: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}