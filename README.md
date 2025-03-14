# Desafio de Programação - Itaú Unibanco

- Este foi o software que desenvolvi para resolver o  desafio proposto no [link](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior).

## Tecnologias utilizadas
* Spring Boot - framework Java
* Maven - gerenciamento de dependências
* Swagger - documentação da API
* Docker - para criar a imagem da aplicação
* Spring Actuator - para monitorar saúde da aplicação
* Junit - para testes unitários e funcionais

## Como rodar este projeto
**1. Clone o repositório**

**2. Compile o projeto**
````
mvn clean install
````
**3. Execute o projeto**
````
mvn spring-boot:run
````
**4. Acesse a aplicação**

API = http://localhost:9090
Documentação = http://localhost:9090/swagger-ui.html

**Opcional: Rodar com docker**
````
docker build -t app .
docker run --rm -p 9090:9090 app
````

**Para rodar os testes:**
````
mvn test
````


