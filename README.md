# A3Back - Sistema de Controle de Estoque

Sistema legado utilizado na A3 de Gestão e Qualidade de Software da Universidade do Sul de Santa Catarina (UNISUL).

O projeto consiste em uma API REST para gerenciamento de estoque, desenvolvida em Java com Spring Boot e banco de dados MySQL. Durante a disciplina foram aplicadas práticas de garantia da qualidade de software com foco em testes automatizados, integração contínua, análise estática de código e controle de versão.

## Objetivos da A3

Durante o desenvolvimento desta atividade foram implementadas as seguintes práticas de qualidade:

* Testes automatizados utilizando JUnit;
* Cobertura de código utilizando JaCoCo;
* Integração contínua utilizando GitHub Actions;
* Análise estática de código utilizando SonarCloud;
* Controle de versão utilizando Git e GitHub;
* Monitoramento da qualidade por meio de métricas e Quality Gate.

## 👥 Integrantes

| Nome                            | GitHub          | RA          |
| ------------------------------- | --------------- | ----------- |
| Lucas Rengel                    | @lucasrengel    | 10724110009 |
| Antonio Victor Iaroseski Segala | @antoniovsegala | 1072417746  |
| Isadora Luchtenberg Fernandes   | @isaluch        | 1072417018  |

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3.2.0
* Maven
* MySQL 8.0
* Spring Data JPA
* JUnit 5
* JaCoCo
* GitHub Actions
* SonarCloud
* GitHub

---

## 📋 Pré-requisitos

* Java JDK 17+
* Maven
* MySQL Server 8.0+

---

## 📊 Qualidade do Código

O projeto utiliza SonarCloud para análise estática de código, monitoramento de qualidade e validação do Quality Gate.

### SonarCloud

https://sonarcloud.io/project/overview?id=lucasrengel_A3Back

---

## 🔰 Guia para Iniciantes (Não tenho Maven)

### Opção A: Instalação via Winget

Abra o PowerShell ou CMD como Administrador e execute:

```powershell
winget install Maven.Maven
```

Após a instalação:

```bash
mvn -v
```

---

### Opção B: Instalação Manual

1. Baixe o Maven:
   https://maven.apache.org/download.cgi

2. Extraia o arquivo ZIP em uma pasta de sua preferência.

3. Configure o PATH do sistema adicionando a pasta:

```text
C:\Program Files\Maven\apache-maven-x.x.x\bin
```

4. Verifique a instalação:

```bash
mvn -v
```

---

## ⚙️ Configuração do Banco de Dados

Crie um banco de dados MySQL chamado:

```sql
CREATE DATABASE estoque_db;
```

Configure as credenciais no arquivo:

```text
src/main/java/unisul/a3/config/DatabaseConnection.java
```

Exemplo:

```java
private static final String USER = "root";
private static final String PASSWORD = "12345";
```

---

## 🔧 Como Executar

### Opção 1: Via Script

Execute o arquivo:

```text
run.bat
```

---

### Opção 2: Via Maven

Entre na pasta do projeto:

```bash
cd A3Back
```

Execute:

```bash
mvn spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

---

## 🧪 Qualidade de Software

Durante esta atividade foram implementadas práticas de garantia da qualidade:

### Testes Automatizados

* Framework: JUnit 5
* Cobertura de código: JaCoCo
* Cobertura alcançada: 80%

### Integração Contínua

Pipeline configurada utilizando GitHub Actions para:

* Compilar o projeto;
* Executar os testes automatizados;
* Validar alterações enviadas ao repositório.

### Análise Estática

Análise realizada utilizando SonarCloud para monitoramento de:

* Segurança;
* Confiabilidade;
* Manutenibilidade;
* Duplicação de código;
* Cobertura de testes.

Quality Gate aprovado.

---

## 🔌 Endpoints da API

### Categorias

| Método | Endpoint             |
| ------ | -------------------- |
| GET    | /api/categorias      |
| GET    | /api/categorias/{id} |
| POST   | /api/categorias      |
| PUT    | /api/categorias/{id} |
| DELETE | /api/categorias/{id} |

---

### Produtos

| Método | Endpoint           |
| ------ | ------------------ |
| GET    | /api/produtos      |
| GET    | /api/produtos/{id} |
| POST   | /api/produtos      |
| PUT    | /api/produtos/{id} |
| DELETE | /api/produtos/{id} |

---

### Movimentações

| Método | Endpoint                |
| ------ | ----------------------- |
| GET    | /api/movimentacoes      |
| GET    | /api/movimentacoes/{id} |
| POST   | /api/movimentacoes      |
| PUT    | /api/movimentacoes/{id} |
| DELETE | /api/movimentacoes/{id} |

---

### Relatórios

| Método | Endpoint                              |
| ------ | ------------------------------------- |
| GET    | /api/relatorios/lista-precos          |
| GET    | /api/relatorios/balanco               |
| GET    | /api/relatorios/abaixo-minimo         |
| GET    | /api/relatorios/por-categoria         |
| GET    | /api/relatorios/maiores-movimentacoes |

---

## 📝 Convenção de Commits

Para manter a rastreabilidade das alterações, o projeto utiliza uma convenção padronizada de commits.

| Prefixo  | Descrição                                     |
| -------- | --------------------------------------------- |
| feat     | Nova funcionalidade                           |
| fix      | Correção de defeito                           |
| test     | Adição ou atualização de testes               |
| docs     | Alterações de documentação                    |
| refactor | Refatoração sem alteração de comportamento    |
| ci       | Alterações relacionadas à integração contínua |
| build    | Configurações de build e dependências         |

### Exemplos

```bash
feat: adiciona cadastro de produto
fix: corrige cálculo de movimentação
test: adiciona testes para categoria
docs: atualiza README
refactor: melhora organização do código
ci: adiciona pipeline github actions
build: configura jacoco
```

---

## 📊 Métricas do Projeto

* Cobertura de testes: 80%
* GitHub Actions configurado
* SonarCloud integrado
* Quality Gate aprovado
* Testes automatizados executados a cada push

---

## 🔗 Repositórios

Backend:
https://github.com/lucasrengel/A3Back

Frontend:
https://github.com/lucasrengel/A3Front

---

## 📚 Disciplina

A3 – Gestão e Qualidade de Software

Universidade do Sul de Santa Catarina – UNISUL

Palhoça – SC
2026
