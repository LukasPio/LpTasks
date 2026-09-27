# LpTasks

API REST de gerenciamento de tarefas em **Kotlin e Spring Boot 3.3**. Foi criada para praticar autenticação com JWT, persistência relacional, filtros e organização de uma aplicação backend em camadas.

## Funcionalidades

- Cadastro e login de usuários com senha armazenada usando BCrypt e emissão de JWT.
- Criação, consulta, atualização e exclusão de tarefas.
- Busca por ID ou título, filtro por categoria e ordenação por prioridade.
- Respostas em um envelope com `body`, `message` e `statusCode`.
- PostgreSQL via Spring Data JPA; Docker Compose para o ambiente local.

**Sobre Redis:** o serviço e as dependências de cache estão configurados no projeto, mas os endpoints ainda **não usam `@Cacheable`/`@CacheEvict`**. Cache com invalidação continua como evolução planejada.

## API

`POST /app/auth/register` e `POST /app/auth/login` são públicos. As rotas de tarefas exigem `Authorization: Bearer <token>`.

| Método | Caminho | Ação |
| --- | --- | --- |
| `GET` | `/app/tasks` | Lista tarefas |
| `GET` | `/app/tasks/id?id=<uuid>` | Busca por ID |
| `GET` | `/app/tasks/title?taskTitle=<texto>` | Busca por título |
| `GET` | `/app/tasks/category?category=<categoria>` | Filtra por categoria |
| `GET` | `/app/tasks/sortByPriority?sortOrder=asc` | Ordena por prioridade |
| `POST` | `/app/tasks` | Cria uma lista de tarefas |
| `PUT` | `/app/tasks/{id}` | Atualiza uma tarefa |
| `DELETE` | `/app/tasks/{id}` | Exclui uma tarefa |

Exemplo de conta de teste e login:

```bash
curl -s -X POST http://localhost:8080/app/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"email":"teste@example.com","password":"senha-local","isAdmin":false}'

curl -s -X POST http://localhost:8080/app/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"email":"teste@example.com","password":"senha-local"}'
```

O login devolve o JWT em `body.token`. Para criar tarefas, envie **uma lista JSON** a `POST /app/tasks` com o token:

```json
[
  {
    "title": "Estudar Kotlin",
    "description": "Revisar APIs REST",
    "category": "STUDY",
    "priority": "HIGH"
  }
]
```

Categorias previstas: `WORK`, `STUDY`, `HOBBY` e `OTHER`. Prioridades: `LOW`, `MEDIUM` e `HIGH`.

## Executar localmente

Requisitos: **JDK 21**, Docker com Compose e Maven (ou Maven Wrapper). Com as portas 5432 e 6379 livres:

```bash
docker compose up -d
./mvnw spring-boot:run
```

O Compose inicializa PostgreSQL e Redis; os valores de desenvolvimento estão em `src/main/resources/application.yml`. Troque credenciais e segredo de assinatura antes de usar em outro ambiente. O banco usa `ddl-auto: update`; ainda não há migrações Flyway.

## Estrutura e limites

`controller/` expõe as rotas; `service/` reúne regras de negócio; `repository/` acessa dados; `security/` contém filtro JWT e configuração de acesso; `dto/` e `exception/` definem os contratos e erros.

É um projeto de estudo, não uma API pronta para produção. O cadastro público recebe o campo `isAdmin`, e as tarefas não estão associadas ao usuário autenticado. Testes abrangentes, paginação, cache efetivo e endurecimento da autorização são melhorias futuras.
