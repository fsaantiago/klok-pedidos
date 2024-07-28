# Desafio Klok - Refatoração PedidoService

### Este projeto é uma aplicação de gerenciamento de pedidos desenvolvida em Spring Boot. A aplicação permite criar, listar e obter pedidos, com autenticação baseada em JWT. O código foi refatorado para melhorar a legibilidade, manutenção e eficiência, garantindo que a lógica de negócios seja clara e bem organizada.


### Instalação

**Clone o repositório:**
```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```
**Navegue até o diretório do projeto:**
```bash
cd seu-repositorio
```
**Instale as dependências:**
```bash
mvn install
```
**Execute a aplicação:**
```bash
mvn spring-boot:run
```

Plataforma usada para criar o pacote inicial: [Spring Initializr](https://start.spring.io/)

### Endpoints
- `POST` /authenticate: Autenticação do usuário e geração do token JWT.
- `POST` /api/pedidos: Criação de um novo pedido.
- `GET` /api/pedidos/{id}: Obtenção de um pedido pelo ID.
- `GET` /api/pedidos: Listagem de todos os pedidos.

### PedidoService
A classe PedidoService foi refatorada para melhor organização e eficiência. Agora ela utiliza serviços auxiliares para cálculo de desconto, verificação de estoque e envio de notificações.

#### Descrição das Classes Refatoradas
**PedidoService:**

  - Função: Gerencia a criação, listagem e obtenção de pedidos.
  - Refatorações:
    - Separação das responsabilidades de cálculo de desconto, verificação de estoque e notificação em serviços dedicados.
    - Melhoria na legibilidade e manutenção do código.
    - Utiliza o PedidoRepository para persistência dos dados.
    - Notifica o cliente sobre o status do pedido.
  - DescontoService:
    - Interface: Define o contrato para o cálculo de descontos.
    - Implementação: DescontoServiceImpl aplica um desconto de 10% para clientes VIP.
  - EstoqueService:
    - Função: Verifica se todos os itens do pedido estão em estoque.
    - Comportamento: Lança uma exceção se algum item não estiver em estoque.
    - Validação: Garante que a lista de itens não seja nula e que todos os itens tenham estoque suficiente.
  - NotificacaoService:
    - Interface: Define o contrato para o envio de notificações aos clientes.
    - Implementação: NotificacaoServiceImpl envia e-mails para os clientes com mensagens específicas sobre o status do pedido.
  - UserDetailsServiceImpl:
    - Função: Implementa o serviço de detalhes do usuário para autenticação.
    - Comportamento: Carrega os detalhes do usuário com base no nome de usuário fornecido e cria um UserDetails para autenticação com Spring Security.
    - Segurança: Utiliza BCryptPasswordEncoder para codificação de senhas.

**Testes**

  - AuthenticationControllerTest
    - Testa a autenticação e a proteção de endpoints.
      - Testes Realizados:
        - testAuthenticate: Verifica se a autenticação está funcionando corretamente com informações mockadas.
        - testProtectedEndpoint: Verifica se o endpoint protegido está funcionando corretamente com informações mockadas e um token JWT gerado.
  - PedidoServiceTest
    - Testa a criação de pedidos com diferentes condições de estoque.
    - Testes Realizados:
      - testCriarPedidoComItensEmEstoque: Verifica se o pedido é criado corretamente quando todos os itens estão em estoque.
      - testCriarPedidoComItensForaDeEstoque: Verifica se o pedido é criado corretamente quando alguns itens estão fora de estoque.
      - testCriarPedidoComItensMisturados: Verifica se o pedido é criado corretamente quando há uma mistura de itens em estoque e fora de estoque.
  - TestApplicationTests
    - Testa a aplicação como um todo, incluindo a verificação de estoque.
    - Testes Realizados:
      - contextLoads: Verifica se a aplicação é carregada corretamente.
      - testCliente: Verifica se a exceção IllegalArgumentException é lançada quando o email do cliente é nulo.
      - testPedido: Verifica se a exceção NullPointerException é lançada quando o cliente é nulo.
      - testEstoqueServiceComItensEmEstoque: Verifica se a verificação de estoque passa quando todos os itens estão em estoque.
      - testEstoqueServiceComItensForaDeEstoque: Verifica se a exceção IllegalArgumentException é lançada quando itens estão fora de estoque.
      - testEstoqueServiceComItensMisturados: Verifica se a exceção IllegalArgumentException é lançada quando há itens misturados (em estoque e fora de estoque).

**Configurações de Segurança**

  - JwtAuthenticationEntryPoint
    - Classe responsável por retornar uma resposta de erro quando a autenticação falha.

  - JwtAuthenticationFilter
    - Filtro que intercepta as requisições para verificar a presença e validade do token JWT.

  - JwtTokenUtil
    - Classe utilitária para geração e validação de tokens JWT.

  - WebSecurityConfig
    - Classe de configuração de segurança da aplicação, definindo regras de autorização e configurando a autenticação JWT.
   
## Tecnologias Utilizadas:

**Linguagem utilizada:** Java (v17)

**Ferramentas de Desenvolvimento:** IntelliJ IDEA

**Spring Boot:** Framework (v3.3.2) para desenvolvimento de aplicações Java.

## Dependências inseridas:
- Spring Boot
  - `spring-boot-starter-web`: Para criar aplicações web, incluindo RESTful.
  - `spring-boot-starter-data-jpa`: Para acessar dados com JPA usando Spring Data.
  - `spring-boot-starter-security`: Para configurar segurança na aplicação.
  - `spring-boot-starter-test`: Para testes unitários e de integração com JUnit, Hamcrest e Mockito.
  - `spring-boot-starter-validation`: Para validação de dados.
- JWT(JSON Web Token)
  - Dependência: `jjwt`
  - Versão: 0.9.1
- Banco de Dados
  - `H2 Database` (para desenvolvimento e testes)
  - Versão: 2.1.214
- Maven
  - Versão: 3.8.1 (ou superior)
  - Plugins:
    - `spring-boot-maven-plugin`: Para empacotar aplicações Spring Boot.
- JUnit 5
  - Dependência: `junit-jupiter-engine`
  - Versão: 5.8.1
- Mockito
  - Dependência: `mockito-core`
  - Versão: 4.0.0
- Lombok
  - Dependência: `lombok`
  - Versão: 1.18.22
