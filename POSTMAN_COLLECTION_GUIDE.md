# MartPilot SaaS API - Postman Collection Guide

## Overview
This Postman collection contains **50+ API endpoints** organized into **8 logical folders** covering all CRUD operations for your multi-tenant marketplace platform.

## 📥 How to Import the Collection

### Option 1: Import from File (Recommended)
1. Open **Postman** desktop application
2. Click **Import** button (top-left)
3. Select **MartPilot_SaaS_API.postman_collection.json** file
4. Click **Import** to add the collection

### Option 2: Import from Link
1. Open Postman
2. Click **Import**
3. Paste the JSON content directly
4. Click **Import**

---

## 🎯 Collection Structure

The collection is organized into 8 folders with complete CRUD operations:

### 📂 Folder Organization

```
MartPilot SaaS API
├── 🏢 Tenants (7 endpoints)
│   ├── Create Tenant
│   ├── Get All Tenants
│   ├── Get Active Tenants
│   ├── Get Tenant by ID
│   ├── Get Tenant by Subdomain
│   ├── Update Tenant
│   └── Delete Tenant
│
├── 👤 Users (6 endpoints)
│   ├── Create User
│   ├── Get All Users
│   ├── Get User by ID
│   ├── Get User by Phone
│   ├── Update User
│   └── Delete User
│
├── 🔗 User-Tenants (5 endpoints)
│   ├── Assign User to Tenant
│   ├── Get User's Tenants
│   ├── Get Tenant's Users
│   ├── Get User-Tenant Mapping
│   └── Remove User from Tenant
│
├── 🏪 Stores (6 endpoints)
│   ├── Create Store
│   ├── Get All Stores
│   ├── Get Active Stores
│   ├── Get Store by ID
│   ├── Update Store
│   └── Delete Store
│
├── 📁 Categories (7 endpoints)
│   ├── Create Root Category
│   ├── Create Subcategory
│   ├── Get All Categories
│   ├── Get Root Categories
│   ├── Get Subcategories
│   ├── Get Category by ID
│   ├── Update Category
│   └── Delete Category
│
├── 📦 Products (6 endpoints)
│   ├── Create Product
│   ├── Get All Products
│   ├── Get Products by Category
│   ├── Get Product by ID
│   ├── Update Product
│   └── Delete Product
│
├── 🛍️ Store Products - Inventory (6 endpoints)
│   ├── Add Product to Store
│   ├── Get Store Inventory
│   ├── Get Stores with Product
│   ├── Get Store-Product by ID
│   ├── Update Stock/Price
│   └── Remove Product from Store
│
├── 🛒 Orders (6 endpoints)
│   ├── Create Order
│   ├── Get All Orders
│   ├── Get User's Orders
│   ├── Get Store's Orders
│   ├── Get Order by ID
│   ├── Update Order Status
│   └── Delete Order
│
└── 📋 Order Items (5 endpoints)
    ├── Add Item to Order
    ├── Get Order Items
    ├── Get Order Item by ID
    ├── Update Order Item
    └── Remove Item from Order
```

---

## 🔧 Environment Variables

The collection includes **11 pre-configured variables**:

| Variable | Default Value | Description |
|----------|---------------|-------------|
| `{{base_url}}` | http://localhost:8080/api/v1 | API base URL |
| `{{tenant_id}}` | 1 | Tenant ID for tenant-scoped operations |
| `{{user_id}}` | 1 | User ID |
| `{{store_id}}` | 1 | Store ID |
| `{{category_id}}` | 1 | Category ID |
| `{{product_id}}` | 1 | Product ID |
| `{{store_product_id}}` | 1 | Store-Product (inventory) ID |
| `{{order_id}}` | 1 | Order ID |
| `{{order_item_id}}` | 1 | Order Item ID |
| `{{subdomain}}` | bestmart | Tenant subdomain |
| `{{phone}}` | +9876543210 | User phone number |

### How to Edit Variables
1. Click **MartPilot SaaS API** collection
2. Go to **Variables** tab
3. Update any values as needed
4. Changes apply to all requests using those variables

---

## 📝 HTTP Methods & Status Codes

### Request Methods
- **GET** → Retrieve data (read-only)
- **POST** → Create new resources (HTTP 201)
- **PUT** → Update existing resources (HTTP 200)
- **DELETE** → Remove resources (HTTP 204)

### Expected Response Codes
| Code | Meaning |
|------|---------|
| **200** | OK - Request successful |
| **201** | Created - Resource created successfully |
| **204** | No Content - Delete successful (empty body) |
| **400** | Bad Request - Invalid input |
| **403** | Forbidden - Tenant access denied |
| **404** | Not Found - Resource doesn't exist |
| **500** | Server Error - Internal error |

---

## 🚀 Quick Start Guide

### Step 1: Create a Tenant
**Request:** `POST /tenants`
```json
{
  "business_name": "My Store",
  "subdomain": "mystore",
  "custom_domain": "mystore.com",
  "owner_name": "John Doe",
  "phone": "+1234567890",
  "email": "john@mystore.com",
  "subscription_plan": "premium"
}
```
**Response:** Returns `tenant_id` (save this for later requests)

