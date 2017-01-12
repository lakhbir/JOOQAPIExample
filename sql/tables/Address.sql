DROP TABLE Address

CREATE TABLE Address
(
   address_id int NOT NULL AUTO_INCREMENT,
   customer_id int NOT NULL,
   address_type varchar(16) NOT NULL,
   address1 varchar(255) NOT NULL,
   address2 varchar(255),
   city varchar(32),
   state char(2) NOT NULL,
   country varchar(32) NOT NULL,
   postal_code varchar(16),
   creation_date timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
   PRIMARY KEY (address_id),
   FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
   FOREIGN KEY (address_type) REFERENCES Address_Type(address_type)
)
