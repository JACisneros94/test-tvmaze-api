package com.tvmaze.api.controller;

import com.tvmaze.api.dto.CommentRequestDTO;
import com.tvmaze.api.model.CommentDocument;
import com.tvmaze.api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comments")
    public ResponseEntity<Map<String, Object>> createComment(@Valid @RequestBody CommentRequestDTO request) {
        if (request.getRating() < 0 || request.getRating() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El rating debe estar entre 0 y 5");
        }

        CommentDocument saved = commentService.saveComment(
                request.getShowId(),
                request.getComment(),
                request.getRating()
        );

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Comentario guardado exitosamente");
        body.put("id", saved.getId());
        body.put("show_id", saved.getShowId());

        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}
