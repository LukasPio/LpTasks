# LpTasks API

API REST para gerenciamento de tarefas com autenticação JWT, construída com **Kotlin + Spring Boot**.

---

## 🛠️ Tecnologias

- **Kotlin** + **Spring Boot 3.3**
- **PostgreSQL** — persistência de dados
- **Redis** — cache
- **Spring Security** + **JWT (Auth0)** — autenticação e autorização
- **Docker Compose** — infraestrutura local
- **Maven** — gerenciamento de dependências

---

## 📋 Pré-requisitos

- Java 21+
- Maven
- Docker e Docker Compose

---

## 🚀 Como rodar

**1. Suba a infraestrutura (banco e cache):**

```bash
docker-compose up -d
```

**2. Execute a aplicação:**

```bash
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

---

## 🔐 Autenticação

A API utiliza **JWT Bearer Token**. Para acessar os endpoints protegidos, inclua o header:

```
Authorization: Bearer <seu_token>
```

### Endpoints de autenticação

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| `POST` | `/app/auth/register` | Público | Cadastrar novo usuário |
| `POST` | `/app/auth/login` | Público | Realizar login e obter token |

#### Registrar usuário
```json
POST /app/auth/register
{
  "email": "usuario@email.com",
  "password": "senha123",
  "isAdmin": false
}
```

#### Login
```json
POST /app/auth/login
{
  "email": "usuario@email.com",
  "password": "senha123"
}
```
**Resposta:**
```json
{
  "body": { "token": "eyJhbGci..." },
  "message": "Successfully loged in",
  "statusCode": 200
}
```

---

## ✅ Endpoints de Tarefas

Todos os endpoints abaixo exigem autenticação.

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/app/tasks` | Listar todas as tarefas |
| `GET` | `/app/tasks/id?id={id}` | Buscar tarefa por ID |
| `GET` | `/app/tasks/title?taskTitle={title}` | Buscar tarefas por título |
| `GET` | `/app/tasks/category?category={category}` | Filtrar por categoria |
| `GET` | `/app/tasks/sortByPriority?sortOrder={asc\|desc}` | Ordenar por prioridade |
| `POST` | `/app/tasks` | Criar uma ou mais tarefas |
| `PUT` | `/app/tasks/{id}` | Atualizar uma tarefa |
| `DELETE` | `/app/tasks/{id}` | Deletar uma tarefa |

### Corpo da requisição (criar/atualizar)

```json
[
  {
    "title": "Estudar Kotlin",
    "description": "Revisar coroutines e flows",
    "category": "STUDY",
    "priority": "HIGH"
  }
]
```

### Categorias disponíveis

| Valor |
|-------|
| `WORK` |
| `STUDY` |
| `HOBBY` |
| `OTHER` |

### Prioridades disponíveis

| Valor |
|-------|
| `LOW` |
| `MEDIUM` |
| `HIGH` |

---

## 📦 Estrutura do projeto

```
src/main/kotlin/com/lucas/lptasks/
├── controller/       # Camada de entrada (REST)
├── service/          # Regras de negócio
├── repository/       # Acesso ao banco de dados
├── model/            # Entidades JPA
├── dto/              # Objetos de transferência de dados
├── security/         # Filtros JWT e configuração do Spring Security
├── exception/        # Exceções customizadas e handler global
├── enum/             # Enums de categoria e prioridade
└── utils/            # Utilitários (validação, ApiResponse)
```

---

## 🗄️ Variáveis de ambiente / Configuração

As configurações ficam em `src/main/resources/application.yml`. Os valores padrão são:

| Propriedade | Padrão |
|-------------|--------|
| `server.port` | `8080` |
| `datasource.url` | `jdbc:postgresql://127.0.0.1:5432/LpTasks` |
| `datasource.username` | `lukas` |
| `datasource.password` | `mistery123` |
| `cache.type` | `redis` |
| `token.secret` | `encrypted123` |

> ⚠️ Em produção, substitua o `token.secret` por um valor seguro e externalize as credenciais via variáveis de ambiente.

---

## 🐳 Docker Compose

O arquivo `docker-compose.yml` sobe dois serviços:

- **PostgreSQL 13** na porta `5432` — inicializa automaticamente com o script `initialize.sql`
- **Redis 7.4** na porta `6379`

```bash
# Subir
docker-compose up -d

# Derrubar
docker-compose down
```

---

## 🗺️ Roadmap

### 🔄 Em andamento

- [ ] **Testes unitários e de integração** — cobertura dos services, controllers e filtros de segurança com JUnit 5 e MockK

---

### 🚀 Próximas implementações

#### Funcionalidades
- [ ] **Paginação** nos endpoints de listagem (`GET /app/tasks`) para suportar grandes volumes de dados
- [ ] **Status da tarefa** — adicionar campo `status` com valores como `TODO`, `IN_PROGRESS`, `DONE`
- [ ] **Data de vencimento** (`dueDate`) por tarefa com suporte a filtro por prazo
- [ ] **Endpoint de busca combinada** — filtrar tarefas por múltiplos critérios ao mesmo tempo (categoria + prioridade + status)
- [ ] **Soft delete** — substituir deleção física por lógica com campo `deletedAt`
- [ ] **Atribuição de tarefas** — vincular tarefas a usuários específicos
- [ ] **Listagem de tarefas por usuário** — cada usuário vê apenas as próprias tarefas

#### Segurança e Auth
- [ ] **Refresh token** — emitir e rotacionar tokens de atualização para evitar re-login frequente
- [ ] **Revogação de token** — invalidar tokens ativos via blacklist no Redis
- [ ] **Rate limiting** — limitar requisições por IP/usuário para prevenir abusos

#### Cache
- [ ] **Aplicar `@Cacheable`** nos endpoints de leitura frequente (`getAllTasks`, `getTasksByCategory`)
- [ ] **Invalidação de cache** automática ao criar, atualizar ou deletar tarefas com `@CacheEvict`

#### Infraestrutura
- [ ] **Migrations com Flyway** — substituir `ddl-auto: update` por versionamento de schema
- [ ] **Dockerizar a aplicação** — adicionar a API ao `docker-compose.yml` para rodar tudo com um único comando
- [ ] **Profiles de ambiente** — separar configurações de `dev`, `test` e `prod`
- [ ] **Logging estruturado** — adicionar logs com correlação de request ID para rastreabilidade

#### Documentação
- [ ] **Swagger / OpenAPI** — documentação interativa dos endpoints via SpringDoc

---

## 📐 Padrão de resposta

Todos os endpoints retornam o mesmo envelope:

```json
{
  "body": { },
  "message": "Mensagem descritiva",
  "statusCode": 200
}
```
