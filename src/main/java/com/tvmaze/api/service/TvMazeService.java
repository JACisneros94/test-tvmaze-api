package com.tvmaze.api.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class TvMazeService {

    private static final String SEARCH_URL = "https://api.tvmaze.com/search/shows";
    private static final String SHOW_URL = "https://api.tvmaze.com/shows/{id}";

    private final RestTemplate restTemplate;

    public TvMazeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> searchShows(String query) {
        String url = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("q", query)
                .toUriString();
        try {
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
            List<Map<String, Object>> body = response.getBody();
            return body != null ? body : List.of();
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Error al consultar la API de TVMaze: " + ex.getMessage(), ex);
        }
    }

    public Map<String, Object> getShow(Long id) {
        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    SHOW_URL,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {},
                    id
            );
            Map<String, Object> body = response.getBody();
            if (body == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No se encontro el show con id " + id);
            }
            return body;
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No se encontro el show con id " + id, ex);
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,
                    "Error al consultar la API de TVMaze: " + ex.getMessage(), ex);
        }
    }
}
