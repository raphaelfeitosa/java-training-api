# java-training-api

Esse repositório disponibiliza uma versão zero de uma API de cadastro de usuários (Users) na pasta [SRC](https://github.com/GuillaumeFalourd/java-training-api/tree/main/src) *a ser melhorada*.

## DESAFIOS

Os desafios podem ser encontrado no arquivo [DESAFIOS.md](https://github.com/GuillaumeFalourd/java-training-api/tree/main/DESAFIOS.md) e correspondem (atualmente) ao nível de conhecimento esperado por desenvolvedores **JUNIORS**.

## Requisitos

- Maven
- Spring
- Java 8 (11 melhor)
- Hibernate
- JPA

Baixar as dependências: na raiz do projeto: executar o comando `mvn clean install`.

Para rodar a aplicação localmente, executar o metodo `main` da classe [TrainingApiApplication.java](https://github.com/GuillaumeFalourd/java-training-api/tree/main/src/main/java/br/com/training/TrainingApiApplication.java).

## Endpoint disponíveis (v0)

### Criar usuário

**POST:** `http://localhost:8080/users` com *body*:

```json
{
    "name":"Name",
    "cpf":"cpf",
    "email":"email",
    "birthDate":"1900-01-01"
}
```

### Obter usuário com CPF

**GET:** `http://localhost:8080/users/{cpf}` vai retornar:

```json
{
    "id": 1,
    "name":"Name",
    "cpf":"cpf",
    "email":"email",
    "birthDate":"1900-01-01"
}
```
## Executando todos os testes Endpoint (v1)

No terminal, navegue até a pasta raiz do projeto e execute

```shell
./mvnw test
```
no Windows

```shell
mvnw.cmd test
```

## Endpoint disponíveis (v1)
Recursos disponíveis para acesso via api: `http://localhost:8080/api/v1`

- ### Rescurso [/users], Methods: GET, POST, PUT, DELETE
  ```
  GET: /users: para listar todos os usuários  
  GET: /users/cpf/{cpf}: para consultar um usuário por cpf 
  GET: /users/email/{email}: para consultar um usuário por email
  GET: /users/{id}: para consultar um usuário por id
  POST: /users: para cadastrar uma usuário
  PUT: /users/{cpf}: para alterar um usuário
  DELETE: /users/{cpf}: para deletar uma usuario 
  ```

- ### Autorização para acessar todos os recursos:
  ```
  Authorization: Basic Auth [Username: root, Password: root]
  ```

- ### Responses
  | Código | Descrição |
  |---|---|
  | `200` | Requisição executada com sucesso (Success).|
  | `201` | Requisição cadastrada com sucesso (Success).|
  | `204` | Requisição processada com sucesso (Success).|
  | `400` | Erros de validação ou cadastro existente (Bad Request).|
  | `401` | Dados de acesso inválidos (Unauthorized).|
  | `404` | Registro pesquisado não encontrado (Not Found).|

### Criar usuário

**POST:** `http://localhost:8080/api/v1/users` com *body*:

- Request (application/json)
    ```json
    {
      "name":"John Doe",
      "cpf":"66921484050",
      "email":"johndoe@gmail.com",
      "birthDate":"1989-09-29"
    }
    ```

- Response 201 (Created)
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "email": "johndoe@gmail.com",
      "cpf": "66921484050",
      "birthDate": "1989-09-29"
    }
    ```
- Response 400 (Bad Request) - Cadastro existente
    ```json
    {
      "timestamp": "2021-08-25T18:55:40Z",
      "code": 400,
      "status": "BAD_REQUEST",
      "message": "CPF already registered.",
      "path": "/api/v1/users",
      "errors": []
    }
    ```

- Response 400 (Bad Request) - Erros na validação
    ```json
    {
      "timestamp": "2021-08-25T18:56:29Z",
      "code": 400,
      "status": "BAD_REQUEST",
      "message": "Error validation",
      "path": "/api/v1/users",
      "errors": [
        {
          "fieldName": "name",
          "message": "O campo name é obrigatório"
        },
        {
          "fieldName": "birthDate",
          "message": "O campo birthDate é obrigatório. formato: yyyy-MM-dd"
        },
        {
          "fieldName": "email",
          "message": "O campo email é obrigatório"
        },
        {
          "fieldName": "cpf",
          "message": "CPF inválido"
        },
        {
          "fieldName": "cpf",
          "message": "O campo cpf é obrigatório"
        }
      ]
    }
    ```
- Response 401 (Unauthorized)

### Atualizar usuário por CPF

**PUT:** `http://localhost:8080/api/v1/users/{cpf}` com *body*:

- Request (application/json)
    ```json
    {
      "name":"John Doe de Souza",
      "cpf":"66921484050",
      "email":"johndoe@gmail.com",
      "birthDate":"1989-09-29"
    }
    ```

- Response 200 (Created)
    ```json
    {
      "id": 1,
      "name": "John Doe de Souza",
      "email": "johndoe@gmail.com",
      "cpf": "66921484050",
      "birthDate": "1989-09-29"
    }
    ```
  
- Response 400 (Bad Request) - Cadastro existente com cpf
    ```json
    {
      "timestamp": "2021-08-25T18:55:40Z",
      "code": 400,
      "status": "BAD_REQUEST",
      "message": "CPF already registered.",
      "path": "/api/v1/users",
      "errors": []
    }
    ```
  
- Response 400 (Bad Request) - Cadastro existente com email
    ```json
    {
      "timestamp": "2021-08-25T18:55:40Z",
      "code": 400,
      "status": "BAD_REQUEST",
      "message": "Email already registered.",
      "path": "/api/v1/users",
      "errors": []
    }
    ```
  
- Response 400 (Bad Request) - Erros na validação
    ```json
    {
      "timestamp": "2021-08-25T18:56:29Z",
      "code": 400,
      "status": "BAD_REQUEST",
      "message": "Error validation",
      "path": "/api/v1/users",
      "errors": [
        {
          "fieldName": "name",
          "message": "O campo name é obrigatório"
        },
        {
          "fieldName": "birthDate",
          "message": "O campo birthDate é obrigatório. formato: yyyy-MM-dd"
        },
        {
          "fieldName": "email",
          "message": "O campo email é obrigatório"
        },
        {
          "fieldName": "cpf",
          "message": "CPF inválido"
        },
        {
          "fieldName": "cpf",
          "message": "O campo cpf é obrigatório"
        }
      ]
    }
    ```
- Response 401 (Unauthorized)

### Obter usuário com CPF

**GET:** `http://localhost:8080/api/v1/users/cpf/{cpf}` vai retornar:

- Response 200 (application/json)
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "email": "johndoe@gmail.com",
      "cpf": "66921484050",
      "birthDate": "1989-09-29"
    }
    ```
- Response 401 (Unauthorized)

### Obter usuário com Email

**GET:** `http://localhost:8080/api/v1/users/email/{email}` vai retornar:

- Response 200 (application/json)
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "email": "johndoe@gmail.com",
      "cpf": "66921484050",
      "birthDate": "1989-09-29"
    }
    ```
- Response 401 (Unauthorized)

### Obter usuário com Id

**GET:** `http://localhost:8080/api/v1/users/{id}` vai retornar:

