# Desafio Backend PicPay - Simplificado

Este projeto é uma implementação do [Desafio Backend do PicPay](https://github.com/PicPay/picpay-desafio-backend), focando na criação de uma API RESTful simplificada de transferências utilizando **Spring Boot**.

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3** (Data JPA, Web, Security, Validation)
- **Spring Cloud OpenFeign**
- **SpringDoc OpenAPI (Swagger)** para documentação da API
- **PostgreSQL** via **Docker Compose**
- **H2 Database** (para testes)
- **Lombok**
- **JWT (Java-JWT)** para Autenticação/Autorização

## 📋 Funcionalidades
A API simula um sistema de pagamentos simplificado onde usuários e lojistas podem realizar transferências e depósitos.

- **Autenticação (`/auth`)**:
  - `POST /auth/register`: Cadastro de usuários comuns e lojistas com criptografia de senha (BCrypt).
  - `POST /auth/login`: Autenticação e geração de token JWT.
- **Usuários (`/users`)**:
  - `POST /users`: Criação de usuários.
  - `GET /users`: Listagem de todos os usuários cadastrados.
- **Transações (`/transactions`)**:
  - `POST /transactions`: Realiza a transferência de saldo entre usuários, validando a disponibilidade do saldo e consultando um serviço autorizador externo via Feign.
  - `POST /transactions/deposit`: Realiza o depósito de saldo na carteira de um usuário.

*A lógica inclui a modelagem das regras de negócios, tais como verificações de tipo de usuário, consulta autorizadora simulada e envio de notificação (simulado) para o recebedor.*

## 🛠️ Como Executar

### Pré-requisitos
Certifique-se de ter o **Docker**, **Docker Compose**, e a **JDK 17** instalados em sua máquina.

### Passos

1. **Clone o repositório** (se ainda não tiver feito):
```bash
git clone https://github.com/kaiowsz/picpay-simplificado.git
cd picpay-simplificado
```

2. **Inicie o Banco de Dados com Docker Compose**:
Na raiz do projeto, execute o comando:
```bash
docker-compose up -d
```
Isso iniciará um container PostgreSQL (`picpay-db`) na porta `5432` da sua máquina, com as credenciais padrões do projeto (usuário `postgres`, senha `123`, banco `picpay`).

3. **Execute a aplicação**:
Utilize o Maven Wrapper para rodar o projeto do Spring Boot:
```bash
./mvnw spring-boot:run
```
A API estará disponível em `http://localhost:8080`.

## 📚 Documentação e Testes de API (Swagger)
Uma vez que o projeto possua o `springdoc-openapi`, a documentação interativa da API poderá ser acessada em:
```text
http://localhost:8080/swagger-ui.html
```
*(Caso utilize a rota padrão `http://localhost:8080/swagger-ui/index.html` ou consulte o base path customizado se existir).*

Você pode testar todos os endpoints diretamente pelo navegador autenticando pelo header "Bearer" após o `/auth/login`.

## 🧪 Testes Automatizados
Para rodar a suíte de testes (que utiliza o banco H2 em memória), apenas execute:
```bash
./mvnw test
```

---
*Desenvolvido como solução prática para o Desafio Backend PicPay.*
