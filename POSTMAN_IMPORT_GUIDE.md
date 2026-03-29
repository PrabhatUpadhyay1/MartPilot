# 🔧 How to Import & Use MartPilot Postman Collection

## ⚡ Quick Start (2 Minutes)

### Step 1: Get the Collection File
File: `MartPilot_SaaS_API.postman_collection.json` (in your project root)

### Step 2: Import into Postman
1. Open **Postman** application
2. Click **Import** button (top-left corner)
3. Select the collection JSON file
4. Click **Import**

### Step 3: Start Testing
1. Go to the **Tenants** folder
2. Click **Create Tenant** request
3. Click **Send** button
4. You should see a 201 response with the created tenant

✅ **Done!** Your collection is ready to use.

---

## 📖 Detailed Import Guide

### For Desktop Application

#### Method 1: Import File
```
1. Open Postman Desktop
2. Click "Import" (⬆️ icon, top-left)
3. Click "Upload Files"
4. Navigate to project folder
5. Select "MartPilot_SaaS_API.postman_collection.json"
6. Click "Import"
```

#### Method 2: Import from Link/Paste
```
1. Click "Import"
2. Click "Raw text" tab
3. Paste JSON content
4. Click "Continue" → "Import"
```

#### Method 3: Drag & Drop
```
1. Locate JSON file in file explorer
2. Drag file onto Postman window
3. Click "Import" in dialog
```

---

## 🔍 After Import - What You'll See

### Collection Structure
```
MartPilot SaaS API (Root)
├── 🏢 Tenants (7 requests)
├── 👤 Users (6 requests)
├── 🔗 User-Tenants (5 requests)
├── 🏪 Stores (6 requests)
├── 📁 Categories (7 requests)
├── 📦 Products (6 requests)
├── 🛍️ Store Products - Inventory (6 requests)
├── 🛒 Orders (6 requests)
└── 📋 Order Items (5 requests)
```

### Variables Tab
Click on the collection name → **Variables** tab

You'll see pre-configured values:
- base_url = http://localhost:8080/api/v1
- tenant_id = 1
- user_id = 1
- etc.

---

## ⚙️ Configure for Your Environment

### Update Base URL (if needed)
1. Click **MartPilot SaaS API** collection
2. Go to **Variables** tab
3. Change `base_url` value to your API URL
4. Examples:
   - Local: `http://localhost:8080/api/v1`
   - Dev: `https://api-dev.example.com/api/v1`
   - Staging: `https://api-staging.example.com/api/v1`

### Update Other Variables (if needed)
- `tenant_id` - Your test tenant ID (starts at 1)
- `user_id` - Your test user ID
- `store_id` - Your test store ID
- etc.

Default values work for local testing!

---

## 🚀 First Test Request

### Test That Everything Works
1. Click **Tenants** folder
2. Click **Get All Tenants** request
3. Verify request shows:
   ```
   GET http://localhost:8080/api/v1/tenants
   ```
4. Click **Send**
5. Should see:
   ```
   Status: 200 OK
   Body: [] (empty array if no tenants)
   ```

✅ If you see 200 OK, your setup is correct!

---

## 📋 Common Issues & Fixes

### Issue: "Unable to get any response"
**Cause**: API server not running  
**Fix**:
```bash
cd C:\Users\prabh\IdeaProjects\martpilot
mvn spring-boot:run
```

### Issue: "Cannot GET /api/v1/tenants"
**Cause**: Wrong base_url  
**Fix**:
1. Click collection
2. Variables tab
3. Update `base_url` to correct URL

### Issue: 404 Not Found
**Cause**: Resource doesn't exist  
**Fix**:
1. Create the resource first
2. Or use correct ID that exists

### Issue: 403 Forbidden
**Cause**: Wrong tenant_id for resource  
**Fix**:
1. Verify tenant_id matches resource owner
2. Check variable values

### Issue: "SyntaxError: Unexpected token"
**Cause**: Invalid JSON in request body  
**Fix**:
1. Click **Body** tab
2. Check JSON syntax
3. Use provided examples

---

## 🎯 Test Workflow

Follow this step-by-step to verify everything:

### Phase 1: Setup (Create Base Data)
```
1. POST /tenants - Create a tenant
   → Copy ID from response to {{tenant_id}}
2. POST /users - Create a user
   → Copy ID from response to {{user_id}}
3. POST /user-tenants - Assign user
4. POST /tenants/{{tenant_id}}/stores - Create store
   → Copy ID to {{store_id}}
```

### Phase 2: Catalog (Create Products)
```
5. POST /tenants/{{tenant_id}}/categories - Create category
   → Copy ID to {{category_id}}
6. POST /tenants/{{tenant_id}}/products - Create product
   → Copy ID to {{product_id}}
7. POST /tenants/{{tenant_id}}/store-products - Add to inventory
   → Copy ID to {{store_product_id}}
```

### Phase 3: Orders (Complete Order Flow)
```
8. POST /tenants/{{tenant_id}}/orders - Create order
   → Copy ID to {{order_id}}
9. POST /order-items - Add item to order
   → Copy ID to {{order_item_id}}
10. PUT /tenants/{{tenant_id}}/orders/{{order_id}} - Update status
```

✅ If all 10 steps succeed, your API is working perfectly!

---

## 📝 How to Copy IDs from Responses

### Method 1: Copy-Paste
1. Send request
2. Look at **Body** tab in response
3. Find the `id` field
4. Copy the number
5. Go to **Variables** tab
6. Paste into the appropriate variable