- Response 200 (application/json)
    ```json
    {
      "id": 1,
      "name": "John Doe",
      "email": "johndoe@gmail.com",
      "cpf": "66921484050",
      "birthDate": "1989-09-29"
    }
    ```
- Response 401 (Unauthorized)

### Obter lista de todos os usuários cadastrados

**GET:** `http://localhost:8080/api/v1/users` vai retornar:

- Response 200 (application/json)
    ```json
    [
      {
        "id": 1,
        "name": "John Doe",
        "email": "johndoe@gmail.com",
        "cpf": "66921484050",
        "birthDate": "1989-09-29"
      },
      {
        "id": 2,
        "name": "Maria Doe",
        "email": "mariadoe@gmail.com",
        "cpf": "248.808.330-53",
        "birthDate": "1985-02-22"
      }
    ]
    ```
- Response 401 (Unauthorized)

### Excluir usuario com cpf

**DELETE** `http://localhost:8080/api/v1/users/{cpf}` sem *body*:

- Response 204 (No Content) - usuário deletado com sucesso

- Response 404 (Not Found) - cadastro inexistente
    ```json
    {
      "timestamp": "2021-08-25T19:15:06Z",
      "code": 404,
      "status": "NOT_FOUND",
      "message": "User not found.",
      "path": "/api/v1/users/66921484050",
      "errors": []
    }
    ```
- Response 401 (Unauthorized)

## Swagger

`http://localhost:8080/api/swagger-ui.html`


