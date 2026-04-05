package org.martpilot.context;

/**
 * TenantContext is a ThreadLocal-based utility to maintain tenant and store information
 * across the request lifecycle. This is crucial for multi-tenant SaaS applications.
 *
 * Usage:
 * 1. Set tenant ID and optionally store ID at the start of request
 * 2. Access tenant ID and store ID throughout the request
 * 3. Clear both at the end of request
 */
public class TenantContext {

    private static final ThreadLocal<Long> currentTenantId = new ThreadLocal<>();
    private static final ThreadLocal<Long> currentStoreId  = new ThreadLocal<>();

    public static void setTenantId(Long id) { currentTenantId.set(id); }
    public static Long  getTenantId()        { return currentTenantId.get(); }
    public static void setStoreId(Long id)   { currentStoreId.set(id); }
    public static Long  getStoreId()         { return currentStoreId.get(); }
    public static void clear() {
        currentTenantId.remove();
        currentStoreId.remove();
    }
}

