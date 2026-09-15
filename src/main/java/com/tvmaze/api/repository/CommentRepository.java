package com.tvmaze.api.repository;

import com.tvmaze.api.model.CommentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<CommentDocument, String> {

    List<CommentDocument> findByShowId(Long showId);
}
