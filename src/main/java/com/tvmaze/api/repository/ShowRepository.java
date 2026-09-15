package com.tvmaze.api.repository;

import com.tvmaze.api.model.ShowDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShowRepository extends MongoRepository<ShowDocument, String> {

    Optional<ShowDocument> findByShowId(Long showId);
}
