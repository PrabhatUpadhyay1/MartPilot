# MartPilot API - Quick Reference Cheat Sheet

## 📋 Endpoints at a Glance

### 🏢 TENANTS
```
POST   /tenants                           → Create
GET    /tenants                           → List All
GET    /tenants/active                    → Active Only
GET    /tenants/{id}                      → Get One
GET    /tenants/subdomain/{subdomain}     → By Subdomain
PUT    /tenants/{id}                      → Update
DELETE /tenants/{id}                      → Delete
```

### 👤 USERS
```
POST   /users                    → Create
GET    /users                    → List All
GET    /users/{id}               → Get One
GET    /users/phone/{phone}      → By Phone
PUT    /users/{id}               → Update
DELETE /users/{id}               → Delete
```

### 🔗 USER-TENANTS
```
POST   /user-tenants                                      → Assign
GET    /user-tenants/user/{userId}                       → User's Tenants
GET    /user-tenants/tenant/{tenantId}                   → Tenant's Users
GET    /user-tenants/user/{userId}/tenant/{tenantId}     → Check
DELETE /user-tenants/user/{userId}/tenant/{tenantId}     → Remove
```

### 🏪 STORES (Tenant-Scoped)
```
POST   /tenants/{tenantId}/stores              → Create
GET    /tenants/{tenantId}/stores              → List All
GET    /tenants/{tenantId}/stores/active       → Active Only
GET    /tenants/{tenantId}/stores/{storeId}    → Get One
PUT    /tenants/{tenantId}/stores/{storeId}    → Update
DELETE /tenants/{tenantId}/stores/{storeId}    → Delete
```

### 📁 CATEGORIES (Tenant-Scoped)
```
POST   /tenants/{tenantId}/categories                          → Create
GET    /tenants/{tenantId}/categories                          → List All
GET    /tenants/{tenantId}/categories/root                     → Root Only
GET    /tenants/{tenantId}/categories/{categoryId}             → Get One
GET    /tenants/{tenantId}/categories/{categoryId}/sub-categories → Sub
PUT    /tenants/{tenantId}/categories/{categoryId}             → Update
DELETE /tenants/{tenantId}/categories/{categoryId}             → Delete
```

### 📦 PRODUCTS (Tenant-Scoped)
```
POST   /tenants/{tenantId}/products                            → Create
GET    /tenants/{tenantId}/products                            → List All
GET    /tenants/{tenantId}/products/{productId}                → Get One
GET    /tenants/{tenantId}/products/category/{categoryId}      → By Category
PUT    /tenants/{tenantId}/products/{productId}                → Update
DELETE /tenants/{tenantId}/products/{productId}                → Delete
```

### 🛍️ STORE PRODUCTS - INVENTORY (Tenant-Scoped)
```
POST   /tenants/{tenantId}/store-products                                    → Add
GET    /tenants/{tenantId}/store-products/store/{storeId}                    → Store Inv
GET    /tenants/{tenantId}/store-products/product/{productId}                → Product
GET    /tenants/{tenantId}/store-products/{storeProductId}                   → Get One
PUT    /tenants/{tenantId}/store-products/{storeProductId}                   → Update
DELETE /tenants/{tenantId}/store-products/{storeProductId}                   → Remove
```

### 🛒 ORDERS (Tenant-Scoped)
```
POST   /tenants/{tenantId}/orders                    → Create
GET    /tenants/{tenantId}/orders                    → List All
GET    /tenants/{tenantId}/orders/{orderId}          → Get One
GET    /tenants/{tenantId}/orders/user/{userId}      → User's Orders
GET    /tenants/{tenantId}/orders/store/{storeId}    → Store's Orders
PUT    /tenants/{tenantId}/orders/{orderId}          → Update Status
DELETE /tenants/{tenantId}/orders/{orderId}          → Delete
```

### 📋 ORDER ITEMS
```
POST   /order-items                              → Add Item
GET    /order-items/{orderItemId}                → Get One
GET    /order-items/order/{orderId}              → Get Items
PUT    /order-items/{orderItemId}                → Update
DELETE /order-items/{orderItemId}                → Remove
```

---

## 🔑 Variables Reference

| Variable | Default | Replace With |
|----------|---------|--------------|
| `{{base_url}}` | http://localhost:8080/api/v1 | Your API URL |
| `{{tenant_id}}` | 1 | Your tenant ID |
| `{{user_id}}` | 1 | Your user ID |
| `{{store_id}}` | 1 | Your store ID |
| `{{category_id}}` | 1 | Your category ID |
| `{{product_id}}` | 1 | Your product ID |
| `{{store_product_id}}` | 1 | Your inventory ID |
| `{{order_id}}` | 1 | Your order ID |
| `{{order_item_id}}` | 1 | Your order item ID |
| `{{subdomain}}` | bestmart | Your subdomain |
| `{{phone}}` | +9876543210 | Your phone |

---

## 📝 Request Templates

### CREATE TENANT
```json
{
  "business_name": "Store Name",
  "subdomain": "storename",
  "custom_domain": "storename.com",
  "owner_name": "Owner",
  "phone": "+1234567890",
  "email": "owner@store.com",
  "subscription_plan": "premium"
}
```

