# A3Back - API de Controle de Estoque

Backend desenvolvido para a A3 de Sistemas Distribuidos e Mobile, responsável por fornecer a API REST para o sistema de gerenciamento de estoque. O projeto utiliza **Java** com **Spring Boot** e banco de dados **MySQL**.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Maven** - Gerenciamento de dependências e build
- **MySQL** - Banco de dados relacional
- **Spring Data JPA** - Persistência de dados

## 📋 Pré-requisitos

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/download.cgi) (Opcional se usar o wrapper ou IDE)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)

## ⚙️ Configuração do Banco de Dados

1. Crie um banco de dados no MySQL chamado `estoque_db` (ou outro nome de sua preferência).
2. Configure as credenciais no arquivo `\src\main\java\unisul\a3\config\DatabaseConnection.java`:

```properties
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
```

> **Nota:** Certifique-se de alterar `seu_usuario` e `sua_senha` para as credenciais do seu MySQL local.

## 🔧 Como Executar

### Opção 1: Via Script (Windows)

Execute o arquivo `run.bat` na raiz do projeto.

### Opção 2: Via Linha de Comando (Maven)

1. Acesse a pasta do projeto:
   ```bash
   cd A3Back
   ```

2. Execute o comando para rodar:
   ```bash
   mvn spring-boot:run
   ```

A API estará disponível em `http://localhost:8080`.

## 🔌 Endpoints da API

### Produtos
- `GET /api/produtos` - Lista todos os produtos
- `GET /api/produtos/{id}` - Busca um produto por ID
- `POST /api/produtos` - Cria um novo produto
- `PUT /api/produtos/{id}` - Atualiza um produto
- `DELETE /api/produtos/{id}` - Remove um produto

### Categorias
- `GET /api/categorias` - Lista todas as categorias
- `POST /api/categorias` - Cria uma nova categoria
- `PUT /api/categorias/{id}` - Atualiza uma categoria
- `DELETE /api/categorias/{id}` - Remove uma categoria

### Movimentações
- `GET /api/movimentacoes` - Lista o histórico de movimentações
- `POST /api/movimentacoes` - Registra uma entrada ou saída
- `DELETE /api/movimentacoes/{id}` - Remove uma movimentação (estorna o estoque)

### Relatórios
- `GET /api/relatorios/lista-precos` - Lista de preços simplificada
- `GET /api/relatorios/balanco` - Valor total do estoque
- `GET /api/relatorios/abaixo-minimo` - Produtos com estoque baixo
- `GET /api/relatorios/por-categoria` - Contagem de produtos por categoria
- `GET /api/relatorios/maiores-movimentacoes` - Maior entrada e maior saída registrada

