```
kubectl exec -it curl-test -n microservices -- sh
```

# API Gateway - cURL Collection

# =========================
# AUTH SERVICE
# =========================

curl -X POST http://gateway-service:8080/api/v1/auth/register -H "Content-Type: application/json" -d '{"username":"testUser","email":"test-user@email.com","password":"Password@123"}'

curl -X POST http://gateway-service:8080/api/v1/auth/login -H "Content-Type: application/json" -d '{"username":"testUser","password":"Password@123"}'


# =========================
# PRODUCT SERVICE
# =========================

curl -X POST http://gateway-service:8080/api/v1/products -H "Content-Type: application/json" -d '{"productName":"iPhone 16","price":69999.99,"quantity":10}'

curl http://gateway-service:8080/api/v1/products

curl http://gateway-service:8080/api/v1/products/{productId}

curl -X PUT http://gateway-service:8080/api/v1/products/{productId} -H "Content-Type: application/json" -d '{"productName":"iPhone 16","price":67999.99,"quantity":10}'

curl -X DELETE http://gateway-service:8080/api/v1/products/{productId}


# =========================
# RESERVE PRODUCT
# =========================

curl -X PATCH http://gateway-service:8080/api/v1/products/{productId}/reserve -H "Content-Type: application/json" -d '{"quantity":2}'

curl -X PATCH http://gateway-service:8080/api/v1/products/f5f5495b-f76d-45b8-9391-4ed74e983b4b/reserve -H "Content-Type: application/json" -d '{"quantity":2}'


# =========================
# ORDER SERVICE
# =========================

curl -X POST http://gateway-service:8080/api/v1/orders -H "Content-Type: application/json" -d '{"userName":"testUser","productId":"f5f5495b-f76d-45b8-9391-4ed74e983b4b","quantity":2}'

curl http://gateway-service:8080/api/v1/orders

curl http://gateway-service:8080/api/v1/orders/{orderId}


# =========================
# ERROR / VALIDATION TESTS
# =========================

# Product not found
curl http://gateway-service:8080/api/v1/products/11111111-1111-1111-1111-111111111111

# Order with nonexistent product
curl -X POST http://gateway-service:8080/api/v1/orders -H "Content-Type: application/json" -d '{"userName":"testUser","productId":"11111111-1111-1111-1111-111111111111","quantity":1}'

# Order with insufficient quantity
curl -X POST http://gateway-service:8080/api/v1/orders -H "Content-Type: application/json" -d '{"userName":"testUser","productId":"f5f5495b-f76d-45b8-9391-4ed74e983b4b","quantity":999}'