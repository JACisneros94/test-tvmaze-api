package com.tvmaze.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "shows")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDocument {

    @Id
    private String id;
    private Long showId;
    private Map<String, Object> data;
    private LocalDateTime cachedAt;
}
