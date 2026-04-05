package org.martpilot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tenants")
@SQLDelete(sql = "UPDATE tenants SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tenant extends BaseEntity {

    @Column(name = "business_name", length = 150)
    private String businessName;

    @Column(unique = true, length = 100)
    private String subdomain;

    @Column(name = "owner_name", length = 100)
    private String ownerName;

    @Column(length = 20)
    private String phone;

    @Column(length = 150)
    private String email;

    @Column(name = "subscription_plan", length = 50)
    private String subscriptionPlan;

    @Column(length = 20)
    private String status = "ACTIVE";
}

