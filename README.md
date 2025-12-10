# CoopCredit - Sistema de Gestión de Créditos Cooperativos

Sistema de microservicios para gestión de solicitudes de crédito con evaluación de riesgo integrada.

##  Arquitectura

- **credit-application**: Microservicio principal para gestión de afiliados y solicitudes de crédito
- **risk-central-mock-service**: Servicio de evaluación de riesgo crediticio
- **PostgreSQL**: Base de datos principal

##  Tecnologías

- **Java 21** (credit-application)
- **Java 17** (risk-central-mock-service)
- **Spring Boot 3.2.5**
- **Docker & Docker Compose**
- **PostgreSQL 15**
- **Flyway** (migraciones de BD)
- **MapStruct** (mapeo de objetos)
- **JWT** (autenticación)
- **Swagger/OpenAPI** (documentación API)

##  Requisitos Previos

- Docker y Docker Compose instalados
- Puertos disponibles: 5432 (PostgreSQL), 8080 (credit-application), 8081 (risk-central)

##  Instalación y Ejecución

### 1. Clonar el repositorio
```bash
git clone git@github.com:yxdth3/CoopCredit.git
cd CoopCredit
```

### 2. Construir y levantar los servicios
```bash
docker-compose build
docker-compose up -d
```

### 3. Verificar estado
```bash
docker-compose ps
```

Deberías ver 3 contenedores corriendo:
- `coopcredit-postgres` (healthy)
- `coopcredit-risk-central` (healthy)
- `coopcredit-credit-application` (healthy)

## 📡 Acceso a los Servicios

### Swagger UI
- **Credit Application**: http://localhost:8080/swagger-ui.html
- **Risk Central**: http://localhost:8081/swagger-ui.html


##  Autenticación

### Registrar Usuario
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123",
  "email": "admin@coopcredit.com",
  "roles": ["ADMIN"]
}
```

### Login
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Respuesta:** Token JWT para usar en las demás peticiones

##  Ejemplos de Uso

### Crear Afiliado
```bash
POST http://localhost:8080/api/affiliates
Authorization: Bearer {token}
Content-Type: application/json

{
  "document": "1234567890",
  "name": "Juan",
  "lastName": "Pérez",
  "email": "juan@example.com",
  "phoneNumber": "3001234567",
  "monthlyIncome": 5000000
}
```

### Crear Solicitud de Crédito
```bash
POST http://localhost:8080/api/credit-applications
Authorization: Bearer {token}
Content-Type: application/json

{
  "affiliateId": 1,
  "requestedAmount": 5000000,
  "termMonths": 36,
  "purpose": "Vivienda"
}
```

**Límites de validación:**
- Monto mínimo: $1,000,000
- Monto máximo: $9,000,000
- Plazo: 12-60 meses

### Evaluar Riesgo
```bash
POST http://localhost:8081/api/risk-evaluation
Content-Type: application/json

{
  "document": "1234567890"
}
```

##  Base de Datos

Las migraciones de Flyway se ejecutan automáticamente al iniciar:
- `V1__create_schema.sql` - Creación de tablas
- `V2__create_indexes.sql` - Índices para optimización
- `V3__insert_initial_data.sql` - Datos iniciales

##  Desarrollo

### Reconstruir un servicio específico
```bash
docker-compose build --no-cache credit-application
docker-compose up -d credit-application
```

### Ver logs
```bash
docker-compose logs -f credit-application
docker-compose logs -f risk-central
```

### Detener servicios
```bash
docker-compose down
```

### Detener y eliminar volúmenes
```bash
docker-compose down -v
```

##  Variables de Entorno

Las variables se configuran en `docker-compose.yml`:

### Credit Application
- `SPRING_PROFILES_ACTIVE=prod`
- `DATABASE_URL=jdbc:postgresql://postgres:5432/coopcredit_db`
- `DATABASE_USERNAME=postgres`
- `DATABASE_PASSWORD=postgres`
- `RISK_CENTRAL_URL=http://risk-central:8081`

##  Arquitectura Hexagonal

El proyecto sigue Clean Architecture con:
- **Domain**: Modelos de negocio y casos de uso
- **Application**: Servicios de aplicación y DTOs
- **Infrastructure**: Adaptadores (REST, JPA, etc.)

##  Testing

Ejecutar tests:
```bash
cd credit-application
mvn test
```

