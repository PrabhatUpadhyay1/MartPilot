# MartPilot Backend API Documentation

## Overview
MartPilot is a multi-tenant SaaS platform for marketplace management. This document describes the complete REST API.

## Base URL
```
http://localhost:8080/api/v1
```

## Architecture Overview

### Layers
1. **Entity Layer** (`org.martpilot.entity`) - JPA entities mapped to database tables
2. **Repository Layer** (`org.martpilot.repository`) - Data access objects extending JpaRepository
3. **Service Layer** (`org.martpilot.service`) - Business logic and validation
4. **Controller Layer** (`org.martpilot.controller`) - REST endpoints
5. **DTO Layer** (`org.martpilot.dto`) - Data transfer objects for request/response

### Multi-Tenancy
- **Tenant-Scoped Resources**: Stores, Categories, Products, StoreProducts, Orders
- **Shared Resources**: Tenants, Users, UserTenants
- **Tenant Validation**: All tenant-scoped operations validate tenant ownership
- **Access Control**: `TenantAccessDeniedException` thrown on unauthorized access

---

## API Endpoints

### 1. TENANT MANAGEMENT

#### Create Tenant
```
POST /tenants
Content-Type: application/json

{
  "business_name": "Best Mart",
  "subdomain": "bestmart",
  "custom_domain": "bestmart.com",
  "owner_name": "John Doe",
  "phone": "+1234567890",
  "email": "john@bestmart.com",
  "subscription_plan": "premium"
}

Response: 201 Created
{
  "id": 1,
  "business_name": "Best Mart",
  "subdomain": "bestmart",
  "custom_domain": "bestmart.com",
  "owner_name": "John Doe",
  "phone": "+1234567890",
  "email": "john@bestmart.com",
  "subscription_plan": "premium",
  "is_active": true,
  "created_at": "2024-03-30T10:00:00"
}
```

#### Get All Tenants
```
GET /tenants
Response: 200 OK
[{...}, {...}]
```

#### Get Active Tenants
```
GET /tenants/active
Response: 200 OK
[{...}, {...}]
```

#### Get Tenant by ID
```
GET /tenants/{id}
Response: 200 OK
{...}
```

#### Get Tenant by Subdomain
```
GET /tenants/subdomain/{subdomain}
Response: 200 OK
{...}
```

#### Update Tenant
```
PUT /tenants/{id}
Content-Type: application/json

{
  "business_name": "Best Mart Updated"
}

Response: 200 OK
{...}
```

#### Delete Tenant
```
DELETE /tenants/{id}
Response: 204 No Content
```

---

### 2. USER MANAGEMENT

#### Create User
```
POST /users
Content-Type: application/json

{
  "name": "John Smith",
  "phone": "+9876543210"
}

Response: 201 Created
{
  "id": 1,
  "name": "John Smith",
  "phone": "+9876543210",
  "created_at": "2024-03-30T10:00:00"
}
```

#### Get All Users
```
GET /users
Response: 200 OK
[{...}, {...}]
```

#### Get User by ID
```
GET /users/{id}
Response: 200 OK
{...}
```

#### Get User by Phone
```
GET /users/phone/{phone}
Response: 200 OK
{...}
```

#### Update User
```
PUT /users/{id}
Content-Type: application/json

{
  "name": "John Smith Updated"
}

Response: 200 OK
{...}
```

#### Delete User
```
DELETE /users/{id}
Response: 204 No Content
```

---

### 3. USER-TENANT MAPPING

#### Assign User to Tenant
```
POST /user-tenants
Content-Type: application/json

{
  "user_id": 1,
  "tenant_id": 1
}

Response: 201 Created
{
  "id": 1,
  "user_id": 1,
  "tenant_id": 1
}
```

#### Get Users by Tenant
```
GET /user-tenants/tenant/{tenantId}
Response: 200 OK
[{...}, {...}]
```

#### Get Tenants by User
```
GET /user-tenants/user/{userId}
Response: 200 OK
[{...}, {...}]
```

#### Remove User from Tenant
```
DELETE /user-tenants/user/{userId}/tenant/{tenantId}
Response: 204 No Content
```

---

### 4. STORE MANAGEMENT (Tenant-Scoped)

#### Create Store
```
POST /tenants/{tenantId}/stores
Content-Type: application/json

{
  "name": "Main Store",
  "address": "123 Main St, City",
  "latitude": 40.7128,
  "longitude": -74.0060
}

Response: 201 Created
{
  "id": 1,
  "tenant_id": 1,
  "name": "Main Store",
  "address": "123 Main St, City",
  "latitude": 40.7128,
  "longitude": -74.0060,
  "is_active": true
}
```

#### Get All Stores for Tenant
```
GET /tenants/{tenantId}/stores
Response: 200 OK
[{...}, {...}]
```

