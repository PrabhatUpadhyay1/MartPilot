package org.martpilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDTO {

    private Long id;

    @JsonProperty("tenant_id")
    private Long tenantId;

    private String name;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    @JsonProperty("is_active")
    private Boolean isActive;
}

