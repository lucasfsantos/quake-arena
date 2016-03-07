1 - Importar o projeto utilizando uma IDE com suporte ao Maven.

2 - Estrutura do Projeto:

Para criação do projeto foi utilizado a plataforma Spring Boot.

O projeto contém os seguintes pacotes:

src/main/java:
	- Entity (Entidades com dados de parse do arquivo game.log)
	- Parser (Camada para realizar a leitura e parser do arquivo de game.log)
	- Service (Camada que obtém os dados do parser e transforma em informações dos jogos)
	- Controller (Camada que realiza a exposição de um recurso da API, dos dados retornados pela camada Service)
	- VO (Entidades utilziadas para exposição no modelo RESTFul com formato JSON )

src/test/java:
	- parser (Testes de leitura do arquivo com Spring JUnit)
	- service (Testes utilizando Mockito)
	- controller (Testes utilizando Mockito)
	
3 - Para executar o projeto, será necessário executar a classe ApplicationBoot.java

4 - Para configurar a porta de execução do projeto, será necessário alterar no arquivo 
"src/main/resources/application.properties" a propriedade server.port que está com default 8080

5 - Recurso para consulta dos jogos:
GET - http://localhost:8080/scores