### Step 2: Create Users
**Request:** `POST /users`
```json
{
  "name": "John Smith",
  "phone": "+9876543210"
}
```
**Response:** Returns `user_id`

### Step 3: Assign User to Tenant
**Request:** `POST /user-tenants`
```json
{
  "user_id": 1,
  "tenant_id": 1
}
```

### Step 4: Create Stores
**Request:** `POST /tenants/{{tenant_id}}/stores`
```json
{
  "name": "Main Store",
  "address": "123 Main St",
  "latitude": 40.7128,
  "longitude": -74.0060
}
```
**Response:** Returns `store_id`

### Step 5: Create Categories
**Request:** `POST /tenants/{{tenant_id}}/categories`
```json
{
  "name": "Electronics",
  "image_url": "https://example.com/electronics.jpg",
  "parent_id": null
}
```
**Response:** Returns `category_id`

### Step 6: Create Products
**Request:** `POST /tenants/{{tenant_id}}/products`
```json
{
  "name": "Laptop",
  "description": "High-performance laptop",
  "category_id": 1,
  "image_url": "https://example.com/laptop.jpg",
  "brand": "Dell"
}
```
**Response:** Returns `product_id`

### Step 7: Add Products to Store Inventory
**Request:** `POST /tenants/{{tenant_id}}/store-products`
```json
{
  "store_id": 1,
  "product_id": 1,
  "price": 999.99,
  "stock": 50
}
```

### Step 8: Create an Order
**Request:** `POST /tenants/{{tenant_id}}/orders`
```json
{
  "user_id": 1,
  "store_id": 1,
  "total_amount": 1999.98,
  "status": "pending",
  "payment_method": "card",
  "payment_status": "unpaid"
}
```
**Response:** Returns `order_id`

### Step 9: Add Items to Order
**Request:** `POST /order-items`
```json
{
  "order_id": 1,
  "product_id": 1,
  "quantity": 2,
  "price": 999.99
}
```

### Step 10: Update Order Status
**Request:** `PUT /tenants/{{tenant_id}}/orders/{{order_id}}`
```json
{
  "status": "completed",
  "payment_status": "paid"
}
```

---

## 🔐 Multi-Tenant Security

### Tenant Isolation
- All tenant-scoped endpoints require `{{tenant_id}}` in the URL path
- The API validates that the resource belongs to the specified tenant
- Attempting to access another tenant's resources returns **403 Forbidden**

### Tenant-Scoped Endpoints
These endpoints include tenant ID in the path:
- `/tenants/{{tenant_id}}/stores`
- `/tenants/{{tenant_id}}/categories`
- `/tenants/{{tenant_id}}/products`
- `/tenants/{{tenant_id}}/store-products`
- `/tenants/{{tenant_id}}/orders`

### Shared Endpoints
These endpoints are NOT tenant-scoped (cross-tenant):
- `/tenants` - Tenant management
- `/users` - User management
- `/user-tenants` - User-Tenant mappings

---

## 📊 Common Usage Patterns

### Pattern 1: Complete Order Flow
```
1. Create Tenant → tenant_id
2. Create User → user_id
3. Assign User to Tenant
4. Create Store → store_id
5. Create Category → category_id
6. Create Product → product_id
7. Add Product to Store Inventory → store_product_id
8. Create Order → order_id
9. Add Items to Order
10. Update Order Status
```

### Pattern 2: Multi-Store Setup
```
1. Create Tenant
2. Create Multiple Stores (different locations)
3. Create Products (shared across stores)
4. Add same Product to multiple stores (different prices/stock)
5. View inventory per store
```

### Pattern 3: Category Hierarchy
```
1. Create Root Category (Electronics)
   └── Create Subcategory (Laptops)
       └── Create another Subcategory (Gaming Laptops)
2. Create Products under each category
3. Query products by category
```

### Pattern 4: User Management
```
1. Create User (global)
2. Assign to Tenant 1
3. Assign to Tenant 2
4. View User's Tenants
5. Remove from Tenant 1
```

---

## 🔍 Query Examples

### Get Specific Tenant's Data
Replace `{{tenant_id}}` in variable tab and use endpoints:
- `GET /tenants/{{tenant_id}}/stores` - All stores
- `GET /tenants/{{tenant_id}}/categories` - All categories
- `GET /tenants/{{tenant_id}}/products` - All products

### Get User's Tenant Access
```
GET /user-tenants/user/{{user_id}}
```
Shows all tenants assigned to user

### Get Store's Inventory
```
GET /tenants/{{tenant_id}}/store-products/store/{{store_id}}
```
Shows all products with prices in specific store

### Get Product's Store Availability
```
GET /tenants/{{tenant_id}}/store-products/product/{{product_id}}
```
Shows all stores carrying product + prices

---

## ✅ Request Checklist

Before sending a request, verify:

