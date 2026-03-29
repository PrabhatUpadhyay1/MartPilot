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
public class CategoryDTO {

    private Long id;

    @JsonProperty("tenant_id")
    private Long tenantId;

    private String name;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("parent_id")
    private Long parentId;
}

