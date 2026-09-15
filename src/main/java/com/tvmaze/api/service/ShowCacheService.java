package com.tvmaze.api.service;

import com.tvmaze.api.model.ShowDocument;
import com.tvmaze.api.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class ShowCacheService {

    private final ShowRepository showRepository;

    public ShowCacheService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public Optional<Map<String, Object>> findByShowId(Long id) {
        return showRepository.findByShowId(id).map(ShowDocument::getData);
    }

    public void saveShow(Long id, Map<String, Object> data) {
        ShowDocument document = showRepository.findByShowId(id)
                .orElseGet(ShowDocument::new);
        document.setShowId(id);
        document.setData(data);
        document.setCachedAt(LocalDateTime.now());
        showRepository.save(document);
    }
}