- [ ] **Correct HTTP Method** (GET, POST, PUT, DELETE)
- [ ] **Correct URL** (check {{variable}} replacements)
- [ ] **Content-Type Header** (application/json for POST/PUT)
- [ ] **Request Body** (JSON format for POST/PUT)
- [ ] **Required Fields** in body
- [ ] **Variable Values** updated correctly
- [ ] **Server Running** (http://localhost:8080 accessible)

---

## 🐛 Error Handling

### Error Response Format
```json
{
  "timestamp": "2024-03-30T10:00:00",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Tenant not found with id: '999'",
  "path": "/api/v1/tenants/999"
}
```

### Common Errors

| Error | Cause | Solution |
|-------|-------|----------|
| **404 Not Found** | Resource doesn't exist | Verify ID is correct, resource was created |
| **403 Forbidden** | Wrong tenant ID | Ensure tenant_id matches resource owner |
| **400 Bad Request** | Invalid JSON/data | Check request body format & required fields |
| **500 Server Error** | Database issue | Check server logs, verify DB connection |

---

## 📚 Additional Resources

- **API Documentation**: See `API_DOCUMENTATION.md`
- **Implementation Summary**: See `IMPLEMENTATION_SUMMARY.md`
- **Database Schema**: See `src/main/resources/db/changelog/`

---

## 💾 Saving Requests

Each endpoint includes:
- ✅ HTTP Method
- ✅ Complete URL with variables
- ✅ Request headers (Content-Type)
- ✅ Sample JSON payload (for POST/PUT)
- ✅ Expected response example
- ✅ Response status code

---

## 🔄 Workflow Examples

### Workflow 1: Setup New Tenant
```postman
1. POST /tenants (Create tenant)
   ↓ Copy tenant_id to variable
2. POST /users (Create user)
   ↓ Copy user_id to variable
3. POST /user-tenants (Assign user)
4. POST /tenants/{{tenant_id}}/stores (Create store)
   ↓ Copy store_id to variable
5. POST /tenants/{{tenant_id}}/categories (Create categories)
   ↓ Copy category_id to variable
6. POST /tenants/{{tenant_id}}/products (Create products)
   ↓ Copy product_id to variable
7. POST /tenants/{{tenant_id}}/store-products (Add inventory)
```

### Workflow 2: Process an Order
```postman
1. POST /tenants/{{tenant_id}}/orders (Create order)
   ↓ Copy order_id to variable
2. POST /order-items (Add product 1)
3. POST /order-items (Add product 2)
4. GET /order-items/order/{{order_id}} (Verify items)
5. PUT /tenants/{{tenant_id}}/orders/{{order_id}} (Update status to completed)
6. PUT /tenants/{{tenant_id}}/orders/{{order_id}} (Update payment status to paid)
```

### Workflow 3: Manage Multi-Store Pricing
```postman
1. GET /tenants/{{tenant_id}}/store-products/product/{{product_id}} (See stores)
2. PUT /tenants/{{tenant_id}}/store-products/{{sp_id_1}} (Update price for store 1)
3. PUT /tenants/{{tenant_id}}/store-products/{{sp_id_2}} (Update price for store 2)
4. GET /tenants/{{tenant_id}}/store-products/store/{{store_id}} (Verify changes)
```

---

## 🎓 Tips & Best Practices

### 1. Use Variables
Instead of hardcoding IDs, use `{{variable_name}}` to make requests reusable

### 2. Copy IDs Quickly
When an endpoint returns an ID:
- Select the ID value
- Right-click → Set variable

### 3. Test in Order
Follow workflows step-by-step. Don't skip steps.

### 4. Save Test Data
Write down important IDs:
- tenant_id = 1
- user_id = 1
- store_id = 1

### 5. Check Status Codes
Verify response matches expected status code before proceeding

### 6. Read Error Messages
Error messages indicate what went wrong. Use them to debug.

### 7. Organize Custom Folders
Create project-specific folders for your test scenarios:
- "Dev Testing"
- "Integration Testing"
- "Load Testing"

---

## 🚀 Next Steps

1. **Import Collection** into Postman
2. **Update Variables** to match your environment
3. **Start with Quick Start Guide** (Step 1-5)
4. **Run Complete Workflow** for order management
5. **Explore Other Endpoints** and their responses
6. **Use as Frontend Development Reference**

---

## 📞 Support

If you encounter issues:
1. Check the error message in the response
2. Verify variable values are correct
3. Ensure server is running on localhost:8080
4. Check database connection
5. Review API documentation in `API_DOCUMENTATION.md`

---

## Summary

✅ **50+ API endpoints** organized in 8 folders  
✅ **Complete CRUD** for all 9 entities  
✅ **Multi-tenant** support with isolation  
✅ **Real request examples** with sample payloads  
✅ **Expected responses** for each endpoint  
✅ **Pre-configured variables** for easy testing  
✅ **Error handling** examples  
✅ **Complete workflows** for common scenarios  

**Ready to use! Import and start testing your APIs.**


