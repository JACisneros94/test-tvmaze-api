package com.tvmaze.api.controller;

import com.tvmaze.api.dto.CommentResponseDTO;
import com.tvmaze.api.service.CommentService;
import com.tvmaze.api.service.ShowCacheService;
import com.tvmaze.api.service.TvMazeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ShowController {

    private final TvMazeService tvMazeService;
    private final ShowCacheService showCacheService;
    private final CommentService commentService;

    public ShowController(TvMazeService tvMazeService,
                          ShowCacheService showCacheService,
                          CommentService commentService) {
        this.tvMazeService = tvMazeService;
        this.showCacheService = showCacheService;
        this.commentService = commentService;
    }

    @GetMapping("/shows/{id}")
    public Map<String, Object> getShow(@PathVariable("id") Long id) {
        Optional<Map<String, Object>> cached = showCacheService.findByShowId(id);

        Map<String, Object> show;
        if (cached.isPresent()) {
            show = new HashMap<>(cached.get());
        } else {
            show = new HashMap<>(tvMazeService.getShow(id));
            showCacheService.saveShow(id, show);
        }

        List<CommentResponseDTO> comments = commentService.findByShowId(id);
        show.put("comments", comments);

        return show;
    }
}
