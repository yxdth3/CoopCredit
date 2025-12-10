# CoopCredit
CoopCredit, a savings and credit cooperative with branches in several cities across the country, currently manages the loan application and evaluation process efficiently, providing:

• Credit histories.

• Fast application approval.

• Risk assessments.

• Rapid credit reviews.

• Secure authentication and access control.

• Effective error handling and validation.

CoopCredit's management has built a comprehensive, modular, secure, scalable, and professionally designed hexagonal loan application system that allows it to operate effectively in real-world environments.

## Project structure
```
coopcredit-system/
├── credit-application-service/
│   ├── src/main/java/.../domain
│   ├── src/main/java/.../application
│   ├── src/main/java/.../infrastructure
│   ├── src/main/resources/db/migration
│   ├── Dockerfile
│   └── pom.xml
│
├── risk-central-mock-service/
│   ├── controller/
│   ├── dto/
│   ├── service/
│   ├── Dockerfile
│   └── pom.xml
│
├── docker-compose.yml
├── start.sh
├── start.bat
└── README.md
```

## Architecture
```
┌─────────────────────────────────────────────────────────────┐
│                    INFRASTRUCTURE LAYER                      │
│  ┌──────────────┐              ┌────────────────────────┐   │
│  │ REST API     │              │  Database (JPA)        │   │
│  │ Controllers  │              │  PostgreSQL            │   │
│  └──────┬───────┘              └────────┬───────────────┘   │
│         │                               │                    │
│         │ Input Adapters                │ Output Adapters    │
└─────────┼───────────────────────────────┼────────────────────┘
          │                               │
┌─────────▼───────────────────────────────▼────────────────────┐
│                    APPLICATION LAYER                          │
│         DTOs, Mappers, Security Services                      │
└───────────────────────────┬───────────────────────────────────┘
                            │
┌───────────────────────────▼───────────────────────────────────┐
│                       DOMAIN LAYER                            │
│  ┌────────────┐  ┌──────────────┐  ┌───────────────────┐    │
│  │  Entities  │  │  Use Cases   │  │  Ports            │    │
│  └────────────┘  └──────────────┘  └───────────────────┘    │
│         Pure Java - No Framework Dependencies                │
└──────────────────────────────────────────────────────────────┘
```
Risk central mock 
```


┌──────────────────────┐         ┌─────────────────────────┐
│  Credit Application  │◄───────►│   Risk Central Mock     │
│  Service (8080)      │  HTTP   │   Service (8081)        │
│                      │  REST   │                         │
└──────────┬───────────┘         └─────────────────────────┘
           │
           ▼
      PostgreSQL DB
```

## Technologies
### Backend

Java 17
Spring Boot
Web
JPA
Security
Validation
Actuator
Hibernate
Flyway

### Security

JWT
BCrypt
RBAC

### Database

PostgreSQL 

### Libraries

Lombok
MapStruct
Micrometer

### Testing

JUnit 5
Mockito
Testcontainers

### DevOps

Docker
Docker Compose
Maven

## Quick start
1. Clone the repo:
   ```
   git clone https://github.com/YOUR_USERNAME/coopcredit-system.git
   cd coopcredit-system
   ```
3. Start services:
   ```
   chmod +x start.sh
   ./start.sh
   ```
5. Access:
   ```
   API: http://localhost:8080
   Risk Central: http://localhost:8081
   Health: http://localhost:8080/actuator/health
   ```

## API Documentation

### Authentication

* Register -> POST /api/auth/register
* Login -> POST /api/auth/login

### Affiliates

* Create Affiliate ->  POST /api/affiliates
* Get by ID -> GET /api/affiliates/{id}
* List all -> GET /api/affiliates

### Credict application

* Create -> POST /api/credit-applications
* Evaluate -> POST /api/credit-applications/{id}/evaluate
* Get pending -> GET /api/credit-applications/pending
* Risk central mock ->  POST http://localhost:8081/api/risk-evaluation

## Testing
Run tests: mvn test
Integration: mvn verify
Testcontainers: mvn test -Dspring.profiles.active=test


## Docker deployment
* Build: docker-compose build
* Run: docker-compose up -d
* Logs: docker-compose logs -f
* Stop: docker-compose down

## Roles and permisions
```

| Role     | Permissions                     |
| -------- | ------------------------------- |
| ADMIN    | Full access                     |
| ANALISTA | Manage affiliates & evaluations |
| AFILIADO | Own data + create applications  |
```

## Default users
```

| Username  | Password | Roles           |
| --------- | -------- | --------------- |
| admin     | admin123 | ADMIN, ANALISTA |
| analyst   | admin123 | ANALISTA        |
| affiliate | admin123 | AFILIADO        |
```
