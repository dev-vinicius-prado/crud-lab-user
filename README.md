# Sistema de Gerenciamento de Usuários - Laboratório Clínico 🏥

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)
![Angular](https://img.shields.io/badge/Angular-17-red)
![License](https://img.shields.io/badge/License-MIT-blue)

Sistema completo para gerenciamento de usuários de um laboratório clínico, desenvolvido com Java 17 (Spring Boot) no backend e Angular 17 no frontend.

## 🚀 Funcionalidades

- ✅ Cadastro completo de usuários
- 📋 Listagem com paginação
- 🔍 Busca por ID, e-mail ou CPF
- ✏️ Atualização de dados
- 🗑️ Exclusão de registros
- 💡 Ativação/desativação de usuários
- 🔒 Autenticação e autorização
- 📱 Interface responsiva

## 🛠️ Tecnologias Utilizadas

### Backend
- Java 17
- Spring Boot 3.5.6
- Spring Security
- Spring Data JPA
- PostgreSQL (Produção)
- H2 Database (Desenvolvimento)
- MapStruct
- Lombok
- Swagger/OpenAPI

### Frontend (Em desenvolvimento)
- Angular 17
- Angular Material
- TypeScript
- SCSS
- Jasmine/Karma

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- Node.js 18 ou superior (para o frontend)
- PostgreSQL (para produção)

## 🔧 Instalação e Execução

### Backend

1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/crud-lab-user.git
cd crud-lab-user
```

2. Compile o projeto
```bash
mvn clean install
```

3. Execute a aplicação
```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

### Documentação da API

Após iniciar a aplicação, acesse:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## 📚 Endpoints da API

### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/users` | Criar novo usuário |
| GET | `/api/users` | Listar usuários (paginado) |
| GET | `/api/users/{id}` | Buscar usuário por ID |
| PUT | `/api/users/{id}` | Atualizar usuário |
| DELETE | `/api/users/{id}` | Excluir usuário |
| PATCH | `/api/users/{id}/toggle-status` | Alternar status do usuário |

## 🔒 Segurança

- Senhas criptografadas com BCrypt
- Validação de dados de entrada
- Proteção contra CSRF
- Configuração de CORS
- Autenticação via token JWT (em desenvolvimento)

## 🧪 Testes

### Backend
```bash
# Executar testes unitários
mvn test

# Relatório de cobertura
mvn verify
```

## 📦 Deploy

O projeto está configurado para deploy em serviços gratuitos:
- Backend: Railway/Render
- Frontend: Vercel/Netlify
- Banco de dados: Railway PostgreSQL

## 🤝 Contribuindo

1. Faça o fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## ✨ Próximos Passos

- [ ] Implementação do frontend em Angular 17
- [ ] Autenticação JWT
- [ ] Perfis de usuário (admin/user)
- [ ] Dashboard com métricas
- [ ] Integração com serviços externos
- [ ] Containerização com Docker

## 👥 Autores

* **Vinicius Prado** - *Trabalho inicial* - [dev-vinicius-prado](https://github.com/dev-vinicius-prado/)

## 📬 Contato

Link do projeto: [https://github.com/seu-usuario/crud-lab-user](https://github.com/seu-usuario/crud-lab-user)