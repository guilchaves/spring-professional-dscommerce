#   Projeto Final - Sistema de Comércio Eletrônico DSCommerce

## Java Spring Professional


### Sobre

Este repositório contém o projeto final do treinamento **Java Spring Professional** ministrado pelo
professor [Nelio Alves](https://www.udemy.com/user/nelio-alves/), oferecido pela plataforma [DevSuperior](https://devsuperior.com.br/). Este projeto visa criar uma API RESTful de comércio
eletrônico usando tecnologias Java e Spring.

### Visão Geral
O sistema desenvolvido neste projeto é uma aplicação completa de comércio eletrônico,
abrangendo cadastros de usuários, produtos e categorias. Cada usuário, seja cliente ou administrador,
tem sua própria área de interação com o sistema. Os administradores têm acesso à área administrativa para gerenciar
usuários, produtos e categorias.


### Funcionalidades Principais

- Cadastro e autenticação de usuários com diferentes papéis (cliente, administrador).
- Catálogo de produtos com capacidade de filtragem por nome.
- Adição, remoção e alteração de itens no carrinho de compras.
- Registro de pedidos com status dinâmicos (aguardando pagamento, pago, enviado, entregue, cancelado).
- Área administrativa para gerenciamento de usuários, produtos e categorias.

### Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- PostgreSQL Driver
- Bean Validation
- H2 Database
- Maven
- Apache Tomcat

### Como executar este projeto
##### Pré-requisitos:
- **Java 17**: [JDK 17](https://www.oracle.com/java/technologies/downloads/) ou superior.
- **IDEs**: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) ou [Spring Tools](https://spring.io/tools).

##### Passos:

1. **Clone o repositório**</br>
Abra o terminal e navegue até o diretório onde deseja armazenar o projeto. Execute o seguinte comando para clonar o repositório:

```bash
git clone https://github.com/guilchaves/spring-professional-dscommerce.git
```
2. **Abra o projeto no IntelliJ IDEA ou STS:**</br>
- _IntelliJ IDEA_: Abra o IntelliJ IDEA e selecione "Open" no menu principal. Navegue até o diretório do projeto e selecione o arquivo pom.xml.
- _Spring Tools Suite_: Abra o STS e selecione "Import...​" > "Existing Maven Projects". Navegue até o diretório do projeto e selecione o arquivo pom.xml.

3. **Baixe as dependências do Maven:**</br>
Aguarde até que o IntelliJ ou STS baixe automaticamente as dependências do Maven. Isso pode levar algum tempo, dependendo da conexão com a internet.</br></br>
4. **Execute o projeto:**</br>
No projeto, navegue até o arquivo `src/main/java/br/com/guilchaves/dscommerce/DscommerceApplication.java`. Este arquivo contém 
a classe principal da aplicação Spring Boot.</br>
- _IntelliJ IDEA_: Clique com o botão direito do mouse no arquivo DscommerceApplication.java e escolha "Run DscommerceApplication".
- _Spring Tools Suite_: Clique com o botão direito do mouse no projeto no navegador de projetos e escolha "Run As" > "Spring Boot App".

5. **Verifique a Execução:**</br>
Após a execução bem-sucedida, abra um navegador da web e acesse `http://localhost:8080` (ou a porta configurada, se diferente) para verificar se a aplicação está em execução.</br>
Acesse `http://localhost:8080/h2-console` para utilizar o console do H2 database.</br>


#### Para testar a API no postman:
Para importar e exportar dados no postman, consulte a documentação oficial [aqui](https://learning.postman.com/docs/getting-started/importing-and-exporting/importing-data/).
</br>
Download da coleção e variáveis de ambiente:
- [Collection](https://drive.google.com/file/d/1TjBh5Nu5znqEB-umnf304MMHe8USVLdj/view?usp=sharing)
- [Environment](https://drive.google.com/file/d/1_L1r4OSXcIJVxGzq-vGHYYP8CpoD5fhD/view?usp=sharing)
 
