1. Criação do projeto no Spring initializr ==06-08-2025==
	1. Dependências:
		1. Spring Web
		2. Spring JPA
		3. Banco H2
2. Configuração do banco H2
	1. Pasta resources
		1. application-test.properties
3. Criação da entidade Client
	1. Marcando a Classe com a annotation `@Entity`
	2. Marcando o atributo id com a annotation `@Id`
4. Criação dos diretórios para organização do projeto
	1. controllers
		1. handlers
	2. services
		1. exceptions
	3. repositories
	4. dto
5. Criação da entidade ClientController
	1. Marcando a Classe com a annotation `RestController` e `RequestMapping`
6. Seeding da base de dados ==07-08-2025==
	1. Criação de um arquivo `import.sql` na pasta `resources`
7. Criação da entidade ClientDTO 
	1. Essa Classe é utilizada para transitação de dados entre as camadas de `Controle` e `Serviço`
8. Criação da interface `ClientRepository`
	1. Essa Interface faz a conexão com o banco de dados, permitindo a interação com a entidade Client
9. Criação da entidade `ClientService`
	1. A Classe é marcada com a annotation `Service`
	2. Criação do método para acessar o `ClientRepository`
10. `ClientController` 
	1. Fazendo a implementação do primeiro método de CRUD -> `findById`
	2. O método é marcado com a annotation `GetMapping` e possui um parâmetro `value= /{id}`, que serve para indicar que ao realizar um `GET` em `products` retornará o valor descrito em id
	3. O valor de id é passado em uma annotation `PathVariable` marcada dentro dos parâmetros do método `findById`
11. Busca paginada de recursos ==08-08-2025==
	1. Criação do método findAll nas camadas de `Controle` e `Serviço`
	2. O método é marcado com a annotation `GetMapping`
	3. O método retorna um objeto do tipo `Page` que contém elementos `ClientDTO`
	4. Ele recebe como parâmetro do `Controller` a interface `Pageable`, responsável por fazer a leitura dos `Query Params` (page, size, sort, etc.) presentes na URL
	5. Dentro da camada de `Serviço` acessamos `ClientRepository`, ele pega o objeto `Pageable` e monta um consulta SQL e a executa, e então, obtemos um objeto do tipo `Page` que contém elementos `Client` ("dado bruto" como resposta do banco de dados) e retornamos o valor para a camada de `Controle` utilizando o método `map` que recebe uma função como parâmetro, que irá fazer a conversão de `Client` para `ClientDTO`
12. Inserção de novo cliente
	1. Criação do método insert nas camadas de `Controle` e `Serviço` 
	2. Na Classe `Controle`, o método é marcado com a annotation `PostMapping`
	3. Através do método **POST** é enviado um corpo em formato **JSON** que contém todos os atributos necessários para a criação de um novo `Client` 
	4. Quando enviado para a camada de `Serviço`, como **DTO**, cria-se uma instancia de `Client`, e é passado para ela todos os atributos contidos no **DTO**, assim, é possível criar no `Repositório` um novo `Client`
13. `ResponseEntity` ==09-08-2025==
	1. Mudança nos métodos existentes na camada de `Controle`, todos agora possuem um retorno do tipo `ResponseEntity<T>`
	2. O intuito dessa mudança é fornecer uma resposta mais adequada de acordo com o tipo de solicitação feita pelo usuário da API, a Classe `ResponseEntity` fornece um controle maior para os desenvolvedores na resposta das requisições **HTTP**
14. Atualização de um cliente
	1. Criação do método update nas camadas de `Controle` e `Serviço`
	2. Na camada de `Controle`, o método é marcado com a annotation `PutMapping`, ela recebe um parâmetro do tipo value, assim como a annotation do `GetMapping` -> `/{id}`
	3. Na camada de `Serviço`, criei um método auxiliar chamado `copyDtoToEntity` que tem a função de transformar um objeto do tipo **DTO** em uma 'entidade final'
		1. O intuito do método auxiliar é evitar a repetição de código quando se fizer necessária a cópia de **DTO** -> **ENTITY**
15. Deletar um cliente ==10-08-2025==
	1. Criação do método delete nas camadas de `Controle` e `Serviço`
	2. O método é marcado com a annotation `DeleteMapping`
		1. Parâmetro semelhante a requisição **GET** 
16. Implementação das exceções personalizadas
	1. Criação da Classe `ResourceNotFoundException`
		1. A Classe é utilizada como exceção personalizada nos métodos **GET**, **PUT** e **DELETE**. Pois, todos os métodos utilizam o ID para a busca de determinado cliente
17. `ClientControllerAdvice`
	1. Talvez um dos recursos mais interessantes que utilizei até o momento, serve para centralizar o tratamento de exceções de uma aplicação Spring
	2. A Classe é marcada com a annotation `ControllerAdvice`
18. Adicionando validações
	1. Utilizando o Bean Validation, os campos `name` e `birthDate` recebem regras de validação
		1. `name` recebe **@NotBlank**
		2. `birthDate` recebe **@PastOrPresent**
19. Customização de respostas #revisar 
	1. Com as validações implementadas, é possível retornar ao usuário qual foi o erro na hora de inserir ou atualizar determinado recurso
	2. Criação da Classe `FieldMessageDTO`
	3. Criação da Classe `ValidationErrorDTO`
	4. As Classes criadas servem para criação da mensagem de erro, assim como foi descrito no tópico 19.1, ao invés de apenas informar o erro, é informado os detalhes do mesmo
20. Desafio finalizado