#### Get Active Stores for Tenant
```
GET /tenants/{tenantId}/stores/active
Response: 200 OK
[{...}, {...}]
```

#### Get Store by ID
```
GET /tenants/{tenantId}/stores/{storeId}
Response: 200 OK
{...}
```

#### Update Store
```
PUT /tenants/{tenantId}/stores/{storeId}
Content-Type: application/json

{
  "name": "Updated Store Name",
  "is_active": false
}

Response: 200 OK
{...}
```

#### Delete Store
```
DELETE /tenants/{tenantId}/stores/{storeId}
Response: 204 No Content
```

---

### 5. CATEGORY MANAGEMENT (Tenant-Scoped)

#### Create Category
```
POST /tenants/{tenantId}/categories
Content-Type: application/json

{
  "name": "Electronics",
  "image_url": "https://...",
  "parent_id": null
}

Response: 201 Created
{
  "id": 1,
  "tenant_id": 1,
  "name": "Electronics",
  "image_url": "https://...",
  "parent_id": null
}
```

#### Get Root Categories
```
GET /tenants/{tenantId}/categories/root
Response: 200 OK
[{...}, {...}]
```

#### Get Sub-Categories
```
GET /tenants/{tenantId}/categories/{categoryId}/sub-categories
Response: 200 OK
[{...}, {...}]
```

#### Get All Categories for Tenant
```
GET /tenants/{tenantId}/categories
Response: 200 OK
[{...}, {...}]
```

#### Update Category
```
PUT /tenants/{tenantId}/categories/{categoryId}
Content-Type: application/json

{
  "name": "Updated Category"
}

Response: 200 OK
{...}
```

#### Delete Category
```
DELETE /tenants/{tenantId}/categories/{categoryId}
Response: 204 No Content
```

---

### 6. PRODUCT MANAGEMENT (Tenant-Scoped)

#### Create Product
```
POST /tenants/{tenantId}/products
Content-Type: application/json

{
  "name": "Laptop",
  "description": "High performance laptop",
  "category_id": 1,
  "image_url": "https://...",
  "brand": "Dell"
}

Response: 201 Created
{
  "id": 1,
  "tenant_id": 1,
  "name": "Laptop",
  "description": "High performance laptop",
  "category_id": 1,
  "image_url": "https://...",
  "brand": "Dell"
}
```

#### Get All Products for Tenant
```
GET /tenants/{tenantId}/products
Response: 200 OK
[{...}, {...}]
```

#### Get Products by Category
```
GET /tenants/{tenantId}/products/category/{categoryId}
Response: 200 OK
[{...}, {...}]
```

#### Get Product by ID
```
GET /tenants/{tenantId}/products/{productId}
Response: 200 OK
{...}
```

#### Update Product
```
PUT /tenants/{tenantId}/products/{productId}
Content-Type: application/json

{
  "name": "Updated Laptop",
  "brand": "HP"
}

Response: 200 OK
{...}
```

#### Delete Product
```
DELETE /tenants/{tenantId}/products/{productId}
Response: 204 No Content
```

---

### 7. STORE-PRODUCT INVENTORY (Tenant-Scoped)

#### Add Product to Store
```
POST /tenants/{tenantId}/store-products
Content-Type: application/json

{
  "store_id": 1,
  "product_id": 1,
  "price": 999.99,
  "stock": 50
}

Response: 201 Created
{
  "id": 1,
  "tenant_id": 1,
  "store_id": 1,
  "product_id": 1,
  "price": 999.99,
  "stock": 50,
  "is_available": true
}
```

#### Get Products in Store
```
GET /tenants/{tenantId}/store-products/store/{storeId}
Response: 200 OK
[{...}, {...}]
```

#### Get Stores with Product
```
GET /tenants/{tenantId}/store-products/product/{productId}
Response: 200 OK
[{...}, {...}]
```

#### Get Store-Product by ID
```
GET /tenants/{tenantId}/store-products/{storeProductId}
Response: 200 OK
{...}
```

#### Update Stock/Price
```
PUT /tenants/{tenantId}/store-products/{storeProductId}
Content-Type: application/json

{
  "price": 899.99,
  "stock": 40
}

Response: 200 OK
{...}
```

#### Remove Product from Store
```
DELETE /tenants/{tenantId}/store-products/{storeProductId}
Response: 204 No Content
```

---

### 8. ORDER MANAGEMENT (Tenant-Scoped)

#### Create Order
```
POST /tenants/{tenantId}/orders
Content-Type: application/json

{
  "user_id": 1,
  "store_id": 1,
  "total_amount": 1999.99,
  "status": "pending",
  "payment_method": "card",
  "payment_status": "unpaid"
}

Response: 201 Created
{
  "id": 1,
  "tenant_id": 1,
  "user_id": 1,
  "store_id": 1,
  "total_amount": 1999.99,
  "status": "pending",
  "payment_method": "card",
  "payment_status": "unpaid",
  "created_at": "2024-03-30T10:00:00"
}
```

