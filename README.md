# API de autenticação e autorização usando os tokens do JWT

Esse projeto tem como objetivo explorar um pouco sobre a autorização, autenticação, JSON Web Token e criptografia de senhas usando um algoritmo de hash.


## Paradigmas utilizados: Orientação a objeto


## Autorização e Autenticação

### Autorização
A autorização refere-se ao processo de conceder permissões específicas a usuários autenticados para acessar recursos ou executar determinadas ações em um sistema. É a fase subsequente à autenticação, onde o sistema determina quais recursos um usuário autenticado tem permissão para acessar. Isso é alcançado por meio de roles, permissões ou políticas que são atribuídas aos usuários ou grupos de usuários.

### Autenticação
A autenticação, por outro lado, é o processo de verificar a identidade de um usuário, garantindo que a pessoa ou entidade que acessa o sistema é quem diz ser. Geralmente, envolve a apresentação de credenciais, como nome de usuário e senha, e pode incluir métodos mais avançados, como autenticação de dois fatores ou uso de tokens.


## JSON Web Token ou JWT
JSON Web Token (JWT) é um padrão aberto (RFC 7519) que define uma forma compacta e autocontida de representar informações entre partes como um objeto JSON. Essas informações podem ser verificadas e confiáveis, pois são digitalmente assinadas. JWTs podem ser assinados usando um segredo (com o algoritmo HMAC) ou um par de chaves pública/privada usando RSA ou ECDSA.

Um JWT geralmente é composto por três partes: o cabeçalho (header), o payload e a assinatura. O cabeçalho tipicamente consiste no tipo de token (JWT) e no algoritmo de assinatura utilizado, enquanto o payload contém as informações declaradas no token. A assinatura é gerada usando o cabeçalho, o payload e uma chave secreta (ou privada) e, assim, pode ser verificada por qualquer pessoa que possua a chave secreta (ou pública).


## Criptografia de senhas
A criptografia de senhas é um componente crucial para garantir a segurança das informações armazenadas em sistemas computacionais. Em vez de armazenar senhas em texto simples, que são vulneráveis a acessos não autorizados, as senhas devem ser submetidas a algoritmos de hash ou criptografia.

Um algoritmo de hash transforma a senha em uma sequência de caracteres aparentemente aleatórios, conhecida como hash. Idealmente, algoritmos de hash são unidirecionais, o que significa que não é possível reverter o processo para obter a senha original a partir do hash. Isso aumenta a segurança, pois, mesmo que um hash seja comprometido, a senha real permanece protegida.

Além disso, é comum incluir uma técnica chamada "salting", onde uma string aleatória única é adicionada à senha antes do processo de hash. Isso ajuda a evitar ataques de dicionário e ataques de força bruta, pois torna cada senha única, mesmo que duas senhas originais sejam iguais.


## Tecnologias Utilizadas
| Tecnologia                       | Descrição                                      |
|----------------------------------|------------------------------------------------|
| Spring Boot                      | Framework para criação de aplicativos Java     |
| Spring Boot Starter Data JPA      | Suporte ao acesso a dados usando JPA           |
| Spring Boot Starter Web           | Suporte para desenvolvimento de aplicativos web|
| Flyway                           | Controle de versão para banco de dados         |
| Spring Boot DevTools              | Ferramentas de desenvolvimento automático      |
| Spring Boot Starter Validation    | Suporte à validação de dados                   |
| Spring Boot Starter Security      | Suporte para segurança e autenticação          |
| Auth0 Java JWT                   | Implementação em Java de JSON Web Tokens (JWT)|
| Spring Security Test              | Suporte para testes de segurança               |
| PostgreSQL                       | Banco de dados relacional PostgreSQL           |
| Spring Boot Starter Test          | Suporte a testes automatizados                 |


## Estrutura do projeto

- **`DDD`**: O projeto segue alguns conceitos do Domain-Driven Design para estruturar o projeto, tais como: Domain, Entidades, Repository, Services.
- **`src/main/java`**: Contém o código-fonte Java.
- **`controllers`**: Controladores REST.
- **`dto`**: DTOs para transferência de dados.
- **`entitidades`**: Entidades do domínio.
- **`repositorios`**: Repositórios JPA.
- **`src/main/resources`**: Contém os recursos, como arquivos de propriedades e scripts SQL.


## Instruções de Utilização da API

### Registro
Para registrar um novo usuário, faça uma requisição POST para o endpoint:

POST http://localhost:8080/api/auth/registro

Exemplo de corpo da requisição para criar um usuário com role "ADMIN":

{
    "login": "user1",
    "senha": "1234",
    "role": "ADMIN"
}

Exemplo de corpo da requisição para criar um usuário com role "USER":

{
    "login": "user2",
    "senha": "5678",
    "role": "USER"
}

#### Resposta do Registro
Vai criar um usuário com login, senha e uma role



### Login
Para realizar o login e obter um token, faça uma requisição POST para o endpoint:

POST http://localhost:8080/api/auth/login

Exemplo de corpo da requisição:

{
    "login": "user1",
    "senha": "1234"
}

#### Resposta do Login se bem sucedido
É um token criptografado que pode ser usado nas demais requisições. 



### Token
Exemplo de uso do Token:

Bearer <token-gerado-no-login>
Lembre-se de incluir o token gerado no cabeçalho (header) de suas requisições posteriores para autenticação.


## Configurações do Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_do_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha


## Outras Configurações para fazer uso do JWT
api.security.token.secret=${JWT_SECRET:my-secret-key}


## License
Este projeto está licenciado sob a Licença MIT.