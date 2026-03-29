package org.martpilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private Long id;

    @JsonProperty("tenant_id")
    private Long tenantId;

    private String name;

    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("image_url")
    private String imageUrl;

    private String brand;
}

