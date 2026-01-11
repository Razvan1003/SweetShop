# Sweet Shop eCommerce (Spring Boot)

Proiect universitar pentru aplicatie eCommerce (vanzare dulciuri). Backend Spring Boot cu arhitectura stratificata, securitate JWT pentru API si UI Thymeleaf + Bootstrap 5.

## Tehnologii
- Java 17 + Spring Boot 3
- Spring Web, Spring Data JPA, Spring Security
- JWT
- PostgreSQL
- Thymeleaf + Bootstrap 5
- Docker + docker-compose

## Arhitectura
- Controller
- Service
- Repository

Pachete:
- `com.sweetshop.controller`
- `com.sweetshop.service`
- `com.sweetshop.repository`
- `com.sweetshop.entity`

## Roluri si permisiuni
- ROLE_ADMIN
  - CRUD produse
  - acces la /admin/**
- ROLE_CLIENT
  - vizualizare produse
  - gestionare cos
  - plasare comenzi
  - gestionare adrese

## Conturi demo
- ADMIN: `admin@sweetshop.com` / `Admin1234`
- CLIENT: `client@sweetshop.com` / `Client123!`
- Adresa demo asociata contului client

## Flux cos -> comanda
1) Clientul adauga produse in cos.
2) In cos selecteaza adresa (obligatoriu).
3) Se plaseaza comanda, apoi se poate vizualiza la /me/orders.

## UI (Thymeleaf)
- `GET /` pagina principala
- `GET /menu` catalog produse (search + filtru categorie)
- `GET /product/{id}` detalii produs + add to cart
- `GET/POST /login`, `GET/POST /register`
- `GET /cart` cos
- `GET /me/orders` lista comenzi client
- `GET/POST /me/addresses` adrese client
- `GET /admin/products` lista produse
- `GET/POST /admin/products/new`
- `GET/POST /admin/products/{id}/edit`
- `POST /admin/products/{id}/delete`

## API REST (JSON)
Base path: `/api`

Auth:
- `POST /api/auth/register`
- `POST /api/auth/login`

Products:
- `GET /api/products`
- `GET /api/products/{id}`
- `POST /api/products` (ADMIN)
- `PUT /api/products/{id}` (ADMIN)
- `DELETE /api/products/{id}` (ADMIN)

Categories:
- `GET /api/categories`

Orders:
- `POST /api/orders` (CLIENT)
- `GET /api/orders/me` (CLIENT)
Body `POST /api/orders`: `productId`, `quantity`, `addressId`

## Rulare cu Docker
```bash
docker compose --env-file docker-compose-sweetshop/.env -f docker-compose-sweetshop/docker-compose.yml up --build
```

Acces:
- UI: `http://localhost:8080/`
- API: `http://localhost:8080/api/products`

## Configurare (cloud/VM)
Aplicatia foloseste variabile de mediu:
- `DB_URL`
- `DB_USER`
- `DB_PASS`
- `JWT_SECRET`

Deployment pe VM:
- instaleaza Docker + Docker Compose
- seteaza valorile din `docker-compose-sweetshop/.env`
- ruleaza comanda Docker de mai sus
- expune portul 8080

## Structura proiectului
```
backend/
  src/main/java/com/sweetshop/...
  src/main/resources/...

docker-compose-sweetshop/
  Dockerfile
  docker-compose.yml
  .env

README.md
```
