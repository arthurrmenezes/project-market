(pt-br)

Projeto CRUD feito em Java, com conexão a bando de dados MySQL, feito a fim de estudos.

A idéia do projeto foi fazer um sistema de cadastro/edição de produtos de um mercado (ou qualquer loja).
O programa permite que o usuário cadastre, liste, edite e exclua produtos.

Fiz o projeto para aprimorar meus conhecimentos em Orientação a Objetos e treinar criar projetos com CRUD e conexão a Banco de Dados.
Aceito sugestões e críticas!
O projeto ainda pode ser atualizado com novas features!

Para rodar o projeto você deve ter instalado em sua máquina:

- Java (JDK)
- IDE de sua preferência (usei IntelliJ para desenvolver)
- MySQL
- mysql-connector (eu utilizei a versão 8.1.0)

Para rodar o código com conexão ao Banco de Dados você deve inserir seu login do MySQL (Nome e senha) na classe Dao, onde está sinalizado (user_name e user_password).

Além disso, no MySQL você deve criar um database 'market' e uma tabela chamada 'products' com os seguintes dados:

create table products (
product_name varchar(50),
product_price float,
product_id INT AUTO_INCREMENT PRIMARY KEY
);

Após isso só rodar a classe Main para executar o programa.



***
(en)

CRUD project developed in Java, with MySQL database connection, created for educational purposes.

The idea behind the project was to create a product registration/editing system for a market (or any store). The program allows users with access to register, list, edit, and delete products.

I developed the project to enhance my knowledge in Object-Oriented Programming and practice creating projects with CRUD operations and database connection. I welcome suggestions and criticisms! The project can still be updated with new features!

To run the project, you must have installed on your machine:

Java (JDK)
Preferred IDE (I used IntelliJ for development)
MySQL
mysql-connector (I used version 8.1.0)
To run the code with a database connection, you need to enter your MySQL login (Username and Password) in the Dao class, where it is indicated (user_name and user_password). After that, just run the Main class to execute the program.

*To run the code with a Database connection, you need to insert your MySQL login (Username and Password) in the Dao class, where it is indicated (user_name and user_password).

Additionally, in MySQL, you should create a database 'market' and a table named 'products' with the following data:

create table products (
product_name varchar(50),
product_price float,
product_id INT AUTO_INCREMENT PRIMARY KEY
);

After that, just run the Main class to execute the program.




