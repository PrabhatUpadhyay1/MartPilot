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
public class StoreProductDTO {

    private Long id;

    @JsonProperty("tenant_id")
    private Long tenantId;

    @JsonProperty("store_id")
    private Long storeId;

    @JsonProperty("product_id")
    private Long productId;

    private BigDecimal price;

    private Integer stock;

    @JsonProperty("is_available")
    private Boolean isAvailable;
}