### Method 2: Postman's Set Variable Feature
1. Send request
2. Right-click on ID value in response
3. Select "Set: variable_name"
4. Confirm

---

## 🔑 Understanding Variables

### What are Variables?
Placeholders that get replaced in requests:
- `{{base_url}}` → `http://localhost:8080/api/v1`
- `{{tenant_id}}` → `1`
- `{{user_id}}` → `1`

### How Variables are Used
```
Before: GET /tenants/{{tenant_id}}/stores
After:  GET /tenants/1/stores (if tenant_id=1)
```

### Change Variables Anytime
```
Collection → Variables → Edit value → Save
```

### Use in Request Body
```json
{
  "tenant_id": "{{tenant_id}}",
  "user_id": "{{user_id}}"
}
```

---

## 📚 Documentation Files Reference

### 1. **POSTMAN_COLLECTION_GUIDE.md** (START HERE)
- Step-by-step import guide
- Collection overview
- How to use variables
- Quick start tutorials
- Common patterns
- Error handling

**Read This First!** ← Most comprehensive

### 2. **API_QUICK_REFERENCE.md** (DURING TESTING)
- All endpoints at a glance
- Request templates
- Status code reference
- Test sequence
- Debug checklist

**Reference During Testing** ← Quick lookup

### 3. **API_DOCUMENTATION.md** (FOR DETAILS)
- Complete endpoint documentation
- Request/response examples
- Error response formats
- Data relationships

**Read for Implementation Details** ← In-depth reference

### 4. **IMPLEMENTATION_SUMMARY.md** (UNDERSTANDING CODE)
- Architecture overview
- Entity relationships
- Service patterns
- Design principles

**Read to Understand Architecture** ← Technical details

---

## ✅ Verification Checklist

After importing, verify:

- [ ] Collection imported successfully
- [ ] All 8 folders visible
- [ ] 50+ requests total
- [ ] Variables tab shows 11 variables
- [ ] base_url is set correctly
- [ ] GET /tenants returns 200 OK
- [ ] POST /tenants can create (201)
- [ ] Variables update correctly
- [ ] Response bodies shown correctly
- [ ] Status codes display properly

---

## 🎓 Tips for Using the Collection

### 1. Organize by Project
```
Create folders in Postman:
- Dev Testing
- Integration Testing
- Performance Testing
- Demo Data
```

### 2. Use Pre-request Scripts
```javascript
// Automatically add timestamp
pm.environment.set("timestamp", Date.now());
```

### 3. Write Tests
```javascript
// Verify status code
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});
```

### 4. Save Examples
```
Right-click response → Save as example
Helps document expected outputs
```

### 5. Use Collections for Onboarding
```
Share with new team members
They can immediately test API
Reduces setup time
```

---

## 🔄 Updating the Collection

### If API Changes
1. Update the JSON file manually
2. Right-click collection → Update
3. Or reimport

### Keep Documentation Updated
After changing API:
1. Update this collection
2. Update documentation files
3. Communicate changes to team

---

## 🌐 Multi-Environment Support

### Setup Different Environments
```
Postman → Environments → Create New

Dev Environment:
  base_url: http://localhost:8080/api/v1
  tenant_id: 1

Staging Environment:
  base_url: https://api-staging.example.com/api/v1
  tenant_id: 100

Production Environment:
  base_url: https://api.example.com/api/v1
  tenant_id: 1
```

### Switch Between Environments
- Click dropdown in top-right (next to eye icon)
- Select environment
- Variables change automatically

---

## 📊 Collection Statistics

```
Total Endpoints: 50+
Request Methods:
  GET:    27 endpoints
  POST:   13 endpoints
  PUT:    7 endpoints
  DELETE: 7 endpoints

Organized Folders: 8
Pre-configured Variables: 11
Response Examples: 50+
HTTP Status Codes Covered: 6
```

---

## 🆘 Getting Help

### If Something Doesn't Work

1. **Check Status Code**
   - 200 = Success
   - 201 = Created
   - 404 = Not found
   - 403 = Forbidden

2. **Read Error Message**
   - Response body explains the problem
   - Use message to troubleshoot

3. **Review Documentation**
   - POSTMAN_COLLECTION_GUIDE.md
   - API_DOCUMENTATION.md
   - API_QUICK_REFERENCE.md

4. **Check Server**
   ```bash
   curl http://localhost:8080/api/v1/tenants
   ```

5. **Verify Database**
   - MySQL running?
   - Tables created by Liquibase?
   - Correct credentials?

---

## 📱 Share with Team

### Export Collection
```
Right-click collection → Share collection → Export
Share the JSON file via email/Git
```

### Team Import
```
Team members follow same import steps
Everyone has identical collection
Use shared Variables for consistency
```

---

## 🎉 You're Ready!

Your Postman collection is now:
- ✅ Imported and configured
- ✅ Organized into 8 folders
- ✅ Pre-configured with variables
- ✅ Ready for immediate testing
- ✅ Documented with examples
- ✅ Shareable with team

### Next: Start Testing!
1. Open POSTMAN_COLLECTION_GUIDE.md
2. Follow the Quick Start Guide
3. Create your first tenant
4. Complete the workflow
5. Explore the API

---

## 📞 Questions?

Refer to:
1. **POSTMAN_COLLECTION_GUIDE.md** - Most comprehensive
2. **API_QUICK_REFERENCE.md** - Quick answers
3. **API_DOCUMENTATION.md** - Detailed reference

---

**Happy API Testing! 🚀**

Collection Version: 2.1  
Updated: March 30, 2024  
Status: Production Ready