### CREATE USER
```json
{
  "name": "User Name",
  "phone": "+9876543210"
}
```

### CREATE STORE
```json
{
  "name": "Store Name",
  "address": "123 Street, City",
  "latitude": 40.7128,
  "longitude": -74.0060
}
```

### CREATE CATEGORY
```json
{
  "name": "Category Name",
  "image_url": "https://example.com/image.jpg",
  "parent_id": null
}
```

### CREATE PRODUCT
```json
{
  "name": "Product Name",
  "description": "Description",
  "category_id": 1,
  "image_url": "https://example.com/image.jpg",
  "brand": "Brand"
}
```

### ADD TO INVENTORY
```json
{
  "store_id": 1,
  "product_id": 1,
  "price": 99.99,
  "stock": 50
}
```

### CREATE ORDER
```json
{
  "user_id": 1,
  "store_id": 1,
  "total_amount": 199.99,
  "status": "pending",
  "payment_method": "card",
  "payment_status": "unpaid"
}
```

### ADD ORDER ITEM
```json
{
  "order_id": 1,
  "product_id": 1,
  "quantity": 2,
  "price": 99.99
}
```

---

## ⚡ HTTP Status Codes

| Code | Method | Meaning |
|------|--------|---------|
| 200 | GET, PUT | Success ✅ |
| 201 | POST | Created ✅ |
| 204 | DELETE | Deleted ✅ |
| 400 | Any | Invalid Request ❌ |
| 403 | Any | Access Denied ❌ |
| 404 | Any | Not Found ❌ |
| 500 | Any | Server Error ❌ |

---

## 🔄 Common Workflows

### Setup Complete Tenant
```
1. POST /tenants
2. POST /users
3. POST /user-tenants
4. POST /tenants/{id}/stores
5. POST /tenants/{id}/categories
6. POST /tenants/{id}/products
7. POST /tenants/{id}/store-products
```

### Place Complete Order
```
1. POST /tenants/{id}/orders
2. POST /order-items
3. POST /order-items (repeat for each item)
4. PUT /tenants/{id}/orders/{id}
```

### Update Prices Multi-Store
```
1. GET /tenants/{id}/store-products/product/{id}
2. PUT /tenants/{id}/store-products/{id} (store 1)
3. PUT /tenants/{id}/store-products/{id} (store 2)
```

---

## 🛡️ Multi-Tenant Rules

✅ **Tenant-Scoped** (include tenant_id in path):
- `/tenants/{id}/stores`
- `/tenants/{id}/categories`
- `/tenants/{id}/products`
- `/tenants/{id}/store-products`
- `/tenants/{id}/orders`

❌ **Cross-Tenant** (no tenant_id):
- `/tenants`
- `/users`
- `/user-tenants`
- `/order-items`

---

## 🎯 Test Sequence

```
✓ Create Tenant (save ID)
  ↓
✓ Create User (save ID)
  ↓
✓ Assign User to Tenant
  ↓
✓ Create Store (save ID)
  ↓
✓ Create Category (save ID)
  ↓
✓ Create Product (save ID)
  ↓
✓ Add to Inventory (save ID)
  ↓
✓ Create Order (save ID)
  ↓
✓ Add Order Item
  ↓
✓ Update Order Status ✓ COMPLETE
```

---

## 🔍 Debug Checklist

- [ ] Server running on localhost:8080?
- [ ] Database connected?
- [ ] Variables updated correctly?
- [ ] JSON syntax valid?
- [ ] Required fields included?
- [ ] Resource exists (for GET/PUT/DELETE)?
- [ ] Correct HTTP method?
- [ ] Content-Type: application/json header?
- [ ] Path variables filled in {{var}}?
- [ ] Tenant ID matches resource owner?

---

## 📱 Response Format

### Success (200/201)
```json
{
  "id": 1,
  "name": "value",
  "tenant_id": 1,
  ...
}
```

### Deleted (204)
```
(empty body)
```

### Error (400/403/404/500)
```json
{
  "timestamp": "2024-03-30T10:00:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Entity not found with id: '999'",
  "path": "/api/v1/..."
}
```

---

## 🚀 Pro Tips

1. **Batch Operations**: Add multiple products to store at once
2. **Export Results**: Save responses for documentation
3. **Use Pre-request Script**: Automatically generate timestamps
4. **Environment Variables**: Switch between dev/staging/prod
5. **Tests Tab**: Add assertions to validate responses
6. **Collections Sharing**: Share with team members
7. **Sync**: Use Postman Cloud to sync across devices

---

## 📞 Quick Links

- **Collection File**: `MartPilot_SaaS_API.postman_collection.json`
- **Full Guide**: `POSTMAN_COLLECTION_GUIDE.md`
- **API Docs**: `API_DOCUMENTATION.md`
- **Code**: `IMPLEMENTATION_SUMMARY.md`

---

**Last Updated**: March 30, 2024  
**API Version**: v1  
**Total Endpoints**: 50+  
**Organized Folders**: 8


