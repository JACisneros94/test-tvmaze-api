package com.tvmaze.api.service;

import com.tvmaze.api.dto.CommentResponseDTO;
import com.tvmaze.api.model.CommentDocument;
import com.tvmaze.api.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentResponseDTO> findByShowId(Long showId) {
        return commentRepository.findByShowId(showId).stream()
                .map(c -> new CommentResponseDTO(c.getComment(), c.getRating()))
                .collect(Collectors.toList());
    }

    public CommentDocument saveComment(Long showId, String comment, int rating) {
        CommentDocument document = CommentDocument.builder()
                .showId(showId)
                .comment(comment)
                .rating(rating)
                .createdAt(LocalDateTime.now())
                .build();
        return commentRepository.save(document);
    }
}
