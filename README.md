# 🏥 Voll.med - Sistema de Gestão de Clínica Médica

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-blue)
![Maven](https://img.shields.io/badge/Maven-3.8+-red)

> Projeto acadêmico desenvolvido durante o curso da Alura, focado na construção de uma API REST completa para gerenciamento de consultas médicas.

## 📋 Sobre o Projeto

O **Voll.med** é uma aplicação backend robusta que simula um sistema de gestão para clínicas médicas. O sistema permite o cadastro de médicos e pacientes, além do agendamento de consultas com validações de regras de negócio complexas.

### ✨ Funcionalidades Principais

- 👨‍⚕️ **Gestão de Médicos**
  - Cadastro, listagem, atualização e exclusão lógica
  - Especialidades: Ortopedia, Cardiologia, Ginecologia e Dermatologia
  - Controle de status (ativo/inativo)

- 👥 **Gestão de Pacientes**
  - CRUD completo de pacientes
  - Armazenamento de dados pessoais e endereço
  - Paginação de resultados

- 📅 **Agendamento de Consultas**
  - Agendamento inteligente com escolha automática de médico
  - Sistema robusto de validações de negócio
  - Verificação de disponibilidade e horários

- 🔐 **Segurança**
  - Autenticação via JWT (JSON Web Token)
  - Endpoints protegidos com Spring Security
  - Hash de senhas com BCrypt

- 📚 **Documentação**
  - API documentada com Swagger/OpenAPI 3.0
  - Interface interativa para testes

## 🏗️ Arquitetura e Tecnologias

### Stack Tecnológico

- **Java 21** - Linguagem de programação
- **Spring Boot 3.4.1** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **MySQL** - Banco de dados relacional
- **Flyway** - Versionamento de banco de dados
- **Lombok** - Redução de código boilerplate
- **Bean Validation** - Validação de dados
- **Auth0 JWT** - Geração e validação de tokens
- **SpringDoc OpenAPI** - Documentação da API
- **Maven** - Gerenciamento de dependências

### Padrões e Boas Práticas

- ✅ Arquitetura em camadas (Controller, Service, Repository, Domain)
- ✅ DTOs (Data Transfer Objects) para comunicação
- ✅ Validações com Bean Validation
- ✅ Strategy Pattern para validadores de agendamento
- ✅ Repository Pattern para acesso a dados
- ✅ Paginação de resultados
- ✅ Soft Delete (exclusão lógica)
- ✅ Migrations com Flyway
- ✅ Tratamento de exceções

## 🎯 Regras de Negócio - Agendamento de Consultas

O sistema implementa diversas validações para garantir a integridade dos agendamentos:

1. **Horário de Funcionamento**: Consultas apenas de segunda a sábado, das 7h às 19h
2. **Antecedência Mínima**: Agendamento com pelo menos 30 minutos de antecedência
3. **Médico Ativo**: Apenas médicos ativos podem ter consultas agendadas
4. **Paciente Ativo**: Apenas pacientes ativos podem agendar consultas
5. **Disponibilidade do Médico**: Médico não pode ter outra consulta no mesmo horário
6. **Limite do Paciente**: Paciente não pode ter mais de uma consulta no mesmo dia

## 🚀 Como Executar

### Pré-requisitos

- Java 21 ou superior
- Maven 3.8+
- MySQL 8.0+
- Git

### Configuração do Ambiente

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd vollmed
```

2. **Configure o banco de dados**

Crie um banco de dados MySQL:
```sql
CREATE DATABASE vollmed;
```

3. **Configure as variáveis de ambiente**

Crie as seguintes variáveis de ambiente:
```bash
export DB_VOLLMED_URL=jdbc:mysql://localhost:3306/vollmed
export DB_VOLLMED_USER=seu_usuario
export DB_VOLLMED_PASSWORD=sua_senha
export JWT_SECRET=sua_chave_secreta_jwt
```

### Executando a Aplicação

```bash
cd api
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Acessando a Documentação da API

Após iniciar a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 📁 Estrutura do Projeto

```
api/
├── src/
│   ├── main/
│   │   ├── java/med/voll/api/
│   │   │   ├── config/          # Configurações da aplicação
│   │   │   ├── controller/      # Controllers REST
│   │   │   ├── domain/          # Entidades e DTOs
│   │   │   │   ├── consulta/    # Domínio de consultas
│   │   │   │   ├── endereco/    # Domínio de endereços
│   │   │   │   ├── medico/      # Domínio de médicos
│   │   │   │   ├── paciente/    # Domínio de pacientes
│   │   │   │   └── usuario/     # Domínio de usuários
│   │   │   ├── repository/      # Repositórios JPA
│   │   │   └── service/         # Serviços de negócio
│   │   └── resources/
│   │       ├── db/migration/    # Scripts Flyway
│   │       └── application.properties
│   └── test/                    # Testes automatizados
└── pom.xml                      # Configuração Maven
```

## 🔒 Autenticação

O sistema utiliza autenticação JWT. Para acessar os endpoints protegidos:

1. **Faça login** no endpoint `/login` com suas credenciais
2. **Receba o token** JWT na resposta
3. **Inclua o token** no header `Authorization: Bearer {seu-token}` nas requisições

## 📊 Endpoints Principais

### Autenticação
- `POST /login` - Autentica usuário e retorna token JWT

### Médicos
- `POST /medicos` - Cadastra novo médico
- `GET /medicos` - Lista médicos (paginado)
- `GET /medicos/{id}` - Detalhes de um médico
- `PUT /medicos` - Atualiza dados do médico
- `DELETE /medicos/{id}` - Exclusão lógica de médico

### Pacientes
- `POST /pacientes` - Cadastra novo paciente
- `GET /pacientes` - Lista pacientes (paginado)
- `GET /pacientes/{id}` - Detalhes de um paciente
- `PUT /pacientes` - Atualiza dados do paciente
- `DELETE /pacientes/{id}` - Exclusão lógica de paciente

### Consultas
- `POST /consultas` - Agenda nova consulta

## 🧪 Testes

Execute os testes com:
```bash
./mvnw test
```

O projeto inclui:
- Testes unitários de repositórios
- Testes de controllers
- Testes de validações de negócio

## 📚 Aprendizados do Projeto

Este projeto acadêmico permitiu o aprendizado e aplicação de:

- Desenvolvimento de APIs REST com Spring Boot
- Implementação de autenticação e autorização com JWT
- Modelagem de banco de dados relacional
- Uso de migrations com Flyway
- Validações complexas de regras de negócio
- Padrões de projeto (Strategy, Repository, DTO)
- Documentação de APIs com OpenAPI/Swagger
- Boas práticas de desenvolvimento Java
- Testes automatizados

## 👨‍💻 Sobre o Desenvolvimento

Projeto desenvolvido como parte do curso de Spring Boot da Alura, focado em:
- Construção de APIs REST profissionais
- Aplicação de boas práticas de desenvolvimento
- Implementação de segurança em aplicações web
- Trabalho com banco de dados relacionais
- Versionamento e controle de schema de banco de dados

## 📝 Licença

Este é um projeto acadêmico desenvolvido para fins educacionais.

---

⭐ Projeto desenvolvido durante o curso de Spring Boot da [Alura](https://www.alura.com.br/)
