# Helpdesk API

REST API para gerenciamento de chamados técnicos (tickets), construída com Spring Boot. O sistema permite que clientes abram chamados, técnicos os gerenciem e administradores controlem usuários e acesso — tudo protegido por autenticação JWT stateless.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Runtime | Java 11 |
| Framework | Spring Boot 2.3.12 |
| Segurança | Spring Security + JWT (jjwt 0.7.0) |
| Persistência | Spring Data JPA / Hibernate |
| Banco (produção) | MySQL 8 |
| Banco (dev/test) | H2 in-memory |
| Build | Maven (Wrapper incluso) |
| Validação | Bean Validation (Jakarta) |

---

## Arquitetura

O projeto segue a arquitetura em camadas padrão do Spring Boot:

```
resources/          ← Controllers REST (@RestController)
service/            ← Regras de negócio
repository/         ← Interfaces JPA
domain/             ← Entidades, DTOs, Enums, Mappers, Validators
security/           ← Filtros JWT, UserDetails
config/             ← SecurityConfig, perfis dev/test
```

### Modelo de domínio

```
Person (abstrata, herança JOINED)
├── Technician  — tem lista de Tickets atribuídos
└── Customer    — tem lista de Tickets abertos

Ticket
├── → Customer   (ManyToOne)
├── → Technician (ManyToOne)
├── Priority: LOW | MEDIUM | HIGH
└── Status:   OPEN | PROCEEDING | CLOSED
```

`Person` usa herança `InheritanceType.JOINED`: existe uma tabela `person` com os campos comuns e tabelas separadas `technician` e `customer`. A autenticação consulta `PersonRepository` que acessa a tabela pai — ou seja, qualquer `Person` (Customer, Technician ou Admin) pode fazer login com o mesmo endpoint.

### Perfis de acesso

| Perfil | Código | Role Spring Security |
|---|---|---|
| ADMIN | 0 | `ROLE_ADMIN` |
| CUSTOMER | 1 | `ROLE_CUSTOMER` |
| TECHNICIAN | 2 | `ROLE_TECHNICIAN` |

Perfis são armazenados como inteiros numa tabela `profiles` (ElementCollection). Um usuário pode acumular múltiplos perfis — o seed de dados cria técnicos com `ADMIN` + `TECHNICIAN` simultaneamente.

---

## Autenticação

O fluxo é completamente stateless (sem sessão):

1. Cliente envia `POST /login` com `{ "email": "...", "password": "..." }`
2. `JWTAuthenticationFilter` autentica via `AuthenticationManager`
3. Em caso de sucesso, o token JWT é retornado no header `Authorization: Bearer <token>`
4. Requisições subsequentes incluem esse header
5. `JWTAuthorizationFilter` valida o token e popula o `SecurityContext`

O token é assinado com HS512. Expiração e secret são configurados por variáveis de ambiente (`JWT_SECRET`, `EXPIRATION_TIME`).

---

## Endpoints

### Autenticação

| Método | Path | Acesso | Descrição |
|---|---|---|---|
| POST | `/login` | Público | Retorna JWT no header `Authorization` |

### Clientes (`/customers`)

| Método | Path | Roles | Descrição |
|---|---|---|---|
| GET | `/customers` | ADMIN, TECHNICIAN | Lista todos os clientes |
| GET | `/customers/{id}` | ADMIN, TECHNICIAN | Busca cliente por ID |
| POST | `/customers` | ADMIN, TECHNICIAN | Cria novo cliente |
| PUT | `/customers/{id}` | ADMIN, TECHNICIAN | Atualiza cliente |
| DELETE | `/customers/{id}` | ADMIN, TECHNICIAN | Remove cliente (bloqueado se tiver tickets abertos) |

### Técnicos (`/technicians`)

| Método | Path | Roles | Descrição |
|---|---|---|---|
| GET | `/technicians` | ADMIN | Lista todos os técnicos |
| GET | `/technicians/{id}` | ADMIN | Busca técnico por ID |
| POST | `/technicians` | ADMIN | Cria novo técnico |
| PUT | `/technicians/{id}` | ADMIN | Atualiza técnico |
| DELETE | `/technicians/{id}` | ADMIN | Remove técnico (bloqueado se tiver tickets abertos) |

### Tickets (`/tickets`)

| Método | Path | Roles | Descrição |
|---|---|---|---|
| GET | `/tickets` | ADMIN, TECHNICIAN | Lista todos os tickets |
| GET | `/tickets/{id}` | ADMIN, TECHNICIAN | Busca ticket por ID |
| POST | `/tickets` | ADMIN, TECHNICIAN, CUSTOMER | Abre novo ticket |
| PUT | `/tickets/{id}` | ADMIN, TECHNICIAN | Atualiza ticket |

