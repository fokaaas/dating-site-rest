package org.spring.datingsite.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PageWithSort {

    @Schema(description = "Page number", example = "0")
    private Integer page;

    @Schema(description = "Size of the page", example = "10")
    private Integer size;

    @Schema(description = "Sort by", example = "name")
    private String sortBy;

    @Schema(description = "Sort direction", example = "asc")
    private String sort;
}
