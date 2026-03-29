package org.martpilot.exception;

public class TenantAccessDeniedException extends RuntimeException {

    public TenantAccessDeniedException(String message) {
        super(message);
    }

    public TenantAccessDeniedException(Long tenantId, Long resourceTenantId) {
        super(String.format("Access denied: Tenant %d is not authorized to access resource belonging to Tenant %d", 
                tenantId, resourceTenantId));
    }
}

