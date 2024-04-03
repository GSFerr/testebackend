Projeto Pessoas e Endereços
Este é um projeto simples para gerenciar informações de pessoas e seus endereços.

Funcionalidades Principais
Cadastro de Pessoas: Permite cadastrar informações básicas sobre pessoas, como nome, data de nascimento e CPF.
Cadastro de Endereços: Permite cadastrar endereços associados a uma pessoa, incluindo informações como rua, número, cidade e estado.
Edição de Pessoas e Endereços: Permite editar as informações cadastradas de pessoas e endereços.
Listagem de Pessoas e Endereços: Permite visualizar todas as pessoas cadastradas e seus respectivos endereços.
Busca por CPF: Permite buscar uma pessoa pelo seu CPF.


Tecnologias Utilizadas
Java
Spring Boot
Spring Data JPA
Spring Web
H2 Database (em memória)


Como Usar
Clone este repositório para o seu ambiente local.
Importe o projeto na sua IDE de preferência.
Execute a aplicação.
Acesse os endpoints fornecidos pela aplicação para interagir com o sistema (por exemplo, via Postman).


Endpoints Principais
POST /pessoas/salvar: Cria uma nova pessoa.
PUT /pessoas/editar/{id}: Edita uma pessoa existente.
GET /pessoas/listar-todas: Lista todas as pessoas cadastradas.
GET /pessoas/listar-por-cpf/{cpf}: Busca uma pessoa pelo CPF.
POST /enderecos/salvar: Cria um novo endereço associado a uma pessoa.
PUT /enderecos/editar/{id}: Edita um endereço existente.
GET /enderecos/listar-todos: Lista todos os endereços cadastrados.
GET /enderecos/listar-por-pessoa/{idPessoa}: Lista os endereços associados a uma pessoa.