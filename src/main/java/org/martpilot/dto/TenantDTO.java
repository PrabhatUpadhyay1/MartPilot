package org.martpilot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantDTO {

    private Long id;

    @JsonProperty("business_name")
    private String businessName;

    private String subdomain;

    @JsonProperty("custom_domain")
    private String customDomain;

    @JsonProperty("owner_name")
    private String ownerName;

    private String phone;

    private String email;

    @JsonProperty("subscription_plan")
    private String subscriptionPlan;

    @JsonProperty("is_active")
    private String isActive;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

