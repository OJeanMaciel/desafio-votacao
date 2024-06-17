Sistema de Votação

Bem-vindo ao repositório do backend do Sistema de Votação! Este projeto foi desenvolvido para gerenciar sessões de votação, permitindo que os associados de uma cooperativa votem em diversas pautas.

# Tecnologias Utilizadas

- Java 17: Linguagem de programação utilizada.
- Spring Boot: Framework utilizado para a construção da aplicação.
- Docker: Utilizado para containerização da aplicação.
- Flyway: Gerenciamento de versionamento do banco de dados.
- Logs: Implementação de logs para monitoramento e debug.
- Testes Unitários: Garantia de qualidade e funcionamento correto do código.

# Pré-requisitos

Para executar este projeto, você precisará ter instalado:

- Java 17
- Docker
- Postgresql 15
- Maven

# Configuração e Instalação

- Usar Docker
- Pasta: /desafio-votacao/voting-system/docker
- Subir o container na porta 5432
- Para executar a aplicação 
- ./mvnw clean install
- ./mvnw spring-boot:run

# Estrutura do Projeto

Foi utilizada a arquitetura em camadas (MVC - Model-View-Controller) para uma melhor organização do código, proporcionando uma divisão clara entre as camadas de apresentação, lógica de negócio e acesso a dados.

Estrutura de diretórios para este projeto é a seguinte:

- src/main/java/: Contém todos os pacotes e classes Java.
    - com.example.voting_system/
        - config/: Configurações específicas da aplicação.
        - controller/: Contém as classes controladoras.
        - dto/: Contém classes Data Transfer Objects.
        - entity/: Contém as classes de modelo (entidades).
        - exception/: Tratamento de exceções específicas.
        - repository/: Contém as interfaces de repositório.
        - service/: Contém as classes de serviço.
        - util/: Utilitários e funções auxiliares.
    - VotingSystemApplication: Classe principal da aplicação.
- src/main/resources/: Contém os recursos estáticos e arquivos de configuração.
    - application.properties: Arquivo de configuração principal.
    - db/migration/: Contém os scripts de migração do Flyway.
- src/test/java/: Contém as classes de testes unitários.
- docker/: Arquivos de configuração do Docker para setup do ambiente.

# Boas Práticas de Programação (Manutenibilidade, Legibilidade) 

- Uso de DTOs para transferência de dados e entities para representação do modelo de dados.

# Documentação da API

A documentação da API pode ser acessada após iniciar a aplicação em http://localhost:8080/api/swagger-ui/index.html. Esta documentação detalha todos os endpoints disponíveis e seus respectivos formatos de requisição e resposta.

# Logs

A aplicação está configurada para gerar logs detalhados, que podem ser encontrados no diretório /logs. Certifique-se de revisar esses logs para monitorar o comportamento da aplicação e para fins de debug.

# Testes

Para executar os testes unitários, utilize o seguinte comando:

- ./mvnw test

# Versionamento da API

- Versionamento via URL
    - Estratégia: Incluir a versão da API na URL.
    - Exemplo: http:/com.example.voting_system/v1/associado
    - Benefícios: Fácil de implementar e claro para os consumidores da API. Permite coexistência de múltiplas versões da API.

# 💬 Estamos aqui para ajudar!

Estamos felizes por ter você por aqui!
