DROP TABLE Customer

CREATE TABLE Customer
(
   customer_id int NOT NULL AUTO_INCREMENT,
   email varchar(255) NOT NULL,
   first_name varchar(255) NOT NULL,
   last_name varchar(255) NOT NULL,
   birthdate date NOT NULL,
   creation_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
   PRIMARY KEY (customer_id),
   UNIQUE(email)
)