> Nota: ao mudar o status para `CLOSED`, o campo `closedAt` é preenchido automaticamente com a data atual. Se o status voltar de `CLOSED` para outro, `closedAt` é zerado.

---

## Regras de negócio relevantes

- **CPF e email são únicos no sistema** — a validação ocorre tanto na criação quanto na atualização (mas só se o campo mudou, evitando falso positivo ao salvar o próprio registro sem alterar esses campos).
- **Deleção protegida** — clientes e técnicos com tickets `OPEN` ou `PROCEEDING` não podem ser removidos.
- **Senhas** — armazenadas com BCrypt. Na atualização, se o valor enviado for idêntico ao hash já salvo, não é re-encodado (pattern de update sem alterar senha).
- **Formato de datas** — `Person.createdAt` usa `dd/MM/yyyy`; `Ticket.createdAt` e `closedAt` usam `MM/dd/yyyy`. Inconsistência presente no código original.

---

## Tratamento de erros

Todos os erros são interceptados por `ResourceExceptionHandler` (`@ControllerAdvice`) e retornam JSON estruturado:

```json
{
  "timestamp": "2025-10-03T16:58:00",
  "status": 404,
  "error": "Object Not Found",
  "message": "Object Not Found!",
  "path": "/customers/99"
}
```

Erros de validação (`@Valid`) retornam `400` com lista detalhada de campos inválidos.

---

## Configuração e execução

### Pré-requisitos

- Java 11+
- Maven 3.6+ (ou usar o wrapper `./mvnw`)
- MySQL 8 (apenas perfil `dev`)

### Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `JWT_SECRET` | Chave secreta para assinar o JWT |
| `EXPIRATION_TIME` | Tempo de expiração do token em milissegundos |
| `DB_DEV_URL` | JDBC URL do MySQL (apenas perfil `dev`) |
| `DB_DEV_USERNAME` | Usuário do banco (apenas perfil `dev`) |
| `DB_DEV_PASSWORD` | Senha do banco (apenas perfil `dev`) |

### Perfil `test` (H2 in-memory — sem configuração externa)

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```

O console H2 fica disponível em `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:testdb`, usuário: `sa`, sem senha).

### Perfil `dev` (MySQL)

```bash
export JWT_SECRET=minha-chave-secreta
export EXPIRATION_TIME=86400000
export DB_DEV_URL=jdbc:mysql://localhost:3306/helpdesk
export DB_DEV_USERNAME=root
export DB_DEV_PASSWORD=senha

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### Build para produção

```bash
./mvnw clean package -DskipTests
java -jar target/helpdesk-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Deploy no Heroku

O arquivo `system.properties` configura `java.runtime.version=11` para compatibilidade com o Heroku.

---

## Seed de dados

Ao iniciar com os perfis `dev` ou `test`, o sistema popula automaticamente o banco com:

- **12 técnicos** (3 deles com perfil adicional de ADMIN: Higor, Alan Turing, Guido van Rossum)
- **12 clientes** (2 deles com perfil ADMIN: Charles Babbage, Bill Gates)
- **10 tickets** em diferentes estados (OPEN, PROCEEDING, CLOSED)

Credenciais de exemplo para login:

| Email | Senha | Perfis |
|---|---|---|
| `higor@mail.com` | `123` | ADMIN + TECHNICIAN |
| `linus@mail.com` | `234` | TECHNICIAN |
| `torvalds@mail.com` | `abc` | CUSTOMER |

---

## Estrutura de pacotes

```
io.github.higur.helpdesk
├── config/
│   ├── SecurityConfig.java          # CORS, JWT filters, BCrypt bean
│   ├── DevConfig.java               # Seed automático no perfil dev
│   └── TestConfig.java              # Seed automático no perfil test
├── domain/
│   ├── Person.java                  # Entidade base (abstrata)
│   ├── Customer.java
│   ├── Technician.java
│   ├── Ticket.java
│   ├── enums/                       # Profile, Status, Priority
│   ├── dtos/                        # Request e Response DTOs por entidade
│   ├── mapping/                     # Mappers (sem MapStruct — manual)
│   └── validator/                   # Validações de unicidade (CPF, email)
├── repository/                      # Interfaces JpaRepository
├── resources/                       # Controllers REST + ExceptionHandler
├── security/
│   ├── JWTUtils.java
│   ├── JWTAuthenticationFilter.java
│   ├── JWTAuthorizationFilter.java
│   ├── CustomUserDetails.java
│   └── service/CustomUserDetailsService.java
└── service/                         # CustomerService, TechnicianService, TicketService, DBService
```
