# Cadastro de Usuário com Thymeleaf + Spring Boot 3

Este projeto foi desenvolvido como parte de um **desafio técnico** para o **Núcleo de Tecnologia da Informação (NTI) da Prefeitura de Serra Talhada**. O objetivo é criar um sistema de cadastro de usuário utilizando **Spring Boot 3**, **Thymeleaf** e **validação de formulários**.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Web
- Spring Validation
- Thymeleaf
- Figma
- Bootstrap (para estilização)
- API ViaCEP (consulta de endereço por CEP)

## 📌 Funcionalidades

1. **Cadastro de Dados Pessoais**

   - CPF (com validação)
   - Nome completo
   - RG
   - Data de nascimento
   - Nome da mãe

2. **Cadastro de Endereço**

   - CEP (busca automática via API)
   - Logradouro
   - Número
   - Complemento (opcional)
   - Bairro
   - Cidade
   - Estado

3. **Confirmação dos Dados** antes da finalização do cadastro.

4. **Mensagem de Sucesso** ao concluir o cadastro.

## 📂 Estrutura do Projeto

```
thymeleaf-springboot/
│-- src/main/java/com/gabrielboliveira/cadastro
│   │-- controller/
│   │   ├── CadastroController.java
│   │-- model/
│   │   ├── DadosPessoaisDTO.java
│   │   ├── EnderecoDTO.java
│   │-- service/
│   │   ├── CepService.java
│   │-- validation/
│   │   ├── CpfValidator.java
│-- src/main/resources/templates/
│   │-- cadastro-dados.html
│   │-- cadastro-endereco.html
│   │-- confirmacao.html
│   │-- sucesso.html
│-- src/main/resources/static/css/
│   │-- styles.css
│-- application.properties
│-- pom.xml
```

## 🔧 Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/Gabriel-Oliveiraa/DesafioTech_Spring-Thymeleaf.git
   ```
2. Acesse a pasta do projeto:
   ```bash
   cd DesafioTech_Spring-Thymeleaf
   ```
3. Compile e execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```
4. Acesse o sistema no navegador:\
   `http://localhost:8080/cadastro/pessoais`

## 📡 API de Consulta de CEP

O sistema utiliza a API **ViaCEP** para buscar endereços automaticamente com base no CEP informado.

## 📜 Licença

Este projeto é de uso livre para fins educacionais e pode ser adaptado conforme necessidade.

---

Desenvolvido por Gabriel Oliveira como parte do desafio técnico para o NTI da Prefeitura de Serra Talhada. 🚀

