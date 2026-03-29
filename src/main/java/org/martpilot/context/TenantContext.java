package org.martpilot.context;

/**
 * TenantContext is a ThreadLocal-based utility to maintain tenant information
 * across the request lifecycle. This is crucial for multi-tenant SaaS applications.
 * 
 * Usage:
 * 1. Set tenant ID at the start of request
 * 2. Access tenant ID throughout the request
 * 3. Clear tenant ID at the end of request
 */
public class TenantContext {

    private static final ThreadLocal<Long> tenantId = new ThreadLocal<>();

    public static void setTenantId(Long id) {
        tenantId.set(id);
    }

    public static Long getTenantId() {
        return tenantId.get();
    }

    public static void clear() {
        tenantId.remove();
    }
}

