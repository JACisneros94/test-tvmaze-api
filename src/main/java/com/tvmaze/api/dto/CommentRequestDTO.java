package com.tvmaze.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDTO {
    @JsonProperty("show_id")
    @NotNull(message = "El campo show_id es obligatorio")
    private Long showId;

    @NotBlank(message = "El comentario no puede estar vacio")
    private String comment;

    @Min(value = 0, message = "El rating minimo es 0")
    @Max(value = 5, message = "El rating maximo es 5")
    private int rating;
}
