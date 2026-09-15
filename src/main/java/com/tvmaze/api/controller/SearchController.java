package com.tvmaze.api.controller;

import com.tvmaze.api.dto.ShowResponseDTO;
import com.tvmaze.api.service.CommentService;
import com.tvmaze.api.service.TvMazeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SearchController {

    private final TvMazeService tvMazeService;
    private final CommentService commentService;

    public SearchController(TvMazeService tvMazeService, CommentService commentService) {
        this.tvMazeService = tvMazeService;
        this.commentService = commentService;
    }

    @GetMapping("/search")
    public List<ShowResponseDTO> search(@RequestParam("search_query") String searchQuery) {
        List<Map<String, Object>> results = tvMazeService.searchShows(searchQuery);
        List<ShowResponseDTO> response = new ArrayList<>();

        for (Map<String, Object> result : results) {
            @SuppressWarnings("unchecked")
            Map<String, Object> show = (Map<String, Object>) result.get("show");
            if (show == null) {
                continue;
            }
            response.add(mapShow(show));
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    private ShowResponseDTO mapShow(Map<String, Object> show) {
        Long id = show.get("id") != null
                ? ((Number) show.get("id")).longValue()
                : null;
        String name = (String) show.get("name");
        String summary = (String) show.get("summary");

        List<String> genres = (List<String>) show.getOrDefault("genres", new ArrayList<String>());

        String channel = resolveChannel(show);

        return ShowResponseDTO.builder()
                .id(id)
                .name(name)
                .channel(channel)
                .summary(summary)
                .genres(genres)
                .comments(id != null ? commentService.findByShowId(id) : new ArrayList<>())
                .build();
    }

    @SuppressWarnings("unchecked")
    private String resolveChannel(Map<String, Object> show) {
        Map<String, Object> network = (Map<String, Object>) show.get("network");
        if (network != null && network.get("name") != null) {
            return (String) network.get("name");
        }
        Map<String, Object> webChannel = (Map<String, Object>) show.get("webChannel");
        if (webChannel != null && webChannel.get("name") != null) {
            return (String) webChannel.get("name");
        }
        return "N/A";
    }
}