#### Get All Orders for Tenant
```
GET /tenants/{tenantId}/orders
Response: 200 OK
[{...}, {...}]
```

#### Get User's Orders
```
GET /tenants/{tenantId}/orders/user/{userId}
Response: 200 OK
[{...}, {...}]
```

#### Get Store's Orders
```
GET /tenants/{tenantId}/orders/store/{storeId}
Response: 200 OK
[{...}, {...}]
```

#### Get Order by ID
```
GET /tenants/{tenantId}/orders/{orderId}
Response: 200 OK
{...}
```

#### Update Order Status
```
PUT /tenants/{tenantId}/orders/{orderId}
Content-Type: application/json

{
  "status": "completed",
  "payment_status": "paid"
}

Response: 200 OK
{...}
```

#### Delete Order
```
DELETE /tenants/{tenantId}/orders/{orderId}
Response: 204 No Content
```

---

### 9. ORDER ITEMS

#### Add Item to Order
```
POST /order-items
Content-Type: application/json

{
  "order_id": 1,
  "product_id": 1,
  "quantity": 2,
  "price": 999.99
}

Response: 201 Created
{
  "id": 1,
  "order_id": 1,
  "product_id": 1,
  "quantity": 2,
  "price": 999.99
}
```

#### Get Order Items
```
GET /order-items/order/{orderId}
Response: 200 OK
[{...}, {...}]
```

#### Update Order Item
```
PUT /order-items/{orderItemId}
Content-Type: application/json

{
  "quantity": 3
}

Response: 200 OK
{...}
```

#### Remove Item from Order
```
DELETE /order-items/{orderItemId}
Response: 204 No Content
```

---

## Error Handling

### Common Error Responses

#### 400 Bad Request
```json
{
  "timestamp": "2024-03-30T10:00:00",
  "status": 400,
  "error": "Invalid Argument",
  "message": "Invalid input provided",
  "path": "/api/v1/tenants"
}
```

#### 404 Not Found
```json
{
  "timestamp": "2024-03-30T10:00:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Tenant not found with id: '999'",
  "path": "/api/v1/tenants/999"
}
```

#### 403 Forbidden (Tenant Access Denied)
```json
{
  "timestamp": "2024-03-30T10:00:00",
  "status": 403,
  "error": "Tenant Access Denied",
  "message": "Access denied: Tenant 1 is not authorized to access resource belonging to Tenant 2",
  "path": "/api/v1/tenants/2/stores/1"
}
```

#### 500 Internal Server Error
```json
{
  "timestamp": "2024-03-30T10:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Database connection failed",
  "path": "/api/v1/tenants"
}
```

---

## Key Features

### Multi-Tenant Isolation
- All tenant-scoped resources are validated for tenant ownership
- Queries are filtered by tenant_id automatically
- Unauthorized access attempts return 403 Forbidden

### Transaction Management
- All service methods are transactional
- Cascading deletes for related entities
- Optimistic locking support for concurrency

### Data Relationships
```
Tenant (1) --- (M) Users (via UserTenant)
       (1) --- (M) Stores
       (1) --- (M) Categories
       (1) --- (M) Products
       (1) --- (M) StoreProducts
       (1) --- (M) Orders

Category (1) --- (M) SubCategories
        (1) --- (M) Products

Product (1) --- (M) StoreProducts
       (1) --- (M) OrderItems

Store (1) --- (M) StoreProducts
     (1) --- (M) Orders

User (1) --- (M) Orders

Order (1) --- (M) OrderItems
```

---

## Database Schema

See Liquibase changelog files in `src/main/resources/db/changelog/`

---

## Project Structure

```
src/main/java/org/martpilot/
├── entity/              # JPA entities
├── repository/          # Data access layer
├── service/            # Business logic interfaces
├── service/impl/       # Business logic implementations
├── controller/         # REST endpoints
├── dto/               # Data transfer objects
├── exception/         # Custom exceptions & handlers
├── context/           # Multi-tenant context
└── config/            # Spring configurations
```

---

## Testing

Run tests with:
```bash
mvn test
```

---

## Building & Running

```bash
# Build
mvn clean package

# Run
mvn spring-boot:run
```

Visit `http://localhost:8080/api/v1/tenants` to test the API.

---

## Future Enhancements

- [ ] JWT Authentication & Authorization
- [ ] Rate Limiting
- [ ] API Versioning
- [ ] Batch Operations
- [ ] Full-text Search
- [ ] Caching with Redis
- [ ] Event-driven Architecture
- [ ] GraphQL API
- [ ] API Documentation with Swagger/OpenAPI


